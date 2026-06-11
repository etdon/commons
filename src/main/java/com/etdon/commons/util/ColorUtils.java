package com.etdon.commons.util;

public final class ColorUtils {

    /**
     * Packs three byte sized input color values into one integer (BGR). The output binary layout looks like this:
     * 0000 0000 bbbb bbbb gggg gggg rrrr rrrr
     * <p>
     * The 4 most significant bits are unused as no alpha value is included.
     *
     * @param red the red color value
     * @param green the green color value
     * @param blue the blue color value
     * @return the packed colors in one integer
     * @deprecated replaced by {@link ColorUtils#pack(int, int, int)}
     */
    @Deprecated
    public static int compress(final int red, final int green, final int blue) {

        return ((blue & 0xFF) << 16) | ((green & 0xFF) << 8) | (red & 0xFF);

    }

    /**
     * Unpacks the values of a packed color integer (BGR) into an integer array with a fixed size of 3. The indexes
     * of the individual color values are as follows:
     * <p>
     * [0] red [1] green [2] blue
     *
     * @param input the packed color integer
     * @return the unpacked integer array
     * @deprecated replaced by {@link ColorUtils#unpack(int)}
     */
    @Deprecated
    public static int[] decompress(final int input) {

        final int[] output = new int[3];
        output[0] = (input & 0xFF);
        output[1] = ((input >>> 8) & 0xFF);
        output[2] = ((input >>> 16) & 0xFF);

        return output;

    }

    /**
     * Packs three byte sized input color values into one integer (RGB). The output binary layout looks like this:
     * 0000 0000 rrrr rrrr gggg gggg bbbb bbbb
     * <p>
     * The 4 most significant bits are unused as no alpha value is included.
     *
     * @param red the red color value
     * @param green the green color value
     * @param blue the blue color value
     * @return the packed colors in one integer
     */
    public static int pack(final int red, final int green, final int blue) {

        return ((red & 0xFF) << 16) | ((green & 0xFF) << 8) | (blue & 0xFF);

    }

    /**
     * Packs three byte sized input color values into one integer (ARGB). The output binary layout looks like this:
     * aaaa aaaa rrrr rrrr gggg gggg bbbb bbbb
     *
     * @param alpha the alpha value
     * @param red the red color value
     * @param green the green color value
     * @param blue the blue color value
     * @return the packed colors in one integer
     */
    public static int pack(final int alpha, final int red, final int green, final int blue) {

        return ((alpha & 0xFF) << 24) | ((red & 0xFF) << 16) | ((green & 0xFF) << 8) | (blue & 0xFF);

    }

    /**
     * Unpacks the values of a packed color integer (ARGB or RGB) into an integer array with a fixed size of 4. The
     * indexes of the individual color values are as follows:
     * <p>
     * [0] alpha (if existent, always <code>0</code> otherwise) [1] red [2] green [3] blue
     *
     * @param input the packed color integer
     * @return the unpacked integer array
     */
    public static int[] unpack(final int input) {

        final int[] output = new int[4];
        output[0] = ((input >> 24) & 0xFF);
        output[1] = ((input >> 16) & 0xFF);
        output[2] = ((input >>> 8) & 0xFF);
        output[3] = (input & 0xFF);

        return output;

    }

    private ColorUtils() {

        throw new UnsupportedOperationException();

    }

}
