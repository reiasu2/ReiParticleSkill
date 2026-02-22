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

import net.minecraft.server.MinecraftServer;

import java.util.function.Consumer;

public interface ModLifecycleVersionBridge {
    void registerClientSetup(Runnable callback);

    void registerClientStartTick(Runnable callback);

    void registerClientEndTick(Runnable callback);

    void registerServerStartTick(Consumer<MinecraftServer> callback);

    void registerServerEndTick(Consumer<MinecraftServer> callback);
}
