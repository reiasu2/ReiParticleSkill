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
package com.reiasu.reiparticlesapi.compat.version.forge120;

import com.reiasu.reiparticlesapi.compat.version.NetworkVersionBridge;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public final class Forge120NetworkBridge implements NetworkVersionBridge {
    private final Forge120ResourceLocationBridge locationBridge = new Forge120ResourceLocationBridge();

    @Override
    public SimpleChannel createSimpleChannel(String modId, String channelName, int protocolVersion) {
        String protocol = Integer.toString(protocolVersion);
        return NetworkRegistry.ChannelBuilder
                .named(locationBridge.modLocation(modId, channelName))
                .networkProtocolVersion(() -> protocol)
                .clientAcceptedVersions(v -> true)
                .serverAcceptedVersions(v -> true)
                .simpleChannel();
    }

    @Override
    public <M> void registerClientboundMessage(
            SimpleChannel channel,
            Class<M> messageType,
            int messageId,
            BiConsumer<M, FriendlyByteBuf> encoder,
            Function<FriendlyByteBuf, M> decoder,
            BiConsumer<M, Supplier<NetworkEvent.Context>> handler
    ) {
        channel.registerMessage(messageId, messageType, encoder, decoder, handler);
    }

    @Override
    public void sendToPlayer(SimpleChannel channel, ServerPlayer player, Object packet) {
        channel.send(PacketDistributor.PLAYER.with(() -> player), packet);
    }
}
