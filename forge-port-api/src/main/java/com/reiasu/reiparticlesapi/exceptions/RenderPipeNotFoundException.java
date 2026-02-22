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
package com.reiasu.reiparticlesapi.exceptions;

import net.minecraft.resources.ResourceLocation;

/**
 * Thrown when a render entity's bound pipe manager cannot be found.
 */
public final class RenderPipeNotFoundException extends Exception {

    private ResourceLocation renderID;

    public RenderPipeNotFoundException(ResourceLocation renderID) {
        super("Render " + renderID + "'s bound pipe manager not found; "
                + "use ClientRenderEntityManager.bindEntityRenderPipe(YourRenderEntity.ID, pipeID) to bind an output pipe manager");
        this.renderID = renderID;
    }

    public ResourceLocation getRenderID() { return renderID; }
    public void setRenderID(ResourceLocation renderID) { this.renderID = renderID; }
}
