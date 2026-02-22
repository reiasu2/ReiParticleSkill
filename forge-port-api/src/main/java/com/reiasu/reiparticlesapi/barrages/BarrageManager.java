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
package com.reiasu.reiparticlesapi.barrages;

import com.reiasu.reiparticlesapi.display.DisplayEntity;
import com.reiasu.reiparticlesapi.display.DisplayEntityManager;
import com.reiasu.reiparticlesapi.network.particle.ServerController;
import com.reiasu.reiparticlesapi.network.particle.ServerParticleGroup;
import com.reiasu.reiparticlesapi.network.particle.ServerParticleGroupManager;
import com.reiasu.reiparticlesapi.network.particle.emitters.ParticleEmitters;
import com.reiasu.reiparticlesapi.network.particle.emitters.ParticleEmittersManager;
import com.reiasu.reiparticlesapi.network.particle.style.ParticleGroupStyle;
import com.reiasu.reiparticlesapi.network.particle.style.ParticleStyleManager;
import com.reiasu.reiparticlesapi.renderer.RenderEntity;
import com.reiasu.reiparticlesapi.renderer.server.ServerRenderEntityManager;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * Manages all active {@link Barrage} instances on the server,
 * handling spawning, ticking, and collision queries.
 */
public final class BarrageManager {
    public static final BarrageManager INSTANCE = new BarrageManager();

    private static final ConcurrentLinkedDeque<Barrage> barrages = new ConcurrentLinkedDeque<>();

    private BarrageManager() {}

    public List<Barrage> collectClipBarrages(ServerLevel world, AABB box) {
        List<Barrage> result = new ArrayList<>();
        for (Barrage b : barrages) {
            if (!b.getValid()) continue;
            if (!world.equals(b.getWorld())) continue;
            if (b.noclip()) continue;
            if (box.contains(b.getLoc()) || box.intersects(b.getHitBox().ofBox(b.getLoc()))) {
                result.add(b);
            }
        }
        return result;
    }

    public void spawn(Barrage barrage) {
        spawnOnWorld(barrage);
        barrages.add(barrage);
    }

    public void doTick() {
        Iterator<Barrage> it = barrages.iterator();
        while (it.hasNext()) {
            Barrage b = it.next();
            b.tick();
            if (!b.getValid()) {
                it.remove();
            }
        }
    }

    private void spawnOnWorld(Barrage barrage) {
        ServerController<?> control = barrage.getBindControl();
        if (control instanceof ServerParticleGroup spg) {
            ServerParticleGroupManager.INSTANCE.addParticleGroup(spg, barrage.getLoc(), barrage.getWorld());
        } else if (control instanceof ParticleGroupStyle style) {
            ParticleStyleManager.spawnStyle(barrage.getWorld(), barrage.getLoc(), style);
        } else if (control instanceof RenderEntity re) {
            ServerRenderEntityManager.INSTANCE.spawn(re);
        } else if (control instanceof ParticleEmitters emitters) {
            ParticleEmittersManager.spawnEmitters(emitters);
        } else if (control instanceof DisplayEntity de) {
            DisplayEntityManager.INSTANCE.spawn(de);
        }
        barrage.setLaunch(true);
    }
}
