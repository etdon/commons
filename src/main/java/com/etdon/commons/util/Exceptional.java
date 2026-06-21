package com.etdon.commons.util;

import com.etdon.commons.conditional.Preconditions;
import org.jetbrains.annotations.NotNullByDefault;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.function.Function;

/**
 * Static factory for exceptions with formatted messages.
 */
@NotNullByDefault
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
    public static RuntimeException of(final String message,
                                      final Object... values) {

        return of(RuntimeException.class, message, values);

    }

    /**
     * Creates a new exception instance of the provided type if a string message accepting constructor is found. The
     * provided message is being formatted replacing all placeholders with the provided values before it's being handed
     * to the target constructor.
     * <p>
     * In most cases the {@link Exceptional#of(Function, String, Object...)} overload is preferred.
     *
     * @param type the exception type class
     * @param message the message
     * @param values the placeholder values
     * @return the exception instance
     * @param <T> the exception type
     * @throws RuntimeException if the instantiation failed
     */
    public static <T extends Exception> T of(final Class<T> type,
                                             final String message,
                                             final Object... values) {

        Preconditions.checkNotNull(type);
        Preconditions.checkNotNull(message);
        Preconditions.checkNotNull(values);
        try {
            final Constructor<T> exception = type.getConstructor(String.class);
            return exception.newInstance(Strings.applyValues(message, values));
        } catch (final NoSuchMethodException |
                       InvocationTargetException |
                       InstantiationException |
                       IllegalAccessException ex) {
            throw new RuntimeException(ex);
        }

    }

    /**
     * Creates a new exception instance using the provided factory. The provided message is being formatted replacing
     * all placeholders with the provided values before it's being handed to the target factory.
     * <p>
     * In most cases this overload is preferred over {@link Exceptional#of(Class, String, Object...)}.
     *
     * @param factory the exception factory
     * @param message the message
     * @param values the placeholder values
     * @return the exception instance
     * @param <T> the exception type
     */
    public static <T extends Exception> T of(final Function<String, T> factory,
                                             final String message,
                                             final Object... values) {

        Preconditions.checkNotNull(factory);
        Preconditions.checkNotNull(message);
        Preconditions.checkNotNull(values);
        return factory.apply(Strings.applyValues(message, values));

    }

    private Exceptional() {

        throw new UnsupportedOperationException();

    }

}
