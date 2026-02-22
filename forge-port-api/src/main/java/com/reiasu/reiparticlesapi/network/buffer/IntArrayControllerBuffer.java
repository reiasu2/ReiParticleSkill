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

public final class IntArrayControllerBuffer extends AbstractControllerBuffer<int[]> {
    public static final ParticleControllerDataBuffer.Id ID =
            new ParticleControllerDataBuffer.Id(new ResourceLocation("reiparticlesapi", "int_array"));

    @Override
    public byte[] encode(int[] value) {
        int[] safe = value == null ? new int[0] : value;
        ByteBuffer buffer = ByteBuffer.allocate(4 + safe.length * 4);
        buffer.putInt(safe.length);
        for (int i : safe) {
            buffer.putInt(i);
        }
        return buffer.array();
    }

    @Override
    public int[] decode(byte[] buf) {
        if (buf.length < 4) {
            return new int[0];
        }
        ByteBuffer buffer = ByteBuffer.wrap(buf);
        int len = Math.max(0, buffer.getInt());
        int[] out = new int[Math.min(len, (buf.length - 4) / 4)];
        for (int i = 0; i < out.length; i++) {
            out[i] = buffer.getInt();
        }
        return out;
    }

    @Override
    public ParticleControllerDataBuffer.Id getBufferID() {
        return ID;
    }
}

