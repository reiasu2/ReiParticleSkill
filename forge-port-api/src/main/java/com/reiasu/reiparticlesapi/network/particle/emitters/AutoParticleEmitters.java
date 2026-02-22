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
package com.reiasu.reiparticlesapi.network.particle.emitters;

import net.minecraft.resources.ResourceLocation;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * Base class for emitters registered via {@code @ReiAutoRegister}.
 * <p>
 * Convention: subclasses declare
 * {@code public static final ResourceLocation CODEC_ID = ...;}
 * This constructor auto-reads that field and calls
 * {@link #setEmittersID(ResourceLocation)} so every instance is
 * immediately sync-ready without manual wiring.
 */
public class AutoParticleEmitters extends ParticleEmitters {

    protected AutoParticleEmitters() {
        ResourceLocation id = resolveCodecId(getClass());
        if (id != null) {
            setEmittersID(id);
        }
    }

    private static ResourceLocation resolveCodecId(Class<?> clazz) {
        try {
            Field f = clazz.getDeclaredField("CODEC_ID");
            if (Modifier.isStatic(f.getModifiers())
                    && ResourceLocation.class.isAssignableFrom(f.getType())) {
                f.setAccessible(true);
                return (ResourceLocation) f.get(null);
            }
        } catch (NoSuchFieldException | IllegalAccessException ignored) {
        }
        return null;
    }
}