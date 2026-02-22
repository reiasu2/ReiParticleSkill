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
package com.reiasu.reiparticlesapi.event.events.key;

import com.reiasu.reiparticlesapi.event.events.entity.player.PlayerEvent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public final class KeyActionEvent extends PlayerEvent {
    private final ResourceLocation keyId;
    private final KeyActionType action;
    private final int pressTick;
    private final boolean release;
    private final boolean serverSide;

    public KeyActionEvent(
            Player player,
            ResourceLocation keyId,
            KeyActionType action,
            int pressTick,
            boolean release,
            boolean serverSide
    ) {
        super(player);
        this.keyId = keyId;
        this.action = action;
        this.pressTick = pressTick;
        this.release = release;
        this.serverSide = serverSide;
    }

    public ResourceLocation getKeyId() {
        return keyId;
    }

    public KeyActionType getAction() {
        return action;
    }

    public int getPressTick() {
        return pressTick;
    }

    public boolean isRelease() {
        return release;
    }

    public boolean getServerSide() {
        return serverSide;
    }
}

