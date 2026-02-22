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

import com.reiasu.reiparticlesapi.display.DisplayEntity;
import com.reiasu.reiparticlesapi.display.DisplayEntityManager;
import com.reiasu.reiparticlesapi.network.packet.PacketDisplayEntityS2C;
import io.netty.buffer.Unpooled;
import net.minecraft.network.FriendlyByteBuf;

import java.util.function.Function;

public final class ClientDisplayEntityPacketHandler {
    private ClientDisplayEntityPacketHandler() {
    }

    public static void receive(PacketDisplayEntityS2C packet) {
        Function<FriendlyByteBuf, DisplayEntity> decoder =
                DisplayEntityManager.INSTANCE.getRegisteredTypes().get(packet.type());
        if (decoder == null) {
            return;
        }
        FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.wrappedBuffer(packet.data()));
        DisplayEntity display = decoder.apply(buf);
        if (display == null) {
            return;
        }

        DisplayEntity old = DisplayEntityManager.INSTANCE.getClientView().get(packet.uuid());
        if (old == null) {
            DisplayEntityManager.INSTANCE.addClient(display);
            return;
        }
        old.update(display);
    }
}

