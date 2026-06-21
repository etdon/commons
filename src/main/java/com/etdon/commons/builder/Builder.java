package com.etdon.commons.builder;

import org.jetbrains.annotations.NotNullByDefault;

/**
 * Builder interface.
 *
 * @param <T> the target type
 */
@NotNullByDefault
@FunctionalInterface
public interface Builder<T> {

    T build();

}
