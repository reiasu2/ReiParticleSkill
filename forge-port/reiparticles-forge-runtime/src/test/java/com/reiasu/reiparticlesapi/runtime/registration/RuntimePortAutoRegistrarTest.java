// SPDX-License-Identifier: LGPL-3.0-only
// Copyright (C) 2025 Reiasu
package com.reiasu.reiparticlesapi.runtime.registration;

import com.reiasu.reiparticlesapi.annotations.ReiAutoRegister;
import com.reiasu.reiparticlesapi.network.particle.emitters.ParticleEmitters;
import com.reiasu.reiparticlesapi.network.particle.emitters.ParticleEmittersManager;
import com.reiasu.reiparticlesapi.network.particle.style.ParticleGroupStyle;
import com.reiasu.reiparticlesapi.network.particle.style.ParticleStyleManager;
import com.reiasu.reiparticlesapi.network.particle.style.ParticleStyleProvider;
import com.reiasu.reiparticlesapi.network.particle.style.ParticleGroupStyle.StyleData;
import com.reiasu.reiparticlesapi.reflect.ReiAPIScanner;
import com.reiasu.reiparticlesapi.testutil.RecordingLogger;
import com.reiasu.reiparticlesapi.utils.RelativeLocation;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RuntimePortAutoRegistrarTest {
    @AfterEach
    void cleanup() {
        ReiAPIScanner.INSTANCE.clear();
    }

    @Test
    void shouldRegisterAnnotatedRuntimePorts() {
        RecordingLogger recorder = new RecordingLogger();

        RuntimePortAutoRegistrar.registerAll(recorder.logger(), "com.reiasu.reiparticlesapi.runtime.registration");

        assertNotNull(ParticleEmittersManager.getCodecFromID(SuccessEmitter.CODEC_ID));
        assertNotNull(ParticleStyleManager.getProvider(SuccessStyle.REGISTRY_KEY));
        assertTrue(recorder.hasEvent("info", "Auto-registered"));
    }

    @Test
    void shouldWarnWhenProviderInstantiationFails() {
        RecordingLogger recorder = new RecordingLogger();

        RuntimePortAutoRegistrar.registerAll(recorder.logger(), "com.reiasu.reiparticlesapi.runtime.registration");

        assertTrue(recorder.hasEvent("warn", "Failed to instantiate Provider"));
        assertTrue(recorder.hasEvent("warn", "no usable Provider inner class"));
    }

    @ReiAutoRegister
    public static final class SuccessEmitter extends ParticleEmitters {
        public static final ResourceLocation CODEC_ID = new ResourceLocation("reiparticlesruntime", "success_emitter_test");

        public static SuccessEmitter decode(FriendlyByteBuf buf) {
            SuccessEmitter emitter = new SuccessEmitter();
            emitter.decodeFromBuffer(buf);
            return emitter;
        }
    }

    @ReiAutoRegister
    public static final class SuccessStyle extends ParticleGroupStyle {
        public static final ResourceLocation REGISTRY_KEY = new ResourceLocation("reiparticlesruntime", "success_style_test");

        @Override
        public Map<StyleData, RelativeLocation> getCurrentFrames() {
            return Map.of();
        }

        @Override
        public void onDisplay() {
        }

        public static final class Provider implements ParticleStyleProvider<SuccessStyle> {
            @Override
            public SuccessStyle create() {
                return new SuccessStyle();
            }
        }
    }

    @ReiAutoRegister
    public static final class BrokenStyle extends ParticleGroupStyle {
        public static final ResourceLocation REGISTRY_KEY = new ResourceLocation("reiparticlesruntime", "broken_style_test");

        @Override
        public Map<StyleData, RelativeLocation> getCurrentFrames() {
            return Map.of();
        }

        @Override
        public void onDisplay() {
        }

        public static final class Provider implements ParticleStyleProvider<BrokenStyle> {
            public Provider() {
                throw new IllegalStateException("boom");
            }

            @Override
            public BrokenStyle create() {
                return new BrokenStyle();
            }
        }
    }
}
