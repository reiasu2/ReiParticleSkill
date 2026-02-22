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
package com.reiasu.reiparticlesapi.event.events.entity;

import com.reiasu.reiparticlesapi.event.api.EventCancelable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

public final class EntityPrePlaceBlockEvent extends EntityEvent implements EventCancelable {
    private final Level world;
    private final BlockPos block;
    private final Direction direction;
    private boolean cancelled;

    public EntityPrePlaceBlockEvent(Entity entity, Level world, BlockPos block, Direction direction) {
        super(entity);
        this.world = world;
        this.block = block;
        this.direction = direction;
    }

    public Level getWorld() {
        return world;
    }

    public BlockPos getBlock() {
        return block;
    }

    public Direction getDirection() {
        return direction;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}

