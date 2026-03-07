// SPDX-License-Identifier: LGPL-3.0-only
// Copyright (C) 2025 Reiasu
package com.reiasu.reiparticlesapi.network.packet.client.listener;

import com.reiasu.reiparticlesapi.network.buffer.ParticleControllerDataBuffer;
import com.reiasu.reiparticlesapi.network.buffer.ParticleControllerDataBuffers;
import com.reiasu.reiparticlesapi.network.packet.PacketParticleGroupS2C;
import com.reiasu.reiparticlesapi.particles.control.group.ClientParticleGroupManager;
import com.reiasu.reiparticlesapi.particles.control.group.ControllableParticleGroup;
import com.reiasu.reiparticlesapi.testutil.RecordingLogger;
import com.reiasu.reiparticlesapi.utils.RelativeLocation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ClientParticleGroupPacketHandlerTest {
    @AfterEach
    void cleanup() {
        ClientParticleGroupManager.INSTANCE.clearAllVisible();
    }

    @Test
    void shouldWarnWhenInvokeReflectionFails() {
        RecordingLogger recorder = new RecordingLogger();
        UUID uuid = UUID.randomUUID();
        ClientParticleGroupManager.INSTANCE.addVisibleGroup(new DummyGroup(uuid));
        Map<String, ParticleControllerDataBuffer<?>> args = Map.of(
                PacketParticleGroupS2C.PacketArgsType.INVOKE.getOfArgs(),
                ParticleControllerDataBuffers.INSTANCE.string("missingMethod")
        );

        ClientParticleGroupPacketHandler.handleChange(uuid, args, recorder.logger());

        assertTrue(recorder.hasEvent("warn", "Failed to invoke"));
    }

    private static final class DummyGroup extends ControllableParticleGroup {
        private DummyGroup(UUID uuid) {
            super(uuid);
        }

        @Override
        public Map<ParticleRelativeData, RelativeLocation> loadParticleLocations() {
            return Map.of();
        }

        @Override
        public void onGroupDisplay() {
        }
    }
}
