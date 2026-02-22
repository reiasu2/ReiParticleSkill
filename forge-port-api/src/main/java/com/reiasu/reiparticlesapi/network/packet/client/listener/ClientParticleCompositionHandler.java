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

import com.reiasu.reiparticlesapi.network.packet.PacketParticleCompositionS2C;
import com.reiasu.reiparticlesapi.network.particle.composition.ParticleComposition;
import com.reiasu.reiparticlesapi.network.particle.composition.manager.ParticleCompositionManager;
import io.netty.buffer.Unpooled;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;

import java.util.function.Function;

public final class ClientParticleCompositionHandler {
    private ClientParticleCompositionHandler() {
    }

    public static void receive(PacketParticleCompositionS2C packet) {
        Function<FriendlyByteBuf, ParticleComposition> decoder =
                ParticleCompositionManager.INSTANCE.getRegisteredTypes().get(packet.getType());
        if (decoder == null) {
            return;
        }
        FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.wrappedBuffer(packet.getData()));
        ParticleComposition decoded = decoder.apply(buf);
        if (decoded == null) {
            return;
        }
        Minecraft minecraft = Minecraft.getInstance();
        decoded.setWorld(minecraft.level);

        ParticleComposition old = ParticleCompositionManager.INSTANCE.getClientView().get(packet.getUuid());
        if (old == null) {
            if (!packet.getDistanceRemove()) {
                ParticleCompositionManager.INSTANCE.addClient(decoded);
            }
            return;
        }
        if (packet.getDistanceRemove()) {
            old.remove();
            return;
        }
        old.update(decoded);
    }
}

