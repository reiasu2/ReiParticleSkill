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
package com.reiasu.reiparticlesapi.utils.helper.impl;

import com.reiasu.reiparticlesapi.network.particle.style.ParticleGroupStyle;
import com.reiasu.reiparticlesapi.particles.Controllable;
import com.reiasu.reiparticlesapi.utils.RelativeLocation;
import com.reiasu.reiparticlesapi.utils.helper.BezierValueScaleHelper;

public final class StyleBezierValueScaleHelper extends BezierValueScaleHelper {
    private ParticleGroupStyle group;

    public StyleBezierValueScaleHelper(
            int scaleTick,
            double minScale,
            double maxScale,
            RelativeLocation controlPoint1,
            RelativeLocation controlPoint2
    ) {
        super(scaleTick, minScale, maxScale, controlPoint1, controlPoint2);
    }

    @Override
    public void loadController(Controllable<?> controller) {
        if (!(controller instanceof ParticleGroupStyle particleGroupStyle)) {
            return;
        }
        this.group = particleGroupStyle;
        particleGroupStyle.scale(getMinScale());
    }

    @Override
    public Controllable<?> getLoadedGroup() {
        return group;
    }

    @Override
    public double getGroupScale() {
        return group == null ? getMinScale() : group.getScale();
    }

    @Override
    public void scale(double scale) {
        if (group == null) {
            return;
        }
        double clamped = Math.max(getMinScale(), Math.min(getMaxScale(), scale));
        group.scale(clamped);
    }
}
