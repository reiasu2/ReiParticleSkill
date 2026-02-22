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
import org.joml.Vector3f;

/**
 * Interpolated Vector3f value with component-wise lerp.
 */
public final class InterpolatorVector3f implements InterpolatorData<Vector3f> {
    private Vector3f value;
    private Vector3f last;

    public InterpolatorVector3f(Vector3f value) {
        this.value = value;
        this.last = new Vector3f(value);
    }

    public Vector3f getLast() {
        return last;
    }

    public void setLast(Vector3f last) {
        this.last = last;
    }

    @Override
    public InterpolatorVector3f update(Vector3f current) {
        this.last = this.value;
        this.value = current;
        return this;
    }

    @Override
    public Vector3f getWithInterpolator(Number progress) {
        double p = progress.doubleValue();
        return new Vector3f(
                (float) GraphMathHelper.lerp(p, last.x, value.x),
                (float) GraphMathHelper.lerp(p, last.y, value.y),
                (float) GraphMathHelper.lerp(p, last.z, value.z)
        );
    }

    @Override
    public Vector3f getCurrent() {
        return value;
    }
}
