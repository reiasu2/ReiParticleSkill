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
package com.reiasu.reiparticlesapi.compat.version;

import com.reiasu.reiparticlesapi.compat.version.forge120.Forge120ClientCameraBridge;
import com.reiasu.reiparticlesapi.compat.version.forge120.Forge120ModLifecycleBridge;
import com.reiasu.reiparticlesapi.compat.version.forge120.Forge120NetworkBridge;
import com.reiasu.reiparticlesapi.compat.version.forge120.Forge120ResourceLocationBridge;

public final class VersionBridgeRegistry {
    private static final ResourceLocationVersionBridge RESOURCE_LOCATION = new Forge120ResourceLocationBridge();
    private static final NetworkVersionBridge NETWORK = new Forge120NetworkBridge();
    private static final ClientCameraVersionBridge CLIENT_CAMERA = new Forge120ClientCameraBridge();
    private static final ModLifecycleVersionBridge LIFECYCLE = new Forge120ModLifecycleBridge();

    private VersionBridgeRegistry() {
    }

    public static ResourceLocationVersionBridge resourceLocation() {
        return RESOURCE_LOCATION;
    }

    public static NetworkVersionBridge network() {
        return NETWORK;
    }

    public static ClientCameraVersionBridge clientCamera() {
        return CLIENT_CAMERA;
    }

    public static ModLifecycleVersionBridge lifecycle() {
        return LIFECYCLE;
    }
}
