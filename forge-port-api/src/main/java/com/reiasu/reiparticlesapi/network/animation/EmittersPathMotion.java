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

import com.reiasu.reiparticlesapi.network.animation.api.AbstractPathMotion;
import com.reiasu.reiparticlesapi.network.particle.emitters.ParticleEmitters;
import net.minecraft.world.phys.Vec3;

/**
 * Path motion targeting a {@link ParticleEmitters}. Teleports the emitter
 * along the computed path. Subclasses implement {@link #pathFunction()}.
 */
public abstract class EmittersPathMotion extends AbstractPathMotion {
    private final ParticleEmitters targetEmitters;

    protected EmittersPathMotion(Vec3 origin, ParticleEmitters targetEmitters) {
        super(origin);
        this.targetEmitters = targetEmitters;
    }

    public final ParticleEmitters getTargetEmitters() {
        return targetEmitters;
    }

    @Override
    public void apply(Vec3 actualPos) {
        targetEmitters.teleportTo(actualPos);
    }

    @Override
    public boolean checkValid() {
        return !targetEmitters.getCanceled();
    }
}
