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
package com.reiasu.reiparticlesapi.utils.helper.emitters;

import net.minecraft.world.phys.Vec3;

/**
 * Utility for applying linear resistance (velocity scaling) to emitter motion.
 */
public final class LinearResistanceHelper {
    public static final LinearResistanceHelper INSTANCE = new LinearResistanceHelper();

    private LinearResistanceHelper() {
    }

    /**
     * Scale the velocity vector by a percentage factor.
     *
     * @param enter   the current velocity
     * @param percent the scaling factor (e.g. 0.95 for 5% deceleration per tick)
     * @return the scaled velocity
     */
    public Vec3 setPercentageVelocity(Vec3 enter, double percent) {
        return enter.scale(percent);
    }
}
