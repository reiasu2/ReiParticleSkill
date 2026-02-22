/*
 * Copyright (C) 2025 Reiasu
 *
 * This file is part of ReiParticlesAPI.
 *
 * ReiParticlesAPI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, version 3 of the License.
 *
 * ReiParticlesAPI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with ReiParticlesAPI. If not, see <https://www.gnu.org/licenses/>.
 */
// SPDX-License-Identifier: LGPL-3.0-only
package com.reiasu.reiparticlesapi.particles.control.group;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Client-side manager for {@link ControllableParticleGroup} instances.
 * <p>
 * Tracks visible groups, ticks them, and provides factory registration
 * for creating groups from server-side packets.
 *
 * @deprecated Use ParticleGroupStyle instead.
 */
@Deprecated
public final class ClientParticleGroupManager {
    public static final ClientParticleGroupManager INSTANCE = new ClientParticleGroupManager();

    private final ConcurrentHashMap<UUID, ControllableParticleGroup> visibleControls = new ConcurrentHashMap<>();
    private final HashMap<Class<? extends ControllableParticleGroup>, ControllableParticleGroupProvider> registerBuilders = new HashMap<>();

    private ClientParticleGroupManager() {}

    public void register(Class<? extends ControllableParticleGroup> type, ControllableParticleGroupProvider provider) {
        registerBuilders.put(type, provider);
    }

    public ControllableParticleGroupProvider getBuilder(Class<? extends ControllableParticleGroup> type) {
        return registerBuilders.get(type);
    }

    public ControllableParticleGroup getControlGroup(UUID groupId) {
        return visibleControls.get(groupId);
    }

    public void addVisibleGroup(ControllableParticleGroup group) {
        visibleControls.put(group.getUuid(), group);
    }

    public void removeVisible(UUID id) {
        ControllableParticleGroup group = visibleControls.get(id);
        if (group != null) {
            group.remove();
        }
        visibleControls.remove(id);
    }

    public void clearAllVisible() {
        for (ControllableParticleGroup group : visibleControls.values()) {
            group.setCanceled(true);
        }
        visibleControls.clear();
    }

    /**
     * Called every client tick. Ticks all visible groups and clears if player is removed.
     */
    public void doClientTick() {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) {
            return;
        }
        if (player.isRemoved()) {
            visibleControls.clear();
            return;
        }
        for (ControllableParticleGroup group : visibleControls.values()) {
            group.tick();
        }
    }
}
