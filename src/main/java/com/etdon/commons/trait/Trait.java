package com.etdon.commons.trait;

import org.jetbrains.annotations.Nullable;

public interface Trait<T> {

    boolean isEligible(@Nullable final T input);

}
