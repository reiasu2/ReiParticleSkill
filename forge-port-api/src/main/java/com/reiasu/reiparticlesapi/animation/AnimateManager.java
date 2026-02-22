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
package com.reiasu.reiparticlesapi.animation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AnimateManager {
    public static final AnimateManager INSTANCE = new AnimateManager();
    private final List<Animate> serverActive = new ArrayList<>();
    private final List<Animate> clientActive = new ArrayList<>();

    private AnimateManager() {
    }

    public void displayAnimateServer(Animate animate) {
        if (animate == null) {
            return;
        }
        animate.start();
        synchronized (serverActive) {
            serverActive.add(animate);
        }
    }

    public void displayAnimateClient(Animate animate) {
        if (animate == null) {
            return;
        }
        animate.start();
        synchronized (clientActive) {
            clientActive.add(animate);
        }
    }

    public void tickServer() {
        synchronized (serverActive) {
            Iterator<Animate> iterator = serverActive.iterator();
            while (iterator.hasNext()) {
                Animate animate = iterator.next();
                animate.tick();
                if (animate.getDone()) {
                    iterator.remove();
                }
            }
        }
    }

    public void tickClient() {
        synchronized (clientActive) {
            Iterator<Animate> iterator = clientActive.iterator();
            while (iterator.hasNext()) {
                Animate animate = iterator.next();
                animate.tick();
                if (animate.getDone()) {
                    iterator.remove();
                }
            }
        }
    }

    public int activeCount() {
        synchronized (serverActive) {
            return serverActive.size();
        }
    }

    public int clientActiveCount() {
        synchronized (clientActive) {
            return clientActive.size();
        }
    }

    public void clear() {
        synchronized (serverActive) {
            for (Animate animate : serverActive) {
                animate.cancel();
            }
            serverActive.clear();
        }
        synchronized (clientActive) {
            for (Animate animate : clientActive) {
                animate.cancel();
            }
            clientActive.clear();
        }
    }
}
