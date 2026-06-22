package com.etdon.commons.util;

import com.etdon.commons.conditional.Preconditions;
import com.etdon.commons.constant.Constants;
import com.etdon.commons.trait.Trait;
import com.etdon.commons.trait.Traits;
import org.jetbrains.annotations.NotNullByDefault;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Utilities for string work.
 */
@NotNullByDefault
public final class Strings {

    /**
     * Replaces the value placeholders in the provided input with the provided values in order of appearance. A
     * placeholder consists of a {@link Constants#DEFAULT_VALUE_START_IDENTIFIER} directly followed by a
     * {@link Constants#DEFAULT_VALUE_END_IDENTIFIER} (e.g. <code>{}</code>) and can be escaped by preceding it with the
     * {@link Constants#ESCAPE_IDENTIFIER}. If the input contains more placeholders than values are provided the surplus
     * placeholders are left untouched. The input is returned unchanged if no values are provided.
     *
     * @param input  the input
     * @param values the placeholder values
     * @return the input with its placeholders replaced
     */
    public static String applyValues(final String input, final Object... values) {

        Preconditions.checkNotNull(input);
        Preconditions.checkNotNull(values);
        if (values.length == 0)
            return input;

        final StringBuilder output = new StringBuilder();
        final char[] chars = input.toCharArray();
        int valueOffset = 0;
        for (int i = 0; i < chars.length; i++) {
            final char c = chars[i];
            if (c == Constants.DEFAULT_VALUE_START_IDENTIFIER) {
                if ((i - 1) >= 0 && chars[i - 1] == Constants.ESCAPE_IDENTIFIER) {
                    output.append(c);
                    continue;
                } else if ((i + 1) < chars.length && chars[i + 1] == Constants.DEFAULT_VALUE_END_IDENTIFIER) {
                    if (valueOffset < values.length) {
                        output.append(values[valueOffset]);
                        valueOffset++;
                    }
                    i++;
                    continue;
                }
            }
            output.append(c);
        }

        return output.toString();

    }

    /**
     * Splits the provided input into parts using the provided character as a delimiter. The delimiter itself is not
     * included in any of the resulting parts.
     *
     * @param input     the input
     * @param splitChar the delimiter character
     * @return the parts
     */
    public static List<String> split(final String input, final char splitChar) {

        Preconditions.checkNotNull(input);
        final List<String> output = new ArrayList<>(5);
        final StringBuilder partBuilder = new StringBuilder();
        for (final char c : input.toCharArray()) {
            if (c == splitChar) {
                output.add(partBuilder.toString());
                partBuilder.setLength(0);
            } else {
                partBuilder.append(c);
            }
        }
        output.add(partBuilder.toString());

        return output;

    }

    /**
     * Repeats the provided input the provided amount of times. The input is returned unchanged if the provided count is
     * less than or equal to <code>1</code>.
     *
     * @param input the input
     * @param count the repeat count
     * @return the repeated input
     */
    public static String repeat(final String input, final int count) {

        Preconditions.checkNotNull(input);
        if (count <= 1)
            return input;

        final StringBuilder output = new StringBuilder();
        for (int i = 0; i < count; i++)
            output.append(input);

        return output.toString();

    }

    /**
     * Splits the provided input into chunks of the provided size separated by a space. Convenience overload of
     * {@link Strings#toChunks(String, int, char)} using a space as the separator.
     *
     * @param input     the input
     * @param chunkSize the chunk size
     * @return the chunked input
     */
    public static String toChunks(final String input, final int chunkSize) {

        return toChunks(input, chunkSize, ' ');

    }

    /**
     * Splits the provided input into chunks of the provided size separated by the provided separator character.
     *
     * @param input     the input
     * @param chunkSize the chunk size
     * @param separator the separator character
     * @return the chunked input
     */
    public static String toChunks(final String input, final int chunkSize, final char separator) {

        final StringBuilder output = new StringBuilder();
        final char[] chars = input.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            output.append(chars[i]);
            if ((i + 1) % chunkSize == 0 && (i + 1) < chars.length)
                output.append(separator);
        }

        return output.toString();

    }

    /**
     * Splits the provided input into lines of the provided size separated by the system line separator.
     *
     * @param input    the input
     * @param lineSize the line size
     * @return the input split into lines
     */
    public static String toLines(final String input, final int lineSize) {

        final StringBuilder output = new StringBuilder();
        final char[] chars = input.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            output.append(chars[i]);
            if ((i + 1) % lineSize == 0 && (i + 1) < chars.length)
                output.append(System.lineSeparator());
        }

        return output.toString();

    }

    /**
     * Surrounds the provided input with the provided affix on both sides.
     *
     * @param affix the affix
     * @param input the input
     * @return the surrounded input
     */
    public static String surround(@Nullable final String affix, @Nullable final String input) {

        return combine(affix, input, affix);

    }

    /**
     * Combines the provided inputs into a single string. <code>null</code> inputs are skipped. An empty string is
     * returned if the provided array is <code>null</code> or empty.
     *
     * @param inputs the inputs
     * @return the combined string
     */
    public static String combine(@Nullable final String... inputs) {

        if (inputs == null || inputs.length == 0)
            return "";

        final StringBuilder stringBuilder = new StringBuilder();
        for (final String input : inputs) {
            if (input == null) continue;
            stringBuilder.append(input);
        }

        return stringBuilder.toString();

    }

    /**
     * Joins the provided inputs into a single string separated by the provided separator. <code>null</code> inputs are
     * skipped and no trailing separator is appended. An empty string is returned if the provided array is
     * <code>null</code> or empty.
     *
     * @param separator the separator
     * @param inputs    the inputs
     * @return the joined string
     */
    public static String list(@Nullable final String separator, @Nullable final String... inputs) {

        if (inputs == null || inputs.length == 0)
            return "";

        final StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < inputs.length; i++) {
            final String input = inputs[i];
            if (input == null) continue;
            stringBuilder.append(input);
            if (separator != null && i < (inputs.length - 1))
                stringBuilder.append(separator);
        }

        return stringBuilder.toString();

    }

    /**
     * Checks if all provided traits apply to the provided input. Delegates to {@link Traits#check(Object, Trait[])}.
     *
     * @param input  the input
     * @param traits the traits to check
     * @return <code>true</code> if all traits apply, <code>false</code> otherwise
     */
    @SafeVarargs
    public static boolean checkTraits(@Nullable final CharSequence input, @Nullable final Trait<CharSequence>... traits) {

        return Traits.check(input, traits);

    }

    private Strings() {

        throw new UnsupportedOperationException();

    }

}
