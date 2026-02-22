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

import com.reiasu.reiparticlesapi.ReiParticlesAPI;
import org.slf4j.Logger;

public final class ReflectiveReiparticlesFacade implements ReiparticlesFacade {
    @Override
    public boolean isOperational() {
        return true;
    }

    @Override
    public void bootstrap(Logger logger) {
        ReiParticlesAPI.init();
        ReiParticlesAPI.INSTANCE.loadScannerPackages();
        logger.info("reiparticlesapi bridge active: init=true, scanner=true");
    }

    @Override
    public void registerParticleStyles(Logger logger) {
        ReiParticlesAPI.INSTANCE.registerParticleStyles();
        logger.info("reiparticlesapi bridge: particle style hooks invoked");
    }

    @Override
    public void registerTestHooks(Logger logger) {
        ReiParticlesAPI.INSTANCE.registerTest();
        logger.info("reiparticlesapi bridge: test hooks invoked");
    }

    @Override
    public void registerKeyBindings(Logger logger) {
        ReiParticlesAPI.INSTANCE.registerKeyBindings();
        logger.info("reiparticlesapi bridge: keybinding hooks invoked");
    }
}
