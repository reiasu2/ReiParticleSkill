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
package com.reiasu.reiparticlesapi.extend;

import net.minecraft.core.Vec3i;
import net.minecraft.world.phys.Vec3;

/**
 * Extension utility for {@link Vec3i} providing conversion to {@link Vec3}.
 * Originally a Kotlin extension function, ported as a static utility method.
 */
public final class Vec3iExtendsKt {

    private Vec3iExtendsKt() {
    }

    /**
     * Converts a {@link Vec3i} to a {@link Vec3} (double-precision).
     */
    public static Vec3 asVec3(Vec3i v) {
        return new Vec3(v.getX(), v.getY(), v.getZ());
    }
}
