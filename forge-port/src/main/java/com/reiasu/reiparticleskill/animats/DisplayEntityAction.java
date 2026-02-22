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
import com.reiasu.reiparticlesapi.display.DisplayEntity;
import com.reiasu.reiparticlesapi.display.DisplayEntityManager;

import java.util.Objects;
import java.util.function.Consumer;

public final class DisplayEntityAction extends AnimateAction {
    private final DisplayEntity display;
    private final Consumer<DisplayEntityAction> ticking;
    private boolean firstTick;

    public DisplayEntityAction(DisplayEntity display, Consumer<DisplayEntityAction> ticking) {
        this.display = Objects.requireNonNull(display, "display");
        this.ticking = Objects.requireNonNull(ticking, "ticking");
    }

    public DisplayEntity getDisplay() {
        return display;
    }

    public Consumer<DisplayEntityAction> getTicking() {
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
        return !display.getValid();
    }

    @Override
    public void tick() {
        if (!firstTick) {
            firstTick = true;
            DisplayEntityManager.INSTANCE.spawn(display);
        }
        ticking.accept(this);
    }

    @Override
    public void onStart() {
        firstTick = false;
    }

    @Override
    public void onDone() {
        display.cancel();
        setDone(true);
    }
}
