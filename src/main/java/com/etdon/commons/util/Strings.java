package com.etdon.commons.util;

import com.etdon.commons.trait.Trait;
import com.etdon.commons.trait.Traits;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class Strings {

    @NotNull
    public static String surround(@Nullable final String affix, @Nullable final String input) {

        return combine(affix, input, affix);

    }

    @NotNull
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

    @NotNull
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
