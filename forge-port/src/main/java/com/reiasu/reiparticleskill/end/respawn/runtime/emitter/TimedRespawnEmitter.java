/*
 * Copyright (C) 2025 Reiasu
 *
 * This file is part of ReiParticleSkill.
 *
 * ReiParticleSkill is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, version 3 of the License.
 *
 * ReiParticleSkill is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with ReiParticleSkill. If not, see <https://www.gnu.org/licenses/>.
 */
// SPDX-License-Identifier: LGPL-3.0-only
package com.reiasu.reiparticleskill.end.respawn.runtime.emitter;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.phys.Vec3;

public abstract class TimedRespawnEmitter implements RespawnEmitter {
    private final int maxTicks;
    private int tick;
    private boolean forceDone;

    protected TimedRespawnEmitter(int maxTicks) {
        this.maxTicks = Math.max(1, maxTicks);
    }

    @Override
    public final int tick(ServerLevel level, Vec3 center) {
        if (done()) {
            return 0;
        }
        int emitted = emit(level, center, tick);
        if (shouldStop(level, center, tick)) {
            forceDone = true;
        }
        tick++;
        return emitted;
    }

    protected abstract int emit(ServerLevel level, Vec3 center, int tick);

    protected boolean shouldStop(ServerLevel level, Vec3 center, int tick) {
        return false;
    }

    @Override
    public final boolean done() {
        return forceDone || tick >= maxTicks;
    }
}
