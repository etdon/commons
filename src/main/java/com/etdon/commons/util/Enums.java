package com.etdon.commons.util;

import com.etdon.commons.conditional.Preconditions;
import org.jetbrains.annotations.NotNullByDefault;

/**
 * Utilities for enum work.
 */
@NotNullByDefault
public final class Enums {

    /**
     * Find the enum value that follows the provided current enum value in the declaration order of the enum
     * implementation and returns it.
     *
     * @param enumClass    the enum type class
     * @param currentValue the current value
     * @param <T>          the enum type
     * @return the following value
     */
    public static <T extends Enum<T>> T findNextValue(final Class<T> enumClass, final T currentValue) {

        Preconditions.checkNotNull(enumClass);
        Preconditions.checkNotNull(currentValue);
        final T[] values = enumClass.getEnumConstants();
        T nextValue = currentValue;
        for (int i = 0; i < values.length; i++) {
            final T value = values[i];
            if (value == currentValue) {
                if (i + 1 >= values.length) {
                    nextValue = values[0];
                } else {
                    nextValue = values[i + 1];
                }
            }
        }

        return nextValue;

    }

    private Enums() {

        throw new UnsupportedOperationException();

    }

}
