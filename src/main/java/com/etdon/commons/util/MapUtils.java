package com.etdon.commons.util;

import com.etdon.commons.conditional.Preconditions;
import com.etdon.commons.tuple.KeyValuePair;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public final class MapUtils {

    @SafeVarargs
    public static <K, V> Map<K, V> newMap(@NotNull final KeyValuePair<K, V>... pairs) {

        Preconditions.checkNotNull(pairs);
        return fillMap(new HashMap<>(), pairs);

    }

    @SafeVarargs
    public static <K, V> Map<K, V> newLinkedMap(@NotNull final KeyValuePair<K, V>... pairs) {

        Preconditions.checkNotNull(pairs);
        return fillMap(new LinkedHashMap<>(), pairs);

    }

    @SafeVarargs
    public static <K, V> Map<K, V> newConcurrentMap(@NotNull final KeyValuePair<K, V>... pairs) {

        Preconditions.checkNotNull(pairs);
        return fillMap(new ConcurrentHashMap<>(), pairs);

    }

    @SafeVarargs
    public static <K, V> Map<K, V> fillMap(@NotNull final Map<K, V> map, @NotNull final KeyValuePair<K, V>... pairs) {

        Preconditions.checkNotNull(map);
        Preconditions.checkNotNull(pairs);
        for (final KeyValuePair<K, V> pair : pairs)
            map.put(pair.getKey(), pair.getValue());

        return map;

    }

    public static <K, V> boolean addCollectionEntry(@NotNull final Map<K, ? extends Collection<V>> map, @NotNull final K key, @NotNull final V entry) {

        Preconditions.checkNotNull(map);
        Preconditions.checkNotNull(key);
        Preconditions.checkNotNull(entry);

        final Collection<V> collection = map.get(key);
        Preconditions.checkNotNull(collection);
        Preconditions.checkState(Collection.class.isAssignableFrom(collection.getClass()));

        collection.add(entry);
        return true;

    }

    private MapUtils() {

        throw new UnsupportedOperationException();

    }

}
