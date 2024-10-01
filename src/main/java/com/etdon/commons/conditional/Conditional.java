package com.etdon.commons.conditional;

import com.etdon.commons.functional.Procedure;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class Conditional {

    public static void executeIfTrue(final boolean state, @NotNull final Procedure procedure) {

        if (state) procedure.execute();

    }

    public static void executeIfFalse(final boolean state, @NotNull final Procedure procedure) {

        if (!state) procedure.execute();

    }

    public static <T> void executeIfNotNull(@Nullable final T reference, @NotNull final Procedure procedure) {

        if (reference != null) procedure.execute();

    }

    public static <T> void executeIfNull(@Nullable final T reference, @NotNull final Procedure procedure) {

        if (reference == null) procedure.execute();

    }

    private Conditional() {

        throw new UnsupportedOperationException();

    }

}
