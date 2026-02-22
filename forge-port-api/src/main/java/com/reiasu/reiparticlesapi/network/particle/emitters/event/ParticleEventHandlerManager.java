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

import java.util.HashMap;
import java.util.Map;

/**
 * Singleton registry for {@link ParticleEventHandler} instances.
 * <p>
 * Forge port note: The Fabric version used {@code ReiAPIScanner} for classpath-scanning
 * auto-registration. In the Forge port, handlers must be registered explicitly via
 * {@link #register(ParticleEventHandler)}.
 */
public final class ParticleEventHandlerManager {

    public static final ParticleEventHandlerManager INSTANCE = new ParticleEventHandlerManager();

    private final Map<String, ParticleEventHandler> registerHandlers = new HashMap<>();

    private ParticleEventHandlerManager() {
    }

    /**
     * Look up a handler by its handler ID.
     */
    public ParticleEventHandler getHandlerById(String id) {
        return registerHandlers.get(id);
    }

    /**
     * Register a handler. Replaces any existing handler with the same ID.
     */
    public void register(ParticleEventHandler handler) {
        registerHandlers.put(handler.getHandlerID(), handler);
    }

    /**
     * Check whether a handler with the given ID is already registered.
     */
    public boolean hasRegister(String id) {
        return registerHandlers.containsKey(id);
    }

    /**
     * Called during mod init. In the Forge port this is a no-op;
     * handlers should register themselves explicitly.
     */
    public void init() {
        // Fabric version used ReiAPIScanner for auto-registration.
        // Forge port: register handlers explicitly or use Forge event bus.
    }
}
