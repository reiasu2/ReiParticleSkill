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
package com.reiasu.reiparticleskill.animats;

import com.reiasu.reiparticlesapi.animation.AnimateAction;
import com.reiasu.reiparticlesapi.network.particle.style.ParticleGroupStyle;
import com.reiasu.reiparticlesapi.network.particle.style.ParticleStyleManager;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.Objects;
import java.util.function.Consumer;

public final class StyleAction extends AnimateAction {
    private final ParticleGroupStyle style;
    private final Level spawnWorld;
    private final Vec3 spawnPos;
    private final Consumer<StyleAction> ticking;
    private boolean firstTick;

    public StyleAction(ParticleGroupStyle style, Level spawnWorld, Vec3 spawnPos, Consumer<StyleAction> ticking) {
        this.style = Objects.requireNonNull(style, "style");
        this.spawnWorld = Objects.requireNonNull(spawnWorld, "spawnWorld");
        this.spawnPos = Objects.requireNonNull(spawnPos, "spawnPos");
        this.ticking = Objects.requireNonNull(ticking, "ticking");
    }

    public ParticleGroupStyle getStyle() {
        return style;
    }

    public Level getSpawnWorld() {
        return spawnWorld;
    }

    public Vec3 getSpawnPos() {
        return spawnPos;
    }

    public Consumer<StyleAction> getTicking() {
        return ticking;
    }

    public boolean getFirstTick() {
        return firstTick;
    }

    public void setFirstTick(boolean firstTick) {
        this.firstTick = firstTick;
    }

    @Override
    public boolean checkDone() {
        return style.getCanceled();
    }

    @Override
    public void tick() {
        if (!firstTick) {
            firstTick = true;
            ParticleStyleManager.spawnStyle(spawnWorld, spawnPos, style);
        }
        ticking.accept(this);
    }

    @Override
    public void onStart() {
        firstTick = false;
    }

    @Override
    public void onDone() {
        style.remove();
        setDone(true);
    }
}
