package com.etdon.commons.builder;

import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface Builder<T> {

    @NotNull
    T build();

}
