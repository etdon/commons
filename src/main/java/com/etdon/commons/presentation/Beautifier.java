package com.etdon.commons.presentation;

import com.etdon.commons.conditional.Preconditions;
import org.jetbrains.annotations.NotNullByDefault;

@NotNullByDefault
public final class Beautifier {

    /**
     * Converts the provided enum value to a beautified string representation, beautified in this context means that
     * underscores get replaced with spaces and only the first letter of each word is upper case.
     * <p>
     * Example: ENUM_VALUE -> Enum Value
     * </p>
     *
     * @param input The enum value.
     * @return The beautified string representation.
     */
    public static String beautifyEnum(final Enum<?> input) {

        Preconditions.checkNotNull(input);
        final char[] chars = input.name().toCharArray();
        boolean capitalize = true;
        for (int i = 0; i < chars.length; i++) {
            final char currentChar = chars[i];
            if (currentChar == '_') {
                chars[i] = ' ';
                capitalize = true;
            } else {
                chars[i] = capitalize ? Character.toUpperCase(currentChar) : Character.toLowerCase(currentChar);
                if (capitalize) capitalize = false;
            }
        }

        return new String(chars);

    }

    private Beautifier() {

        throw new UnsupportedOperationException();

    }

}
