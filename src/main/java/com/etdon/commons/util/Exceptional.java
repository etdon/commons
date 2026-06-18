package com.etdon.commons.util;

import com.etdon.commons.conditional.Preconditions;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Static factory for exceptions with formatted messages. Not suited for use in performance critical methods.
 */
public final class Exceptional {

    /**
     * Creates a new runtime exception instance. The provided message is being formatted replacing all placeholders
     * with the provided values before it's being handed to the target constructor.
     *
     * @param message the message
     * @param values the placeholder values
     * @return the exception instance
     * @throws RuntimeException if the instantiation failed
     * @see Exceptional#of(Class, String, Object...)
     */
    public static RuntimeException of(@NotNull final String message,
                                      @NotNull final Object... values) {

        return of(RuntimeException.class, message, values);

    }

    /**
     * Creates a new exception instance of the provided type if a string message accepting constructor is found. The
     * provided message is being formatted replacing all placeholders with the provided values before it's being handed
     * to the target constructor.
     *
     * @param type the exception type class
     * @param message the message
     * @param values the placeholder values
     * @return the exception instance
     * @param <T> the exception type
     * @throws RuntimeException if the instantiation failed
     */
    public static <T extends Exception> T of(@NotNull final Class<T> type,
                                             @NotNull final String message,
                                             @NotNull final Object... values) {

        Preconditions.checkNotNull(type);
        Preconditions.checkNotNull(message);
        Preconditions.checkNotNull(values);
        try {
            final Constructor<T> exception = type.getConstructor(String.class);
            return exception.newInstance(StringUtils.applyValues(message, values));
        } catch (final NoSuchMethodException |
                       InvocationTargetException |
                       InstantiationException |
                       IllegalAccessException ex) {
            throw new RuntimeException(ex);
        }

    }

    private Exceptional() {

        throw new UnsupportedOperationException();

    }

}
