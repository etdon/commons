package com.etdon.commons.trait;

import org.jetbrains.annotations.Nullable;

/**
 * Utilities for trait work.
 */
public final class Traits {

    /**
     * Checks if all provided traits apply to the provided input.
     *
     * @param input  the input
     * @param traits the traits to check
     * @return <code>true</code> if all traits apply, <code>false</code> otherwise
     * @param <T> the input type
     */
    @SafeVarargs
    public static <T> boolean check(@Nullable final T input, @Nullable final Trait<T>... traits) {

        if (input == null)
            return traits == null || traits.length == 0;
        if (traits == null)
            return true;

        boolean eligible = true;
        for (final Trait<T> trait : traits) {
            if (trait == null) continue;
            if (!trait.isEligible(input))
                return false;
        }

        return eligible;

    }

    private Traits() {

        throw new UnsupportedOperationException();

    }

}
