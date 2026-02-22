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
package com.reiasu.reiparticleskill.client;

import com.reiasu.reiparticleskill.ReiParticleSkillForge;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(modid = ReiParticleSkillForge.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class SkillKeyMappings {
    public static final KeyMapping FORMATION_1 = new KeyMapping(
            "key.reiparticleskill.formation1",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_R,
            "key.reiparticleskill.category"
    );

    public static final KeyMapping FORMATION_2 = new KeyMapping(
            "key.reiparticleskill.formation2",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_G,
            "key.reiparticleskill.category"
    );

    private SkillKeyMappings() {
    }

    @SubscribeEvent
    public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(FORMATION_1);
        event.register(FORMATION_2);
    }
}
