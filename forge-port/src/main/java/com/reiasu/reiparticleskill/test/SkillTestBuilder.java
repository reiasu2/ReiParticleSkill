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
package com.reiasu.reiparticleskill.test;

import com.reiasu.reiparticlesapi.test.SimpleTestGroup;
import com.reiasu.reiparticlesapi.test.SimpleTestOption;
import com.reiasu.reiparticlesapi.test.api.TestGroup;
import com.reiasu.reiparticlesapi.test.api.TestGroupBuilder;
import com.reiasu.reiparticlesapi.test.api.TestOption;
import com.reiasu.reiparticleskill.particles.core.emitters.p2.formation.SwordFormationEmitters;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;

/**
 * Skill test builder that creates a test group with sword formation emitter tests.
 * Server-side port of the Fabric original, adapted to Forge test API.
 */
public final class SkillTestBuilder implements TestGroupBuilder {
    public static final String ID = "skill-test-builder";

    private final ServerPlayer player;

    public SkillTestBuilder(ServerPlayer player) {
        this.player = player;
    }

    public ServerPlayer getPlayer() {
        return player;
    }

    @Override
    public String groupID() {
        return ID;
    }

    @Override
    public TestGroup build() {
        SimpleTestGroup group = new SimpleTestGroup(groupID(), player);
        group.appendOption(() -> createSwordFormationTest());
        return group;
    }

    private TestOption createSwordFormationTest() {
        Vec3 eyePos = player.getEyePosition();
        SwordFormationEmitters emitter = new SwordFormationEmitters(eyePos, player.level());
        emitter.setMaxTick(-1);
        return new SimpleTestOption("sword-formation");
    }
}
