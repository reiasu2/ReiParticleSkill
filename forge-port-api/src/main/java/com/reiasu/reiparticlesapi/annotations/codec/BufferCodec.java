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
package com.reiasu.reiparticlesapi.annotations.codec;

import net.minecraft.network.FriendlyByteBuf;

/**
 * Replacement for Fabric/Mojang's {@code StreamCodec<FriendlyByteBuf, T>} which
 * was introduced in Minecraft 1.20.5 and does not exist in Forge 1.20.1.
 * <p>
 * Provides symmetric encode/decode for a type {@code T} to/from a
 * {@link FriendlyByteBuf}.
 *
 * @param <T> the type to serialize
 */
public interface BufferCodec<T> {

    /**
     * Creates a {@link BufferCodec} from explicit encoder and decoder lambdas.
     */
    static <T> BufferCodec<T> of(Encoder<T> encoder, Decoder<T> decoder) {
        return new BufferCodec<T>() {
            @Override
            public void encode(FriendlyByteBuf buf, T value) {
                encoder.encode(buf, value);
            }

            @Override
            public T decode(FriendlyByteBuf buf) {
                return decoder.decode(buf);
            }
        };
    }

    /**
     * Writes {@code value} into the buffer.
     */
    void encode(FriendlyByteBuf buf, T value);

    /**
     * Reads a value from the buffer.
     */
    T decode(FriendlyByteBuf buf);

    @FunctionalInterface
    interface Encoder<T> {
        void encode(FriendlyByteBuf buf, T value);
    }

    @FunctionalInterface
    interface Decoder<T> {
        T decode(FriendlyByteBuf buf);
    }
}
