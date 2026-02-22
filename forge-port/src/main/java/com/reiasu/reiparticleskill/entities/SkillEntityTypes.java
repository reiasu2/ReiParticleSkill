/*
 * Copyright (C) 2025 Reiasu
 *
 * This file is part of ReiParticleSkill.
 *
 * ReiParticleSkill is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, version 3 of the License.
 *
 * ReiParticleSkill is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with ReiParticleSkill. If not, see <https://www.gnu.org/licenses/>.
 */
// SPDX-License-Identifier: LGPL-3.0-only
package com.reiasu.reiparticleskill.entities;

import com.reiasu.reiparticleskill.ReiParticleSkillForge;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class SkillEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, ReiParticleSkillForge.MOD_ID);

    public static final RegistryObject<EntityType<BarrageItemEntity>> BARRAGE_ITEM =
            ENTITIES.register("barrage_item", () ->
                    EntityType.Builder.<BarrageItemEntity>of(BarrageItemEntity::new, MobCategory.MISC)
                            .sized(0.4F, 0.4F)
                            .clientTrackingRange(64)
                            .updateInterval(1)
                            .build(ReiParticleSkillForge.MOD_ID + ":barrage_item"));

    private SkillEntityTypes() {
    }

    public static void register(IEventBus bus) {
        ENTITIES.register(bus);
    }
}
