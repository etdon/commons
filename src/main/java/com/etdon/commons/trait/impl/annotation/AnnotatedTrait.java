package com.etdon.commons.trait.impl.annotation;

import com.etdon.commons.conditional.Preconditions;
import com.etdon.commons.trait.Trait;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

public final class AnnotatedTrait implements Trait<AnnotatedElement> {

    private final Class<? extends Annotation> type;

    private AnnotatedTrait(final Class<? extends Annotation> type) {

        this.type = type;

    }

    @Override
    public boolean isEligible(@Nullable final AnnotatedElement input) {

        if (input == null)
            return false;

        return input.getAnnotation(this.type) != null;

    }

    @NotNull
    public Class<? extends Annotation> getType() {

        return this.type;

    }

    public static AnnotatedTrait of(@NotNull final Class<? extends Annotation> type) {

        Preconditions.checkNotNull(type);
        return new AnnotatedTrait(type);

    }

}
