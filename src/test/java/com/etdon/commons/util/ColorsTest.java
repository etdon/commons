package com.etdon.commons.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @see Colors
 */
public class ColorsTest {

    @Test
    public void pack_RGB_Success() {

        final int red = 0b1111_1111;
        final int green = 0b1010_0101;
        final int blue = 0b0101_1010;
        assertEquals(0b0000_0000_1111_1111_1010_0101_0101_1010, Colors.pack(red, green, blue));

    }

    @Test
    public void unpack_RGB_Success() {

        final int[] colors = Colors.unpack(0b0000_0000_1111_1111_1010_0101_0101_1010);
        assertEquals(0b0000_0000, colors[0]);
        assertEquals(0b1111_1111, colors[1]);
        assertEquals(0b1010_0101, colors[2]);
        assertEquals(0b0101_1010, colors[3]);

    }

    @Test
    public void pack_ARGB_Success() {

        final int alpha = 0b0110_0110;
        final int red = 0b1111_1111;
        final int green = 0b1010_0101;
        final int blue = 0b0101_1010;
        assertEquals(0b0110_0110_1111_1111_1010_0101_0101_1010, Colors.pack(alpha, red, green, blue));

    }

    @Test
    public void unpack_ARGB_Success() {

        final int[] colors = Colors.unpack(0b0110_0110_1111_1111_1010_0101_0101_1010);
        assertEquals(0b0110_0110, colors[0]);
        assertEquals(0b1111_1111, colors[1]);
        assertEquals(0b1010_0101, colors[2]);
        assertEquals(0b0101_1010, colors[3]);

    }

}
