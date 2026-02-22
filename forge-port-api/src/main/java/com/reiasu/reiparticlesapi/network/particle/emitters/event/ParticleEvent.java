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

/**
 * Base interface for particle-level events fired during emitter simulation.
 */
public interface ParticleEvent {

    /**
     * Returns the unique string identifier for this event type.
     */
    String getEventID();

    /**
     * Gets the particle instance.
     */
    ControllableParticle getParticle();

    /**
     * Sets the particle instance.
     */
    void setParticle(ControllableParticle particle);

    /**
     * Gets the particle's mutable data.
     */
    ControllableParticleData getParticleData();

    /**
     * Sets the particle's mutable data.
     */
    void setParticleData(ControllableParticleData data);

    /**
     * Whether this event has been canceled by a handler.
     */
    boolean getCanceled();

    /**
     * Cancel or un-cancel this event.
     */
    void setCanceled(boolean canceled);
}
