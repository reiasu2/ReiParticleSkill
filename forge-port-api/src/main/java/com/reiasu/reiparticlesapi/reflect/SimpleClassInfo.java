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
package com.reiasu.reiparticlesapi.reflect;

import java.lang.annotation.Annotation;
import java.util.HashSet;

/**
 * Lightweight class metadata holder storing a class name and its annotation names.
 * Used by classpath scanning to defer class loading until needed.
 */
public final class SimpleClassInfo {

    private final String type;
    private final HashSet<String> annotations;

    public SimpleClassInfo(String type, HashSet<String> annotations) {
        this.type = type;
        this.annotations = annotations;
    }

    public String getType() {
        return type;
    }

    public HashSet<String> getAnnotations() {
        return annotations;
    }

    /**
     * Checks whether this class info contains the given annotation type.
     */
    public boolean isAnnotationPresent(Class<? extends Annotation> anno) {
        return annotations.contains(anno.getName());
    }

    /**
     * Loads and returns the actual {@link Class} represented by this info.
     *
     * @throws ClassNotFoundException if the class cannot be found
     */
    public Class<?> toClass() throws ClassNotFoundException {
        return Class.forName(type);
    }
}
