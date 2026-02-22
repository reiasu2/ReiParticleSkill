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
package com.reiasu.reiparticlesapi.utils.storage;

import java.util.function.Supplier;

/**
 * Lazy-initialized memoization container.
 * The value is computed on first access via the supplier and cached.
 * Can be manually overridden or reset.
 *
 * @param <T> the cached value type
 */
public final class Memo<T> {
    private final Supplier<T> supplier;
    private T memo;

    public Memo(Supplier<T> supplier) {
        this.supplier = supplier;
    }

    public Supplier<T> getSupplier() {
        return supplier;
    }

    public T get() {
        if (memo == null) {
            resetMemo();
        }
        return memo;
    }

    public Memo<T> setMemoValue(T memo) {
        this.memo = memo;
        return this;
    }

    public Memo<T> resetMemo() {
        this.memo = supplier.get();
        return this;
    }
}
