package com.etdon.commons.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @see ByteUtils
 */
public class ByteUtilsTest {

    @Test
    public void bytesToHexString_WithSplit_Success() {

        final byte[] input = {0xA, 0xF, 0x10, 0x5, 0x3};
        assertEquals("0A 0F 10 05 03", ByteUtils.bytesToHexString(input, 1));

    }

    @Test
    public void byteToBinaryString_WithSplit_Success() {

        assertEquals("0001 1111", ByteUtils.byteToBinaryString((byte) 0x1F, 4));

    }

}
