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
package com.reiasu.reiparticlesapi.network.packet.client.listener;

import com.reiasu.reiparticlesapi.client.CameraShakeClientState;
import com.reiasu.reiparticlesapi.network.packet.CameraShakeS2CPacket;
import com.reiasu.reiparticlesapi.network.packet.PacketCameraShakeS2C;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;

public final class ClientCameraShakeHandler {
    private ClientCameraShakeHandler() {
    }

    public static void receive(PacketCameraShakeS2C packet) {
        Minecraft minecraft = Minecraft.getInstance();
        Player player = minecraft.player;
        if (player == null) {
            return;
        }
        double range = packet.range();
        if (range > 0.0 && player.position().distanceTo(packet.origin()) > range) {
            return;
        }
        CameraShakeClientState.start(new CameraShakeS2CPacket(packet.range(), packet.origin(), packet.amplitude(), packet.tick()));
    }
}

