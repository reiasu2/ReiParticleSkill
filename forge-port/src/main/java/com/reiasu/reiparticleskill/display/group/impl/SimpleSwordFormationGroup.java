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
package com.reiasu.reiparticleskill.display.group.impl;

import com.reiasu.reiparticlesapi.display.DisplayEntity;
import com.reiasu.reiparticlesapi.utils.RelativeLocation;
import com.reiasu.reiparticlesapi.utils.builder.PointsBuilder;
import com.reiasu.reiparticleskill.display.BarrageItemDisplay;
import com.reiasu.reiparticleskill.display.group.ServerOnlyDisplayGroup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public final class SimpleSwordFormationGroup extends ServerOnlyDisplayGroup {
    private Vec3 direction;
    private double rotationSpeed;
    private int age;
    private double radius;
    private int count;

    public SimpleSwordFormationGroup(Vec3 pos, Level world, Vec3 direction, double rotationSpeed) {
        super(pos, world);
        this.direction = direction == null ? Vec3.ZERO : direction;
        this.rotationSpeed = rotationSpeed;
        this.radius = 8.0;
        this.count = 12;
    }

    public Vec3 getDirection() {
        return direction;
    }

    public void setDirection(Vec3 direction) {
        this.direction = direction == null ? Vec3.ZERO : direction;
    }

    public double getRotationSpeed() {
        return rotationSpeed;
    }

    public void setRotationSpeed(double rotationSpeed) {
        this.rotationSpeed = rotationSpeed;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = Math.max(0, age);
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = Math.max(0.1, radius);
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = Math.max(3, count);
    }

    @Override
    public Map<Supplier<Object>, RelativeLocation> getDisplayers() {
        Map<Supplier<Object>, RelativeLocation> displayers = new LinkedHashMap<>();
        List<RelativeLocation> points = new PointsBuilder()
                .addCircle(radius, count)
                .create();

        for (RelativeLocation point : points) {
            displayers.put(this::createSwordDisplay, point.copy());
        }
        return displayers;
    }

    @Override
    public void tick() {
        RelativeLocation directionAxis = RelativeLocation.of(direction);
        if (directionAxis.length() < 1.0E-8) {
            directionAxis = new RelativeLocation(0.0, 0.0, 1.0);
        } else {
            directionAxis.normalize();
        }

        for (Object control : getDisplayed().keySet()) {
            if (control instanceof BarrageItemDisplay display) {
                display.rotateToPoint(directionAxis);
            }
        }

        rotateToWithAngle(directionAxis, rotationSpeed);
        age++;

        boolean anyAlive = false;
        for (Object control : getDisplayed().keySet()) {
            if (control instanceof DisplayEntity entity && entity.getValid()) {
                anyAlive = true;
                break;
            }
        }
        if (!anyAlive) {
            remove();
        }
    }

    @Override
    public void onDisplay() {
    }

    private BarrageItemDisplay createSwordDisplay() {
        BarrageItemDisplay display = new BarrageItemDisplay(Vec3.ZERO);
        display.setItem(new ItemStack(Items.IRON_SWORD));

        RelativeLocation lookAt = RelativeLocation.of(direction);
        if (lookAt.length() < 1.0E-8) {
            lookAt = new RelativeLocation(0.0, 0.0, 1.0);
        }
        display.rotateToPoint(lookAt);
        display.setTargetScale(10.0F);
        display.setScaledSpeed(1.0F);
        return display;
    }
}
