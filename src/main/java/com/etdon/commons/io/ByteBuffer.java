package com.etdon.commons.io;

import com.etdon.commons.conditional.Preconditions;
import com.etdon.commons.util.Exceptional;
import org.jetbrains.annotations.NotNull;

/**
 * Byte buffer implementation used to construct byte arrays in the endianness of choice. Put methods exist for all
 * primitive types.
 */
public class ByteBuffer {

    private ByteOrder byteOrder = ByteOrder.LITTLE_ENDIAN;
    private byte[] buffer;
    private int size;

    /**
     * Initialize using {@link ByteBuffer#auto()} or {@link ByteBuffer#size(int)}.
     *
     * @param initialSize the initial size.
     */
    private ByteBuffer(final int initialSize) {

        this.buffer = new byte[initialSize];

    }

    /**
     * Puts the provided <code>byte</code> value into the buffer resizing it if necessary.
     *
     * @param value the <code>byte</code> value
     */
    public void put(final byte value) {

        this.ensureSize(1);
        this.buffer[this.size++] = value;

    }

    /**
     * Puts the provided <code>byte</code> values into the buffer resizing it if necessary. The order in which the bytes are added
     * depends on the current {@link ByteBuffer#byteOrder} of the {@link ByteBuffer}.
     *
     * @param values the <code>byte</code> values
     */
    public void put(final byte... values) {

        if (values.length == 0)
            return;

        this.ensureSize(values.length);
        if (this.byteOrder == ByteOrder.LITTLE_ENDIAN) {
            for (final byte value : values)
                this.buffer[this.size++] = value;
        } else {
            for (int i = values.length - 1; i >= 0; i--)
                this.buffer[this.size++] = values[i];
        }

    }

    /**
     * Puts the provided <code>boolean</code> value into the buffer by adding a byte with the value <code>1</code> if
     * the boolean is <code>true</code> or <code>0</code> if it's <code>false</code>.
     *
     * @param value the <code>boolean</code> value
     */
    public void put(final boolean value) {

        this.put((byte) (value ? 1 : 0));

    }

    /**
     * Puts the provided <code>char</code> value into the buffer by casting it to a byte.
     *
     * @param value the <code>char</code> value
     */
    public void put(final char value) {

        this.put((byte) value);

    }

    /**
     * Puts the provided <code>short</code> value into the buffer by putting the individual bytes of the
     * <code>short</code> into a local buffer and calling {@link ByteBuffer#put(byte...)}.
     *
     * @param value the <code>short</code> value
     */
    public void put(final short value) {

        final byte[] buffer = new byte[Short.BYTES];
        for (int i = 0; i < Short.BYTES; i++)
            buffer[i] = (byte) ((value >>> (i * 8)) & 0xFF);
        this.put(buffer);

    }

    /**
     * Puts the provided <code>int</code> value into the buffer by putting the individual bytes of the <code>int</code>
     * into a local buffer and calling {@link ByteBuffer#put(byte...)}.
     *
     * @param value the <code>int</code> value
     */
    public void put(final int value) {

        final byte[] buffer = new byte[Integer.BYTES];
        for (int i = 0; i < Integer.BYTES; i++)
            buffer[i] = (byte) ((value >>> (i * 8)) & 0xFF);
        this.put(buffer);

    }

    /**
     * Puts the provided <code>float</code> value into the buffer by putting the individual bytes of the
     * <code>float</code> into a local buffer and calling {@link ByteBuffer#put(byte...)}.
     *
     * @param value the <code>float</code> value
     */
    public void put(final float value) {

        final int floatBits = Float.floatToIntBits(value);
        final byte[] buffer = new byte[Float.BYTES];
        for (int i = 0; i < Float.BYTES; i++)
            buffer[i] = (byte) ((floatBits >>> (i * 8)) & 0xFF);
        this.put(buffer);

    }

    /**
     * Puts the provided <code>long</code> value into the buffer by putting the individual bytes of the
     * <code>long</code> into a local buffer and calling {@link ByteBuffer#put(byte...)}.
     *
     * @param value the <code>long</code> value
     */
    public void put(final long value) {

        final byte[] buffer = new byte[Long.BYTES];
        for (int i = 0; i < Long.BYTES; i++)
            buffer[i] = (byte) ((value >>> (i * 8)) & 0xFF);
        this.put(buffer);

    }

    /**
     * Puts the provided <code>double</code> value into the buffer by putting the individual bytes of the
     * <code>double</code> into a local buffer and calling {@link ByteBuffer#put(byte...)}.
     *
     * @param value the <code>double</code> value
     */
    public void put(final double value) {

        final long doubleBits = Double.doubleToLongBits(value);
        final byte[] buffer = new byte[Double.BYTES];
        for (int i = 0; i < Double.BYTES; i++)
            buffer[i] = (byte) ((doubleBits >>> (i * 8)) & 0xFF);
        this.put(buffer);

    }

    /**
     * Sets the <code>byte</code> at the provided index to the provided <code>byte</code> value.
     *
     * @param index the index
     * @param value the value
     */
    public void set(final int index, final byte value) {

        if (this.buffer.length <= index)
            throw Exceptional.of(RuntimeException.class, "Cannot size index '{}' for buffer with length '{}'.", index, this.buffer.length);
        this.buffer[index] = value;

    }

    /**
     * Exports the byte buffer to an accurately sized byte array.
     *
     * @return the byte array
     */
    public byte[] get() {

        if (this.buffer.length != this.size) {
            final byte[] resizedBuffer = new byte[this.size];
            System.arraycopy(this.buffer, 0, resizedBuffer, 0, this.size);
            this.buffer = resizedBuffer;
        }

        return this.buffer;

    }

    /**
     * Ensures that the internal byte array has the capacity to add the provided byte count and resizes it if not.
     *
     * @param count the byte count
     */
    private void ensureSize(final int count) {

        if (this.buffer.length >= (this.size + count)) return;
        final int growth = Math.max((this.size + count) - this.buffer.length, this.buffer.length);
        final byte[] resizedBuffer = new byte[this.buffer.length + growth];
        System.arraycopy(this.buffer, 0, resizedBuffer, 0, this.buffer.length);
        this.buffer = resizedBuffer;

    }

    /**
     * Sets the {@link ByteBuffer#byteOrder} value to the provided {@link ByteOrder}.
     *
     * @param byteOrder the byte order
     */
    public void setByteOrder(@NotNull final ByteOrder byteOrder) {

        Preconditions.checkNotNull(byteOrder);
        this.byteOrder = byteOrder;

    }

    /**
     * Returns the current {@link ByteBuffer#byteOrder} value.
     *
     * @return the byte order
     */
    public ByteOrder getByteOrder() {

        return this.byteOrder;

    }

    /**
     * Creates a new {@link ByteBuffer} with an initial size of <code>64</code>.
     *
     * @return the new {@link ByteBuffer}
     */
    public static ByteBuffer auto() {

        return new ByteBuffer(64);

    }

    /**
     * Creates a new {@link ByteBuffer} with the provided initial size.
     *
     * @param initialSize the initial size
     * @return the new {@link ByteBuffer}.
     */
    public static ByteBuffer size(final int initialSize) {

        Preconditions.checkState(initialSize >= 0, "The size of a byte buffer cannot be negative.");
        return new ByteBuffer(initialSize);

    }

}
