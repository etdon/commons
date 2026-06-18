package com.etdon.commons.builder;

import org.jetbrains.annotations.NotNull;

/**
 * Builder interface.
 *
 * @param <T> the target type
 */
@FunctionalInterface
public interface Builder<T> {

    @NotNull
    T build();

}
