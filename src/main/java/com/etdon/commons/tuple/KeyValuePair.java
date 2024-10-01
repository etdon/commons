package com.etdon.commons.tuple;

import org.jetbrains.annotations.Nullable;

public interface KeyValuePair<K, V> {

    K getKey();

    void setKey(@Nullable final K key);

    V getValue();

    void setValue(@Nullable final V value);

}
