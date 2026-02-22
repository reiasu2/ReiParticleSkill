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
package com.reiasu.reiparticlesapi.event.events.server;

import com.reiasu.reiparticlesapi.event.api.ReiEvent;
import net.minecraft.server.MinecraftServer;

import java.util.Objects;

public abstract class ServerEvent extends ReiEvent {
    private final MinecraftServer server;

    protected ServerEvent(MinecraftServer server) {
        this.server = Objects.requireNonNull(server, "server");
    }

    public MinecraftServer getServer() {
        return server;
    }
}

