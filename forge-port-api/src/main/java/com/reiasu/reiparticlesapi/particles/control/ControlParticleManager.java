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
package com.reiasu.reiparticlesapi.particles.control;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Manages the mapping between control UUIDs and their {@link ParticleController} instances.
 * <p>
 * Each controlled particle gets a UUID; the manager stores and retrieves the
 * corresponding controller so the particle can be mutated from server-driven
 * commands.
 */
public final class ControlParticleManager {

    public static final ControlParticleManager INSTANCE = new ControlParticleManager();

    private final ConcurrentHashMap<UUID, ParticleController> controls = new ConcurrentHashMap<>();

    private ControlParticleManager() {
    }

    /**
     * Look up a controller by UUID. Returns {@code null} if none is registered.
     */
    public ParticleController getControl(UUID uuid) {
        return controls.get(uuid);
    }

    /**
     * Remove a controller by UUID.
     */
    public void removeControl(UUID uuid) {
        controls.remove(uuid);
    }

    /**
     * Create a new controller for the given UUID and register it.
     *
     * @param uuid the control UUID
     * @return the newly created controller
     */
    public ParticleController createControl(UUID uuid) {
        ParticleController controller = new ParticleController(uuid);
        controls.put(uuid, controller);
        return controller;
    }
}
