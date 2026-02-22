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
package com.reiasu.reiparticlesapi.network.animation;

import com.reiasu.reiparticlesapi.network.animation.api.AbstractPathMotion;
import com.reiasu.reiparticlesapi.network.particle.style.ParticleGroupStyle;
import net.minecraft.world.phys.Vec3;

/**
 * Path motion targeting a {@link ParticleGroupStyle}. Teleports the style
 * along the computed path. Subclasses implement {@link #pathFunction()}.
 */
public abstract class StylePathMotion extends AbstractPathMotion {
    private final ParticleGroupStyle targetStyle;

    protected StylePathMotion(Vec3 origin, ParticleGroupStyle targetStyle) {
        super(origin);
        this.targetStyle = targetStyle;
    }

    public final ParticleGroupStyle getTargetStyle() {
        return targetStyle;
    }

    @Override
    public void apply(Vec3 actualPos) {
        targetStyle.teleportTo(actualPos);
    }

    @Override
    public boolean checkValid() {
        return !targetStyle.getCanceled();
    }
}
