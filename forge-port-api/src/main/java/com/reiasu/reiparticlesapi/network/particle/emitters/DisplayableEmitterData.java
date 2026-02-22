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
package com.reiasu.reiparticlesapi.network.particle.emitters;

import com.reiasu.reiparticlesapi.network.particle.data.SerializableData;
import com.reiasu.reiparticlesapi.particles.ParticleDisplayer;

/**
 * A {@link SerializableData} implementation for emitter display data.
 * <p>
 * All methods currently throw {@link UnsupportedOperationException} matching
 * the original Fabric source which also had "Not yet implemented" stubs.
 * This class exists as a placeholder type for emitter data factories.
 */
public final class DisplayableEmitterData implements SerializableData {

    @Override
    public SerializableData clone() {
        throw new UnsupportedOperationException("DisplayableEmitterData.clone() not yet implemented");
    }

    @Override
    public ParticleDisplayer createDisplayer() {
        throw new UnsupportedOperationException("DisplayableEmitterData.createDisplayer() not yet implemented");
    }
}
