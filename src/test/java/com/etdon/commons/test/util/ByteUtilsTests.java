package com.etdon.commons.test.util;

import com.etdon.commons.util.ByteUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ByteUtilsTests {

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
