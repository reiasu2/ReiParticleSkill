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
package com.reiasu.reiparticlesapi.particles;

import com.reiasu.reiparticlesapi.particles.impl.*;
import net.minecraft.world.level.block.Blocks;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Registry and factory for {@link ControllableParticleEffect} instances.
 * Each effect type is registered with a prototype that is cloned on demand.
 */
public final class ControllableParticleEffectManager {
    public static final ControllableParticleEffectManager INSTANCE = new ControllableParticleEffectManager();

    private static final Map<Class<? extends ControllableParticleEffect>, ControllableParticleEffect> buffer =
            new LinkedHashMap<>();

    private ControllableParticleEffectManager() {}

    public void register(ControllableParticleEffect effect) {
        buffer.put(effect.getClass(), effect.clone());
    }

    public ControllableParticleEffect createWithUUID(UUID uuid, Class<? extends ControllableParticleEffect> type) {
        ControllableParticleEffect prototype = buffer.get(type);
        if (prototype == null) {
            throw new IllegalArgumentException("No registered effect for type: " + type.getName());
        }
        ControllableParticleEffect instance = prototype.clone();
        instance.setControlUUID(uuid);
        return instance;
    }

    public void init() {
        // Called during mod initialization to ensure static block has run
    }

    static {
        INSTANCE.register(new ControllableCloudEffect(UUID.randomUUID(), false));
        INSTANCE.register(new ControllableEnchantmentEffect(UUID.randomUUID(), false));
        INSTANCE.register(new ControllableFireworkEffect(UUID.randomUUID(), false));
        INSTANCE.register(new ControllableFlashEffect(UUID.randomUUID(), false));
        INSTANCE.register(new ControllableEndRodEffect(UUID.randomUUID(), false));
        INSTANCE.register(new ControllableFallingDustEffect(UUID.randomUUID(),
                Blocks.SAND.defaultBlockState(), false));
        INSTANCE.register(new ControllableSplashEffect(UUID.randomUUID(), false));
    }
}
