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
package com.reiasu.reiparticlesapi.utils.interpolator.emitters;

import com.reiasu.reiparticlesapi.utils.CircularQueue;
import com.reiasu.reiparticlesapi.utils.Math3DUtil;
import com.reiasu.reiparticlesapi.utils.RelativeLocation;
import com.reiasu.reiparticlesapi.utils.interpolator.Interpolator;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Line-segment interpolator for emitter trails.
 * Inserts two control points and fills the line between them.
 */
public final class LineEmitterInterpolator implements Interpolator {
    private double limit = 256.0;
    private final CircularQueue<RelativeLocation> queue = new CircularQueue<>(2);
    private double refinerCount = 1.0;

    @Override
    public double getRefinerCount() {
        return refinerCount;
    }

    public void setRefinerCount(double value) {
        this.refinerCount = value;
    }

    @Override
    public LineEmitterInterpolator insertPoint(Vec3 vec) {
        queue.addFirst(RelativeLocation.of(vec));
        return this;
    }

    @Override
    public LineEmitterInterpolator insertPoint(Vector3f vec) {
        queue.addFirst(new RelativeLocation(vec.x, vec.y, vec.z));
        return this;
    }

    @Override
    public LineEmitterInterpolator insertPoint(RelativeLocation vec) {
        queue.addFirst(vec.copy());
        return this;
    }

    @Override
    public LineEmitterInterpolator setLimit(double limit) {
        this.limit = limit;
        return this;
    }

    @Override
    public LineEmitterInterpolator setRefiner(double refiner) {
        this.refinerCount = Math.max(refiner, 0.001);
        return this;
    }

    @Override
    public List<RelativeLocation> getRefinedResult() {
        if (queue.empty()) {
            return new ArrayList<>();
        }
        if (queue.notNullSize() == 1) {
            return new ArrayList<>(Collections.singletonList(queue.get(0)));
        }
        if (queue.get(0).distance(queue.get(1)) > limit) {
            return new ArrayList<>(Collections.singletonList(queue.get(1)));
        }
        return Math3DUtil.INSTANCE.fillLine(queue.get(0), queue.get(1), getRefinerCount());
    }
}
