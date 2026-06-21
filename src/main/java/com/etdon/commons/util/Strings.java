package com.etdon.commons.util;

import com.etdon.commons.conditional.Preconditions;
import com.etdon.commons.constant.Constants;
import com.etdon.commons.trait.Trait;
import com.etdon.commons.trait.Traits;
import org.jetbrains.annotations.NotNullByDefault;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

@NotNullByDefault
public final class Strings {

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

    public static String repeat(final String input, final int count) {

        Preconditions.checkNotNull(input);
        if (count <= 1)
            return input;

        final StringBuilder output = new StringBuilder();
        for (int i = 0; i < count; i++)
            output.append(input);

        return output.toString();

    }

    public static String toChunks(final String input, final int chunkSize) {

        return toChunks(input, chunkSize, ' ');

    }

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

    public static String surround(@Nullable final String affix, @Nullable final String input) {

        return combine(affix, input, affix);

    }

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

    @SafeVarargs
    public static boolean checkTraits(@Nullable final CharSequence input, @Nullable final Trait<CharSequence>... traits) {

        return Traits.check(input, traits);

    }

    private Strings() {

        throw new UnsupportedOperationException();

    }

}
