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

import com.reiasu.reiparticlesapi.event.events.key.KeyActionType;
import com.reiasu.reiparticlesapi.network.packet.server.listener.ServerKeyActionHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record PacketKeyActionC2S(ResourceLocation keyId, KeyActionType action, int pressTick, boolean isRelease) {
    public static void encode(PacketKeyActionC2S packet, FriendlyByteBuf buf) {
        buf.writeResourceLocation(packet.keyId);
        buf.writeInt(packet.action.getId());
        buf.writeInt(packet.pressTick);
        buf.writeBoolean(packet.isRelease);
    }

    public static PacketKeyActionC2S decode(FriendlyByteBuf buf) {
        ResourceLocation keyId = buf.readResourceLocation();
        KeyActionType action = KeyActionType.fromId(buf.readInt());
        int pressTick = buf.readInt();
        boolean isRelease = buf.readBoolean();
        return new PacketKeyActionC2S(keyId, action, pressTick, isRelease);
    }

    public static void handle(PacketKeyActionC2S packet, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        ServerPlayer sender = context.getSender();
        if (sender != null) {
            context.enqueueWork(() -> ServerKeyActionHandler.receive(packet, sender));
        }
        context.setPacketHandled(true);
    }
}

