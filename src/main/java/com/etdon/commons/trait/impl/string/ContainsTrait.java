package com.etdon.commons.trait.impl.string;

import com.etdon.commons.trait.Trait;
import org.jetbrains.annotations.Nullable;

final class ContainsTrait implements Trait<CharSequence> {

    private final CharSequence target;

    private ContainsTrait(final CharSequence target) {

        this.target = target;

    }

    @Override
    public boolean isEligible(@Nullable final CharSequence input) {

        return false;

    }

    @Nullable
    public CharSequence getTarget() {

        return this.target;

    }

    public static ContainsTrait of(@Nullable final CharSequence target) {

        return new ContainsTrait(target);

    }

}
