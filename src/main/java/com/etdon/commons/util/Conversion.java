package com.etdon.commons.util;

import com.etdon.commons.conditional.Preconditions;

/**
 * Utilities for data conversion.
 */
public final class Conversion {

    /**
     * Converts the provided long to a byte array storing it in big endian format.
     *
     * @param input the input
     * @return the byte array
     */
    public static byte[] longToByteArray(final long input) {

        final byte[] output = new byte[8];
        for (int i = 0; i < 7; i++)
            output[output.length - (i + 1)] = (byte) ((input >>> (i * 8)) & 0xFF);

        return output;

    }

    /**
     * Converts the provided byte array to a long reading it in big endian format.
     *
     * @param input the input
     * @return the byte array
     */
    public static long byteArrayToLong(final byte[] input) {

        Preconditions.checkState(input.length == 8, "The provided byte array does not have a length of 8.");
        long output = 0;
        for (int i = 0; i < 7; i++)
            output |= (long) input[input.length - (i + 1)] << (i * 8);

        return output;

    }

    private Conversion() {

        throw new UnsupportedOperationException();

    }

}
