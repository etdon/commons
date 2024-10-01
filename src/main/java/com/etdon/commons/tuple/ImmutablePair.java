package com.etdon.commons.tuple;

import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class ImmutablePair<K, V> implements KeyValuePair<K, V> {

    public final K key;
    public final V value;

    public ImmutablePair(final K key, final V value) {

        this.key = key;
        this.value = value;

    }

    @Override
    public K getKey() {

        return key;

    }

    @Override
    public void setKey(@Nullable final K key) {

        throw new UnsupportedOperationException();

    }

    @Override
    public V getValue() {

        return value;

    }

    @Override
    public void setValue(@Nullable final V value) {

        throw new UnsupportedOperationException();

    }

    @Override
    public int hashCode() {

        return Objects.hash(key, value);

    }

    @Override
    public boolean equals(@Nullable final Object input) {

        if (this == input) return true;
        if (input == null || getClass() != input.getClass()) return false;

        ImmutablePair<?, ?> pair = (ImmutablePair<?, ?>) input;
        return Objects.equals(key, pair.key) && Objects.equals(value, pair.value);

    }

    public static <K, V> ImmutablePair<K, V> of(final K key, final V value) {

        return new ImmutablePair<>(key, value);

    }

}
