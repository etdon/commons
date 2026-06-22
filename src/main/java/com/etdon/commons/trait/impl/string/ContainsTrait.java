package com.etdon.commons.trait.impl.string;

import com.etdon.commons.trait.Trait;
import org.jetbrains.annotations.Nullable;

/**
 * {@link Trait} that is eligible for character sequences containing a target character sequence as a subsequence.
 */
final class ContainsTrait implements Trait<CharSequence> {

    private final CharSequence target;

    private ContainsTrait(final CharSequence target) {

        this.target = target;

    }

    @Override
    public boolean isEligible(@Nullable final CharSequence input) {

        if (input == null && this.target == null)
            return true;
        if (input == null || input.length() < this.target.length())
            return false;

        int targetIndex = 0;
        for (int i = 0; i < input.length(); i++) {
            if (targetIndex >= this.target.length())
                return true;
            if (input.length() - i < (this.target.length() - targetIndex))
                return false;
            if (input.charAt(i) == this.target.charAt(targetIndex)) {
                targetIndex++;
            } else {
                if (targetIndex > 0)
                    targetIndex = 0;
            }
        }

        return true;

    }

    @Nullable
    public CharSequence getTarget() {

        return this.target;

    }

    public static ContainsTrait of(@Nullable final CharSequence target) {

        return new ContainsTrait(target);

    }

}
