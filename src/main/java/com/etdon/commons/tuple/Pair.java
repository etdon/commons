package com.etdon.commons.tuple;

import com.etdon.commons.conditional.Preconditions;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class Pair<K, V> implements KeyValuePair<K, V> {

    private K key;
    private V value;

    public Pair(@Nullable final K key,
                @Nullable final V value) {

        this.key = key;
        this.value = value;

    }

    @Override
    public K getKey() {

        return this.key;

    }

    @Override
    public void setKey(@Nullable final K key) {

        this.key = key;

    }

    @Override
    public V getValue() {

        return this.value;

    }

    @Override
    public void setValue(@Nullable final V value) {

        this.value = value;

    }

    @Override
    public int hashCode() {

        return Objects.hash(key, value);

    }

    @Override
    public boolean equals(@Nullable final Object input) {

        if (this == input) return true;
        if (input == null || getClass() != input.getClass()) return false;

        Pair<?, ?> pair = (Pair<?, ?>) input;
        return Objects.equals(key, pair.key) && Objects.equals(value, pair.value);

    }

    public static <K, V> Pair<K, V> of(@Nullable final K key, @Nullable final V value) {

        return new Pair<>(key, value);

    }

}
