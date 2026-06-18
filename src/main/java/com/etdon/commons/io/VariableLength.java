package com.etdon.commons.io;

import com.etdon.commons.conditional.Preconditions;

/**
 * Provides methods to decode and encode signed and unsigned integers in the LEB128 encoding format. Integers encoded
 * in this format are commonly referred to as "VarInts" and are often used to achieve lossless data compression in
 * scenarios where values are expected to be relatively small compared to the maximum value of their respective type.
 * <p>
 * This implementation currently does <b>not</b> support {@link java.math.BigInteger} values.
 */
public final class VariableLength {

    /**
     * Each segment contains 7 bits of data stored in the least significant positions.
     */
    private static final byte SEGMENT_MASK = 0b0111_1111;
    /**
     * The most significant bit indicates that the value is continued with the following byte in the sequence.
     */
    private static final byte CONTINUATION_BIT = (byte) 0b1000_0000;
    /**
     * The sign bit is the second high-order bit of the last byte in the sequence if the value ends abnormally and
     * indicates wherever or not the value is negative.
     */
    private static final byte SIGN_BIT = 0b0100_0000;
    /**
     * The segment size used for shifting matching the {@link VariableLength#SEGMENT_MASK}.
     */
    private static final int SHIFT = 7;

    /**
     * Decodes the provided unsigned input variable length byte array into a long. If the size of the input array is
     * <code>0</code> this method returns <code>0</code> instantly.
     *
     * @param input the input byte array
     * @return the decoded value
     */
    public static long readUnsigned(final byte[] input) {

        if (input.length == 0)
            return 0;

        long result = 0;
        int index = 0, shift = 0;
        byte current;
        do {
            Preconditions.checkState(index < input.length, "Invalid input: continuation bit set on the last byte of the sequence.");
            Preconditions.checkState(shift < Long.SIZE, "Invalid input: the encoded value exceeds the maximum length of a long.");
            current = input[index++];
            result |= (long) (current & SEGMENT_MASK) << shift;
            shift += SHIFT;
        } while ((current & CONTINUATION_BIT) != 0);

        return result;

    }

    /**
     * Encodes the provided unsigned input into a variable length byte array. The size of the byte array is trimmed to
     * exact needs.
     *
     * @param input the input
     * @return the encoded byte array
     */
    public static byte[] writeUnsigned(long input) {

        if (input == 0)
            return new byte[1];

        final ByteBuffer byteBuffer = ByteBuffer.size(10);
        byte current;
        do {
            current = (byte) (input & SEGMENT_MASK);
            input >>>= SHIFT;
            if (input != 0)
                current |= CONTINUATION_BIT;
            byteBuffer.put(current);
        } while (input != 0);

        return byteBuffer.get();

    }

    /**
     * Decodes the provided signed input variable length byte array into a long. If the size of the input array is
     * <code>0</code> this method returns <code>0</code> instantly.
     *
     * @param input the input byte array
     * @return the decoded value
     */
    public static long readSigned(final byte[] input) {

        if (input.length == 0)
            return 0;

        long result = 0;
        int index = 0, shift = 0;
        byte current;
        do {
            Preconditions.checkState(index < input.length, "Invalid input: continuation bit set on the last byte of the sequence.");
            Preconditions.checkState(shift < Long.SIZE, "Invalid input: the encoded value exceeds the maximum length of a long.");
            current = input[index++];
            result |= (long) (current & SEGMENT_MASK) << shift;
            shift += SHIFT;
        } while ((current & CONTINUATION_BIT) != 0);

        if (shift < Long.SIZE && (current & SIGN_BIT) != 0)
            result |= -(1L << shift);

        return result;

    }

    /**
     * Encodes the provided signed input into a variable length byte array. The size of the byte array is trimmed to
     * exact needs.
     *
     * @param input the input
     * @return the encoded byte array
     */
    public static byte[] writeSigned(long input) {

        if (input == 0)
            return new byte[1];

        final ByteBuffer byteBuffer = ByteBuffer.size(10);
        boolean more = true;
        byte current;
        while (more) {
            current = (byte) (input & SEGMENT_MASK);
            input >>= SHIFT;
            if ((input == 0 && (current & SIGN_BIT) == 0) || (input == -1 && (current & SIGN_BIT) != 0)) {
                more = false;
            } else {
                current |= CONTINUATION_BIT;
            }
            byteBuffer.put(current);
        }

        return byteBuffer.get();

    }

    private VariableLength() {

        throw new UnsupportedOperationException();

    }

}
