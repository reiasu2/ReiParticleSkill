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
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Suppresses the vanilla End Crystal beam rendering by clearing
 * beamTarget at multiple points: both during client tick AND right
 * before entity rendering. This eliminates flashing caused by
 * server data sync restoring beamTarget between tick and render.
 */
@Mod.EventBusSubscriber(modid = ReiParticleSkillForge.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public final class EndCrystalBeamSuppressor {

    private EndCrystalBeamSuppressor() {
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        clearBeamTargets();
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onRenderLevel(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_SKY) return;
        clearBeamTargets();
    }

    private static void clearBeamTargets() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null) return;
        for (Entity entity : mc.level.entitiesForRendering()) {
            if (entity instanceof EndCrystal crystal && crystal.getBeamTarget() != null) {
                crystal.setBeamTarget(null);
            }
        }
    }
}
