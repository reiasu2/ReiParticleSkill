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
import com.reiasu.reiparticlesapi.utils.GraphMathHelper;
import net.minecraft.world.phys.Vec3;

/**
 * Applies velocity damping (drag) to particles. Combines exponential damping
 * with optional linear drag and a minimum speed cutoff.
 */
public final class ParticleDragCommand implements ParticleCommand {

    private double damping = 0.15;
    private double minSpeed = 0.0;
    private double linear = 0.0;

    public ParticleDragCommand() {
    }

    public ParticleDragCommand(double damping, double minSpeed, double linear) {
        this.damping = damping;
        this.minSpeed = minSpeed;
        this.linear = linear;
    }

    // Fluent setters

    public ParticleDragCommand damping(double v) { this.damping = v; return this; }
    public ParticleDragCommand minSpeed(double v) { this.minSpeed = v; return this; }
    public ParticleDragCommand linear(double v) { this.linear = v; return this; }

    // Standard getters/setters

    public double getDamping() { return damping; }
    public void setDamping(double damping) { this.damping = damping; }
    public double getMinSpeed() { return minSpeed; }
    public void setMinSpeed(double minSpeed) { this.minSpeed = minSpeed; }
    public double getLinear() { return linear; }
    public void setLinear(double linear) { this.linear = linear; }

    @Override
    public void execute(ControllableParticleData data, ControllableParticle particle) {
        Vec3 v = data.getVelocity();
        double speed = v.length();

        if (minSpeed > 0.0 && speed <= minSpeed) {
            data.setVelocity(Vec3.ZERO);
            return;
        }

        double factor = GraphMathHelper.expDampFactor(damping, 1.0);
        v = v.scale(factor);

        if (linear > 0.0 && v.lengthSqr() > 1.0E-18) {
            double shrink = Math.max(0.0, Math.min(1.0, 1.0 - linear));
            v = v.scale(shrink);
        }

        data.setVelocity(v);
    }
}
