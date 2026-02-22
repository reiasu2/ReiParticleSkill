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
package com.reiasu.reiparticlesapi.commands;

import com.reiasu.reiparticlesapi.display.DisplayEntityManager;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;

/**
 * Registers the {@code /cleanapi} command tree for ReiParticlesAPI.
 * <p>
 * Currently provides:
 * <ul>
 *   <li>{@code /cleanapi display} &ndash; clears all client-side display entities</li>
 * </ul>
 */
public final class APICommand {
    public static final APICommand INSTANCE = new APICommand();

    private APICommand() {
    }

    /**
     * Register the {@code /cleanapi} command with the given dispatcher.
     *
     * @param dispatcher the server command dispatcher
     */
    public void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                LiteralArgumentBuilder.<CommandSourceStack>literal("cleanapi")
                        .then(LiteralArgumentBuilder.<CommandSourceStack>literal("display")
                                .executes(ctx -> {
                                    DisplayEntityManager.INSTANCE.clearClient();
                                    return 1;
                                }))
        );
    }
}
