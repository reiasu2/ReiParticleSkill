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
package com.reiasu.reiparticlesapi.event;

import com.reiasu.reiparticlesapi.annotations.events.EventHandler;
import com.reiasu.reiparticlesapi.annotations.events.EventListener;
import com.reiasu.reiparticlesapi.event.api.ReiEvent;
import com.reiasu.reiparticlesapi.event.api.EventExecutor;
import com.reiasu.reiparticlesapi.event.api.EventInterruptible;
import com.reiasu.reiparticlesapi.event.api.EventPriority;
import com.mojang.logging.LogUtils;
import org.slf4j.Logger;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public final class ReiEventBus {
    public static final ReiEventBus INSTANCE = new ReiEventBus();
    private static final Logger LOGGER = LogUtils.getLogger();

    private final Map<String, Set<String>> pendingListenersByMod = new ConcurrentHashMap<>();
    private final Map<Class<? extends ReiEvent>, EnumMap<EventPriority, CopyOnWriteArrayList<EventExecutor>>> handlerLists =
            new ConcurrentHashMap<>();
    private final AtomicBoolean initialized = new AtomicBoolean(false);

    private ReiEventBus() {
    }

    public void appendListenerTarget(String modId, String target) {
        if (modId == null || modId.isBlank() || target == null || target.isBlank()) {
            return;
        }
        pendingListenersByMod.computeIfAbsent(modId, ignored -> ConcurrentHashMap.newKeySet()).add(target);
    }

    public void initListeners() {
        if (!initialized.compareAndSet(false, true)) {
            return;
        }
        for (Map.Entry<String, Set<String>> entry : pendingListenersByMod.entrySet()) {
            String modId = entry.getKey();
            for (String target : entry.getValue()) {
                registerListenerClassName(modId, target);
            }
        }
    }

    /**
     * No-op on Forge: classpath scanning for event listeners is not yet implemented.
     * Listeners must be registered explicitly via {@link #appendListenerTarget(String, String)}
     * or {@link #registerAnnotatedClass(Class)}.
     * <p>
     * This method exists for API compatibility with NeoForge/Fabric ports that
     * perform automatic classpath scanning.
     */
    public void scanListeners() {
        // TODO: implement ClassGraph-based listener scanning for NeoForge/Fabric ports
        LOGGER.info("scanListeners() is a no-op on Forge. Use registerAnnotatedClass() or registerListenerInstance() instead.");
    }

    public void registerAnnotatedClass(Class<?> listenerClass) {
        EventListener anno = listenerClass.getAnnotation(EventListener.class);
        if (anno == null) {
            registerListenerClass("reiparticlesapi", listenerClass);
            return;
        }
        registerListenerClass(anno.modId(), listenerClass);
    }

    public void registerListenerClass(String modId, Class<?> listenerClass) {
        registerListenerInstance(modId, createListenerInstance(listenerClass));
    }

    public void registerListenerInstance(String modId, Object listener) {
        Objects.requireNonNull(modId, "modId");
        Objects.requireNonNull(listener, "listener");

        for (Method method : listener.getClass().getDeclaredMethods()) {
            EventHandler handler = method.getAnnotation(EventHandler.class);
            if (handler == null) {
                continue;
            }
            if (method.getParameterCount() != 1) {
                continue;
            }
            Class<?> paramType = method.getParameterTypes()[0];
            if (!ReiEvent.class.isAssignableFrom(paramType)) {
                continue;
            }
            method.setAccessible(true);
            @SuppressWarnings("unchecked")
            Class<? extends ReiEvent> eventType = (Class<? extends ReiEvent>) paramType;
            registerHandler(modId, listener, method, eventType, handler.priority());
        }
    }

    public int handlerCount(Class<? extends ReiEvent> eventType) {
        EnumMap<EventPriority, CopyOnWriteArrayList<EventExecutor>> bucket = handlerLists.get(eventType);
        if (bucket == null) {
            return 0;
        }
        int sum = 0;
        for (CopyOnWriteArrayList<EventExecutor> handlers : bucket.values()) {
            sum += handlers.size();
        }
        return sum;
    }

    public void clear() {
        handlerLists.clear();
        pendingListenersByMod.clear();
        initialized.set(false);
    }

    public static <T extends ReiEvent> T call(T event) {
        return INSTANCE.callEvent(event);
    }

    public <T extends ReiEvent> T callEvent(T event) {
        Objects.requireNonNull(event, "event");
        Class<?> currentEventType = event.getClass();

        while (currentEventType != null && ReiEvent.class.isAssignableFrom(currentEventType)) {
            @SuppressWarnings("unchecked")
            Class<? extends ReiEvent> eventClass = (Class<? extends ReiEvent>) currentEventType;
            EnumMap<EventPriority, CopyOnWriteArrayList<EventExecutor>> byPriority = handlerLists.get(eventClass);
            if (byPriority != null) {
                for (EventPriority priority : EventPriority.values()) {
                    CopyOnWriteArrayList<EventExecutor> executors = byPriority.get(priority);
                    if (executors == null || executors.isEmpty()) {
                        continue;
                    }
                    for (EventExecutor executor : executors) {
                        try {
                            executor.getExecutor().accept(event);
                        } catch (Throwable t) {
                            LOGGER.error("Failed handling event {} in mod {}", event.getClass().getName(), executor.getModId(), t);
                        }
                        if (event instanceof EventInterruptible interruptible && interruptible.isInterrupted()) {
                            return event;
                        }
                    }
                }
            }
            currentEventType = currentEventType.getSuperclass();
        }
        return event;
    }

    private void registerListenerClassName(String modId, String target) {
        try {
            Class<?> listenerClass = Class.forName(target);
            registerListenerClass(modId, listenerClass);
        } catch (Throwable t) {
            LOGGER.error("Failed to register listener class {} for mod {}", target, modId, t);
        }
    }

    private void registerHandler(
            String modId,
            Object listener,
            Method method,
            Class<? extends ReiEvent> eventType,
            EventPriority priority
    ) {
        EnumMap<EventPriority, CopyOnWriteArrayList<EventExecutor>> byPriority =
                handlerLists.computeIfAbsent(eventType, ignored -> new EnumMap<>(EventPriority.class));
        CopyOnWriteArrayList<EventExecutor> executors =
                byPriority.computeIfAbsent(priority, ignored -> new CopyOnWriteArrayList<>());
        MethodHandle handle = toMethodHandle(listener, method);
        executors.add(new EventExecutor(modId, event -> invokeHandle(handle, event)));
    }

    private static Object createListenerInstance(Class<?> listenerClass) {
        try {
            Field instanceField = listenerClass.getDeclaredField("INSTANCE");
            if (Modifier.isStatic(instanceField.getModifiers())) {
                instanceField.setAccessible(true);
                Object existing = instanceField.get(null);
                if (existing != null) {
                    return existing;
                }
            }
        } catch (NoSuchFieldException ignored) {
            // not a Kotlin object or no singleton instance field
        } catch (Throwable t) {
            throw new IllegalStateException("Failed to resolve INSTANCE for " + listenerClass.getName(), t);
        }

        try {
            var ctor = listenerClass.getDeclaredConstructor();
            ctor.setAccessible(true);
            return ctor.newInstance();
        } catch (Throwable t) {
            throw new IllegalStateException("Failed to instantiate listener " + listenerClass.getName(), t);
        }
    }

    private static MethodHandle toMethodHandle(Object listener, Method method) {
        try {
            return MethodHandles.lookup().unreflect(method).bindTo(listener);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException("Cannot create MethodHandle for " + method, e);
        }
    }

    private static void invokeHandle(MethodHandle handle, ReiEvent event) {
        try {
            handle.invoke(event);
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }
}
