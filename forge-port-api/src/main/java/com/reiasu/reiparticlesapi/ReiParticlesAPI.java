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
package com.reiasu.reiparticlesapi;

import com.reiasu.reiparticlesapi.event.ReiEventBus;
import com.reiasu.reiparticlesapi.event.api.ReiEvent;
import com.reiasu.reiparticlesapi.test.SimpleTestGroupBuilder;
import com.reiasu.reiparticlesapi.test.TestManager;
import com.mojang.logging.LogUtils;
import net.minecraft.server.level.ServerPlayer;
import org.slf4j.Logger;

import java.util.Comparator;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Main entry point for the ReiParticles API.
 * <p>
 * Provides lifecycle management (initialization, scanner loading, hook registration),
 * a tick-driven {@link Scheduler} for deferred tasks, and convenience methods for
 * the {@link com.reiasu.reiparticlesapi.event.ReiEventBus ReiEventBus} event system.
 * <p>
 * Typical mod integration:
 * <pre>{@code
 * // During mod construction
 * ReiParticlesAPI.init();
 * ReiParticlesAPI.INSTANCE.loadScannerPackages();
 *
 * // Register event listeners
 * ReiParticlesAPI.INSTANCE.appendEventListenerTarget("mymod", "com.example.mymod.listeners");
 * ReiParticlesAPI.INSTANCE.initEventListeners();
 *
 * // In server tick handler
 * ReiParticlesAPI.scheduler.tick();
 * }</pre>
 */
public final class ReiParticlesAPI {
    public static final ReiParticlesAPI INSTANCE = new ReiParticlesAPI();
    public static final Scheduler scheduler = new Scheduler();
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final AtomicBoolean INITIALIZED = new AtomicBoolean(false);
    private static final AtomicBoolean SCANNERS_LOADED = new AtomicBoolean(false);
    private static final AtomicBoolean TEST_HOOKS_REGISTERED = new AtomicBoolean(false);
    private static final AtomicBoolean STYLE_HOOKS_REGISTERED = new AtomicBoolean(false);
    private static final AtomicBoolean KEY_HOOKS_REGISTERED = new AtomicBoolean(false);

    private ReiParticlesAPI() {
    }

    /** Initializes the API. Safe to call multiple times; only the first call takes effect. */
    public static void init() {
        if (INITIALIZED.compareAndSet(false, true)) {
            LOGGER.info("ReiParticlesAPI init completed");
        } else {
            LOGGER.debug("init() called again — already initialized, skipping");
        }
    }

    /** Returns {@code true} if {@link #init()} has been called. */
    public static boolean isInitialized() {
        return INITIALIZED.get();
    }

    /** Scans and initializes event listener packages. Call once after all listener targets are registered. */
    public void loadScannerPackages() {
        if (SCANNERS_LOADED.compareAndSet(false, true)) {
            if (!INITIALIZED.get()) {
                LOGGER.warn("loadScannerPackages() called before init() — call init() first");
            }
            LOGGER.info("ReiParticlesAPI scanner packages loaded");
            ReiEventBus.INSTANCE.scanListeners();
            ReiEventBus.INSTANCE.initListeners();
        } else {
            LOGGER.debug("loadScannerPackages() called again — already loaded, skipping");
        }
    }

    public boolean scannersLoaded() {
        return SCANNERS_LOADED.get();
    }

    public void registerTest() {
        if (TEST_HOOKS_REGISTERED.compareAndSet(false, true)) {
            TestManager.INSTANCE.register("api-test-group-builder", user -> buildSmokeTestGroup(user));
            LOGGER.info("ReiParticlesAPI test hooks registered");
        }
    }

    public boolean testHooksRegistered() {
        return TEST_HOOKS_REGISTERED.get();
    }

    public void registerParticleStyles() {
        if (STYLE_HOOKS_REGISTERED.compareAndSet(false, true)) {
            // TODO: add actual style registration logic (e.g., load from registry)
            LOGGER.info("ReiParticlesAPI particle styles registered");
        }
    }

    public boolean styleHooksRegistered() {
        return STYLE_HOOKS_REGISTERED.get();
    }

    public void registerKeyBindings() {
        if (KEY_HOOKS_REGISTERED.compareAndSet(false, true)) {
            // TODO: add actual key binding registration logic
            LOGGER.info("ReiParticlesAPI key hooks registered");
        }
    }

    public boolean keyHooksRegistered() {
        return KEY_HOOKS_REGISTERED.get();
    }

    /**
     * Registers a package for event listener scanning.
     *
     * @param modId  the mod identifier
     * @param target fully-qualified package name to scan for {@code @ReiAutoRegister} listeners
     */
    public void appendEventListenerTarget(String modId, String target) {
        ReiEventBus.INSTANCE.appendListenerTarget(modId, target);
    }

    /** Initializes all registered event listeners. Call after all targets have been appended. */
    public void initEventListeners() {
        if (!SCANNERS_LOADED.get()) {
            LOGGER.debug("initEventListeners() called before loadScannerPackages() — Forge scanning is a no-op, listeners must be registered explicitly");
        }
        ReiEventBus.INSTANCE.initListeners();
    }

    /**
     * Manually registers a single event listener instance.
     *
     * @param modId    the mod identifier
     * @param listener the listener object (must have methods annotated with event handler annotations)
     */
    public void registerEventListener(String modId, Object listener) {
        ReiEventBus.INSTANCE.registerListenerInstance(modId, listener);
    }

    /**
     * Fires an event through the {@link com.reiasu.reiparticlesapi.event.ReiEventBus ReiEventBus}.
     *
     * @param event the event to dispatch
     * @param <T>   event type
     * @return the same event instance (may have been modified by listeners)
     */
    public <T extends ReiEvent> T callEvent(T event) {
        return ReiEventBus.call(event);
    }

    /**
     * Tick-driven scheduler that runs tasks after a specified number of
     * actual server ticks, avoiding 50ms approximation drift under lag.
     * <p>
     * Call {@link #tick()} once per server tick from the mod's tick handler.
     */
    public static final class Scheduler {
        private final AtomicLong currentTick = new AtomicLong(0);
        private final PriorityBlockingQueue<ScheduledTask> tasks =
                new PriorityBlockingQueue<>(16, Comparator.comparingLong(ScheduledTask::executionTick));

        public void runTask(int ticks, Runnable task) {
            long executionTick = currentTick.get() + Math.max(1, ticks);
            tasks.add(new ScheduledTask(executionTick, task));
        }

        // Note: tick() is called from the single server-tick thread only.
        // PriorityBlockingQueue keeps tasks sorted by executionTick, so we
        // only drain tasks that are due — no need to re-enqueue future tasks.
        public void tick() {
            long now = currentTick.incrementAndGet();
            ScheduledTask entry;
            while ((entry = tasks.peek()) != null && now >= entry.executionTick) {
                tasks.poll();
                try {
                    entry.task.run();
                } catch (Exception e) {
                    LOGGER.warn("Scheduled task failed", e);
                }
            }
        }

        public void shutdown() {
            tasks.clear();
        }

        private record ScheduledTask(long executionTick, Runnable task) {}
    }

    private static SimpleTestGroupBuilder buildSmokeTestGroup(ServerPlayer user) {
        return new SimpleTestGroupBuilder("api-test-group-builder", user);
    }
}
