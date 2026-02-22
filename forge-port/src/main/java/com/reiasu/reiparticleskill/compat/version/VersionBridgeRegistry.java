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
package com.reiasu.reiparticleskill.compat.version;

import com.reiasu.reiparticleskill.compat.version.forge120.Forge120CommandSourceBridge;
import com.reiasu.reiparticleskill.compat.version.forge120.Forge120EndRespawnBridge;
import com.reiasu.reiparticleskill.compat.version.forge120.Forge120ModLifecycleBridge;

public final class VersionBridgeRegistry {
    private static final CommandSourceVersionBridge COMMAND_SOURCE = new Forge120CommandSourceBridge();
    private static final ModLifecycleVersionBridge LIFECYCLE = new Forge120ModLifecycleBridge();
    private static final EndRespawnVersionBridge END_RESPAWN = new Forge120EndRespawnBridge();

    private VersionBridgeRegistry() {
    }

    public static CommandSourceVersionBridge commandSource() {
        return COMMAND_SOURCE;
    }

    public static ModLifecycleVersionBridge lifecycle() {
        return LIFECYCLE;
    }

    public static EndRespawnVersionBridge endRespawn() {
        return END_RESPAWN;
    }
}
