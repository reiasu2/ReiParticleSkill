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
package com.reiasu.reiparticlesapi.utils.presets;

import com.reiasu.reiparticlesapi.utils.builder.FourierSeriesBuilder;

/**
 * Pre-built Fourier series configurations for common geometric shapes.
 */
public final class FourierPresets {
    public static final FourierPresets INSTANCE = new FourierPresets();

    private FourierPresets() {
    }

    public FourierSeriesBuilder pentagon() {
        return new FourierSeriesBuilder()
                .addFourier(3.0, 2.0, 0.0)
                .addFourier(1.0, -8.0, 0.0)
                .addFourier(3.0, 2.0, 0.0);
    }

    public FourierSeriesBuilder clover() {
        return new FourierSeriesBuilder()
                .addFourier(3.0, 2.0, 0.0)
                .addFourier(1.0, -8.0, 0.0)
                .addFourier(-3.0, 2.0, 0.0)
                .addFourier(6.0, -2.0, 0.0);
    }

    public FourierSeriesBuilder boomerang() {
        return new FourierSeriesBuilder()
                .addFourier(3.0, 1.0, 0.0)
                .addFourier(7.0, -2.0, 0.0)
                .addFourier(2.0, 4.0, 0.0);
    }

    public FourierSeriesBuilder runesOnAllSides() {
        return new FourierSeriesBuilder()
                .addFourier(3.0, -1.0, 0.0)
                .addFourier(7.0, -5.0, 0.0)
                .addFourier(2.0, 11.0, 0.0);
    }

    public FourierSeriesBuilder knot() {
        return new FourierSeriesBuilder()
                .addFourier(3.0, -1.0, 0.0)
                .addFourier(0.0, -5.0, 0.0)
                .addFourier(4.0, 11.0, 0.0)
                .addFourier(4.0, -4.0, 0.0);
    }

    public FourierSeriesBuilder circlesAndTriangles() {
        return new FourierSeriesBuilder()
                .addFourier(3.0, -1.0, 0.0)
                .addFourier(0.0, -5.0, 0.0)
                .addFourier(4.0, 11.0, 0.0)
                .addFourier(5.0, 2.0, 0.0);
    }

    public FourierSeriesBuilder bowsOnAllSides() {
        return new FourierSeriesBuilder()
                .addFourier(3.0, -1.0, 0.0)
                .addFourier(-3.0, -5.0, 0.0)
                .addFourier(4.0, 11.0, 0.0)
                .addFourier(5.0, 3.0, 0.0)
                .addFourier(-3.0, -5.0, 0.0)
                .addFourier(1.0, 3.0, 0.0);
    }

    public FourierSeriesBuilder rhombic() {
        return new FourierSeriesBuilder()
                .addFourier(-3.0, 1.0, 0.0)
                .addFourier(-9.0, -3.0, 0.0)
                .addFourier(8.0, -3.0, 0.0);
    }
}
