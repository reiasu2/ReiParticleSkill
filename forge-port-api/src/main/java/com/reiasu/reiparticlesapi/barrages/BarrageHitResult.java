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
package com.reiasu.reiparticlesapi.barrages;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.ArrayList;

/**
 * Holds the result of a barrage collision check, collecting all hit blocks,
 * entities, and other barrages in a single tick.
 */
public final class BarrageHitResult {

    @Nullable
    private BlockState hitBlockState;
    private final ArrayList<BlockPos> hitBlocks = new ArrayList<>();
    private final ArrayList<LivingEntity> entities = new ArrayList<>();
    private final ArrayList<Barrage> barrages = new ArrayList<>();

    @Nullable
    public BlockState getHitBlockState() {
        return hitBlockState;
    }

    public void setHitBlockState(@Nullable BlockState hitBlockState) {
        this.hitBlockState = hitBlockState;
    }

    public ArrayList<BlockPos> getHitBlocks() {
        return hitBlocks;
    }

    public ArrayList<LivingEntity> getEntities() {
        return entities;
    }

    public ArrayList<Barrage> getBarrages() {
        return barrages;
    }
}
