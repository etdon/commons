package com.etdon.commons.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @see Conversion
 */
public class ConversionTest {

    @Test
    public void longToByteArray_Example_Success() {

        final long input = 3229223L;
        final byte[] output = Conversion.longToByteArray(input);
        assertEquals("0000000000314627", Bytes.toHexString(output));

    }

    @Test
    public void byteArrayToLong_Example_Success() {

        final byte[] input = new byte[]{0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x13, 0x14};
        final long output = Conversion.byteArrayToLong(input);
        assertEquals(4884L, output);

    }

    @Test
    public void byteArrayToLong_InvalidSize_Throws() {

        final byte[] input = new byte[]{0x0};
        assertThrows(IllegalStateException.class, () -> Conversion.byteArrayToLong(input));

    }

}
