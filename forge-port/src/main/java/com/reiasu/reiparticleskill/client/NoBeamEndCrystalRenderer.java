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

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EndCrystalRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;

/**
 * Custom End Crystal renderer that suppresses the vanilla beam.
 * Temporarily clears beamTarget before delegating to the vanilla
 * renderer, then restores it. This is deterministic with no timing
 * races because it runs entirely on the Render thread.
 */
public class NoBeamEndCrystalRenderer extends EndCrystalRenderer {

    public NoBeamEndCrystalRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(EndCrystal crystal, float entityYaw, float partialTick,
                       PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        BlockPos original = crystal.getBeamTarget();
        if (original != null) {
            crystal.setBeamTarget(null);
        }
        super.render(crystal, entityYaw, partialTick, poseStack, bufferSource, packedLight);
        if (original != null) {
            crystal.setBeamTarget(original);
        }
    }
}
