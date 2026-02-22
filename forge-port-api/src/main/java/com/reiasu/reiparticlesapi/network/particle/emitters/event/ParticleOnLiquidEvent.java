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
import net.minecraft.core.BlockPos;

/**
 * Fired when a particle enters a liquid block (water, lava, etc.).
 */
public final class ParticleOnLiquidEvent implements ParticleEvent {

    public static final String EVENT_ID = "ParticleOnLiquidEvent";

    private ControllableParticle particle;
    private ControllableParticleData particleData;
    private BlockPos hit;
    private boolean canceled;

    public ParticleOnLiquidEvent(ControllableParticle particle, ControllableParticleData particleData, BlockPos hit) {
        this.particle = particle;
        this.particleData = particleData;
        this.hit = hit;
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

    public BlockPos getHit() {
        return hit;
    }

    public void setHit(BlockPos hit) {
        this.hit = hit;
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
