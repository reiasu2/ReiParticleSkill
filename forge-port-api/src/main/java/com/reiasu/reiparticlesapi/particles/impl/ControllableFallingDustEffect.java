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
package com.reiasu.reiparticlesapi.particles.impl;

import com.reiasu.reiparticlesapi.particles.ControllableParticleEffect;
import com.reiasu.reiparticlesapi.particles.ReiModParticles;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.UUID;

public class ControllableFallingDustEffect implements ControllableParticleEffect {
    private UUID uuid;
    private final BlockState blockState;
    private final boolean faceToPlayer;

    public ControllableFallingDustEffect(UUID uuid, BlockState blockState, boolean faceToPlayer) {
        this.uuid = uuid;
        this.blockState = blockState;
        this.faceToPlayer = faceToPlayer;
    }

    public ControllableFallingDustEffect(UUID uuid, BlockState blockState, boolean faceToPlayer, int ignored, Object ignored2) {
        this(uuid, blockState, faceToPlayer);
    }

    @Override
    public UUID getControlUUID() { return uuid; }

    @Override
    public void setControlUUID(UUID uuid) { this.uuid = uuid; }

    @Override
    public ControllableFallingDustEffect clone() {
        return new ControllableFallingDustEffect(UUID.randomUUID(), blockState, faceToPlayer);
    }

    public BlockState getBlockState() { return blockState; }
    @Override public boolean getFaceToPlayer() { return faceToPlayer; }

    @Override public ParticleType<?> getType() { return ReiModParticles.CONTROLLABLE_FALLING_DUST.get(); }
    @Override public void writeToNetwork(FriendlyByteBuf buf) {
        buf.writeUUID(uuid); buf.writeBoolean(faceToPlayer);
    }
    @Override public String writeToString() { return "coo:falling_dust"; }
}
