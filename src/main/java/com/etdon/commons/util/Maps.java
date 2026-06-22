package com.etdon.commons.util;

import com.etdon.commons.conditional.Preconditions;
import com.etdon.commons.tuple.KeyValuePair;
import org.jetbrains.annotations.NotNullByDefault;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Utilities for creating and modifying maps.
 */
@NotNullByDefault
public final class Maps {

    /**
     * Creates a new {@link HashMap} populated with the provided pairs.
     *
     * @param pairs the pairs
     * @param <K>   the key type
     * @param <V>   the value type
     * @return the map
     */
    @SafeVarargs
    public static <K, V> Map<K, V> of(final KeyValuePair<K, V>... pairs) {

        Preconditions.checkNotNull(pairs);
        return fill(new HashMap<>(), pairs);

    }

    /**
     * Creates a new {@link LinkedHashMap} populated with the provided pairs.
     *
     * @param pairs the pairs
     * @param <K>   the key type
     * @param <V>   the value type
     * @return the map
     */
    @SafeVarargs
    public static <K, V> Map<K, V> ofLinked(final KeyValuePair<K, V>... pairs) {

        Preconditions.checkNotNull(pairs);
        return fill(new LinkedHashMap<>(), pairs);

    }

    /**
     * Creates a new {@link ConcurrentHashMap} populated with the provided pairs.
     *
     * @param pairs the pairs
     * @param <K>   the key type
     * @param <V>   the value type
     * @return the map
     */
    @SafeVarargs
    public static <K, V> Map<K, V> ofConcurrent(final KeyValuePair<K, V>... pairs) {

        Preconditions.checkNotNull(pairs);
        return fill(new ConcurrentHashMap<>(), pairs);

    }

    /**
     * Populates the provided map with the provided pairs and returns it. Neither the keys nor the values of the
     * provided pairs may be <code>null</code>.
     *
     * @param map   the map
     * @param pairs the pairs
     * @param <K>   the key type
     * @param <V>   the value type
     * @return the provided map
     */
    @SafeVarargs
    public static <K, V> Map<K, V> fill(final Map<K, V> map, final KeyValuePair<K, V>... pairs) {

        Preconditions.checkNotNull(map);
        Preconditions.checkNotNull(pairs);
        for (final KeyValuePair<K, V> pair : pairs)
            map.put(Objects.requireNonNull(pair.getKey()), Objects.requireNonNull(pair.getValue()));

        return map;

    }

    /**
     * Adds the provided entry to the collection mapped to the provided key. The map must already contain a non-<code>null</code>
     * collection for the provided key.
     *
     * @param map   the map
     * @param key   the key
     * @param entry the entry
     * @param <K>   the key type
     * @param <V>   the collection element type
     * @return <code>true</code> if the entry was added
     */
    public static <K, V> boolean addCollectionEntry(final Map<K, ? extends Collection<V>> map, final K key, final V entry) {

        Preconditions.checkNotNull(map);
        Preconditions.checkNotNull(key);
        Preconditions.checkNotNull(entry);
        final Collection<V> collection = map.get(key);
        Preconditions.checkNotNull(collection);
        collection.add(entry);

        return true;

    }

    private Maps() {

        throw new UnsupportedOperationException();

    }

}
