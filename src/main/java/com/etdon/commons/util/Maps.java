package com.etdon.commons.util;

import com.etdon.commons.conditional.Preconditions;
import com.etdon.commons.tuple.KeyValuePair;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class Maps {

    @SafeVarargs
    public static <K, V> Map<K, V> of(@NotNull final KeyValuePair<K, V>... pairs) {

        Preconditions.checkNotNull(pairs);
        return fill(new HashMap<>(), pairs);

    }

    @SafeVarargs
    public static <K, V> Map<K, V> ofLinked(@NotNull final KeyValuePair<K, V>... pairs) {

        Preconditions.checkNotNull(pairs);
        return fill(new LinkedHashMap<>(), pairs);

    }

    @SafeVarargs
    public static <K, V> Map<K, V> ofConcurrent(@NotNull final KeyValuePair<K, V>... pairs) {

        Preconditions.checkNotNull(pairs);
        return fill(new ConcurrentHashMap<>(), pairs);

    }

    @SafeVarargs
    public static <K, V> Map<K, V> fill(@NotNull final Map<K, V> map, @NotNull final KeyValuePair<K, V>... pairs) {

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
        collection.add(entry);

        return true;

    }

    private Maps() {

        throw new UnsupportedOperationException();

    }

}
