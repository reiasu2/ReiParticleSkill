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
import com.reiasu.reiparticlesapi.utils.RelativeLocation;

/**
 * Interpolated RelativeLocation value with component-wise lerp.
 */
public final class InterpolatorRelativeLocation implements InterpolatorData<RelativeLocation> {
    private RelativeLocation value;
    private RelativeLocation last;

    public InterpolatorRelativeLocation(RelativeLocation value) {
        this.value = value;
        this.last = value;
    }

    public RelativeLocation getLast() {
        return last;
    }

    public void setLast(RelativeLocation last) {
        this.last = last;
    }

    @Override
    public InterpolatorRelativeLocation update(RelativeLocation current) {
        this.last = this.value;
        this.value = current;
        return this;
    }

    @Override
    public RelativeLocation getWithInterpolator(Number progress) {
        double p = progress.doubleValue();
        return new RelativeLocation(
                GraphMathHelper.lerp(p, last.getX(), value.getX()),
                GraphMathHelper.lerp(p, last.getY(), value.getY()),
                GraphMathHelper.lerp(p, last.getZ(), value.getZ())
        );
    }

    @Override
    public RelativeLocation getCurrent() {
        return value;
    }
}
