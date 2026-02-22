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
package com.reiasu.reiparticlesapi.network.animation;

import com.reiasu.reiparticlesapi.particles.ControllableParticle;
import net.minecraft.world.phys.Vec3;

import java.util.function.IntFunction;

/**
 * Concrete path motion that uses a lambda to compute the path offset.
 * The function takes the current tick index and returns a Vec3 offset from origin.
 * <p>
 * Inherits teleport and validity logic from {@link ParticlePathMotion}.
 */
public final class LambdaParticlePathMotion extends ParticlePathMotion {
    private final IntFunction<Vec3> path;

    /**
     * @param origin   the world origin position
     * @param particle the target particle
     * @param path     function from tick index to Vec3 offset
     */
    public LambdaParticlePathMotion(Vec3 origin, ControllableParticle particle, IntFunction<Vec3> path) {
        super(origin, particle);
        this.path = path;
    }

    public IntFunction<Vec3> getPath() {
        return path;
    }

    @Override
    public Vec3 pathFunction() {
        return path.apply(getCurrentTick());
    }
}
