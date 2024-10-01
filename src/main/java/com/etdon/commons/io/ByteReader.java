package com.etdon.commons.io;

import com.etdon.commons.conditional.Preconditions;

public class ByteReader {

    private final byte[] bytes;
    private int offset = 0;

    public ByteReader(final byte[] bytes) {

        this.bytes = bytes;

    }

    /**
     * Adds the provided count of bytes to the internal offset.
     *
     * @param count The byte count.
     */

    public void skip(final int count) {

        Preconditions.checkState(this.offset + count < this.bytes.length, "The provided byte count ({}) added to the internal offset ({}) is too large for the internal byte array (length: {})", count, this.offset, this.bytes.length);
        this.offset += count;

    }

    /**
     * Sets the internal offset to the provided offset.
     *
     * @param offset The offset.
     */

    public void jump(final int offset) {

        Preconditions.checkState(offset < this.bytes.length, "The provided offset ({}) is too large for the internal byte array (length: {}).", this.offset, this.bytes.length);
        this.offset = offset;

    }

    /**
     * Reads the current byte and returns it before advancing the internal offset.
     *
     * @return The byte.
     */

    public byte readByte() {

        Preconditions.checkState(this.offset < this.bytes.length, "The byte reader has reached the end of the internal byte array.");
        return this.bytes[this.offset++];

    }

    /**
     * Reads the current byte and returns it without advancing the internal offset.
     *
     * @return The byte.
     */

    public byte peakByte() {

        Preconditions.checkState(this.offset < this.bytes.length);
        return this.bytes[this.offset];

    }

    public byte readOffsetByte(final int offset) {

        Preconditions.checkState(this.offset + offset < this.bytes.length);
        return this.bytes[this.offset += offset];

    }

    public byte peakOffsetByte(final int offset) {

        Preconditions.checkState(this.offset + offset < this.bytes.length);
        return this.bytes[this.offset + offset];

    }

    /**
     * Reads the provided count of bytes from the internal byte array before advancing the internal offset.
     *
     * @param count The byte count.
     * @return The bytes.
     */

    public byte[] readBytes(final int count) {

        Preconditions.checkState(this.offset + count - 1 < this.bytes.length, "The byte reader has reached the end of the internal byte array.");
        final byte[] bytes = new byte[count];
        System.arraycopy(this.bytes, this.offset, bytes, 0, count);
        this.offset += count;

        return bytes;

    }

    /**
     * Reads the provided count of bytes from the internal byte array without advancing the internal offset.
     *
     * @param count The byte count.
     * @return The bytes.
     */

    public byte[] peakBytes(final int count) {

        Preconditions.checkState(this.offset + count - 1 < this.bytes.length, "The byte reader has reached the end of the internal byte array.");
        final byte[] bytes = new byte[count];
        System.arraycopy(this.bytes, this.offset, bytes, 0, count);

        return bytes;

    }

    public byte[] readOffsetBytes(final int count, final int offset) {

        Preconditions.checkState(this.offset + offset + count - 1 < this.bytes.length);
        final byte[] bytes = new byte[count];
        System.arraycopy(this.bytes, this.offset + offset, bytes, 0, count);
        this.offset += offset + count;

        return bytes;

    }

    public byte[] peakOffsetBytes(final int count, final int offset) {

        Preconditions.checkState(this.offset + offset + count - 1 < this.bytes.length);
        final byte[] bytes = new byte[count];
        System.arraycopy(this.bytes, this.offset + offset, bytes, 0, count);

        return bytes;

    }

    public boolean readBoolean() {

        return this.readByte() == 0x1;

    }

    public boolean peakBoolean() {

        return this.peakByte() == 0x1;

    }

    public short readBigEndianShort() {

        return (short) (this.readByte() << 8 | this.readByte() & 0xFF);

    }

    public short peakBigEndianShort() {

        return (short) (this.peakByte() << 8 | this.peakOffsetByte(1) & 0xFF);

    }

    public short readLittleEndianShort() {

        return (short) (this.readByte() & 0xFF | (this.readByte() & 0xFF) << 8);

    }

    public short peakLittleEndianShort() {

        return (short) (this.peakByte() & 0xFF | (this.peakOffsetByte(1) & 0xFF) << 8);

    }

    /**
     * Returns the internal offset.
     *
     * @return The internal offset.
     */

    public int getOffset() {

        return this.offset;

    }

}
