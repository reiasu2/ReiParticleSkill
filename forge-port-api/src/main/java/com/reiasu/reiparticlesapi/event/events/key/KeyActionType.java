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

public enum KeyActionType {
    SINGLE_CLICK(0),
    DOUBLE_CLICK(1),
    LONG_PRESS(2);

    private final int id;

    KeyActionType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static KeyActionType fromId(int id) {
        for (KeyActionType value : values()) {
            if (value.id == id) {
                return value;
            }
        }
        return SINGLE_CLICK;
    }
}

