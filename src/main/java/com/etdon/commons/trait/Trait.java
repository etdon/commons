package com.etdon.commons.trait;

import org.jetbrains.annotations.Nullable;

/**
 * Represents a trait that a given input may or may not exhibit.
 *
 * @param <T> the input type
 */
public interface Trait<T> {

    /**
     * Checks if the provided input exhibits this trait.
     *
     * @param input the input
     * @return <code>true</code> if the input is eligible, <code>false</code> otherwise
     */
    boolean isEligible(@Nullable final T input);

}
