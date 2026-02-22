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
package com.reiasu.reiparticlesapi.utils.interpolator.data;

import com.reiasu.reiparticlesapi.utils.GraphMathHelper;

/**
 * Interpolated double value with lerp support.
 */
public final class InterpolatorDouble implements InterpolatorData<Double> {
    private double value;
    private double last;

    public InterpolatorDouble(double value) {
        this.value = value;
    }

    public double getLast() {
        return last;
    }

    public void setLast(double last) {
        this.last = last;
    }

    public InterpolatorDouble update(double current) {
        this.last = this.value;
        this.value = current;
        return this;
    }

    @Override
    public InterpolatorDouble update(Double current) {
        return update(current.doubleValue());
    }

    @Override
    public Double getWithInterpolator(Number progress) {
        return GraphMathHelper.lerp(progress.doubleValue(), last, value);
    }

    @Override
    public Double getCurrent() {
        return value;
    }

    // Operator-style methods

    public InterpolatorDouble plus(double d) {
        update(value + d);
        return this;
    }

    public InterpolatorDouble minus(double d) {
        update(value - d);
        return this;
    }

    public InterpolatorDouble times(double d) {
        update(value * d);
        return this;
    }

    public InterpolatorDouble div(double d) {
        if (d == 0.0) {
            throw new IllegalArgumentException("Division by zero");
        }
        update(value / d);
        return this;
    }

    public InterpolatorDouble unaryMinus() {
        update(-value);
        return this;
    }

    public InterpolatorDouble plus(InterpolatorDouble other) {
        update(value + other.value);
        return this;
    }

    public InterpolatorDouble minus(InterpolatorDouble other) {
        update(value - other.value);
        return this;
    }

    public InterpolatorDouble times(InterpolatorDouble other) {
        update(value * other.value);
        return this;
    }

    public InterpolatorDouble div(InterpolatorDouble other) {
        if (other.value == 0.0) {
            throw new IllegalArgumentException("Division by zero");
        }
        update(value / other.value);
        return this;
    }
}
