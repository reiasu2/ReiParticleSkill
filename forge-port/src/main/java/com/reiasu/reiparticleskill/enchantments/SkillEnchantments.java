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
package com.reiasu.reiparticleskill.enchantments;

import com.reiasu.reiparticleskill.ReiParticleSkillForge;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class SkillEnchantments {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS =
            DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, ReiParticleSkillForge.MOD_ID);

    public static final RegistryObject<Enchantment> SWORD_LIGHT =
            ENCHANTMENTS.register("sword_light", SwordLightEnchantment::new);

    private SkillEnchantments() {
    }

    public static void register(IEventBus bus) {
        ENCHANTMENTS.register(bus);
    }

    public static int getSwordLightLevel(ItemStack stack) {
        if (stack == null || stack.isEmpty() || !SWORD_LIGHT.isPresent()) {
            return 0;
        }
        return EnchantmentHelper.getItemEnchantmentLevel(SWORD_LIGHT.get(), stack);
    }
}
