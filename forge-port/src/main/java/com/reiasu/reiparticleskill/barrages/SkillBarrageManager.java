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
package com.reiasu.reiparticleskill.barrages;

import com.reiasu.reiparticlesapi.barrages.Barrage;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public enum SkillBarrageManager {
    INSTANCE;

    private final CopyOnWriteArrayList<Barrage> activeBarrages = new CopyOnWriteArrayList<>();

    public void spawn(Barrage barrage) {
        if (barrage == null || !barrage.getValid()) {
            return;
        }
        activeBarrages.add(barrage);
    }

    public void tickAll() {
        if (activeBarrages.isEmpty()) {
            return;
        }
        activeBarrages.removeIf(barrage -> {
            if (barrage == null || !barrage.getValid()) {
                return true;
            }
            barrage.tick();
            return !barrage.getValid();
        });
    }

    public void clear() {
        for (Barrage barrage : activeBarrages) {
            if (barrage == null) {
                continue;
            }
            try {
                barrage.getBindControl().cancel();
            } catch (Throwable ignored) {
                // best effort cleanup
            }
        }
        activeBarrages.clear();
    }

    public int activeCount() {
        return activeBarrages.size();
    }

    public List<Barrage> snapshot() {
        return List.copyOf(activeBarrages);
    }
}
