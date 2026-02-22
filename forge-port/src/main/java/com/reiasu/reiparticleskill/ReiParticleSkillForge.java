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
package com.reiasu.reiparticleskill;

import com.reiasu.reiparticlesapi.ReiParticlesAPI;
import com.reiasu.reiparticleskill.command.ReiParticleSkillDebugCommand;
import com.reiasu.reiparticleskill.command.SkillActionCommand;
import com.reiasu.reiparticleskill.command.port.APITestCommandPort;
import com.reiasu.reiparticleskill.command.port.DisplayCommandPort;
import com.reiasu.reiparticleskill.command.port.RailgunCommandPort;
import com.reiasu.reiparticleskill.compat.reiparticles.ReiparticlesFacade;
import com.reiasu.reiparticleskill.compat.reiparticles.ReiparticlesFacadeProvider;
import com.reiasu.reiparticleskill.compat.version.ModLifecycleVersionBridge;
import com.reiasu.reiparticleskill.compat.version.VersionBridgeRegistry;
import com.reiasu.reiparticleskill.enchantments.SkillEnchantments;
import com.reiasu.reiparticleskill.entities.SkillEntityTypes;
import com.reiasu.reiparticleskill.end.respawn.EndRespawnStateBridge;
import com.reiasu.reiparticleskill.end.respawn.EndRespawnWatcher;
import com.reiasu.reiparticleskill.listener.KeyListener;
import com.reiasu.reiparticleskill.listener.ServerListener;
import com.reiasu.reiparticleskill.register.RuntimePortAutoRegistrar;
import com.reiasu.reiparticleskill.sounds.SkillSoundEvents;
import com.reiasu.reiparticleskill.config.SkillClientConfig;
import com.mojang.logging.LogUtils;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

@Mod(ReiParticleSkillForge.MOD_ID)
public final class ReiParticleSkillForge {
    public static final String MOD_ID = "reiparticleskill";
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final ModLifecycleVersionBridge LIFECYCLE = VersionBridgeRegistry.lifecycle();
    private final ReiparticlesFacade reiparticles = ReiparticlesFacadeProvider.get();
    private final EndRespawnStateBridge endRespawnBridge = new EndRespawnStateBridge();

    public ReiParticleSkillForge() {
        var modBus = FMLJavaModLoadingContext.get().getModEventBus();
        SkillEntityTypes.register(modBus);
        SkillEnchantments.register(modBus);
        SkillSoundEvents.register(modBus);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, SkillClientConfig.SPEC);

        LIFECYCLE.registerClientSetup(this::onClientSetup);
        LIFECYCLE.registerCommandRegistration(this::onRegisterCommands);
        LIFECYCLE.registerServerEndTick(server -> {
            EndRespawnWatcher.tickServer(server, endRespawnBridge, LOGGER);
            ServerListener.onServerPostTick(server);
        });

        reiparticles.bootstrap(LOGGER);
        registerApiListeners();
        registerRuntimePorts();
        reiparticles.registerParticleStyles(LOGGER);
        reiparticles.registerTestHooks(LOGGER);

        LOGGER.info("ReiParticleSkill Forge runtime initialized");
    }

    private void onClientSetup() {
        reiparticles.registerKeyBindings(LOGGER);
        LOGGER.info("ReiParticleSkill client setup completed");
    }

    private void onRegisterCommands(com.mojang.brigadier.CommandDispatcher<net.minecraft.commands.CommandSourceStack> dispatcher) {
        ReiParticleSkillDebugCommand.register(dispatcher, endRespawnBridge, LOGGER);
        SkillActionCommand.register(dispatcher);
        DisplayCommandPort.register(dispatcher);
        RailgunCommandPort.register(dispatcher);
        APITestCommandPort.register(dispatcher);
        LOGGER.info("Registered reiparticleskill debug commands");
    }

    private void registerApiListeners() {
        if (!reiparticles.isOperational()) {
            return;
        }
        try {
            ReiParticlesAPI.INSTANCE.registerEventListener(MOD_ID, new KeyListener());
            LOGGER.info("Registered ReiParticleSkill API listeners");
        } catch (Throwable t) {
            LOGGER.warn("Failed to register ReiParticleSkill API listeners", t);
        }
    }

    private void registerRuntimePorts() {
        try {
            RuntimePortAutoRegistrar.registerAll(LOGGER,
                    "com.reiasu.reiparticleskill.particles",
                    "com.reiasu.reiparticleskill.end.respawn.runtime.emitter.client");
        } catch (Throwable t) {
            LOGGER.warn("Failed to auto-register ReiParticleSkill runtime ports", t);
        }
    }
}
