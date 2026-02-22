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
package com.reiasu.reiparticlesapi.utils;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A fixed-capacity circular queue (ring buffer).
 * When full, new elements overwrite the oldest entries.
 */
public final class CircularQueue<T> implements Iterable<T> {
    private final int capacity;
    private final Object[] data;
    private int size;
    private int head;

    public CircularQueue(int capacity) {
        this.capacity = capacity;
        this.data = new Object[capacity];
    }

    public int getCapacity() {
        return capacity;
    }

    public void addFirst(T value) {
        if (size < capacity) {
            data[head++] = value;
            size++;
        } else {
            head %= capacity;
            data[head++] = value;
        }
    }

    @SuppressWarnings("unchecked")
    public T get(int index) {
        if (index >= size) {
            throw new IndexOutOfBoundsException(
                    "Index out of bounds: size=" + size + ", index=" + index);
        }
        int i = size < capacity ? index : (index + head) % capacity;
        return (T) data[i];
    }

    public boolean empty() {
        return size == 0;
    }

    public int notNullSize() {
        return size;
    }

    @Override
    public Iterator<T> iterator() {
        return new CircularIterator();
    }

    private class CircularIterator implements Iterator<T> {
        private int current = 0;

        @Override
        public boolean hasNext() {
            return current < size;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return get(current++);
        }
    }
}
