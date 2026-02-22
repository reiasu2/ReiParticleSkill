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
package com.reiasu.reiparticlesapi.network.particle.emitters.environment.wind;

import com.reiasu.reiparticlesapi.network.particle.emitters.ParticleEmitters;
import net.minecraft.world.phys.Vec3;

/**
 * Defines a wind effect that applies directional force to particles within an emitter.
 * <p>
 * Wind can be absolute (fixed direction) or relative to the emitter position.
 * The {@code windSpeedExpress} field allows configuring wind strength; in the original
 * Fabric version this was a math expression with variable {@code l} (distance),
 * but in the Forge port it is parsed as a constant double for simplicity.
 * <p>
 * Forge port note: {@code getCodec()} from Fabric StreamCodec is omitted.
 */
public interface WindDirection {

    /**
     * The base wind direction vector.
     */
    Vec3 getDirection();

    void setDirection(Vec3 direction);

    /**
     * Whether wind is relative to the emitter position (true) or absolute (false).
     */
    boolean getRelative();

    void setRelative(boolean relative);

    /**
     * Wind speed expression string. Parsed as a constant double in Forge port.
     * In Fabric, this was a math expression with variable {@code l} = distance.
     */
    String getWindSpeedExpress();

    void setWindSpeedExpress(String express);

    /**
     * Bind this wind direction to an emitter instance for relative calculations.
     */
    WindDirection loadEmitters(ParticleEmitters emitters);

    /**
     * Whether an emitter has been bound.
     */
    boolean hasLoadedEmitters();

    /**
     * Unique string identifier for this wind type.
     */
    String getID();

    /**
     * Compute the wind vector at a given particle position.
     *
     * @param particlePos the particle's current world position
     * @return the wind force vector
     */
    Vec3 getWind(Vec3 particlePos);

    /**
     * Check whether the given position is within this wind's area of effect.
     */
    boolean inRange(Vec3 pos);
}
