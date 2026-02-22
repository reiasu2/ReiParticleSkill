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
package com.reiasu.reiparticlesapi.network.particle.emitters.command;

import com.reiasu.reiparticlesapi.network.particle.emitters.ControllableParticleData;
import com.reiasu.reiparticlesapi.particles.ControllableParticle;
import net.minecraft.world.phys.Vec3;

/**
 * Applies downward gravity to a particle's velocity.
 */
public final class ParticleGravityCommand implements ParticleCommand {

    private final double gravity;

    public ParticleGravityCommand(double gravity) {
        this.gravity = gravity;
    }

    public double getGravity() {
        return gravity;
    }

    @Override
    public void execute(ControllableParticleData data, ControllableParticle particle) {
        Vec3 vel = data.getVelocity();
        data.setVelocity(vel.add(0.0, -gravity, 0.0));
    }
}
