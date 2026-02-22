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

public final class LongArrayControllerBuffer extends AbstractControllerBuffer<long[]> {
    public static final ParticleControllerDataBuffer.Id ID =
            new ParticleControllerDataBuffer.Id(new ResourceLocation("reiparticlesapi", "long_array"));

    @Override
    public byte[] encode(long[] value) {
        long[] safe = value == null ? new long[0] : value;
        ByteBuffer buffer = ByteBuffer.allocate(4 + safe.length * 8);
        buffer.putInt(safe.length);
        for (long i : safe) {
            buffer.putLong(i);
        }
        return buffer.array();
    }

    @Override
    public long[] decode(byte[] buf) {
        if (buf.length < 4) {
            return new long[0];
        }
        ByteBuffer buffer = ByteBuffer.wrap(buf);
        int len = Math.max(0, buffer.getInt());
        long[] out = new long[Math.min(len, (buf.length - 4) / 8)];
        for (int i = 0; i < out.length; i++) {
            out[i] = buffer.getLong();
        }
        return out;
    }

    @Override
    public ParticleControllerDataBuffer.Id getBufferID() {
        return ID;
    }
}

