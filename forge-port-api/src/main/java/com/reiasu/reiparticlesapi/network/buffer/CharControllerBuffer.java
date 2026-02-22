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
package com.reiasu.reiparticlesapi.network.buffer;

import net.minecraft.resources.ResourceLocation;

import java.nio.ByteBuffer;

/**
 * Buffer for single char (2-byte) values.
 */
public final class CharControllerBuffer extends AbstractControllerBuffer<Character> {
    public static final ParticleControllerDataBuffer.Id ID =
            new ParticleControllerDataBuffer.Id(new ResourceLocation("reiparticlesapi", "char"));

    @Override
    public byte[] encode(Character value) {
        return ByteBuffer.allocate(2).putChar(value == null ? 0 : value).array();
    }

    @Override
    public Character decode(byte[] buf) {
        if (buf.length < 2) {
            return (char) 0;
        }
        return ByteBuffer.wrap(buf).getChar();
    }

    @Override
    public ParticleControllerDataBuffer.Id getBufferID() {
        return ID;
    }
}
