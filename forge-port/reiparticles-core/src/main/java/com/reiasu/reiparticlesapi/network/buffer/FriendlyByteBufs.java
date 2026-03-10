// SPDX-License-Identifier: LGPL-3.0-only
// Copyright (C) 2025 Reiasu
package com.reiasu.reiparticlesapi.network.buffer;

import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import net.minecraft.network.FriendlyByteBuf;

import java.util.function.Consumer;

public final class FriendlyByteBufs {
    private FriendlyByteBufs() {
    }

    public static byte[] encodeToByteArray(Consumer<FriendlyByteBuf> writer) {
        FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
        try {
            writer.accept(buf);
            return ByteBufUtil.getBytes(buf, buf.readerIndex(), buf.readableBytes(), true);
        } finally {
            buf.release();
        }
    }
}
