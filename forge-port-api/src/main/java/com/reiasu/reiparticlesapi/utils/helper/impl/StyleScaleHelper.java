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
import com.reiasu.reiparticlesapi.utils.helper.ScaleHelper;

public final class StyleScaleHelper extends ScaleHelper {
    private ParticleGroupStyle group;

    public StyleScaleHelper(double minScale, double maxScale, int scaleTick) {
        super(minScale, maxScale, scaleTick);
    }

    public ParticleGroupStyle getGroup() {
        return group;
    }

    public void setGroup(ParticleGroupStyle group) {
        this.group = group;
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
