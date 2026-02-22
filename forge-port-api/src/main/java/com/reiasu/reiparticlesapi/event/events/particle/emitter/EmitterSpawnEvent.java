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
package com.reiasu.reiparticlesapi.event.events.particle.emitter;

import com.reiasu.reiparticlesapi.event.api.ReiEvent;
import com.reiasu.reiparticlesapi.network.particle.emitters.ParticleEmitters;

public final class EmitterSpawnEvent extends ReiEvent {
    private final ParticleEmitters emitter;
    private final boolean clientSide;

    public EmitterSpawnEvent(ParticleEmitters emitter, boolean clientSide) {
        this.emitter = emitter;
        this.clientSide = clientSide;
    }

    public ParticleEmitters getEmitter() {
        return emitter;
    }

    public boolean isClientSide() {
        return clientSide;
    }
}

