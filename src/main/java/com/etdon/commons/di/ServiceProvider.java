package com.etdon.commons.di;

import org.jetbrains.annotations.NotNullByDefault;
import org.jetbrains.annotations.Nullable;

/**
 * Stores and resolves service instances by their type and an optional identifier.
 */
@NotNullByDefault
public interface ServiceProvider {

    /**
     * Registers the provided service under its runtime class.
     *
     * @param service the service
     * @param <T>     the service type
     */
    <T> void register(final T service);

    /**
     * Registers the provided service under the provided class.
     *
     * @param clazz   the class
     * @param service the service
     * @param <T>     the service type
     */
    <T> void register(final Class<T> clazz, final T service);

    /**
     * Registers the provided service under its runtime class and the provided identifier.
     *
     * @param identifier the identifier
     * @param service    the service
     * @param <T>        the service type
     */
    <T> void register(final String identifier, final T service);

    /**
     * Registers the provided service under the provided class and identifier.
     *
     * @param clazz      the class
     * @param identifier the identifier
     * @param service    the service
     * @param <T>        the service type
     */
    <T> void register(final Class<T> clazz, final String identifier, final T service);

    /**
     * Returns the service registered under the provided class.
     *
     * @param clazz the class
     * @param <T>   the service type
     * @return the service or <code>null</code> if none is registered
     */
    @Nullable
    <T> T get(final Class<T> clazz);

    /**
     * Returns the service registered under the provided class or throws if none is registered.
     *
     * @param clazz the class
     * @param <T>   the service type
     * @return the service
     * @throws NullPointerException if no service is registered under the provided class
     */
    <T> T getOrThrow(final Class<T> clazz);

    /**
     * Returns the service registered under the provided class and identifier.
     *
     * @param clazz      the class
     * @param identifier the identifier
     * @param <T>        the service type
     * @return the service or <code>null</code> if none is registered
     */
    @Nullable
    <T> T get(final Class<T> clazz, final String identifier);

    /**
     * Returns the service registered under the provided class and identifier or throws if none is registered.
     *
     * @param clazz      the class
     * @param identifier the identifier
     * @param <T>        the service type
     * @return the service
     * @throws NullPointerException if no service is registered under the provided class and identifier
     */
    <T> T getOrThrow(final Class<T> clazz, final String identifier);

    /**
     * Checks if a service is registered under the provided class.
     *
     * @param clazz the class
     * @return <code>true</code> if a service is registered, <code>false</code> otherwise
     */
    boolean has(final Class<?> clazz);

    /**
     * Checks if a service is registered under the provided class and identifier.
     *
     * @param clazz      the class
     * @param identifier the identifier
     * @return <code>true</code> if a service is registered, <code>false</code> otherwise
     */
    boolean has(final Class<?> clazz, final String identifier);

}
