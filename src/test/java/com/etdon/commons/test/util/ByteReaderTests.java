package com.etdon.commons.test.util;

import com.etdon.commons.io.ByteOrder;
import com.etdon.commons.io.ByteReader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ByteReaderTests {

    @Test
    public void explore_Example_Success() {

        final byte[] input = new byte[]{0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0A};
        final ByteReader byteReader = ByteReader.of(input);
        byteReader.skip(6);
        assertEquals(ByteOrder.LITTLE_ENDIAN, byteReader.getByteOrder());
        assertEquals(6, byteReader.getOffset());
        try (final ByteReader.Explorer explorer = byteReader.explore(ByteOrder.BIG_ENDIAN, 2)) {
            assertEquals(ByteOrder.BIG_ENDIAN, byteReader.getByteOrder());
            assertEquals(6, explorer.getRetreatOffset());
            assertEquals(2, byteReader.getOffset());
        }
        assertEquals(ByteOrder.LITTLE_ENDIAN, byteReader.getByteOrder());
        assertEquals(6, byteReader.getOffset());

    }

}
