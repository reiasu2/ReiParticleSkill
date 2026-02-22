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

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public final class UUIDControllerBuffer extends AbstractControllerBuffer<UUID> {
    public static final ParticleControllerDataBuffer.Id ID =
            new ParticleControllerDataBuffer.Id(new ResourceLocation("reiparticlesapi", "uuid"));

    @Override
    public byte[] encode(UUID value) {
        UUID safe = value == null ? new UUID(0L, 0L) : value;
        return safe.toString().getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public UUID decode(byte[] buf) {
        if (buf.length == 0) {
            return new UUID(0L, 0L);
        }
        return UUID.fromString(new String(buf, StandardCharsets.UTF_8));
    }

    @Override
    public ParticleControllerDataBuffer.Id getBufferID() {
        return ID;
    }
}

