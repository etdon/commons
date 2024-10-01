package com.etdon.commons.util;

import java.nio.charset.Charset;

public final class ByteUtils {

    public static final char[] DIGITS = {
            '0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'A', 'B',
            'C', 'D', 'E', 'F', 'G', 'H',
            'I', 'J', 'K', 'L', 'M', 'N',
            'O', 'P', 'Q', 'R', 'S', 'T',
            'U', 'V', 'W', 'X', 'Y', 'Z'
    };

    public static String byteToHexString(byte b) {

        final byte[] buffer = new byte[2];
        buffer[1] = (byte) DIGITS[b & 0xF];
        buffer[0] = (byte) DIGITS[b >>> 4 & 0xF];

        return new String(buffer, 0, 2, Charset.defaultCharset());

    }

    public static String bytesToHexString(final byte[] bytes) {

        final StringBuilder stringBuilder = new StringBuilder();
        for (final byte b : bytes)
            stringBuilder.append(byteToHexString(b));

        return stringBuilder.toString();

    }

    public static String byteToBinaryString(byte b) {

        return byteToBinaryString(b, 4);

    }

    public static String byteToBinaryString(byte b, final int split) {

        final StringBuilder binaryStringBuilder = new StringBuilder(Byte.SIZE);
        for (int i = 0; i < Byte.SIZE; i++) {
            binaryStringBuilder.append((b << i % Byte.SIZE & 0x80) == 0 ? '0' : '1');
            if ((i + 1) % split == 0)
                binaryStringBuilder.append(' ');
        }

        return binaryStringBuilder.toString();

    }

    public static int estimateVarLongByteSize(long input) {

        int byteCount = 0;
        if (input == 0)
            return 1;

        while (input != 0) {
            input >>>= 7;
            byteCount++;
        }

        return byteCount;

    }

    private ByteUtils() {

        throw new UnsupportedOperationException();

    }

}
