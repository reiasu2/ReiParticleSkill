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
package com.reiasu.reiparticleskill.datagen;

import com.reiasu.reiparticleskill.ReiParticleSkillForge;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

public final class SkillLanguageProvider extends LanguageProvider {
    private final String locale;

    public SkillLanguageProvider(PackOutput output, String locale) {
        super(output, ReiParticleSkillForge.MOD_ID, locale);
        this.locale = locale;
    }

    @Override
    protected void addTranslations() {
        if ("zh_cn".equals(locale)) {
            add("enchantment.reiparticleskill.sword_light", "剑气");
            add("key.reiparticleskill.category", "粒子技能");
            add("key.reiparticleskill.formation1", "剑阵1");
            add("key.reiparticleskill.formation2", "剑阵2");
            add("sounds.reiparticleskill.sword_formation", "剑阵");
            return;
        }

        add("enchantment.reiparticleskill.sword_light", "Sword Light");
        add("key.reiparticleskill.category", "ReiParticleSkill");
        add("key.reiparticleskill.formation1", "Formation I");
        add("key.reiparticleskill.formation2", "Formation II");
        add("sounds.reiparticleskill.sword_formation", "Sword Formation");
    }
}
