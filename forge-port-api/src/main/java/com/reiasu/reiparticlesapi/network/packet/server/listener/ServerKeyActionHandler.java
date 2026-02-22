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
package com.reiasu.reiparticlesapi.network.packet.server.listener;

import com.reiasu.reiparticlesapi.event.ReiEventBus;
import com.reiasu.reiparticlesapi.event.events.key.KeyActionEvent;
import com.reiasu.reiparticlesapi.network.packet.PacketKeyActionC2S;
import net.minecraft.server.level.ServerPlayer;

public final class ServerKeyActionHandler {
    private ServerKeyActionHandler() {
    }

    public static void receive(PacketKeyActionC2S packet, ServerPlayer player) {
        ReiEventBus.call(new KeyActionEvent(
                player,
                packet.keyId(),
                packet.action(),
                packet.pressTick(),
                packet.isRelease(),
                true
        ));
    }
}

