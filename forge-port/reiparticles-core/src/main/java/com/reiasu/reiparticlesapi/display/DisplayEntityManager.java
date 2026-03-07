// SPDX-License-Identifier: LGPL-3.0-only
// Copyright (C) 2025 Reiasu
package com.reiasu.reiparticlesapi.display;

import com.mojang.logging.LogUtils;
import com.reiasu.reiparticlesapi.network.ReiParticlesNetwork;
import com.reiasu.reiparticlesapi.network.packet.PacketDisplayEntityS2C;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public final class DisplayEntityManager {
    public static final DisplayEntityManager INSTANCE = new DisplayEntityManager();
    private static final Logger LOGGER = LogUtils.getLogger();

    private final List<DisplayEntity> displays = new ArrayList<>();
    private final Map<UUID, DisplayEntity> serverView = new ConcurrentHashMap<>();
    private final Map<UUID, DisplayEntity> clientView = new ConcurrentHashMap<>();
    private final Map<String, Function<FriendlyByteBuf, DisplayEntity>> registeredTypes = new ConcurrentHashMap<>();
    private volatile boolean builtinTypesRegistered;

    private DisplayEntityManager() {
    }

    public void registerBuiltinTypes() {
        if (builtinTypesRegistered) {
            return;
        }
        builtinTypesRegistered = true;
        registerType(DebugDisplayEntity.TYPE_ID, DebugDisplayEntity::decode);
    }

    public Map<UUID, DisplayEntity> getServerView() {
        return serverView;
    }

    public Map<UUID, DisplayEntity> getClientView() {
        return clientView;
    }

    public Map<String, Function<FriendlyByteBuf, DisplayEntity>> getRegisteredTypes() {
        return registeredTypes;
    }

    public void registerType(String id, Function<FriendlyByteBuf, DisplayEntity> decoder) {
        if (id == null || id.isBlank() || decoder == null) {
            return;
        }
        registeredTypes.put(id, decoder);
    }

    public void spawn(Object display) {
        spawn(display, null);
    }

    public void spawn(Object display, ServerLevel level) {
        if (display instanceof DisplayEntity entity) {
            if (level != null) {
                entity.bindLevel(level);
            }
            synchronized (displays) {
                displays.add(entity);
            }
            serverView.put(entity.getControlUUID(), entity);
            sync(entity, PacketDisplayEntityS2C.Method.CREATE);
            entity.clearDirty();
        }
    }

    public void addClient(DisplayEntity entity) {
        clientView.put(entity.getControlUUID(), entity);
    }

    public void tickAll() {
        synchronized (displays) {
            Iterator<DisplayEntity> iterator = displays.iterator();
            while (iterator.hasNext()) {
                DisplayEntity display = iterator.next();
                try {
                    display.tick();
                } catch (Exception e) {
                    LOGGER.warn("Display entity {} ({}) failed during server tick; removing display",
                            display.getControlUUID(), display.getClass().getName(), e);
                    display.cancel();
                }
                if (display.getCanceled()) {
                    iterator.remove();
                    serverView.remove(display.getControlUUID());
                    sync(display, PacketDisplayEntityS2C.Method.REMOVE);
                    continue;
                }
                if (display.consumeDirty()) {
                    sync(display, PacketDisplayEntityS2C.Method.TOGGLE);
                }
            }
        }
    }

    public void tickClient() {
        Iterator<Map.Entry<UUID, DisplayEntity>> iterator = clientView.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<UUID, DisplayEntity> entry = iterator.next();
            DisplayEntity display = entry.getValue();
            try {
                display.tick();
            } catch (Exception e) {
                LOGGER.warn("Display entity {} ({}) failed during client tick; removing display",
                        display.getControlUUID(), display.getClass().getName(), e);
                display.cancel();
            }
            if (display.getCanceled()) {
                iterator.remove();
            }
        }
    }

    public int activeCount() {
        synchronized (displays) {
            return displays.size();
        }
    }

    public void clearClient() {
        for (DisplayEntity display : clientView.values()) {
            display.cancel();
        }
        clientView.clear();
    }

    public void clear() {
        synchronized (displays) {
            for (DisplayEntity display : displays) {
                sync(display, PacketDisplayEntityS2C.Method.REMOVE);
                display.cancel();
            }
            displays.clear();
        }
        serverView.clear();
        for (DisplayEntity display : clientView.values()) {
            display.cancel();
        }
        clientView.clear();
    }

    public List<DisplayEntity> getDisplays() {
        synchronized (displays) {
            return Collections.unmodifiableList(new ArrayList<>(displays));
        }
    }

    private void sync(DisplayEntity entity, PacketDisplayEntityS2C.Method method) {
        if (entity == null || entity.typeId() == null || entity.typeId().isBlank()) {
            return;
        }
        ServerLevel level = entity.level() instanceof ServerLevel serverLevel ? serverLevel : null;
        if (level == null) {
            return;
        }
        PacketDisplayEntityS2C packet = switch (method) {
            case CREATE -> PacketDisplayEntityS2C.ofCreate(entity);
            case TOGGLE -> PacketDisplayEntityS2C.ofToggle(entity);
            case REMOVE -> PacketDisplayEntityS2C.ofRemove(entity);
        };
        for (ServerPlayer player : level.players()) {
            ReiParticlesNetwork.sendTo(player, packet);
        }
    }
}
