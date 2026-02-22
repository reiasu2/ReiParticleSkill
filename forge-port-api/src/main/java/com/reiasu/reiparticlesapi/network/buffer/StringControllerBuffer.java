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

public final class StringControllerBuffer extends AbstractControllerBuffer<String> {
    public static final ParticleControllerDataBuffer.Id ID =
            new ParticleControllerDataBuffer.Id(new ResourceLocation("reiparticlesapi", "string"));

    @Override
    public byte[] encode(String value) {
        return value == null ? new byte[0] : value.getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public String decode(byte[] buf) {
        return new String(buf, StandardCharsets.UTF_8);
    }

    @Override
    public ParticleControllerDataBuffer.Id getBufferID() {
        return ID;
    }
}

