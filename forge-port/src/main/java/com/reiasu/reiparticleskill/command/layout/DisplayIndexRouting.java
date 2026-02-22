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
package com.reiasu.reiparticleskill.command.layout;

import java.util.Optional;

public final class DisplayIndexRouting {
    private DisplayIndexRouting() {
    }

    public static Optional<DisplayIndexPlan> plan(int index) {
        return switch (index) {
            case 0 -> Optional.of(new DisplayIndexPlan(index, DisplayIndexKind.EMITTER, Optional.empty()));
            case 1 -> Optional.of(new DisplayIndexPlan(index, DisplayIndexKind.STYLE, Optional.empty()));
            case 2 -> Optional.of(new DisplayIndexPlan(index, DisplayIndexKind.ENTITY, DisplayCommandLayout.profileForIndex(index)));
            case 3 -> Optional.of(new DisplayIndexPlan(index, DisplayIndexKind.DISPLAY, DisplayCommandLayout.profileForIndex(index)));
            case 4 -> Optional.of(new DisplayIndexPlan(index, DisplayIndexKind.GROUP, Optional.empty()));
            case 5 -> Optional.of(new DisplayIndexPlan(index, DisplayIndexKind.COMPOSITION, Optional.empty()));
            case 6 -> Optional.of(new DisplayIndexPlan(index, DisplayIndexKind.DISPLAY, DisplayCommandLayout.profileForIndex(index)));
            default -> Optional.empty();
        };
    }

    public static boolean usesGroupSpawn(int index) {
        return index == 4;
    }

    public static boolean usesCompositionSpawn(int index) {
        return index == 5;
    }

    public static Optional<DisplaySpawnProfile> spawnProfile(int index) {
        return DisplayCommandLayout.profileForIndex(index);
    }
}
