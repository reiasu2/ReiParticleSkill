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

import com.reiasu.reiparticleskill.end.respawn.EndRespawnPhase;
import net.minecraft.world.level.dimension.end.EndDragonFight;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;

public interface EndRespawnVersionBridge {
    Optional<EndRespawnPhase> detectPhase(EndDragonFight fight);

    Vec3 portalCenter(EndDragonFight fight);
}
