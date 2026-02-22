/*
 * Copyright (C) 2025 Reiasu
 *
 * This file is part of ReiParticleSkill.
 *
 * ReiParticleSkill is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, version 3 of the License.
 *
 * ReiParticleSkill is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with ReiParticleSkill. If not, see <https://www.gnu.org/licenses/>.
 */
// SPDX-License-Identifier: LGPL-3.0-only
package com.reiasu.reiparticleskill.display.group;

import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public final class ServerDisplayGroupManager {
    public static final ServerDisplayGroupManager INSTANCE = new ServerDisplayGroupManager();
    private final Set<ServerOnlyDisplayGroup> groups = Collections.newSetFromMap(new ConcurrentHashMap<>());

    private ServerDisplayGroupManager() {
    }

    public Set<ServerOnlyDisplayGroup> getGroups() {
        return groups;
    }

    public void doTick() {
        Iterator<ServerOnlyDisplayGroup> iterator = groups.iterator();
        while (iterator.hasNext()) {
            ServerOnlyDisplayGroup group = iterator.next();
            group.tick();
            if (group.getCanceled()) {
                iterator.remove();
            }
        }
    }

    public void spawn(ServerOnlyDisplayGroup group) {
        if (group == null) {
            return;
        }
        group.display();
        groups.add(group);
    }

    public void clear() {
        for (ServerOnlyDisplayGroup group : groups) {
            group.remove();
        }
        groups.clear();
    }
}
