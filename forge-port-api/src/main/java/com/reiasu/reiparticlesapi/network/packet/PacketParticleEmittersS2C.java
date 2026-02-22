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

import com.reiasu.reiparticlesapi.network.packet.client.listener.ClientParticleEmittersPacketHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record PacketParticleEmittersS2C(int emitterID, byte[] emitterData, PacketType type) {
    public enum PacketType {
        CHANGE_OR_CREATE(0),
        REMOVE(1);

        private final int id;

        PacketType(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public static PacketType fromID(int id) {
            return switch (id) {
                case 0 -> CHANGE_OR_CREATE;
                case 1 -> REMOVE;
                default -> CHANGE_OR_CREATE;
            };
        }
    }

    public static void encode(PacketParticleEmittersS2C packet, FriendlyByteBuf buf) {
        buf.writeVarInt(packet.type.getId());
        buf.writeVarInt(packet.emitterID);
        buf.writeVarInt(packet.emitterData.length);
        buf.writeBytes(packet.emitterData);
    }

    public static PacketParticleEmittersS2C decode(FriendlyByteBuf buf) {
        PacketType packetType = PacketType.fromID(buf.readVarInt());
        int emitterID = buf.readVarInt();
        int size = buf.readVarInt();
        byte[] data = new byte[size];
        buf.readBytes(data);
        return new PacketParticleEmittersS2C(emitterID, data, packetType);
    }

    public static void handle(PacketParticleEmittersS2C packet, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientParticleEmittersPacketHandler.receive(packet)));
        context.setPacketHandled(true);
    }
}
