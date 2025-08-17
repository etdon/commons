package com.etdon.commons.conditional;

import com.etdon.commons.functional.Procedure;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class Conditional {

    /**
     * Executes the provided procedure if the provided state is <code>true</code>.
     *
     * @param state     The state.
     * @param procedure The procedure.
     */
    public static void executeIfTrue(final boolean state, @NotNull final Procedure procedure) {

        Preconditions.checkNotNull(procedure);
        if (state) procedure.execute();

    }

    /**
     * Executes the provided procedure if the provided state is <code>false</code>.
     *
     * @param state     The state.
     * @param procedure The procedure.
     */
    public static void executeIfFalse(final boolean state, @NotNull final Procedure procedure) {

        Preconditions.checkNotNull(procedure);
        if (!state) procedure.execute();

    }

    /**
     * Executes the provided procedure if the provided reference is not <code>null</code>.
     *
     * @param reference The reference.
     * @param procedure The procedure.
     * @param <T>       The reference type.
     */
    public static <T> void executeIfNotNull(@Nullable final T reference, @NotNull final Procedure procedure) {

        Preconditions.checkNotNull(procedure);
        if (reference != null) procedure.execute();

    }

    /**
     * Executes the provided procedure if the provided reference is <code>null</code>.
     *
     * @param reference The reference.
     * @param procedure The procedure.
     * @param <T>       The reference type.
     */
    public static <T> void executeIfNull(@Nullable final T reference, @NotNull final Procedure procedure) {

        Preconditions.checkNotNull(procedure);
        if (reference == null) procedure.execute();

    }

    private Conditional() {

        throw new UnsupportedOperationException();

    }

}
