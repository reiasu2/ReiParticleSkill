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
package com.reiasu.reiparticleskill.display.group.layout;

import com.reiasu.reiparticleskill.util.geom.PointsBuilder;
import com.reiasu.reiparticleskill.util.geom.RelativeLocation;

import java.util.List;

public final class SimpleSwordFormationLayout {
    private RelativeLocation direction;
    private final double rotationSpeed;
    private int age;
    private double radius;
    private int count;
    private double phase;

    public SimpleSwordFormationLayout(RelativeLocation direction, double rotationSpeed) {
        this.direction = direction.copy();
        this.rotationSpeed = rotationSpeed;
        this.radius = 8.0;
        this.count = 12;
        this.phase = 0.0;
    }

    public RelativeLocation getDirection() {
        return direction.copy();
    }

    public void setDirection(RelativeLocation direction) {
        this.direction = direction.copy();
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getAge() {
        return age;
    }

    public void tick() {
        age++;
        phase += rotationSpeed;
    }

    public List<RelativeLocation> currentOffsets() {
        return new PointsBuilder()
                .addCircle(radius, count)
                .rotateAsAxis(phase)
                .create();
    }

    public static SimpleSwordFormationLayout fromSpec(RelativeLocation direction, FormationLayerSpec spec) {
        SimpleSwordFormationLayout layout = new SimpleSwordFormationLayout(direction, spec.rotationSpeed());
        layout.setRadius(spec.radius());
        layout.setCount(spec.count());
        return layout;
    }
}