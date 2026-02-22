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

import com.reiasu.reiparticlesapi.utils.RelativeLocation;
import com.reiasu.reiparticleskill.display.group.ServerOnlyDisplayGroup;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

public final class SimpleSwordFormationGroupGroup extends ServerOnlyDisplayGroup {
    private Vec3 direction;

    public SimpleSwordFormationGroupGroup(Vec3 pos, Level world, Vec3 direction) {
        super(pos, world);
        this.direction = direction == null ? Vec3.ZERO : direction;
    }

    public Vec3 getDirection() {
        return direction;
    }

    public void setDirection(Vec3 direction) {
        this.direction = direction == null ? Vec3.ZERO : direction;
    }

    @Override
    public Map<Supplier<Object>, RelativeLocation> getDisplayers() {
        Map<Supplier<Object>, RelativeLocation> displayers = new LinkedHashMap<>();
        displayers.put(
                () -> new SimpleSwordFormationGroup(Vec3.ZERO, getWorld(), direction, Math.toRadians(5.625)),
                new RelativeLocation()
        );
        displayers.put(
                () -> {
                    SimpleSwordFormationGroup group = new SimpleSwordFormationGroup(Vec3.ZERO, getWorld(), direction, Math.toRadians(-5.625));
                    group.setRadius(16.0);
                    return group;
                },
                new RelativeLocation()
        );
        displayers.put(
                () -> {
                    SimpleSwordFormationGroup group = new SimpleSwordFormationGroup(Vec3.ZERO, getWorld(), direction, Math.toRadians(2.8125));
                    group.setRadius(32.0);
                    group.setCount(36);
                    return group;
                },
                new RelativeLocation()
        );
        displayers.put(
                () -> {
                    SimpleSwordFormationGroup group = new SimpleSwordFormationGroup(Vec3.ZERO, getWorld(), direction, Math.toRadians(-1.40625));
                    group.setRadius(48.0);
                    group.setCount(12);
                    return group;
                },
                new RelativeLocation()
        );
        return displayers;
    }

    @Override
    public void tick() {
        boolean anyAlive = false;
        for (Object value : getDisplayed().keySet()) {
            if (value instanceof SimpleSwordFormationGroup group && !group.getCanceled()) {
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
}
