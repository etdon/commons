package com.etdon.commons.di;

import org.jetbrains.annotations.NotNullByDefault;
import org.jetbrains.annotations.Nullable;

@NotNullByDefault
public interface ServiceProvider {

    <T> void register(final T service);

    <T> void register(final Class<T> clazz, final T service);

    <T> void register(final String identifier, final T service);

    <T> void register(final Class<T> clazz, final String identifier, final T service);

    @Nullable
    <T> T get(final Class<T> clazz);

    <T> T getOrThrow(final Class<T> clazz);

    @Nullable
    <T> T get(final Class<T> clazz, final String identifier);

    <T> T getOrThrow(final Class<T> clazz, final String identifier);

    boolean has(final Class<?> clazz);

    boolean has(final Class<?> clazz, final String identifier);

}
