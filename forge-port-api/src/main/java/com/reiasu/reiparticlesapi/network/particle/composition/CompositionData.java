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
package com.reiasu.reiparticlesapi.network.particle.composition;

import com.reiasu.reiparticlesapi.particles.Controllable;
import com.reiasu.reiparticlesapi.particles.ControllableParticle;
import com.reiasu.reiparticlesapi.particles.ControllableParticleEffect;
import com.reiasu.reiparticlesapi.particles.ParticleDisplayer;
import com.reiasu.reiparticlesapi.particles.control.ParticleController;

import javax.annotation.Nullable;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Data attached to each particle entry in a {@link ParticleComposition}.
 * <p>
 * Each instance carries a unique {@link UUID} that identifies it within
 * the composition, and an {@code order} value used for sequenced display.
 * <p>
 * Client-side fields:
 * <ul>
 *   <li>{@link #displayerBuilder} — factory that creates the {@link ParticleDisplayer}
 *       for this entry (e.g. single-particle, group, style).</li>
 *   <li>{@link #particleInit} — callback run once when the particle controller
 *       is first loaded (optional).</li>
 *   <li>{@link #controllable} — the {@link Controllable} handle returned by the
 *       displayer after spawning. Used for teleport/remove/rotate operations.</li>
 * </ul>
 */
public class CompositionData implements Comparable<CompositionData> {

    private final UUID uuid = UUID.randomUUID();
    private int order;

    private Supplier<ParticleDisplayer> displayerBuilder;
    private Consumer<ParticleController> particleInit;
    @Nullable
    private Controllable<?> controllable;

    public UUID getUuid() {
        return uuid;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public Supplier<ParticleDisplayer> getDisplayerBuilder() {
        return displayerBuilder;
    }

    public CompositionData setDisplayerBuilder(Supplier<ParticleDisplayer> displayerBuilder) {
        this.displayerBuilder = displayerBuilder;
        return this;
    }

    public CompositionData setDisplayerWithEffect(Supplier<ControllableParticleEffect> effectSupplier) {
        this.displayerBuilder = () -> ParticleDisplayer.withSingle(effectSupplier.get());
        return this;
    }

    public Consumer<ParticleController> getParticleInit() {
        return particleInit;
    }

    public CompositionData setParticleInit(Consumer<ParticleController> particleInit) {
        this.particleInit = particleInit;
        return this;
    }

    @Nullable
    public Controllable<?> getControllable() {
        return controllable;
    }

    public void setControllable(@Nullable Controllable<?> controllable) {
        this.controllable = controllable;
    }

    @Override
    public int compareTo(CompositionData other) {
        return this.order - other.order;
    }
}
