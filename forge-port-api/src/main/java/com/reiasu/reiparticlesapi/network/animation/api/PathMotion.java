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
package com.reiasu.reiparticlesapi.network.animation.api;

import net.minecraft.world.phys.Vec3;

/**
 * Interface defining path-based motion for particle entities.
 * Implementations compute per-tick offsets from an origin, and apply
 * the resulting world position to their target.
 */
public interface PathMotion {
    int getCurrentTick();

    void setCurrentTick(int tick);

    Vec3 getOrigin();

    void setOrigin(Vec3 origin);

    /**
     * Apply the computed world position to the motion target
     * (e.g. teleport a style/emitter to this position).
     */
    void apply(Vec3 actualPos);

    /**
     * Compute and return the next path offset from origin.
     * This also advances the internal tick counter.
     */
    Vec3 next();

    /**
     * Check whether the motion target is still alive/valid.
     * Returns false if the target has been removed or cancelled.
     */
    boolean checkValid();
}
