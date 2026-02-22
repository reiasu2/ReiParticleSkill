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
package com.reiasu.reiparticleskill.util.geom;

public final class GraphMathHelper {
    private GraphMathHelper() {
    }

    public static float lerp(float alpha, float from, float to) {
        return from + (to - from) * alpha;
    }

    public static double lerp(double alpha, double from, double to) {
        return from + (to - from) * alpha;
    }

    public static double inverseLerp(double value, double min, double max) {
        if (max == min) {
            return 0.0;
        }
        return (value - min) / (max - min);
    }

    public static double smoothstep(double edge0, double edge1, double x) {
        double t = Math3DUtil.INSTANCE.clamp(inverseLerp(x, edge0, edge1), 0.0, 1.0);
        return t * t * (3.0 - 2.0 * t);
    }
}
