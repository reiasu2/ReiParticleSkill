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
package com.reiasu.reiparticleskill.keys;

import com.reiasu.reiparticleskill.ReiParticleSkillForge;
import net.minecraft.resources.ResourceLocation;

public final class SkillKeys {
    public static final ResourceLocation FORMATION_1 = id("formation1");
    public static final ResourceLocation FORMATION_2 = id("formation2");

    private SkillKeys() {
    }

    public static ResourceLocation id(String path) {
        return new ResourceLocation(ReiParticleSkillForge.MOD_ID, path);
    }
}
