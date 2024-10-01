package com.etdon.commons.util;

public final class ColorUtils {

    public static int compress(final int red, final int green, final int blue) {

        return ((blue & 0xFF) << 16) | ((green & 0xFF) << 8) | (red & 0xFF);

    }

    private ColorUtils() {

        throw new UnsupportedOperationException();

    }

}
