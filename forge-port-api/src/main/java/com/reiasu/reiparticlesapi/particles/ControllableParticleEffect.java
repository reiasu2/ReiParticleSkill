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

import net.minecraft.core.particles.ParticleOptions;

import java.util.UUID;

/**
 * Marker interface for controllable particle effects.
 * <p>
 * Extends {@link ParticleOptions} so implementations can be used with
 * {@link net.minecraft.client.particle.ParticleProvider} factories.
 * The actual serialization is handled by the ReiParticles custom network,
 * so {@link ParticleOptions} methods are implemented with defaults in
 * each concrete class.
 */
public interface ControllableParticleEffect extends ParticleOptions {
    UUID getControlUUID();
    void setControlUUID(UUID uuid);
    boolean getFaceToPlayer();
    ControllableParticleEffect clone();
}