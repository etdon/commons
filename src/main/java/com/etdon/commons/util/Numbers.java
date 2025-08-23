package com.etdon.commons.util;

import com.etdon.commons.conditional.Preconditions;
import org.jetbrains.annotations.NotNull;

public final class Numbers {

    @NotNull
    public static String toHexString(@NotNull final Number input) {

        Preconditions.checkNotNull(input);
        final StringBuilder stringBuilder = new StringBuilder();
        final long value = input.longValue();
        for (int i = 7; i >= 0; i--) {
            final byte segment = (byte) ((value >>> (i * 8)) & 0xFF);
            if (stringBuilder.length() == 0 && segment == 0) continue;
            stringBuilder.append(ByteUtils.byteToHexString(segment));
        }

        return stringBuilder.toString();

    }

    private Numbers() {

        throw new UnsupportedOperationException();

    }

}
