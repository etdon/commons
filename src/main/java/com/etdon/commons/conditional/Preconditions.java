package com.etdon.commons.conditional;

import com.etdon.commons.util.StringUtils;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class Preconditions {

    private static final String DEFAULT_CHECK_NOT_NULL_MESSAGE = "Reference was null.";
    private static final String DEFAULT_CHECK_NULL_MESSAGE = "Reference was not null.";
    private static final String DEFAULT_CHECK_STATE_MESSAGE = "State was false.";

    /**
     * Throws a {@link NullPointerException} if the provided reference is <code>null</code>.
     *
     * @param reference The reference.
     * @param <T>       The reference type.
     * @throws NullPointerException If the provided reference is <code>null</code>.
     */

    @Contract("null -> fail")
    public static <T> void checkNotNull(@Nullable final T reference) throws NullPointerException {

        if (reference == null)
            throw new NullPointerException(DEFAULT_CHECK_NOT_NULL_MESSAGE);

    }

    /**
     * Throws a {@link NullPointerException} with the provided message if the provided reference is <code>null</code>.
     *
     * @param reference The reference.
     * @param message   The message.
     * @param values    The message placeholder values.
     * @param <T>       The reference type.
     * @throws NullPointerException If the provided reference is <code>null</code>.
     */

    @Contract("null, _, _ -> fail")
    public static <T> void checkNotNull(@Nullable final T reference, @Nullable final String message, @NotNull final Object... values) throws NullPointerException {

        if (reference == null)
            throw new NullPointerException(message != null ? StringUtils.applyValues(message, values) : DEFAULT_CHECK_NOT_NULL_MESSAGE);

    }

    /**
     * Throws an {@link IllegalArgumentException} if the provided reference is not <code>null</code>.
     *
     * @param reference The reference.
     * @param <T>       The reference type.
     * @throws IllegalArgumentException If the provided reference is not <code>null</code>.
     */

    @Contract("!null -> fail")
    public static <T> void checkNull(@Nullable final T reference) throws IllegalArgumentException {

        if (reference != null)
            throw new IllegalArgumentException(DEFAULT_CHECK_NULL_MESSAGE);

    }

    /**
     * Throws an {@link IllegalArgumentException} with the provided message if the provided reference is not <code>null</code>.
     *
     * @param reference The reference.
     * @param message   The message.
     * @param values    The message placeholder values.
     * @param <T>       The reference type.
     * @throws IllegalArgumentException If the provided reference is not <code>null</code>.
     */

    @Contract("!null, _, _ -> fail")
    public static <T> void checkNull(@Nullable final T reference, @Nullable final String message, @NotNull final Object... values) throws IllegalArgumentException {

        if (reference != null)
            throw new IllegalArgumentException(message != null ? StringUtils.applyValues(message, values) : DEFAULT_CHECK_NULL_MESSAGE);

    }

    /**
     * Throws an {@link IllegalStateException} if the provided state is <code>false</code>.
     *
     * @param state The state.
     * @throws IllegalStateException If the provided state is <code>false</code>.
     */

    @Contract("false -> fail")
    public static void checkState(final boolean state) throws IllegalStateException {

        if (!state)
            throw new IllegalStateException(DEFAULT_CHECK_STATE_MESSAGE);

    }

    /**
     * Throws an {@link IllegalStateException} with the provided message if the provided state is <code>false</code>.
     *
     * @param state   The state.
     * @param message The message.
     * @param values  The message placeholder values.
     * @throws IllegalStateException If the provided state is <code>false</code>.
     */

    @Contract("false, _, _ -> fail")
    public static void checkState(final boolean state, @Nullable final String message, @NotNull final Object... values) throws IllegalStateException {

        if (!state)
            throw new IllegalStateException(message != null ? StringUtils.applyValues(message, values) : DEFAULT_CHECK_STATE_MESSAGE);

    }

    private Preconditions() {

        throw new UnsupportedOperationException();

    }

}
