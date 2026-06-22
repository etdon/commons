package com.etdon.commons.io;

import com.etdon.commons.conditional.Preconditions;
import org.jetbrains.annotations.NotNullByDefault;

/**
 * Byte reader implementation used to sequentially read primitive types from a byte array in the endianness of choice.
 * Read methods advance the internal offset while peek methods leave it untouched.
 */
@NotNullByDefault
public class ByteReader {

    private ByteOrder byteOrder = ByteOrder.LITTLE_ENDIAN;
    private final byte[] bytes;
    private int offset = 0;

    /**
     * Creates a new byte reader for the provided byte array using {@link ByteOrder#LITTLE_ENDIAN}.
     *
     * @param bytes the byte array
     */
    public ByteReader(final byte[] bytes) {

        this.bytes = bytes;

    }

    /**
     * Creates a new byte reader for the provided byte array using the provided byte order.
     *
     * @param byteOrder the byte order
     * @param bytes     the byte array
     */
    public ByteReader(final ByteOrder byteOrder,
                      final byte[] bytes) {

        Preconditions.checkNotNull(byteOrder);
        this.byteOrder = byteOrder;
        this.bytes = bytes;

    }

    /**
     * Adds the provided count of bytes to the internal offset.
     *
     * @param count the byte count
     */
    public void skip(final int count) {

        Preconditions.checkState(this.offset + count < this.bytes.length, "The provided byte count ({}) added to the internal offset ({}) is too large for the internal byte array (length: {})", count, this.offset, this.bytes.length);
        this.offset += count;

    }

    /**
     * Sets the internal offset to the provided offset.
     *
     * @param offset the offset
     */
    public void jump(final int offset) {

        Preconditions.checkState(offset < this.bytes.length, "The provided offset ({}) is too large for the internal byte array (length: {}).", offset, this.bytes.length);
        this.offset = offset;

    }

    /**
     * Creates a new auto-closeable explorer session that can be used to jump to a specific offset and automatically
     * jump back to the previous offset once it's out of try-with scope or closed manually.
     *
     * @param offset the target offset
     * @return the auto-closeable explorer session
     */
    public Explorer explore(final int offset) {

        return Explorer.of(this, offset);

    }

    /**
     * Creates a new auto-closeable explorer session that can be used to change the byte order and automatically jump
     * back to the previous one once it's out of try-with scope or closed manually.
     *
     * @param byteOrder the target byte order
     * @return the auto-closeable explorer session
     */
    public Explorer explore(final ByteOrder byteOrder) {

        return Explorer.of(this, byteOrder);

    }

    /**
     * Creates a new auto-closeable explorer session that can be used to jump to a specific offset as well as change
     * the byte order and automatically jump back to the previous offset as well as change back the byte order once
     * it's out of try-with scope or closed manually.
     *
     * @param byteOrder the target byte order
     * @param offset    the target offset
     * @return the auto-closeable explorer session
     */
    public Explorer explore(final ByteOrder byteOrder, final int offset) {

        return Explorer.of(this, byteOrder, offset);

    }

    /**
     * Reads the current byte before advancing the internal offset.
     *
     * @return the byte
     */
    public byte readByte() {

        Preconditions.checkState(this.offset < this.bytes.length, "The byte reader has reached the end of the internal byte array.");
        return this.bytes[this.offset++];

    }

    /**
     * Reads the current byte without advancing the internal offset.
     *
     * @return the byte
     */
    public byte peekByte() {

        Preconditions.checkState(this.offset < this.bytes.length);
        return this.bytes[this.offset];

    }

    /**
     * Reads the byte at the provided offset and respectively advances the internal offset.
     *
     * @param offset the offset
     * @return the byte
     */
    public byte readOffsetByte(final int offset) {

        Preconditions.checkState(this.offset + offset < this.bytes.length);
        return this.bytes[this.offset += offset];

    }

    /**
     * Reads the byte at the provided offset without advancing the internal offset.
     *
     * @param offset the offset
     * @return the byte
     */
    public byte peekOffsetByte(final int offset) {

        Preconditions.checkState(this.offset + offset < this.bytes.length);
        return this.bytes[this.offset + offset];

    }

    /**
     * Reads the provided count of bytes from the internal byte array before advancing the internal offset.
     *
     * @param count the byte count
     * @return the bytes
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
     * @param count the byte count
     * @return the bytes
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
     * @param count  the byte count
     * @param offset the offset
     * @return the bytes
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
     * @param count  the byte count
     * @param offset the offset
     * @return the bytes
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
     * @return the boolean
     */
    public boolean readBoolean() {

        return this.readByte() == 0x01;

    }

    /**
     * Reads a boolean without advancing the internal offset.
     *
     * @return the boolean
     */
    public boolean peekBoolean() {

        return this.peekByte() == 0x01;

    }

    /**
     * Reads a short and advances the internal offset.
     *
     * @return the short
     */
    public short readShort() {

        if (this.byteOrder == ByteOrder.LITTLE_ENDIAN) {
            return (short) (this.readByte() & 0xFF | (this.readByte() & 0xFF) << 8);
        } else {
            return (short) (this.readByte() << 8 | this.readByte() & 0xFF);
        }

    }

    /**
     * Reads a short without advancing the internal offset.
     *
     * @return the short
     */
    public short peekShort() {

        if (this.byteOrder == ByteOrder.LITTLE_ENDIAN) {
            return (short) (this.peekByte() & 0xFF | (this.peekOffsetByte(1) & 0xFF) << 8);
        } else {
            return (short) (this.peekByte() << 8 | this.peekOffsetByte(1) & 0xFF);
        }

    }

    /**
     * Reads a short from the provided offset without advancing the internal offset.
     *
     * @param offset the offset
     * @return the short
     */
    public short peekOffsetShort(int offset) {

        if (this.byteOrder == ByteOrder.LITTLE_ENDIAN) {
            return (short) (this.peekOffsetByte(offset++) & 0xFF | (this.peekOffsetByte(offset) & 0xFF) << 8);
        } else {
            return (short) (this.peekOffsetByte(offset++) << 8 | this.peekOffsetByte(offset) & 0xFF);
        }

    }

    /**
     * Reads an integer and advances the internal offset.
     *
     * @return the integer
     */
    public int readInteger() {

        if (this.byteOrder == ByteOrder.LITTLE_ENDIAN) {
            return (this.readByte() & 0xFF | (this.readByte() & 0xFF) << 8 | (this.readByte() & 0xFF) << 16 | this.readByte() << 24);
        } else {
            return (this.readByte() << 24 | (this.readByte() & 0xFF) << 16 | (this.readByte() & 0xFF) << 8 | this.readByte() & 0xFF);
        }

    }

    /**
     * Reads an integer without advancing the internal offset.
     *
     * @return the integer
     */
    public int peekInteger() {

        if (this.byteOrder == ByteOrder.LITTLE_ENDIAN) {
            return (this.peekOffsetByte(0) & 0xFF | (this.peekOffsetByte(1) & 0xFF) << 8 | (this.peekOffsetByte(2) & 0xFF) << 16 | this.peekOffsetByte(3) << 24);
        } else {
            return (this.peekOffsetByte(0) << 24 | (this.peekOffsetByte(1) & 0xFF) << 16 | (this.peekOffsetByte(2) & 0xFF) << 8 | this.peekOffsetByte(3) & 0xFF);
        }

    }

    /**
     * Reads an integer from the provided offset without advancing the internal offset.
     *
     * @param offset the offset
     * @return the integer
     */
    public int peekOffsetInteger(int offset) {

        if (this.byteOrder == ByteOrder.LITTLE_ENDIAN) {
            return (this.peekOffsetByte(offset++) & 0xFF | (this.peekOffsetByte(offset++) & 0xFF) << 8 | (this.peekOffsetByte(offset++) & 0xFF) << 16 | this.peekOffsetByte(offset) << 24);
        } else {
            return (this.peekOffsetByte(offset++) << 24 | (this.peekOffsetByte(offset++) & 0xFF) << 16 | (this.peekOffsetByte(offset++) & 0xFF) << 8 | this.peekOffsetByte(offset) & 0xFF);
        }

    }

    /**
     * Reads a long and advances the internal offset.
     *
     * @return the long
     */
    public long readLong() {

        if (this.byteOrder == ByteOrder.LITTLE_ENDIAN) {
            return (long) this.readInteger() & 0xFFFFFFFFL | ((long) this.readInteger() << 32);
        } else {
            return ((long) this.readInteger() << 32) | (long) this.readInteger() & 0xFFFFFFFFL;
        }

    }

    /**
     * Reads a long without advancing the internal offset.
     *
     * @return the long
     */
    public long peekLong() {

        if (this.byteOrder == ByteOrder.LITTLE_ENDIAN) {
            return (long) this.peekInteger() & 0xFFFFFFFFL | ((long) this.peekOffsetInteger(4) << 32);
        } else {
            return ((long) this.peekInteger() << 32) | (long) this.peekOffsetInteger(4) & 0xFFFFFFFFL;
        }

    }

    /**
     * Reads a long from the provided offset without advancing the internal offset.
     *
     * @param offset the offset
     * @return the long
     */
    public long peekOffsetLong(int offset) {

        if (this.byteOrder == ByteOrder.LITTLE_ENDIAN) {
            return (long) this.peekOffsetInteger(offset += 4) & 0xFFFFFFFFL | ((long) this.peekOffsetInteger(offset) << 32);
        } else {
            return ((long) this.peekOffsetInteger(offset += 4) << 32) | (long) this.peekOffsetInteger(offset) & 0xFFFFFFFFL;
        }

    }

    /**
     * Sets the byte order to the provided byte order.
     *
     * @param byteOrder the byte order
     */
    public void setByteOrder(final ByteOrder byteOrder) {

        Preconditions.checkNotNull(byteOrder);
        this.byteOrder = byteOrder;

    }

    /**
     * Returns the current byte order.
     *
     * @return the byte order
     */
    public ByteOrder getByteOrder() {

        return this.byteOrder;

    }

    /**
     * Returns the internal offset.
     *
     * @return the internal offset
     */
    public int getOffset() {

        return this.offset;

    }

    /**
     * Creates a new byte reader for the provided byte array using {@link ByteOrder#LITTLE_ENDIAN}.
     *
     * @param bytes the byte array
     * @return the byte reader
     */
    public static ByteReader of(final byte[] bytes) {

        return new ByteReader(bytes);

    }

    /**
     * Creates a new byte reader for the provided byte array using the provided byte order.
     *
     * @param byteOrder the byte order
     * @param bytes     the byte array
     * @return the byte reader
     */
    public static ByteReader of(final ByteOrder byteOrder, final byte[] bytes) {

        Preconditions.checkNotNull(byteOrder);
        return new ByteReader(byteOrder, bytes);

    }

    /**
     * Auto-closeable session that captures the byte order and offset of a byte reader on creation and restores them
     * once the session is closed, either manually or by leaving its try-with-resources scope. Created using the
     * {@link ByteReader#explore(int)}, {@link ByteReader#explore(ByteOrder)} and {@link ByteReader#explore(ByteOrder, int)}
     * methods.
     */
    public static class Explorer implements AutoCloseable {

        private final ByteReader byteReader;
        private final ByteOrder retreatByteOrder;
        private final int retreatOffset;

        private Explorer(final ByteReader byteReader,
                         final ByteOrder retreatByteOrder,
                         final int retreatOffset) {

            this.byteReader = byteReader;
            this.retreatByteOrder = retreatByteOrder;
            this.retreatOffset = retreatOffset;

        }

        @Override
        public void close() {

            this.byteReader.setByteOrder(this.retreatByteOrder);
            this.byteReader.jump(this.retreatOffset);

        }

        /**
         * Returns the byte order that is restored once the session is closed.
         *
         * @return the retreat byte order
         */
        public ByteOrder getRetreatByteOrder() {

            return this.retreatByteOrder;

        }

        /**
         * Returns the offset that is restored once the session is closed.
         *
         * @return the retreat offset
         */
        public int getRetreatOffset() {

            return this.retreatOffset;

        }

        /**
         * Creates a new explorer session that jumps the provided byte reader to the provided offset, keeping its
         * current byte order, and restores the previous offset once closed.
         *
         * @param byteReader   the byte reader
         * @param targetOffset the target offset
         * @return the explorer session
         */
        public static Explorer of(final ByteReader byteReader, final int targetOffset) {

            return of(byteReader, byteReader.getByteOrder(), targetOffset);

        }

        /**
         * Creates a new explorer session that sets the provided byte reader to the provided byte order, keeping its
         * current offset, and restores the previous byte order once closed.
         *
         * @param byteReader      the byte reader
         * @param targetByteOrder the target byte order
         * @return the explorer session
         */
        public static Explorer of(final ByteReader byteReader, final ByteOrder targetByteOrder) {

            return of(byteReader, targetByteOrder, byteReader.getOffset());

        }

        /**
         * Creates a new explorer session that sets the provided byte reader to the provided byte order and jumps it to
         * the provided offset, restoring the previous byte order and offset once closed.
         *
         * @param byteReader      the byte reader
         * @param targetByteOrder the target byte order
         * @param targetOffset    the target offset
         * @return the explorer session
         */
        public static Explorer of(final ByteReader byteReader, final ByteOrder targetByteOrder, final int targetOffset) {

            Preconditions.checkNotNull(byteReader);
            Preconditions.checkNotNull(targetByteOrder);
            final ByteOrder currentByteOrder = byteReader.getByteOrder();
            if (currentByteOrder != targetByteOrder)
                byteReader.setByteOrder(targetByteOrder);

            final int currentOffset = byteReader.getOffset();
            if (currentOffset != targetOffset)
                byteReader.jump(targetOffset);

            return new Explorer(byteReader, currentByteOrder, currentOffset);

        }

    }

}
