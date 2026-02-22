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

import java.util.Objects;

public interface ParticleControllerDataBuffer<T> {
    T getLoadedValue();

    void setLoadedValue(T value);

    byte[] encode(T value);

    default byte[] encode() {
        T value = getLoadedValue();
        if (value == null) {
            return new byte[0];
        }
        return encode(value);
    }

    T decode(byte[] buf);

    Id getBufferID();

    record Id(ResourceLocation value) {
        public Id {
            Objects.requireNonNull(value, "value");
        }

        public static Id toID(String string) {
            Objects.requireNonNull(string, "string");
            String[] split = string.split(":", 2);
            if (split.length != 2 || split[0].isBlank() || split[1].isBlank()) {
                throw new IllegalArgumentException("Invalid ID format: " + string);
            }
            return new Id(new ResourceLocation(split[0], split[1]));
        }
    }
}

