package com.etdon.commons.util;

import java.nio.charset.Charset;

/**
 * Utilities for byte work.
 */
public final class Bytes {

    public static final char[] DIGITS = {
            '0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'A', 'B',
            'C', 'D', 'E', 'F', 'G', 'H',
            'I', 'J', 'K', 'L', 'M', 'N',
            'O', 'P', 'Q', 'R', 'S', 'T',
            'U', 'V', 'W', 'X', 'Y', 'Z'
    };

    /**
     * Converts the provided <code>byte</code> to its two character hexadecimal string representation.
     *
     * @param b the <code>byte</code> value
     * @return the hexadecimal string representation
     */
    public static String toHexString(byte b) {

        final byte[] buffer = new byte[2];
        buffer[1] = (byte) DIGITS[b & 0xF];
        buffer[0] = (byte) DIGITS[b >>> 4 & 0xF];

        return new String(buffer, 0, 2, Charset.defaultCharset());

    }

    /**
     * Converts the provided <code>byte</code> array to its hexadecimal string representation.
     *
     * @param bytes the <code>byte</code> array
     * @return the hexadecimal string representation
     */
    public static String toHexString(final byte[] bytes) {

        final StringBuilder hexStringBuilder = new StringBuilder();
        for (final byte b : bytes)
            hexStringBuilder.append(toHexString(b));

        return hexStringBuilder.toString();

    }

    /**
     * Converts the provided <code>byte</code> array to its hexadecimal string representation inserting a space after
     * every provided amount of bytes.
     *
     * @param bytes the <code>byte</code> array
     * @param split the amount of bytes after which a space is inserted
     * @return the hexadecimal string representation
     */
    public static String toHexString(final byte[] bytes, final int split) {

        final StringBuilder hexStringBuilder = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            hexStringBuilder.append(toHexString(bytes[i]));
            if ((i + 1) % split == 0 && (i + 1) < bytes.length)
                hexStringBuilder.append(' ');
        }

        return hexStringBuilder.toString();

    }

    /**
     * Converts the provided <code>byte</code> to its binary string representation inserting a space after every four
     * bits. Convenience overload of {@link Bytes#toBinaryString(byte, int)} using a split of <code>4</code>.
     *
     * @param b the <code>byte</code> value
     * @return the binary string representation
     */
    public static String toBinaryString(byte b) {

        return toBinaryString(b, 4);

    }

    /**
     * Converts the provided <code>byte</code> to its binary string representation inserting a space after every
     * provided amount of bits.
     *
     * @param b     the <code>byte</code> value
     * @param split the amount of bits after which a space is inserted
     * @return the binary string representation
     */
    public static String toBinaryString(byte b, final int split) {

        final StringBuilder binaryStringBuilder = new StringBuilder(Byte.SIZE);
        for (int i = 0; i < Byte.SIZE; i++) {
            binaryStringBuilder.append((b << i % Byte.SIZE & 0x80) == 0 ? '0' : '1');
            if ((i + 1) % split == 0 && (i + 1) < Byte.SIZE)
                binaryStringBuilder.append(' ');
        }

        return binaryStringBuilder.toString();

    }

    private Bytes() {

        throw new UnsupportedOperationException();

    }

}
