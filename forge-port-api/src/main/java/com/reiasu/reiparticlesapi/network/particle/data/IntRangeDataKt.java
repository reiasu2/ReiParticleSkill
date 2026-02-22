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
package com.reiasu.reiparticlesapi.network.particle.data;

/**
 * Utility methods for {@link IntRangeData}, ported from Kotlin extension functions.
 */
public final class IntRangeDataKt {

    private IntRangeDataKt() {
    }

    /**
     * Checks whether the given integer falls within the range (inclusive on both ends).
     */
    public static boolean isIn(int value, IntRangeData range) {
        return value >= range.getMin() && value <= range.getMax();
    }

    /**
     * Creates an {@link IntRangeData} where the receiver is the min.
     */
    public static IntRangeData minRangeTo(int min, int max) {
        return new IntRangeData(min, max);
    }

    /**
     * Creates an {@link IntRangeData} where the receiver is the max.
     */
    public static IntRangeData maxRangeTo(int max, int min) {
        return new IntRangeData(min, max);
    }
}
