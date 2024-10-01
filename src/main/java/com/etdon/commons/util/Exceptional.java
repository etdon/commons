package com.etdon.commons.util;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public final class Exceptional {

    public static <T extends Exception> T of(@NotNull final Class<T> type,
                                             @NotNull final String message,
                                             @NotNull final Object... values) {

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
