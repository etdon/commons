package com.etdon.commons.trait.impl.string;

import com.etdon.commons.trait.Trait;
import org.jetbrains.annotations.Nullable;

public final class MixedCaseStringTrait implements Trait<CharSequence> {

    @Override
    public boolean isEligible(@Nullable final CharSequence input) {

        if (input == null || input.length() == 0)
            return false;

        boolean upperCase = false, lowerCase = false;
        for (int i = 0; i < input.length(); i++) {
            final char c = input.charAt(i);
            if (Character.isUpperCase(c))
                upperCase = true;
            if (Character.isLowerCase(c))
                lowerCase = true;
            if (upperCase && lowerCase)
                return true;
        }

        return false;

    }

}
