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
package com.reiasu.reiparticlesapi.animation.timeline;

/**
 * Animates an angle value over a given duration using an easing function.
 * Provides delta-based progress for both "glow" (forward) and "fade" (reverse) animations.
 */
public final class AngleAnimator {
    private final int durationTicks;
    private final double targetAngle;
    private final Ease ease;
    private int tick;
    private double lastAngle;
    private boolean finished;

    public AngleAnimator(int durationTicks, double targetAngle, Ease ease) {
        this.durationTicks = durationTicks;
        this.targetAngle = targetAngle;
        this.ease = ease;
    }

    public AngleAnimator(int durationTicks, double targetAngle) {
        this(durationTicks, targetAngle, Eases.linear);
    }

    public boolean getFinished() {
        return this.finished;
    }

    /**
     * Returns the angle delta for a forward (growing) animation step.
     */
    public double glowDelta() {
        if (this.finished) {
            return 0.0;
        }
        this.tick++;
        double t = clamp01((double) this.tick / (double) this.durationTicks);
        double eased = this.ease.cal(t);
        double angleNow = this.targetAngle * eased;
        double delta = angleNow - this.lastAngle;
        this.lastAngle = angleNow;
        if (this.tick >= this.durationTicks) {
            this.finished = true;
        }
        return delta;
    }

    /**
     * Returns the angle delta for a reverse (fading) animation step.
     */
    public double fadeDelta() {
        if (this.finished && this.tick >= this.durationTicks) {
            this.finished = false;
            this.tick = 0;
            this.lastAngle = this.targetAngle;
        } else if (this.finished) {
            return 0.0;
        }
        this.tick++;
        double t = clamp01((double) this.tick / (double) this.durationTicks);
        double eased = this.ease.cal(1.0 - t);
        double angleNow = this.targetAngle * eased;
        double delta = angleNow - this.lastAngle;
        this.lastAngle = angleNow;
        if (this.tick >= this.durationTicks) {
            this.finished = true;
        }
        return delta;
    }

    public void reset() {
        this.tick = 0;
        this.lastAngle = 0.0;
        this.finished = false;
    }

    private static double clamp01(double value) {
        return Math.max(0.0, Math.min(1.0, value));
    }
}
