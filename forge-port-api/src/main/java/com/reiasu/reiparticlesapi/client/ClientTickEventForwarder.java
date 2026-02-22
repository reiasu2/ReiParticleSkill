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
package com.reiasu.reiparticlesapi.client;

import com.reiasu.reiparticlesapi.event.ReiEventBus;
import com.reiasu.reiparticlesapi.event.events.client.ClientPostTickEvent;
import com.reiasu.reiparticlesapi.event.events.client.ClientPreTickEvent;
import com.reiasu.reiparticlesapi.event.events.world.client.ClientWorldPostTickEvent;
import com.reiasu.reiparticlesapi.event.events.world.client.ClientWorldPreTickEvent;
import net.minecraft.client.Minecraft;

public final class ClientTickEventForwarder {
    private ClientTickEventForwarder() {
    }

    public static void onClientStartTick() {
        Minecraft minecraft = Minecraft.getInstance();
        ReiEventBus.call(new ClientPreTickEvent(minecraft));
        if (minecraft.level != null) {
            ReiEventBus.call(new ClientWorldPreTickEvent(minecraft.level));
        }
    }

    public static void onClientEndTick() {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.level != null) {
            ReiEventBus.call(new ClientWorldPostTickEvent(minecraft.level));
        }
        ReiEventBus.call(new ClientPostTickEvent(minecraft));
    }
}

