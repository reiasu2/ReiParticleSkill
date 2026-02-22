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
package com.reiasu.reiparticlesapi.network.particle.emitters;

/**
 * Physics constants used by particle emitter simulations.
 * Values are tuned for Minecraft's coordinate and tick system.
 */
public final class PhysicConstant {
    public static final PhysicConstant INSTANCE = new PhysicConstant();

    /** Gravity acceleration per tick (blocks/tick^2). Minecraft default ~0.05 */
    public static final double EARTH_GRAVITY = 0.05;

    /** Sea-level air density (kg/m^3) */
    public static final double SEA_AIR_DENSITY = 1.225;

    /** Drag coefficient for particle air resistance */
    public static final double DRAG_COEFFICIENT = 0.01;

    /** Cross-sectional area for drag calculations */
    public static final double CROSS_SECTIONAL_AREA = 0.01;

    private PhysicConstant() {
    }
}
