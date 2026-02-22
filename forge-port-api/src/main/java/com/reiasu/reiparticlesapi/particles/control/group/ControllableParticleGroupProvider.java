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
package com.reiasu.reiparticlesapi.particles.control.group;

import com.reiasu.reiparticlesapi.network.buffer.ParticleControllerDataBuffer;

import java.util.Map;
import java.util.UUID;

/**
 * Provider (factory) for creating and modifying {@link ControllableParticleGroup} instances.
 * <p>
 * Registered with {@link ClientParticleGroupManager} for client-side group creation
 * when receiving server-side packets.
 *
 * @deprecated Use ParticleGroupStyle instead.
 */
@Deprecated
public interface ControllableParticleGroupProvider {

    /**
     * Create a new particle group with the given UUID and initialization args.
     */
    ControllableParticleGroup createGroup(UUID uuid, Map<String, ? extends ParticleControllerDataBuffer<?>> args);

    /**
     * Apply changes to an existing group with the given args.
     */
    void changeGroup(ControllableParticleGroup group, Map<String, ? extends ParticleControllerDataBuffer<?>> args);
}
