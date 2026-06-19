package com.etdon.commons.io;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @see ByteBuffer
 */
public class ByteBufferTest {

    @Test
    public void get_LeakInternalArray_Failure() {

        final ByteBuffer byteBuffer = ByteBuffer.size(10);
        byteBuffer.put(0x1);
        final byte[] bytes = byteBuffer.get();
        bytes[0] = 0x2;
        assertEquals((byte) 0x1, byteBuffer.get()[0]);

    }

    @Test
    public void contentEquals_Example_Success() {

        final byte[] bytes = new byte[]{0x1, 0x2, 0x3};
        final ByteBuffer byteBuffer = ByteBuffer.auto();
        byteBuffer.put((byte) 0x1, (byte) 0x2, (byte) 0x3);
        assertTrue(byteBuffer.contentEquals(bytes));

    }

}
