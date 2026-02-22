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

import com.reiasu.reiparticlesapi.network.buffer.ParticleControllerDataBuffer;
import com.reiasu.reiparticlesapi.network.particle.style.ParticleGroupStyle;
import com.reiasu.reiparticlesapi.particles.Controllable;
import com.reiasu.reiparticlesapi.utils.helper.StatusHelper;

import java.util.HashMap;
import java.util.Map;

public final class StyleStatusHelper extends StatusHelper {
    private ParticleGroupStyle group;
    private boolean init;

    public ParticleGroupStyle getGroup() {
        return group;
    }

    public void setGroup(ParticleGroupStyle group) {
        this.group = group;
    }

    @Override
    public void changeStatus(int status) {
        if (group == null || group.getClient()) {
            return;
        }
        Map<String, ParticleControllerDataBuffer<?>> args = new HashMap<>();
        for (ArgPair pair : toArgsPairs()) {
            args.put(pair.name(), pair.value());
        }
        group.change(args);
    }

    @Override
    public void initHelper() {
        if (group == null || init) {
            return;
        }
        init = true;
        group.addPreTickAction(style -> {
            if (getDisplayStatus() != Status.DISABLE.id()) {
                return;
            }
            setCurrent(getCurrent() + 1);
            if (getCurrent() >= getClosedInternal()) {
                style.remove();
            }
        });
    }

    @Override
    public void loadController(Controllable<?> controller) {
        if (!(controller instanceof ParticleGroupStyle particleGroupStyle)) {
            return;
        }
        this.group = particleGroupStyle;
        initHelper();
    }
}
