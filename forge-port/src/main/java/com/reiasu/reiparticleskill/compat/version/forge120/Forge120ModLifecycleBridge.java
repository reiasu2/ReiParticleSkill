/*
 * Copyright (C) 2025 Reiasu
 *
 * This file is part of ReiParticleSkill.
 *
 * ReiParticleSkill is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, version 3 of the License.
 *
 * ReiParticleSkill is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with ReiParticleSkill. If not, see <https://www.gnu.org/licenses/>.
 */
// SPDX-License-Identifier: LGPL-3.0-only
package com.reiasu.reiparticleskill.compat.version.forge120;

import com.reiasu.reiparticleskill.compat.version.ModLifecycleVersionBridge;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
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
    public void registerCommandRegistration(Consumer<CommandDispatcher<CommandSourceStack>> callback) {
        MinecraftForge.EVENT_BUS.addListener((RegisterCommandsEvent event) -> callback.accept(event.getDispatcher()));
    }

    @Override
    public void registerServerEndTick(Consumer<MinecraftServer> callback) {
        MinecraftForge.EVENT_BUS.addListener((TickEvent.ServerTickEvent event) -> {
            if (event.phase == TickEvent.Phase.END) {
                callback.accept(event.getServer());
            }
        });
    }
}
