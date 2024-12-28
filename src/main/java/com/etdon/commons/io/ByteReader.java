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

    //<editor-fold desc="Short">
    public short readBigEndianShort() {

        return (short) (this.readByte() << 8 | this.readByte() & 0xFF);

    }

    public short peakBigEndianShort() {

        return (short) (this.peakByte() << 8 | this.peakOffsetByte(1) & 0xFF);

    }

    public short peakOffsetBigEndianShort(int offset) {

        return (short) (this.peakOffsetByte(offset++) << 8 | this.peakOffsetByte(offset) & 0xFF);

    }

    public short readLittleEndianShort() {

        return (short) (this.readByte() & 0xFF | (this.readByte() & 0xFF) << 8);

    }

    public short peakLittleEndianShort() {

        return (short) (this.peakByte() & 0xFF | (this.peakOffsetByte(1) & 0xFF) << 8);

    }

    public short peakOffsetLittleEndianShort(int offset) {

        return (short) (this.peakOffsetByte(offset++) & 0xFF | (this.peakOffsetByte(offset) & 0xFF) << 8);

    }
    //</editor-fold>

    //<editor-fold desc="Integer">
    public int readBigEndianInteger() {

        return (this.readByte() << 24 | (this.readByte() & 0xFF) << 16 | (this.readByte() & 0xFF) << 8 | this.readByte() & 0xFF);

    }

    public int peakBigEndianInteger() {

        return (this.peakOffsetByte(0) << 24 | (this.peakOffsetByte(1) & 0xFF) << 16 | (this.peakOffsetByte(2) & 0xFF) << 8 | this.peakOffsetByte(3) & 0xFF);

    }

    public int peakOffsetBigEndianInteger(int offset) {

        return (this.peakOffsetByte(offset++) << 24 | (this.peakOffsetByte(offset++) & 0xFF) << 16 | (this.peakOffsetByte(offset++) & 0xFF) << 8 | this.peakOffsetByte(offset) & 0xFF);

    }

    public int readLittleEndianInteger() {

        return (this.readByte() & 0xFF | (this.readByte() & 0xFF) << 8 | (this.readByte() & 0xFF) << 16 | this.readByte() << 24);

    }

    public int peakLittleEndianInteger() {

        return (this.peakOffsetByte(0) & 0xFF | (this.peakOffsetByte(1) & 0xFF) << 8 | (this.peakOffsetByte(2) & 0xFF) << 16 | this.peakOffsetByte(3) << 24);

    }

    public int peakOffsetLittleEndianInteger(int offset) {

        return (this.peakOffsetByte(offset++) & 0xFF | (this.peakOffsetByte(offset++) & 0xFF) << 8 | (this.peakOffsetByte(offset++) & 0xFF) << 16 | this.peakOffsetByte(offset) << 24);

    }
    //</editor-fold>

    //<editor-fold desc="Long">
    public long readBigEndianLong() {

        return ((long) this.readBigEndianInteger() << 32) | (long) this.readBigEndianInteger() & 0xFFFFFFFFL;

    }

    public long peakBigEndianLong() {

        return ((long) this.peakBigEndianInteger() << 32) | (long) this.peakOffsetBigEndianInteger(4) & 0xFFFFFFFFL;

    }

    public long peakOffsetBigEndianLong(int offset) {

        return ((long) this.peakOffsetBigEndianInteger(offset += 4) << 32) | (long) this.peakOffsetBigEndianInteger(offset) & 0xFFFFFFFFL;

    }

    public long readLittleEndianLong() {

        return (long) this.readLittleEndianInteger() & 0xFFFFFFFFL | ((long) this.readLittleEndianInteger() << 32);

    }

    public long peakLittleEndianLong() {

        return (long) this.peakLittleEndianInteger() & 0xFFFFFFFFL | ((long) this.peakOffsetLittleEndianInteger(4) << 32);

    }

    public long peakOffsetLittleEndianLong(int offset) {

        return (long) this.peakOffsetLittleEndianInteger(offset += 4) & 0xFFFFFFFFL | ((long) this.peakOffsetLittleEndianInteger(offset) << 32);

    }
    //</editor-fold>

    /**
     * Returns the internal offset.
     *
     * @return The internal offset.
     */

    public int getOffset() {

        return this.offset;

    }

    public static ByteReader from(final byte[] bytes) {

        return new ByteReader(bytes);

    }

}
