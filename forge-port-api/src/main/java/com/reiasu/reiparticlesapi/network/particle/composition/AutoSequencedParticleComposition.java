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

import com.reiasu.reiparticlesapi.annotations.codec.BufferCodec;
import com.reiasu.reiparticlesapi.annotations.composition.handler.ParticleCompositionHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

/**
 * Abstract sequenced composition that auto-generates its network codec
 * via {@link ParticleCompositionHelper}.
 * <p>
 * Subclasses must:
 * <ul>
 *   <li>Provide a public constructor {@code (Vec3, Level)}</li>
 *   <li>Annotate serializable fields with {@link com.reiasu.reiparticlesapi.annotations.CodecField}</li>
 *   <li>Implement {@link #getParticleSequenced()} and {@link #onDisplay()}</li>
 * </ul>
 */
public abstract class AutoSequencedParticleComposition extends SequencedParticleComposition {

    protected AutoSequencedParticleComposition(Vec3 position, Level world) {
        super(position, world);
    }

    protected AutoSequencedParticleComposition(Vec3 position) {
        super(position);
    }

    /**
     * Returns the auto-generated codec for this composition type.
     * Uses reflection to serialize all {@link com.reiasu.reiparticlesapi.annotations.CodecField}
     * annotated fields.
     */
    public BufferCodec<ParticleComposition> getCodec() {
        return ParticleCompositionHelper.INSTANCE.generateCodec(this);
    }
}
