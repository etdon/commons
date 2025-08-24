package com.etdon.commons.trait;

import org.jetbrains.annotations.Nullable;

public final class Traits {

    @SafeVarargs
    public static <T> boolean check(@Nullable final T input, @Nullable final Trait<T>... traits) {

        if (input == null)
            return traits == null || traits.length == 0;
        if (traits == null)
            return true;

        boolean eligible = true;
        for (final Trait<T> trait : traits) {
            if (trait == null) continue;
            if (!trait.isEligible(input)) {
                eligible = false;
                break;
            }
        }

        return eligible;

    }

    private Traits() {

        throw new UnsupportedOperationException();

    }

}
