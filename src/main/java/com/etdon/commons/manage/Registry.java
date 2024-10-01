package com.etdon.commons.manage;

import com.etdon.commons.conditional.Preconditions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * Simple key-value registry.
 *
 * @param <K> The key type.
 * @param <V> The value type.
 */
public abstract class Registry<K, V> {

    private final Map<K, V> registered;

    /**
     * Default zero-args constructor which sets the internal map to a new {@link HashMap} instance.
     */
    public Registry() {

        this.registered = new HashMap<>();

    }

    /**
     * Constructor that allows you to provide your own map that will be used internally, this way all map implementations
     * are supported.
     *
     * @param map The map.
     */
    public Registry(@NotNull final Map<K, V> map) {

        Preconditions.checkNotNull(map);
        this.registered = map;

    }

    /**
     * Adds a new entry consisting of a key and value.
     *
     * @param key   The key.
     * @param value The value.
     */
    public void register(@NotNull final K key, @Nullable final V value) {

        Preconditions.checkNotNull(key);
        Preconditions.checkNotNull(value);
        this.registered.put(key, value);

    }

    /**
     * Removes the entry with the specified key if present.
     *
     * @param key The key.
     */
    public void unregister(@NotNull final K key) {

        Preconditions.checkNotNull(key);
        this.registered.remove(key);

    }

    /**
     * Gets the stored value for the specified key if present.
     *
     * @param key The key.
     * @return The stored value or <code>null</code> if the key is not present.
     */
    @Nullable
    public V get(@NotNull final K key) {

        Preconditions.checkNotNull(key);
        return this.registered.get(key);

    }

    /**
     * Checks if the registry contains the provided key.
     *
     * @param key The key.
     * @return <code>true</code> if the provided key is in the registry, <code>false</code> otherwise.
     */
    public boolean containsKey(@NotNull final K key) {

        Preconditions.checkNotNull(key);
        return this.registered.containsKey(key);

    }

    /**
     * Checks if the registry contains the provided value.
     *
     * @param value The value.
     * @return <code>true</code> if the provided value is in the registry, <code>false</code> otherwise.
     */
    public boolean containsValue(@NotNull final V value) {

        Preconditions.checkNotNull(value);
        return this.registered.containsValue(value);

    }

    /**
     * Returns a copy of the internal map.
     *
     * @return The map copy.
     */
    @NotNull
    public Map<K, V> getInternalMapCopy() {

        return this.getInternalMapCopy(false);

    }

    /**
     * Returns a copy of the internal map. If the precise parameter is set to <code>true</code> it'll try to create a
     * new instance based on the internal map implementation by looking for a constructor that accepts a map. If a
     * constructor has been found a new instance will be created with the internally stored map provided as a parameter.
     * If no constructor has been found or if the precise parameter is set to <code>false</code> it'll return a new
     * {@link HashMap} instance with the entries of the internally stored map.
     *
     * @param precise <code>true</code> if a precise copy is required, <code>false</code> otherwise.
     * @return The map copy or <code>null</code> if the precise creation failed.
     */
    @SuppressWarnings("unchecked")
    @Nullable
    public Map<K, V> getInternalMapCopy(final boolean precise) {

        if (precise) {
            try {
                final Constructor<?> constructor = this.registered.getClass().getConstructor(Map.class);
                return (Map<K, V>) constructor.newInstance(this.registered);
            } catch (final InstantiationException | InvocationTargetException |
                           NoSuchMethodException | IllegalAccessException ex) {
                return null;
            }
        }

        return new HashMap<>(this.registered);

    }

    /**
     * Clears the registry.
     */
    public void clear() {

        this.registered.clear();

    }

}
