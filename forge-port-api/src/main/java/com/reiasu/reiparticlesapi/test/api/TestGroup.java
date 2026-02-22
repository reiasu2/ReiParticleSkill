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
package com.reiasu.reiparticlesapi.test.api;

import net.minecraft.server.level.ServerPlayer;

import java.util.function.Supplier;

public interface TestGroup {
    ServerPlayer getUser();

    TestGroup appendOption(Supplier<TestOption> supplier);

    void init();

    void start();

    boolean isDone();

    TestOption skipCurrent();

    void doTick();

    void onOptionFailure(Throwable t, TestOption option);

    void onOptionSuccess(TestOption option);

    void onGroupFinished();

    String groupID();
}
