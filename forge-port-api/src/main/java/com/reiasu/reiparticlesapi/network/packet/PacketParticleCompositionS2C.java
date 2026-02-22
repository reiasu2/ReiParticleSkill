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

import com.reiasu.reiparticlesapi.network.packet.client.listener.ClientParticleCompositionHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public final class PacketParticleCompositionS2C {
    private final UUID uuid;
    private final String type;
    private final byte[] data;
    private boolean distanceRemove;

    public PacketParticleCompositionS2C(UUID uuid, String type, byte[] data) {
        this.uuid = uuid;
        this.type = type;
        this.data = data;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getType() {
        return type;
    }

    public byte[] getData() {
        return data;
    }

    public boolean getDistanceRemove() {
        return distanceRemove;
    }

    public void setDistanceRemove(boolean distanceRemove) {
        this.distanceRemove = distanceRemove;
    }

    public static void encode(PacketParticleCompositionS2C packet, FriendlyByteBuf buf) {
        buf.writeUtf(packet.type);
        buf.writeUUID(packet.uuid);
        buf.writeBoolean(packet.distanceRemove);
        buf.writeInt(packet.data.length);
        buf.writeBytes(packet.data);
    }

    public static PacketParticleCompositionS2C decode(FriendlyByteBuf buf) {
        String type = buf.readUtf();
        UUID uuid = buf.readUUID();
        boolean distanceRemove = buf.readBoolean();
        int size = buf.readInt();
        byte[] data = new byte[size];
        buf.readBytes(data);
        PacketParticleCompositionS2C packet = new PacketParticleCompositionS2C(uuid, type, data);
        packet.setDistanceRemove(distanceRemove);
        return packet;
    }

    public static void handle(PacketParticleCompositionS2C packet, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientParticleCompositionHandler.receive(packet)));
        context.setPacketHandled(true);
    }
}
