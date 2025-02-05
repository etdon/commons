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
     * Reads the current byte before advancing the internal offset.
     *
     * @return The byte.
     */

    public byte readByte() {

        Preconditions.checkState(this.offset < this.bytes.length, "The byte reader has reached the end of the internal byte array.");
        return this.bytes[this.offset++];

    }

    /**
     * Reads the current byte without advancing the internal offset.
     *
     * @return The byte.
     */

    public byte peekByte() {

        Preconditions.checkState(this.offset < this.bytes.length);
        return this.bytes[this.offset];

    }

    /**
     * Reads the byte at the provided offset and respectively advances the internal offset.
     *
     * @param offset The offset.
     * @return The byte.
     */

    public byte readOffsetByte(final int offset) {

        Preconditions.checkState(this.offset + offset < this.bytes.length);
        return this.bytes[this.offset += offset];

    }

    /**
     * Reads the byte at the provided offset without advancing the internal offset.
     *
     * @param offset The offset.
     * @return The byte.
     */

    public byte peekOffsetByte(final int offset) {

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

    public byte[] peekBytes(final int count) {

        Preconditions.checkState(this.offset + count - 1 < this.bytes.length, "The byte reader has reached the end of the internal byte array.");
        final byte[] bytes = new byte[count];
        System.arraycopy(this.bytes, this.offset, bytes, 0, count);

        return bytes;

    }

    /**
     * Reads the provided count of bytes at the provided offset from the internal byte array and respectively advances
     * the internal offset.
     *
     * @param count  The byte count.
     * @param offset The offset.
     * @return The bytes.
     */

    public byte[] readOffsetBytes(final int count, final int offset) {

        Preconditions.checkState(this.offset + offset + count - 1 < this.bytes.length);
        final byte[] bytes = new byte[count];
        System.arraycopy(this.bytes, this.offset + offset, bytes, 0, count);
        this.offset += offset + count;

        return bytes;

    }

    /**
     * Reads the provided count of bytes at the provided offset from the internal byte array without advancing the
     * internal offset.
     *
     * @param count  The byte count.
     * @param offset The offset.
     * @return The bytes.
     */

    public byte[] peekOffsetBytes(final int count, final int offset) {

        Preconditions.checkState(this.offset + offset + count - 1 < this.bytes.length);
        final byte[] bytes = new byte[count];
        System.arraycopy(this.bytes, this.offset + offset, bytes, 0, count);

        return bytes;

    }

    /**
     * Reads a boolean and advances the internal offset.
     *
     * @return The boolean.
     */

    public boolean readBoolean() {

        return this.readByte() == 0x1;

    }

    /**
     * Reads a boolean without advancing the internal offset.
     *
     * @return The boolean.
     */

    public boolean peekBoolean() {

        return this.peekByte() == 0x1;

    }

    //<editor-fold desc="Short">

    /**
     * Reads a short in big endian format and advances the internal offset.
     *
     * @return The short.
     */

    public short readBigEndianShort() {

        return (short) (this.readByte() << 8 | this.readByte() & 0xFF);

    }

    /**
     * Reads a short in big endian format without advancing the internal offset.
     *
     * @return The short.
     */

    public short peekBigEndianShort() {

        return (short) (this.peekByte() << 8 | this.peekOffsetByte(1) & 0xFF);

    }

    public short peekOffsetBigEndianShort(int offset) {

        return (short) (this.peekOffsetByte(offset++) << 8 | this.peekOffsetByte(offset) & 0xFF);

    }

    public short readLittleEndianShort() {

        return (short) (this.readByte() & 0xFF | (this.readByte() & 0xFF) << 8);

    }

    public short peekLittleEndianShort() {

        return (short) (this.peekByte() & 0xFF | (this.peekOffsetByte(1) & 0xFF) << 8);

    }

    public short peekOffsetLittleEndianShort(int offset) {

        return (short) (this.peekOffsetByte(offset++) & 0xFF | (this.peekOffsetByte(offset) & 0xFF) << 8);

    }
    //</editor-fold>

    //<editor-fold desc="Integer">
    public int readBigEndianInteger() {

        return (this.readByte() << 24 | (this.readByte() & 0xFF) << 16 | (this.readByte() & 0xFF) << 8 | this.readByte() & 0xFF);

    }

    public int peekBigEndianInteger() {

        return (this.peekOffsetByte(0) << 24 | (this.peekOffsetByte(1) & 0xFF) << 16 | (this.peekOffsetByte(2) & 0xFF) << 8 | this.peekOffsetByte(3) & 0xFF);

    }

    public int peekOffsetBigEndianInteger(int offset) {

        return (this.peekOffsetByte(offset++) << 24 | (this.peekOffsetByte(offset++) & 0xFF) << 16 | (this.peekOffsetByte(offset++) & 0xFF) << 8 | this.peekOffsetByte(offset) & 0xFF);

    }

    public int readLittleEndianInteger() {

        return (this.readByte() & 0xFF | (this.readByte() & 0xFF) << 8 | (this.readByte() & 0xFF) << 16 | this.readByte() << 24);

    }

    public int peekLittleEndianInteger() {

        return (this.peekOffsetByte(0) & 0xFF | (this.peekOffsetByte(1) & 0xFF) << 8 | (this.peekOffsetByte(2) & 0xFF) << 16 | this.peekOffsetByte(3) << 24);

    }

    public int peekOffsetLittleEndianInteger(int offset) {

        return (this.peekOffsetByte(offset++) & 0xFF | (this.peekOffsetByte(offset++) & 0xFF) << 8 | (this.peekOffsetByte(offset++) & 0xFF) << 16 | this.peekOffsetByte(offset) << 24);

    }
    //</editor-fold>

    //<editor-fold desc="Long">
    public long readBigEndianLong() {

        return ((long) this.readBigEndianInteger() << 32) | (long) this.readBigEndianInteger() & 0xFFFFFFFFL;

    }

    public long peekBigEndianLong() {

        return ((long) this.peekBigEndianInteger() << 32) | (long) this.peekOffsetBigEndianInteger(4) & 0xFFFFFFFFL;

    }

    public long peekOffsetBigEndianLong(int offset) {

        return ((long) this.peekOffsetBigEndianInteger(offset += 4) << 32) | (long) this.peekOffsetBigEndianInteger(offset) & 0xFFFFFFFFL;

    }

    public long readLittleEndianLong() {

        return (long) this.readLittleEndianInteger() & 0xFFFFFFFFL | ((long) this.readLittleEndianInteger() << 32);

    }

    public long peekLittleEndianLong() {

        return (long) this.peekLittleEndianInteger() & 0xFFFFFFFFL | ((long) this.peekOffsetLittleEndianInteger(4) << 32);

    }

    public long peekOffsetLittleEndianLong(int offset) {

        return (long) this.peekOffsetLittleEndianInteger(offset += 4) & 0xFFFFFFFFL | ((long) this.peekOffsetLittleEndianInteger(offset) << 32);

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
