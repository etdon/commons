package com.etdon.commons.conditional;

import com.etdon.commons.util.Strings;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Utilities for validating method arguments and state, throwing an appropriate exception if a check fails.
 */
public final class Preconditions {

    private static final String DEFAULT_CHECK_NOT_NULL_MESSAGE = "Reference was null.";
    private static final String DEFAULT_CHECK_NULL_MESSAGE = "Reference was not null.";
    private static final String DEFAULT_CHECK_STATE_MESSAGE = "State was false.";
    private static final String DEFAULT_CHECK_ARGUMENT_MESSAGE = "Argument expression was false.";

    /**
     * Throws a {@link NullPointerException} if the provided reference is <code>null</code>.
     *
     * @param reference the reference
     * @param <T>       the reference type
     * @throws NullPointerException if the provided reference is <code>null</code>
     */
    @Contract("null -> fail")
    public static <T> void checkNotNull(@Nullable final T reference) throws NullPointerException {

        if (reference == null)
            throw new NullPointerException(DEFAULT_CHECK_NOT_NULL_MESSAGE);

    }

    /**
     * Throws a {@link NullPointerException} with the provided message if the provided reference is <code>null</code>.
     *
     * @param reference the reference
     * @param message   the message
     * @param values    the message placeholder values
     * @param <T>       the reference type
     * @throws NullPointerException if the provided reference is <code>null</code>
     */
    @Contract("null, _, _ -> fail")
    public static <T> void checkNotNull(@Nullable final T reference, @Nullable final String message, @NotNull final Object... values) throws NullPointerException {

        if (reference == null)
            throw new NullPointerException(message != null ? Strings.applyValues(message, values) : DEFAULT_CHECK_NOT_NULL_MESSAGE);

    }

    /**
     * Throws an {@link IllegalArgumentException} if the provided reference is not <code>null</code>.
     *
     * @param reference the reference
     * @param <T>       the reference type
     * @throws IllegalArgumentException if the provided reference is not <code>null</code>
     */
    @Contract("!null -> fail")
    public static <T> void checkNull(@Nullable final T reference) throws IllegalArgumentException {

        if (reference != null)
            throw new IllegalArgumentException(DEFAULT_CHECK_NULL_MESSAGE);

    }

    /**
     * Throws an {@link IllegalArgumentException} with the provided message if the provided reference is not <code>null</code>.
     *
     * @param reference the reference
     * @param message   the message
     * @param values    the message placeholder values
     * @param <T>       the reference type
     * @throws IllegalArgumentException if the provided reference is not <code>null</code>
     */
    @Contract("!null, _, _ -> fail")
    public static <T> void checkNull(@Nullable final T reference, @Nullable final String message, @NotNull final Object... values) throws IllegalArgumentException {

        if (reference != null)
            throw new IllegalArgumentException(message != null ? Strings.applyValues(message, values) : DEFAULT_CHECK_NULL_MESSAGE);

    }

    /**
     * Throws an {@link IllegalStateException} if the provided state is <code>false</code>.
     *
     * @param state the state
     * @throws IllegalStateException if the provided state is <code>false</code>
     */
    @Contract("false -> fail")
    public static void checkState(final boolean state) throws IllegalStateException {

        if (!state)
            throw new IllegalStateException(DEFAULT_CHECK_STATE_MESSAGE);

    }

    /**
     * Throws an {@link IllegalStateException} with the provided message if the provided state is <code>false</code>.
     *
     * @param state   the state
     * @param message the message
     * @param values  the message placeholder values
     * @throws IllegalStateException if the provided state is <code>false</code>
     */
    @Contract("false, _, _ -> fail")
    public static void checkState(final boolean state, @Nullable final String message, @NotNull final Object... values) throws IllegalStateException {

        if (!state)
            throw new IllegalStateException(message != null ? Strings.applyValues(message, values) : DEFAULT_CHECK_STATE_MESSAGE);

    }

    /**
     * Throws an {@link IllegalArgumentException} if the provided expression is <code>false</code>.
     *
     * @param expression the expression
     * @throws IllegalArgumentException if the provided expression is <code>false</code>
     */
    @Contract("false -> fail")
    public static void checkArgument(final boolean expression) throws IllegalArgumentException {

        if (!expression)
            throw new IllegalArgumentException();

    }

    /**
     * Throws an {@link IllegalArgumentException} with the provided message if the provided expression is
     * <code>false</code>.
     *
     * @param expression the expression
     * @param message    the message
     * @param values     the message placeholder values
     * @throws IllegalArgumentException if the provided expression is <code>false</code>
     */
    @Contract("false, _, _ -> fail")
    public static void checkArgument(final boolean expression, @Nullable final String message, @NotNull final Object... values) throws IllegalArgumentException {

        if (!expression)
            throw new IllegalArgumentException(message != null ? Strings.applyValues(message, values) : DEFAULT_CHECK_ARGUMENT_MESSAGE);

    }

    private Preconditions() {

        throw new UnsupportedOperationException();

    }

}
