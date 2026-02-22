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
package com.reiasu.reiparticleskill.util.geom;

public final class RelativeLocation {
    private double x;
    private double y;
    private double z;

    public RelativeLocation() {
        this(0.0, 0.0, 0.0);
    }

    public RelativeLocation(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public RelativeLocation copy() {
        return new RelativeLocation(x, y, z);
    }

    public RelativeLocation add(double dx, double dy, double dz) {
        this.x += dx;
        this.y += dy;
        this.z += dz;
        return this;
    }

    public RelativeLocation add(RelativeLocation other) {
        return add(other.x, other.y, other.z);
    }

    public RelativeLocation subtract(RelativeLocation other) {
        return add(-other.x, -other.y, -other.z);
    }

    public RelativeLocation scale(double factor) {
        this.x *= factor;
        this.y *= factor;
        this.z *= factor;
        return this;
    }

    public RelativeLocation unaryMinus() {
        return new RelativeLocation(-x, -y, -z);
    }

    public double length() {
        return Math.sqrt(x * x + y * y + z * z);
    }

    public RelativeLocation normalize() {
        double len = length();
        if (len == 0.0) {
            return this;
        }
        return scale(1.0 / len);
    }
}
