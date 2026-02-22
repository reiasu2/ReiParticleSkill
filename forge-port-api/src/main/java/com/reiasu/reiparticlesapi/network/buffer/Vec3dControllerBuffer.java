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
import net.minecraft.world.phys.Vec3;

import java.nio.ByteBuffer;

public final class Vec3dControllerBuffer extends AbstractControllerBuffer<Vec3> {
    public static final ParticleControllerDataBuffer.Id ID =
            new ParticleControllerDataBuffer.Id(new ResourceLocation("reiparticlesapi", "vec3d"));

    @Override
    public byte[] encode(Vec3 value) {
        Vec3 safe = value == null ? Vec3.ZERO : value;
        return ByteBuffer.allocate(24)
                .putDouble(safe.x)
                .putDouble(safe.y)
                .putDouble(safe.z)
                .array();
    }

    @Override
    public Vec3 decode(byte[] buf) {
        if (buf.length < 24) {
            return Vec3.ZERO;
        }
        ByteBuffer buffer = ByteBuffer.wrap(buf);
        return new Vec3(buffer.getDouble(), buffer.getDouble(), buffer.getDouble());
    }

    @Override
    public ParticleControllerDataBuffer.Id getBufferID() {
        return ID;
    }
}

