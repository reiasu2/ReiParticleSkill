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
package com.reiasu.reiparticlesapi.particles.impl.particles;

import com.reiasu.reiparticlesapi.particles.ControllableParticle;
import com.reiasu.reiparticlesapi.particles.impl.ControllableCloudEffect;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.world.phys.Vec3;

import java.util.UUID;

public final class ControllableCloudParticle extends ControllableParticle {
    private final SpriteSet provider;

    public ControllableCloudParticle(ClientLevel world, Vec3 pos, Vec3 velocity,
                                    UUID controlUUID, boolean faceToCamera, SpriteSet provider) {
        super(world, pos, velocity, controlUUID, faceToCamera);
        this.provider = provider;
        this.pickSprite(provider);
        this.getController().addPreTickAction(p -> p.setSpriteFromAge(this.provider));
    }

    public SpriteSet getProvider() { return provider; }

    public static class Factory implements ParticleProvider<ControllableCloudEffect> {
        private final SpriteSet provider;

        public Factory(SpriteSet provider) {
            this.provider = provider;
        }

        public SpriteSet getProvider() { return provider; }

        @Override
        public Particle createParticle(ControllableCloudEffect parameters, ClientLevel world,
                                       double x, double y, double z,
                                       double velocityX, double velocityY, double velocityZ) {
            return new ControllableCloudParticle(world,
                    new Vec3(x, y, z), new Vec3(velocityX, velocityY, velocityZ),
                    parameters.getControlUUID(), parameters.getFaceToPlayer(), this.provider);
        }
    }
}
