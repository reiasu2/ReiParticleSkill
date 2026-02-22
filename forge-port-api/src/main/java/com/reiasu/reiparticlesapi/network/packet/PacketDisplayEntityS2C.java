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
package com.reiasu.reiparticlesapi.network.packet;

import com.reiasu.reiparticlesapi.network.packet.client.listener.ClientDisplayEntityPacketHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public record PacketDisplayEntityS2C(UUID uuid, String type, byte[] data) {
    public static void encode(PacketDisplayEntityS2C packet, FriendlyByteBuf buf) {
        buf.writeUtf(packet.type);
        buf.writeUUID(packet.uuid);
        buf.writeInt(packet.data.length);
        buf.writeBytes(packet.data);
    }

    public static PacketDisplayEntityS2C decode(FriendlyByteBuf buf) {
        String type = buf.readUtf();
        UUID uuid = buf.readUUID();
        int size = buf.readInt();
        byte[] data = new byte[size];
        buf.readBytes(data);
        return new PacketDisplayEntityS2C(uuid, type, data);
    }

    public static void handle(PacketDisplayEntityS2C packet, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientDisplayEntityPacketHandler.receive(packet)));
        context.setPacketHandled(true);
    }
}
