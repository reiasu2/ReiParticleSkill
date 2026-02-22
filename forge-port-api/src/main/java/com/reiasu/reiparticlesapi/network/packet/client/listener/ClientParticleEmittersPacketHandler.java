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

import com.reiasu.reiparticlesapi.network.packet.PacketParticleEmittersS2C;
import com.reiasu.reiparticlesapi.network.particle.emitters.ParticleEmitters;
import com.reiasu.reiparticlesapi.network.particle.emitters.ParticleEmittersManager;
import io.netty.buffer.Unpooled;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;

import java.util.function.Function;

public final class ClientParticleEmittersPacketHandler {
    private ClientParticleEmittersPacketHandler() {
    }

    public static void receive(PacketParticleEmittersS2C packet) {
        Function<FriendlyByteBuf, ParticleEmitters> decoder = ParticleEmittersManager.getCodecFromRawID(packet.emitterID());
        if (decoder == null) {
            return;
        }
        FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.wrappedBuffer(packet.emitterData()));
        ParticleEmitters emitters = decoder.apply(buf);
        if (emitters == null) {
            return;
        }
        Minecraft minecraft = Minecraft.getInstance();
        switch (packet.type()) {
            case CHANGE_OR_CREATE -> {
                if (minecraft.level != null) {
                    ParticleEmittersManager.createOrChangeClient(emitters, minecraft.level);
                }
            }
            case REMOVE -> {
                ParticleEmitters target = ParticleEmittersManager.getClientEmitters().get(emitters.getUuid());
                if (target != null) {
                    target.cancel();
                }
            }
        }
    }
}

