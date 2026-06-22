package com.etdon.commons.tuple;

import org.jetbrains.annotations.Nullable;

/**
 * Represents a key-value pair.
 *
 * @param <K> the key type
 * @param <V> the value type
 */
public interface KeyValuePair<K, V> {

    /**
     * Returns the key.
     *
     * @return the key
     */
    @Nullable
    K getKey();

    /**
     * Sets the key to the provided key.
     *
     * @param key the key
     */
    void setKey(@Nullable final K key);

    /**
     * Returns the value.
     *
     * @return the value
     */
    @Nullable
    V getValue();

    /**
     * Sets the value to the provided value.
     *
     * @param value the value
     */
    void setValue(@Nullable final V value);

}
