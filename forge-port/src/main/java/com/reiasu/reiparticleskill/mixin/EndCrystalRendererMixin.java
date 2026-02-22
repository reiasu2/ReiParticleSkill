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
package com.reiasu.reiparticleskill.mixin;

import com.reiasu.reiparticleskill.config.SkillClientConfig;
import net.minecraft.client.renderer.entity.EndCrystalRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Suppresses the vanilla End Crystal beam so the mod's custom
 * particle effects are not obscured by the bright white beam.
 */
@Mixin(EndCrystalRenderer.class)
public abstract class EndCrystalRendererMixin {

    @Redirect(
            method = "render(Lnet/minecraft/world/entity/boss/enderdragon/EndCrystal;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/boss/enderdragon/EndCrystal;getBeamTarget()Lnet/minecraft/core/BlockPos;")
    )
    private BlockPos reiparticleskill$hideBeam(EndCrystal crystal) {
        if (!SkillClientConfig.INSTANCE.isSuppressCrystalBeam()) {
            return crystal.getBeamTarget();
        }
        return null;
    }
}
