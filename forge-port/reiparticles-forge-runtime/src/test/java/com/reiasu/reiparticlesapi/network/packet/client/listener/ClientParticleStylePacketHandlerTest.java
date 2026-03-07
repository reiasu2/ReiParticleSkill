// SPDX-License-Identifier: LGPL-3.0-only
// Copyright (C) 2025 Reiasu
package com.reiasu.reiparticlesapi.network.packet.client.listener;

import com.reiasu.reiparticlesapi.network.buffer.ParticleControllerDataBuffer;
import com.reiasu.reiparticlesapi.network.buffer.ParticleControllerDataBuffers;
import com.reiasu.reiparticlesapi.network.particle.style.ParticleGroupStyle;
import com.reiasu.reiparticlesapi.network.particle.style.ParticleStyleManager;
import com.reiasu.reiparticlesapi.network.particle.style.ParticleStyleProvider;
import com.reiasu.reiparticlesapi.testutil.RecordingLogger;
import com.reiasu.reiparticlesapi.utils.RelativeLocation;
import net.minecraft.resources.ResourceLocation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ClientParticleStylePacketHandlerTest {
    @AfterEach
    void cleanup() {
        ParticleStyleManager.getClientViewStyles().clear();
    }

    @Test
    void shouldWarnWhenStyleCreationFails() {
        RecordingLogger recorder = new RecordingLogger();
        ResourceLocation key = new ResourceLocation("reiparticlesruntime", "broken_style_" + System.nanoTime());
        int styleTypeId = ParticleStyleManager.register(key, new ThrowingProvider());
        Map<String, ParticleControllerDataBuffer<?>> args = Map.of(
                "style_type_id", ParticleControllerDataBuffers.INSTANCE.intValue(styleTypeId)
        );

        ClientParticleStylePacketHandler.handleCreate(UUID.randomUUID(), args, recorder.logger());

        assertTrue(recorder.hasEvent("warn", "Failed to spawn particle style"));
    }

    private static final class ThrowingProvider implements ParticleStyleProvider<ThrowingStyle> {
        @Override
        public ThrowingStyle create() {
            return new ThrowingStyle();
        }

        @Override
        public ThrowingStyle createStyle(UUID uuid, Map<String, ? extends ParticleControllerDataBuffer<?>> args) {
            throw new IllegalStateException("boom");
        }
    }

    private static final class ThrowingStyle extends ParticleGroupStyle {
        @Override
        public Map<StyleData, RelativeLocation> getCurrentFrames() {
            return Map.of();
        }

        @Override
        public void onDisplay() {
        }
    }
}
