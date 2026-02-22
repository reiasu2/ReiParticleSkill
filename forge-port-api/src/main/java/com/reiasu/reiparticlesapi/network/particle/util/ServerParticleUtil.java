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
package com.reiasu.reiparticlesapi.network.particle.util;

import com.reiasu.reiparticlesapi.network.ReiParticlesNetwork;
import com.reiasu.reiparticlesapi.network.packet.PacketParticleS2C;
import com.reiasu.reiparticlesapi.utils.RelativeLocation;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;

/**
 * Utility for spawning vanilla and custom particles on the server side,
 * sending them to all players (optionally filtered by range).
 */
public final class ServerParticleUtil {
    public static final ServerParticleUtil INSTANCE = new ServerParticleUtil();

    private ServerParticleUtil() {}

    public void spawnSingle(ParticleOptions type, ServerLevel world, Vec3 pos,
                            Vec3 delta, boolean force, double speed, int count) {
        for (ServerPlayer player : world.players()) {
            world.sendParticles(player, type, force, pos.x, pos.y, pos.z,
                    count, delta.x, delta.y, delta.z, speed);
        }
    }

    public void spawnSingle(ParticleOptions type, ServerLevel world, Vec3 pos,
                            Vec3 delta, boolean force, double speed, int count, double range) {
        for (ServerPlayer player : world.players()) {
            if (player.position().distanceTo(pos) > range) continue;
            world.sendParticles(player, type, force, pos.x, pos.y, pos.z,
                    count, delta.x, delta.y, delta.z, speed);
        }
    }

    public void spawnSingle(ParticleOptions type, ServerLevel world,
                            RelativeLocation pos, RelativeLocation velocity, double range) {
        spawnSingle(type, world, pos.toVector(), velocity.toVector(), range);
    }

    public void spawnSingle(ParticleOptions type, ServerLevel world,
                            RelativeLocation pos, RelativeLocation velocity) {
        spawnSingle(type, world, pos.toVector(), velocity.toVector());
    }

    public void spawnSingle(ParticleOptions type, ServerLevel world,
                            Vec3 pos, Vec3 velocity, double range) {
        PacketParticleS2C packet = new PacketParticleS2C(type, pos, velocity);
        for (ServerPlayer player : world.players()) {
            if (player.position().distanceTo(pos) > range) continue;
            ReiParticlesNetwork.sendTo(player, packet);
        }
    }

    public void spawnSingle(ParticleOptions type, ServerLevel world,
                            Vec3 pos, Vec3 velocity) {
        PacketParticleS2C packet = new PacketParticleS2C(type, pos, velocity);
        for (ServerPlayer player : world.players()) {
            ReiParticlesNetwork.sendTo(player, packet);
        }
    }
}
