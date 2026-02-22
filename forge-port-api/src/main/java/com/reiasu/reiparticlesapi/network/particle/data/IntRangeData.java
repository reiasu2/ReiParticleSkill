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

import java.util.concurrent.ThreadLocalRandom;

/**
 * A range of integer values with random sampling support.
 * <p>
 * The {@link #random()} method returns a value in {@code [min, max)} following
 * the original Kotlin Random.nextInt(min, max) semantics.
 */
public final class IntRangeData extends RangeData<Integer> {

    public IntRangeData(int min, int max) {
        super(min, max);
    }

    /**
     * Returns a random integer in {@code [min, max)}.
     */
    public int random() {
        return ThreadLocalRandom.current().nextInt(getMin(), getMax());
    }
}
