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
package com.reiasu.reiparticlesapi.network.particle.style;

import com.reiasu.reiparticlesapi.utils.RelativeLocation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class ParticleStyleManagerTest {
    @AfterEach
    void cleanup() {
        ParticleStyleManager.getClientViewStyles().clear();
        ParticleStyleManager.getServerViewStyles().clear();
        ParticleStyleManager.getVisible().clear();
    }

    @Test
    void shouldContinueClientTickAfterStyleFailure() {
        FailingStyle failing = new FailingStyle();
        CountingStyle healthy = new CountingStyle();
        ParticleStyleManager.getClientViewStyles().put(failing.getUuid(), failing);
        ParticleStyleManager.getClientViewStyles().put(healthy.getUuid(), healthy);

        ParticleStyleManager.doTickClient();

        assertEquals(1, healthy.ticks);
        assertEquals(1, ParticleStyleManager.getClientViewStyles().size());
        assertSame(healthy, ParticleStyleManager.getClientViewStyles().get(healthy.getUuid()));
    }

    private static final class CountingStyle extends ParticleGroupStyle {
        private int ticks;

        @Override
        public Map<StyleData, RelativeLocation> getCurrentFrames() {
            return Map.of();
        }

        @Override
        public void onDisplay() {
        }

        @Override
        public void tick() {
            super.tick();
            ticks++;
        }
    }

    private static final class FailingStyle extends ParticleGroupStyle {
        @Override
        public Map<StyleData, RelativeLocation> getCurrentFrames() {
            return Map.of();
        }

        @Override
        public void onDisplay() {
        }

        @Override
        public void tick() {
            throw new IllegalStateException("boom");
        }
    }
}
