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
package com.reiasu.reiparticleskill.compat.version.forge120;

import com.reiasu.reiparticleskill.compat.version.CommandSourceVersionBridge;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;

public final class Forge120CommandSourceBridge implements CommandSourceVersionBridge {
    @Override
    public ServerLevel level(CommandSourceStack source) {
        return source.getLevel();
    }

    @Override
    public Vec3 position(CommandSourceStack source) {
        return source.getPosition();
    }

    @Override
    public ServerPlayer playerOrNull(CommandSourceStack source) {
        return source.getPlayer();
    }

    @Override
    public void sendSuccess(CommandSourceStack source, String message) {
        source.sendSuccess(() -> Component.literal(message), false);
    }

    @Override
    public void sendFailure(CommandSourceStack source, String message) {
        source.sendFailure(Component.literal(message));
    }
}
