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
package com.reiasu.reiparticlesapi.particles;

import com.reiasu.reiparticlesapi.network.buffer.ParticleControllerDataBuffer;
import com.reiasu.reiparticlesapi.utils.RelativeLocation;
import net.minecraft.world.phys.Vec3;

import java.util.Map;
import java.util.UUID;

/**
 * Base interface for objects that can be remotely controlled (particles, compositions, etc.).
 *
 * @param <T> the type of the underlying controlled object
 */
public interface Controllable<T> {

    /**
     * The unique control UUID for this controllable.
     */
    UUID controlUUID();

    /**
     * Rotate this controllable to face the given point.
     */
    void rotateToPoint(RelativeLocation to);

    /**
     * Rotate this controllable to face the given point with an additional angle offset.
     */
    void rotateToWithAngle(RelativeLocation to, double radian);

    /**
     * Rotate this controllable around its axis by the given angle.
     */
    void rotateAsAxis(double radian);

    /**
     * Teleport this controllable to the given position.
     */
    void teleportTo(Vec3 pos);

    /**
     * Teleport this controllable to the given coordinates.
     */
    void teleportTo(double x, double y, double z);

    /**
     * Remove/destroy this controllable.
     */
    void remove();

    /**
     * Get the underlying controlled object.
     */
    T getControlObject();

    /**
     * Get the controlled object cast to type S.
     */
    @SuppressWarnings("unchecked")
    default <S> S getControlCasted() {
        return (S) getControlObject();
    }

    /**
     * Get the controlled object cast to type S, or null if the cast fails.
     */
    @SuppressWarnings("unchecked")
    default <S> S getControlCastedOrNull() {
        try {
            return (S) getControlObject();
        } catch (Exception e) {
            return null;
        }
    }

    // ---- Buffer-based load/change (used by composition system) ----

    default void load(Map<String, ? extends ParticleControllerDataBuffer<?>> args) {
    }

    default Map<String, ParticleControllerDataBuffer<?>> toArgs() {
        return Map.of();
    }

    default void change(Map<String, ? extends ParticleControllerDataBuffer<?>> args) {
        load(args);
    }
}
