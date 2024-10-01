package com.etdon.commons.util;

import com.etdon.commons.conditional.Preconditions;
import com.etdon.commons.constant.Constants;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public final class StringUtils {

    public static String applyValues(@NotNull final String input, @NotNull final Object... values) {

        Preconditions.checkNotNull(input);
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

    public static List<String> split(@NotNull final String input, final char splitChar) {

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

    public static String repeat(@NotNull final String input, final int count) {

        Preconditions.checkNotNull(input);
        if (count <= 1)
            return input;

        final StringBuilder output = new StringBuilder();
        for (int i = 0; i < count; i++)
            output.append(input);

        return output.toString();

    }

    public static String toLines(@NotNull final String input, final int lineSize) {

        final StringBuilder output = new StringBuilder();
        final StringBuilder line = new StringBuilder();
        final char[] chars = input.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            final char c = chars[i];
            line.append(c);
            if (line.length() >= lineSize) {
                output.append(line);
                line.setLength(0);
                if (i < chars.length - 1)
                    output.append(System.lineSeparator());
            }
        }

        return output.toString();

    }

    private StringUtils() {

        throw new UnsupportedOperationException();

    }

}
