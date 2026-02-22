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
package com.reiasu.reiparticleskill.entities.renderer;

import com.reiasu.reiparticleskill.entities.BarrageItemEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.phys.Vec3;

public class BarrageItemRenderer extends EntityRenderer<BarrageItemEntity> {
    private final ItemRenderer itemRenderer;

    public BarrageItemRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public void render(
            BarrageItemEntity entity,
            float entityYaw,
            float partialTick,
            PoseStack poseStack,
            MultiBufferSource buffer,
            int packedLight
    ) {
        if (entity.getItem().isEmpty()) {
            return;
        }

        poseStack.pushPose();
        Vec3 motion = entity.getDeltaMovement();
        float yaw = entity.getYRot();
        float pitch = entity.getXRot();
        if (motion.lengthSqr() > 1.0E-6) {
            yaw = (float) Math.toDegrees(Math.atan2(-motion.x, motion.z));
            pitch = (float) Math.toDegrees(Math.atan2(-motion.y, Math.sqrt(motion.x * motion.x + motion.z * motion.z)));
        }

        poseStack.mulPose(Axis.YP.rotationDegrees(yaw));
        poseStack.mulPose(Axis.XP.rotationDegrees(pitch - 90.0F));
        poseStack.mulPose(Axis.ZP.rotationDegrees(entity.getRoll() + (entity.isBlock() ? 0.0F : 45.0F)));

        float scale = Math.max(0.01F, entity.getScale());
        poseStack.scale(scale, scale, scale);

        itemRenderer.renderStatic(
                entity.getItem(),
                ItemDisplayContext.GROUND,
                packedLight,
                OverlayTexture.NO_OVERLAY,
                poseStack,
                buffer,
                entity.level(),
                entity.getId()
        );
        poseStack.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(BarrageItemEntity entity) {
        return ItemRenderer.ENCHANTED_GLINT_ITEM;
    }
}
