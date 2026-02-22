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

import com.reiasu.reiparticlesapi.network.packet.PacketParticleS2C;
import net.minecraft.client.Minecraft;

public final class ClientParticlePacketHandler {
    private ClientParticlePacketHandler() {
    }

    public static void receive(PacketParticleS2C packet) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.level == null) {
            return;
        }
        minecraft.level.addParticle(
                packet.type(),
                true,
                packet.pos().x,
                packet.pos().y,
                packet.pos().z,
                packet.velocity().x,
                packet.velocity().y,
                packet.velocity().z
        );
    }
}

