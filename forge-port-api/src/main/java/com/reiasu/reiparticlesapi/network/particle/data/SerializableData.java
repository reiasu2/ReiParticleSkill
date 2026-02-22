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
package com.reiasu.reiparticlesapi.network.particle.data;

import com.reiasu.reiparticlesapi.particles.ParticleDisplayer;

/**
 * Marker interface for data objects that can be network-serialized and
 * used to create a {@link ParticleDisplayer} on the client side.
 * <p>
 * Forge port note: the original Fabric {@code getCodec()} returned a
 * StreamCodec for network serialization. In the Forge port, serialization
 * is handled at a higher level using FriendlyByteBuf directly, so the
 * codec method is omitted. Implementations should still support
 * clone and createDisplayer.
 */
public interface SerializableData {

    /**
     * Returns a deep copy of this data.
     */
    SerializableData clone();

    /**
     * Creates a {@link ParticleDisplayer} that renders the particle
     * described by this data.
     */
    ParticleDisplayer createDisplayer();
}
