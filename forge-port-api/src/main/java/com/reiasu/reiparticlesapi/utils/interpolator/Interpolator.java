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
package com.reiasu.reiparticlesapi.utils.interpolator;

import com.reiasu.reiparticlesapi.utils.RelativeLocation;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

import java.util.List;

/**
 * Interpolator interface for generating refined point lists between inserted control points.
 */
public interface Interpolator {
    double getRefinerCount();

    Interpolator insertPoint(Vec3 vec);

    Interpolator insertPoint(Vector3f vec);

    Interpolator insertPoint(RelativeLocation vec);

    Interpolator setLimit(double limit);

    Interpolator setRefiner(double refiner);

    List<RelativeLocation> getRefinedResult();
}
