package com.etdon.commons.test.io;

import com.etdon.commons.io.VariableLength;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class VariableLengthTests {

    @Test
    public void readUnsigned_MultiByte_Success() {

        assertEquals(16384, VariableLength.readUnsigned(new byte[]{(byte) 0x80, (byte) 0x80, 0x01}));

    }

    @Test
    public void readUnsigned_ContinuationOnLastByte_Throws() {

        assertThrows(IllegalStateException.class, () -> VariableLength.readUnsigned(new byte[]{(byte) 0x80}));

    }

    @Test
    public void readUnsigned_Overlong_Throws() {

        final byte[] input = {(byte) 0x80, (byte) 0x80, (byte) 0x80, (byte) 0x80, (byte) 0x80, (byte) 0x80, (byte) 0x80, (byte) 0x80, (byte) 0x80, (byte) 0x80, (byte) 0x80};
        assertThrows(IllegalStateException.class, () -> VariableLength.readUnsigned(input));

    }

    @Test
    public void readSigned_Overlong_Throws() {

        final byte[] input = {(byte) 0x80, (byte) 0x80, (byte) 0x80, (byte) 0x80, (byte) 0x80, (byte) 0x80, (byte) 0x80, (byte) 0x80, (byte) 0x80, (byte) 0x80, (byte) 0x80};
        assertThrows(IllegalStateException.class, () -> VariableLength.readSigned(input));

    }

    @Test
    public void unsigned_RoundTrip_Success() {

        final long[] values = {0, 1, 127, 128, 255, 300, 16384, Integer.MAX_VALUE, Long.MAX_VALUE, -1};
        for (final long value : values)
            assertEquals(value, VariableLength.readUnsigned(VariableLength.writeUnsigned(value)));

    }

    @Test
    public void writeUnsigned_Zero_Success() {

        assertArrayEquals(new byte[]{0x00}, VariableLength.writeUnsigned(0));

    }

    @Test
    public void writeUnsigned_SingleByte_Success() {

        assertArrayEquals(new byte[]{0x7F}, VariableLength.writeUnsigned(127));

    }

    @Test
    public void writeUnsigned_MultiByte_Success() {

        assertArrayEquals(new byte[]{(byte) 0x80, (byte) 0x80, 0x01}, VariableLength.writeUnsigned(16384));

    }


    @Test
    public void readSigned_Negative_Success() {

        assertEquals(-1, VariableLength.readSigned(new byte[]{0x7F}));
        assertEquals(-128, VariableLength.readSigned(new byte[]{(byte) 0x80, 0x7F}));

    }

    @Test
    public void readSigned_ContinuationOnLastByte_Throws() {

        assertThrows(IllegalStateException.class, () -> VariableLength.readSigned(new byte[]{(byte) 0x80}));

    }

    @Test
    public void signed_RoundTrip_Success() {

        final long[] values = {0, 1, -1, 63, 64, -64, -65, 127, 128, -128, -129, Integer.MIN_VALUE, Long.MIN_VALUE, Long.MAX_VALUE};
        for (final long value : values)
            assertEquals(value, VariableLength.readSigned(VariableLength.writeSigned(value)));

    }

    @Test
    public void writeSigned_Zero_Success() {

        assertArrayEquals(new byte[]{0x00}, VariableLength.writeSigned(0));

    }

    @Test
    public void writeSigned_PositiveWithSignBit_Success() {

        assertArrayEquals(new byte[]{(byte) 0xC0, 0x00}, VariableLength.writeSigned(64));

    }

    @Test
    public void writeSigned_Negative_Success() {

        assertArrayEquals(new byte[]{0x7F}, VariableLength.writeSigned(-1));
        assertArrayEquals(new byte[]{(byte) 0x80, 0x7F}, VariableLength.writeSigned(-128));

    }

}
