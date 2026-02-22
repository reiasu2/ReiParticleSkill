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
package com.reiasu.reiparticlesapi.particles;

import net.minecraft.world.phys.Vec3;

/**
 * Interpolator for particle position lerping between ticks.
 */
public interface ParticleLerpInterpolator {
    /**
     * Compute an interpolated position between previous and current tick positions.
     *
     * @param prev    the position at the previous tick
     * @param current the position at the current tick
     * @param delta   the partial-tick fraction (0..1)
     * @return the interpolated position
     */
    Vec3 consume(Vec3 prev, Vec3 current, float delta);
}
