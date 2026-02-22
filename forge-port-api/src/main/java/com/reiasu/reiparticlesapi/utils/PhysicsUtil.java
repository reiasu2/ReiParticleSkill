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
package com.reiasu.reiparticlesapi.utils;

import net.minecraft.core.Direction;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

/**
 * Utility class for physics-related calculations including collision detection
 * and movement resolution.
 */
public final class PhysicsUtil {
    public static final PhysicsUtil INSTANCE = new PhysicsUtil();

    private PhysicsUtil() {
    }

    // ---- Collision / Raycast ----

    /**
     * Perform a block raycast from {@code currentPos} in the direction of {@code velocity}.
     *
     * @param currentPos the starting position
     * @param velocity   the movement delta
     * @param world      the world to raycast in
     * @return the block hit result
     */
    public BlockHitResult collide(Vec3 currentPos, Vec3 velocity, Level world) {
        ClipContext context = new ClipContext(
                currentPos,
                currentPos.add(velocity),
                ClipContext.Block.COLLIDER,
                ClipContext.Fluid.NONE,
                null
        );
        return world.clip(context);
    }

    /**
     * Compute a position slightly before the collision point, offset along the surface normal.
     *
     * @param res the block hit result
     * @return a position slightly offset from the hit point along the block normal
     */
    public Vec3 fixBeforeCollidePosition(BlockHitResult res) {
        Direction dir = res.getDirection();
        Vec3 normal = new Vec3(dir.getStepX(), dir.getStepY(), dir.getStepZ());
        Vec3 location = res.getLocation();
        Vec3 normalizedOffset = normal.normalize();
        return location.add(normalizedOffset.scale(0.07));
    }

    // ---- Drag / Steering (existing methods) ----

    public static RelativeLocation applyDrag(RelativeLocation velocity, double dragFactor) {
        return velocity.scale(dragFactor);
    }

    public static RelativeLocation steer(RelativeLocation velocity, RelativeLocation desired, double blend) {
        double clampedBlend = Math3DUtil.INSTANCE.clamp(blend, 0.0, 1.0);
        velocity.setX(Math3DUtil.INSTANCE.lerp(velocity.getX(), desired.getX(), clampedBlend));
        velocity.setY(Math3DUtil.INSTANCE.lerp(velocity.getY(), desired.getY(), clampedBlend));
        velocity.setZ(Math3DUtil.INSTANCE.lerp(velocity.getZ(), desired.getZ(), clampedBlend));
        return velocity;
    }

    public static RelativeLocation capSpeed(RelativeLocation velocity, double maxSpeed) {
        double len = velocity.length();
        if (len > maxSpeed && len > 0.0) {
            velocity.scale(maxSpeed / len);
        }
        return velocity;
    }
}
