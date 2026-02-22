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
package com.reiasu.reiparticleskill.display.group.layout;

import com.reiasu.reiparticleskill.util.geom.RelativeLocation;

import java.util.List;

public final class SwordFormationLayerPresets {
    private SwordFormationLayerPresets() {
    }

    public static List<FormationLayerSpec> defaultLayerSpecs() {
        return List.of(
                new FormationLayerSpec(8.0, 12, 0.09817477042468103),
                new FormationLayerSpec(16.0, 12, -0.09817477042468103),
                new FormationLayerSpec(32.0, 36, 0.04908738521234052),
                new FormationLayerSpec(48.0, 12, -0.02454369260617026)
        );
    }

    public static List<SimpleSwordFormationLayout> createDefaultLayouts(RelativeLocation direction) {
        return defaultLayerSpecs().stream()
                .map(spec -> SimpleSwordFormationLayout.fromSpec(direction, spec))
                .toList();
    }
}