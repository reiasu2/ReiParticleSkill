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
package com.reiasu.reiparticlesapi.test;

import com.reiasu.reiparticlesapi.test.api.TestGroup;
import com.reiasu.reiparticlesapi.test.api.TestOption;
import net.minecraft.server.level.ServerPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public final class SimpleTestGroup implements TestGroup {
    private final String groupId;
    private final ServerPlayer user;
    private final List<Supplier<TestOption>> optionSuppliers = new ArrayList<>();
    private int currentIndex = -1;
    private TestOption currentOption;
    private boolean done;

    public SimpleTestGroup(String groupId, ServerPlayer user) {
        this.groupId = groupId;
        this.user = user;
    }

    @Override
    public ServerPlayer getUser() {
        return user;
    }

    @Override
    public TestGroup appendOption(Supplier<TestOption> supplier) {
        if (supplier != null) {
            optionSuppliers.add(supplier);
        }
        return this;
    }

    @Override
    public void init() {
        if (optionSuppliers.isEmpty()) {
            appendOption(() -> new SimpleTestOption("smoke"));
            appendOption(() -> new SimpleTestOption("lifecycle"));
        }
    }

    @Override
    public void start() {
        init();
        moveToNextOption();
    }

    @Override
    public boolean isDone() {
        return done;
    }

    @Override
    public TestOption skipCurrent() {
        TestOption old = currentOption;
        if (old != null) {
            old.stop();
            onOptionSuccess(old);
        }
        moveToNextOption();
        return old;
    }

    @Override
    public void doTick() {
        if (done || currentOption == null) {
            return;
        }
        try {
            currentOption.doTick();
            if (!currentOption.isValid()) {
                onOptionSuccess(currentOption);
                moveToNextOption();
            }
        } catch (Throwable t) {
            onOptionFailure(t, currentOption);
            moveToNextOption();
        }
    }

    @Override
    public void onOptionFailure(Throwable t, TestOption option) {
        option.onFailed();
    }

    @Override
    public void onOptionSuccess(TestOption option) {
        option.onSuccess();
    }

    @Override
    public void onGroupFinished() {
        done = true;
    }

    @Override
    public String groupID() {
        return groupId;
    }

    private void moveToNextOption() {
        currentIndex++;
        if (currentIndex >= optionSuppliers.size()) {
            currentOption = null;
            onGroupFinished();
            return;
        }
        currentOption = optionSuppliers.get(currentIndex).get();
        if (currentOption == null) {
            moveToNextOption();
            return;
        }
        currentOption.start();
    }
}
