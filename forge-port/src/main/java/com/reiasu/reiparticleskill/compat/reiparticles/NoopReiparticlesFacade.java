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
package com.reiasu.reiparticleskill.compat.reiparticles;

import org.slf4j.Logger;

/**
 * Dead-code fallback: reiparticlesapi is declared as {@code mandatory=true} in mods.toml,
 * so this class is never instantiated at runtime. Retained only for compile-time safety
 * until the facade layer is removed entirely.
 *
 * @deprecated Will be removed once the optional-dependency facade is cleaned up.
 */
@Deprecated(forRemoval = true)
public final class NoopReiparticlesFacade implements ReiparticlesFacade {
    @Override
    public boolean isOperational() {
        return false;
    }

    @Override
    public void bootstrap(Logger logger) {
        logger.warn("reiparticlesapi facade is running in NOOP mode; gameplay features are disabled until ported.");
    }

    @Override
    public void registerParticleStyles(Logger logger) {
        logger.warn("Skipping particle style registration (NOOP facade)");
    }

    @Override
    public void registerTestHooks(Logger logger) {
        logger.warn("Skipping test hook registration (NOOP facade)");
    }

    @Override
    public void registerKeyBindings(Logger logger) {
        logger.warn("Skipping keybinding bridge registration (NOOP facade)");
    }
}