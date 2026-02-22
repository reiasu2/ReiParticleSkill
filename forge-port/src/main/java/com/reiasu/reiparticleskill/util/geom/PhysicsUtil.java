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
package com.reiasu.reiparticleskill.util.geom;

public final class PhysicsUtil {
    private PhysicsUtil() {
    }

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
