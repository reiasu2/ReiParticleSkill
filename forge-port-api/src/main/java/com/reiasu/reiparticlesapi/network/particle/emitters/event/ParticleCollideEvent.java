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
package com.reiasu.reiparticlesapi.network.particle.emitters.event;

import com.reiasu.reiparticlesapi.network.particle.emitters.ControllableParticleData;
import com.reiasu.reiparticlesapi.particles.ControllableParticle;
import net.minecraft.world.phys.HitResult;

/**
 * Fired when a particle collides with any block or entity (general ray-cast hit).
 */
public final class ParticleCollideEvent implements ParticleEvent {

    public static final String EVENT_ID = "ParticleColliderEvent";

    private ControllableParticle particle;
    private ControllableParticleData particleData;
    private HitResult res;
    private boolean canceled;

    public ParticleCollideEvent(ControllableParticle particle, ControllableParticleData particleData, HitResult res) {
        this.particle = particle;
        this.particleData = particleData;
        this.res = res;
    }

    @Override
    public String getEventID() {
        return EVENT_ID;
    }

    @Override
    public ControllableParticle getParticle() {
        return particle;
    }

    @Override
    public void setParticle(ControllableParticle particle) {
        this.particle = particle;
    }

    @Override
    public ControllableParticleData getParticleData() {
        return particleData;
    }

    @Override
    public void setParticleData(ControllableParticleData data) {
        this.particleData = data;
    }

    public HitResult getRes() {
        return res;
    }

    public void setRes(HitResult res) {
        this.res = res;
    }

    @Override
    public boolean getCanceled() {
        return canceled;
    }

    @Override
    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }
}
