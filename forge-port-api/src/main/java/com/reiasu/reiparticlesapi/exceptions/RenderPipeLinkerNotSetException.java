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
 * Thrown when a render pipeline's pipe linker function has not been set.
 */
public final class RenderPipeLinkerNotSetException extends Exception {

    private ResourceLocation renderID;

    public RenderPipeLinkerNotSetException(ResourceLocation renderID) {
        super("Render " + renderID + "'s pipe linker not set; use manager.setLinkerFunc to set pipes link");
        this.renderID = renderID;
    }

    public ResourceLocation getRenderID() { return renderID; }
    public void setRenderID(ResourceLocation renderID) { this.renderID = renderID; }
}
