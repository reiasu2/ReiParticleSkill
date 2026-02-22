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

import java.util.Objects;
import java.util.function.Consumer;

public final class TickingAction extends AnimateAction {
    private final int maxTick;
    private final Consumer<TickingAction> ticking;
    private boolean firstTick;
    private boolean canceled;

    public TickingAction(int maxTick, Consumer<TickingAction> ticking) {
        this.maxTick = maxTick;
        this.ticking = Objects.requireNonNull(ticking, "ticking");
    }

    public int getMaxTick() {
        return maxTick;
    }

    public Consumer<TickingAction> getTicking() {
        return ticking;
    }

    public boolean getFirstTick() {
        return firstTick;
    }

    public void setFirstTick(boolean firstTick) {
        this.firstTick = firstTick;
    }

    public boolean getCanceled() {
        return canceled;
    }

    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }

    @Override
    public boolean checkDone() {
        return canceled;
    }

    @Override
    public void tick() {
        ticking.accept(this);
        if (!firstTick) {
            firstTick = true;
        }
        if (maxTick != -1 && getTickCount() >= maxTick) {
            onDone();
        }
    }

    @Override
    public void onStart() {
        firstTick = false;
        canceled = false;
    }

    @Override
    public void onDone() {
        canceled = true;
        setDone(true);
    }

    @Override
    public void cancel() {
        super.cancel();
        canceled = true;
    }
}
