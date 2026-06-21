package com.etdon.commons.util;

import com.etdon.commons.conditional.Preconditions;
import com.etdon.commons.tuple.KeyValuePair;
import org.jetbrains.annotations.NotNullByDefault;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@NotNullByDefault
public final class Maps {

    @SafeVarargs
    public static <K, V> Map<K, V> of(final KeyValuePair<K, V>... pairs) {

        Preconditions.checkNotNull(pairs);
        return fill(new HashMap<>(), pairs);

    }

    @SafeVarargs
    public static <K, V> Map<K, V> ofLinked(final KeyValuePair<K, V>... pairs) {

        Preconditions.checkNotNull(pairs);
        return fill(new LinkedHashMap<>(), pairs);

    }

    @SafeVarargs
    public static <K, V> Map<K, V> ofConcurrent(final KeyValuePair<K, V>... pairs) {

        Preconditions.checkNotNull(pairs);
        return fill(new ConcurrentHashMap<>(), pairs);

    }

    @SafeVarargs
    public static <K, V> Map<K, V> fill(final Map<K, V> map, final KeyValuePair<K, V>... pairs) {

        Preconditions.checkNotNull(map);
        Preconditions.checkNotNull(pairs);
        for (final KeyValuePair<K, V> pair : pairs)
            map.put(Objects.requireNonNull(pair.getKey()), Objects.requireNonNull(pair.getValue()));

        return map;

    }

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
