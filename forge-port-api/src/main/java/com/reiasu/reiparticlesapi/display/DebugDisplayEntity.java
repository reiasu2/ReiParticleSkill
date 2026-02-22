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
package com.reiasu.reiparticlesapi.display;

import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import org.joml.Vector3f;

public class DebugDisplayEntity extends DisplayEntity {
    private final double x;
    private final double y;
    private final double z;
    private final String kind;
    private final int maxTicks;
    private int tick;
    private static final DustParticleOptions COLOR =
            new DustParticleOptions(new Vector3f(0.95f, 0.75f, 0.25f), 1.0f);

    public DebugDisplayEntity(double x, double y, double z, String kind) {
        this(null, x, y, z, kind, 80);
    }

    public DebugDisplayEntity(ServerLevel level, double x, double y, double z, String kind) {
        this(level, x, y, z, kind, 80);
    }

    public DebugDisplayEntity(ServerLevel level, double x, double y, double z, String kind, int maxTicks) {
        bindLevel(level);
        this.x = x;
        this.y = y;
        this.z = z;
        this.kind = kind;
        this.maxTicks = Math.max(1, maxTicks);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public String getKind() {
        return kind;
    }

    @Override
    public void tick() {
        if (!getValid()) {
            return;
        }
        ServerLevel level = level();
        if (level != null) {
            double ringRadius = 0.4 + tick * 0.02;
            int points = "group".equalsIgnoreCase(kind) ? 8 : 5;
            for (int i = 0; i < points; i++) {
                double t = (Math.PI * 2.0 * i) / points + tick * 0.12;
                double px = x + Math.cos(t) * ringRadius;
                double py = y + 0.1 + Math.sin(t * 2.0) * 0.04;
                double pz = z + Math.sin(t) * ringRadius;
                level.sendParticles(COLOR, px, py, pz, 1, 0.0, 0.0, 0.0, 0.0);
            }
            if ("group".equalsIgnoreCase(kind)) {
                level.sendParticles(ParticleTypes.END_ROD, x, y + 0.2, z, 2, 0.12, 0.16, 0.12, 0.01);
            }
        }
        tick++;
        if (tick >= maxTicks) {
            cancel();
        }
    }
}
