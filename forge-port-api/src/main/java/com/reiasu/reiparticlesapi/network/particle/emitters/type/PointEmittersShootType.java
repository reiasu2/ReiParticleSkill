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
package com.reiasu.reiparticlesapi.network.particle.emitters.type;

import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Shoot type that places all particles at the emitter origin.
 * If the base direction is (near-)zero, a random direction is assigned instead.
 */
public final class PointEmittersShootType implements EmittersShootType {

    public static final String ID = "point";

    private final Random random = new Random(System.currentTimeMillis());

    @Override
    public String getID() {
        return ID;
    }

    @Override
    public List<Vec3> getPositions(Vec3 origin, int tick, int count) {
        List<Vec3> result = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            result.add(origin);
        }
        return result;
    }

    @Override
    public Vec3 getDefaultDirection(Vec3 enter, int tick, Vec3 pos, Vec3 origin) {
        if (enter.lengthSqr() < 1.0E-7) {
            return new Vec3(
                    random.nextDouble(-1.0, 1.0),
                    random.nextDouble(-1.0, 1.0),
                    random.nextDouble(-1.0, 1.0)
            );
        }
        return enter;
    }
}
