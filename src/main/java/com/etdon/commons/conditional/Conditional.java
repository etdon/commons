package com.etdon.commons.conditional;

import com.etdon.commons.functional.Procedure;
import org.jetbrains.annotations.NotNullByDefault;
import org.jetbrains.annotations.Nullable;

/**
 * Utilities for executing procedures based on a condition.
 */
@NotNullByDefault
public final class Conditional {

    /**
     * Executes the provided procedure if the provided state is <code>true</code>.
     *
     * @param state     the state
     * @param procedure the procedure
     */
    public static void executeIfTrue(final boolean state, final Procedure procedure) {

        Preconditions.checkNotNull(procedure);
        if (state) procedure.execute();

    }

    /**
     * Executes the provided procedure if the provided state is <code>false</code>.
     *
     * @param state     the state
     * @param procedure the procedure
     */
    public static void executeIfFalse(final boolean state, final Procedure procedure) {

        Preconditions.checkNotNull(procedure);
        if (!state) procedure.execute();

    }

    /**
     * Executes the provided procedure if the provided reference is not <code>null</code>.
     *
     * @param reference the reference
     * @param procedure the procedure
     * @param <T>       the reference type
     */
    public static <T> void executeIfNotNull(@Nullable final T reference, final Procedure procedure) {

        Preconditions.checkNotNull(procedure);
        if (reference != null) procedure.execute();

    }

    /**
     * Executes the provided procedure if the provided reference is <code>null</code>.
     *
     * @param reference the reference
     * @param procedure the procedure
     * @param <T>       the reference type
     */
    public static <T> void executeIfNull(@Nullable final T reference, final Procedure procedure) {

        Preconditions.checkNotNull(procedure);
        if (reference == null) procedure.execute();

    }

    private Conditional() {

        throw new UnsupportedOperationException();

    }

}
