package com.etdon.commons.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @see Bytes
 */
public class BytesTest {

    @Test
    public void toHexString_WithSplit_Success() {

        final byte[] input = {0xA, 0xF, 0x10, 0x5, 0x3};
        assertEquals("0A 0F 10 05 03", Bytes.toHexString(input, 1));

    }

    @Test
    public void toBinaryString_WithSplit_Success() {

        assertEquals("0001 1111", Bytes.toBinaryString((byte) 0x1F, 4));

    }

}
