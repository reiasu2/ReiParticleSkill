// SPDX-License-Identifier: LGPL-3.0-only
// Copyright (C) 2025 Reiasu
package com.reiasu.reiparticlesapi.testutil;

import org.slf4j.Logger;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

public final class RecordingLogger {
    private final List<LogEvent> events = new ArrayList<>();
    private final Logger logger = (Logger) Proxy.newProxyInstance(
            Logger.class.getClassLoader(),
            new Class<?>[]{Logger.class},
            (proxy, method, args) -> {
                String name = method.getName();
                if (name.equals("warn") || name.equals("info") || name.equals("debug") || name.equals("error")) {
                    String template = args != null && args.length > 0 && args[0] instanceof String s ? s : "";
                    Object[] payload = args == null || args.length <= 1
                            ? new Object[0]
                            : java.util.Arrays.copyOfRange(args, 1, args.length);
                    events.add(new LogEvent(name, template, payload));
                    return null;
                }
                if (method.getReturnType() == boolean.class) {
                    return true;
                }
                return null;
            });

    public Logger logger() {
        return logger;
    }

    public List<LogEvent> events() {
        return events;
    }

    public boolean hasEvent(String level, String templateFragment) {
        return events.stream().anyMatch(event -> event.level().equals(level) && event.template().contains(templateFragment));
    }

    public record LogEvent(String level, String template, Object[] arguments) {
    }
}
