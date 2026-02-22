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
package com.reiasu.reiparticlesapi.event.api;

import java.util.Objects;
import java.util.function.Consumer;

public final class EventExecutor {
    private final String modId;
    private final Consumer<ReiEvent> executor;

    public EventExecutor(String modId, Consumer<ReiEvent> executor) {
        this.modId = Objects.requireNonNull(modId, "modId");
        this.executor = Objects.requireNonNull(executor, "executor");
    }

    public String getModId() {
        return modId;
    }

    public Consumer<ReiEvent> getExecutor() {
        return executor;
    }
}

