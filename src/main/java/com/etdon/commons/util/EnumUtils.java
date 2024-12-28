package com.etdon.commons.util;

import com.etdon.commons.conditional.Preconditions;
import org.jetbrains.annotations.NotNull;

public final class EnumUtils {

    /**
     * Find the enum value that follows the provided current enum value in the declaration order of the enum
     * implementation and returns it.
     *
     * @param enumClass    The enum class.
     * @param currentValue The current value.
     * @param <T>          The enum type.
     * @return The following value.
     */

    public static <T extends Enum<T>> T findNextEnumValue(@NotNull final Class<T> enumClass, @NotNull final T currentValue) {

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

    private EnumUtils() {

        throw new UnsupportedOperationException();

    }

}
