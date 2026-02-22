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
import com.reiasu.reiparticlesapi.event.api.EventInterruptible;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public final class EntityPreMoveEvent extends EntityEvent implements EventCancelable, EventInterruptible {
    private Vec3 movement;
    private boolean cancelled;
    private boolean interrupted;

    public EntityPreMoveEvent(Entity entity, Vec3 movement) {
        super(entity);
        this.movement = movement;
    }

    public Vec3 getMovement() {
        return movement;
    }

    public void setMovement(Vec3 movement) {
        this.movement = movement;
    }

    public Vec3 getFinalPos() {
        return getEntity().position().add(movement);
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    public boolean isInterrupted() {
        return interrupted;
    }

    @Override
    public void setInterrupted(boolean interrupted) {
        this.interrupted = interrupted;
    }
}

