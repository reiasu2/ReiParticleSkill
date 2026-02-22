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
package com.reiasu.reiparticlesapi.compat.version.forge120;

import com.reiasu.reiparticlesapi.compat.version.ModLifecycleVersionBridge;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.function.Consumer;

public final class Forge120ModLifecycleBridge implements ModLifecycleVersionBridge {
    @Override
    public void registerClientSetup(Runnable callback) {
        FMLJavaModLoadingContext.get().getModEventBus().addListener((FMLClientSetupEvent event) -> callback.run());
    }

    @Override
    public void registerClientStartTick(Runnable callback) {
        MinecraftForge.EVENT_BUS.addListener((TickEvent.ClientTickEvent event) -> {
            if (event.phase == TickEvent.Phase.START) {
                callback.run();
            }
        });
    }

    @Override
    public void registerClientEndTick(Runnable callback) {
        MinecraftForge.EVENT_BUS.addListener((TickEvent.ClientTickEvent event) -> {
            if (event.phase == TickEvent.Phase.END) {
                callback.run();
            }
        });
    }

    @Override
    public void registerServerEndTick(Consumer<MinecraftServer> callback) {
        MinecraftForge.EVENT_BUS.addListener((TickEvent.ServerTickEvent event) -> {
            if (event.phase == TickEvent.Phase.END && event.getServer() != null) {
                callback.accept(event.getServer());
            }
        });
    }

    @Override
    public void registerServerStartTick(Consumer<MinecraftServer> callback) {
        MinecraftForge.EVENT_BUS.addListener((TickEvent.ServerTickEvent event) -> {
            if (event.phase == TickEvent.Phase.START && event.getServer() != null) {
                callback.accept(event.getServer());
            }
        });
    }
}
