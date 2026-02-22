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
 * Abstract base class for a min-max range of comparable values.
 * Enforces the invariant that {@code min <= max} at construction time.
 *
 * @param <T> the value type, must be self-comparable
 */
public abstract class RangeData<T extends Comparable<? super T>> {

    private T min;
    private T max;

    protected RangeData(T min, T max) {
        if (min.compareTo(max) > 0) {
            throw new IllegalArgumentException("min must be <= " + max);
        }
        this.min = min;
        this.max = max;
    }

    public T getMin() {
        return min;
    }

    public void setMin(T min) {
        this.min = min;
    }

    public T getMax() {
        return max;
    }

    public void setMax(T max) {
        this.max = max;
    }
}
