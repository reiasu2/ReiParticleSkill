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

import com.reiasu.reiparticlesapi.client.CameraShakeClientState;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record CameraShakeS2CPacket(double range, Vec3 origin, double amplitude, int tick) {
    public static void encode(CameraShakeS2CPacket packet, FriendlyByteBuf buf) {
        buf.writeDouble(packet.range);
        buf.writeDouble(packet.origin.x);
        buf.writeDouble(packet.origin.y);
        buf.writeDouble(packet.origin.z);
        buf.writeDouble(packet.amplitude);
        buf.writeInt(packet.tick);
    }

    public static CameraShakeS2CPacket decode(FriendlyByteBuf buf) {
        double range = buf.readDouble();
        Vec3 origin = new Vec3(buf.readDouble(), buf.readDouble(), buf.readDouble());
        double amplitude = buf.readDouble();
        int tick = buf.readInt();
        return new CameraShakeS2CPacket(range, origin, amplitude, tick);
    }

    public static void handle(CameraShakeS2CPacket packet, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() ->
                DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> CameraShakeClientState.start(packet))
        );
        context.setPacketHandled(true);
    }
}
