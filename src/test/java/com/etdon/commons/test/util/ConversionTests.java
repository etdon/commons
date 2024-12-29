package com.etdon.commons.test.util;

import com.etdon.commons.util.ByteUtils;
import com.etdon.commons.util.Conversion;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ConversionTests {

    @Test
    public void convertLongToByteArray_Example_Success() {

        final long input = 3229223L;
        final byte[] output = Conversion.convertLongToByteArray(input);
        assertEquals("0000000000314627", ByteUtils.bytesToHexString(output));

    }

    @Test
    public void convertByteArrayToLong_Example_Success() {

        final byte[] input = new byte[]{0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x13, 0x14};
        final long output = Conversion.convertByteArrayToLong(input);
        assertEquals(4884L, output);

    }

    @Test
    public void convertByteArrayToLong_InvalidSize_Throws() {

        final byte[] input = new byte[]{0x0};
        assertThrows(IllegalStateException.class, () -> Conversion.convertByteArrayToLong(input));

    }

}
