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

import com.reiasu.reiparticlesapi.test.api.TestOption;

public final class SimpleTestOption implements TestOption {
    private final String id;
    private int ticks;
    private boolean started;
    private boolean valid = true;

    public SimpleTestOption(String id) {
        this.id = id;
    }

    @Override
    public void start() {
        started = true;
    }

    @Override
    public void stop() {
        valid = false;
    }

    @Override
    public boolean isValid() {
        return valid;
    }

    @Override
    public void onFailed() {
        valid = false;
    }

    @Override
    public void onSuccess() {
        valid = false;
    }

    @Override
    public String optionID() {
        return id;
    }

    @Override
    public void doTick() {
        if (!started || !valid) {
            return;
        }
        ticks++;
        if (ticks >= 20) {
            onSuccess();
        }
    }
}
