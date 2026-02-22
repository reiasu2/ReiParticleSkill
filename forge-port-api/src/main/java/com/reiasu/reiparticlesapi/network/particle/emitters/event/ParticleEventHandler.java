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

/**
 * A handler that reacts to a specific {@link ParticleEvent} type.
 * Handlers are ordered by priority (lower priority value = executed first).
 */
public interface ParticleEventHandler extends Comparable<ParticleEventHandler> {

    /**
     * Process the given event. May mutate event state or cancel it.
     */
    void handle(ParticleEvent event);

    /**
     * The event ID this handler targets (must match {@link ParticleEvent#getEventID()}).
     */
    String getTargetEventID();

    /**
     * Unique identifier for this handler instance.
     */
    String getHandlerID();

    /**
     * Execution priority. Lower values run first.
     */
    int getPriority();

    @Override
    default int compareTo(ParticleEventHandler other) {
        return this.getPriority() - other.getPriority();
    }
}
