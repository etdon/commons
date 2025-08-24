package com.etdon.commons.trait.impl.string;

import com.etdon.commons.trait.Trait;

import java.util.Objects;
import java.util.function.Function;

public final class StringTrait {

    public static final Trait<CharSequence> NULL = Objects::isNull;
    public static final Trait<CharSequence> NOT_NULL = Objects::nonNull;
    public static final Trait<CharSequence> EMPTY = (input) -> input == null || input.length() == 0;
    public static final Trait<CharSequence> NOT_EMPTY = (input) -> input != null && input.length() > 0;
    public static final Trait<CharSequence> MIXED_CASE = new MixedCaseStringTrait();
    private static final Function<CharSequence, Trait<CharSequence>> CONTAINS = ContainsTrait::of;

    private StringTrait() {

        throw new UnsupportedOperationException();

    }

}
