package com.etdon.commons.test.util;

import com.etdon.commons.trait.impl.string.StringTrait;
import com.etdon.commons.util.Strings;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ContainsTraitTests {

    @Test
    public void isEligible_Example_Success() {

        final String input = "Hello World!";
        final String target = "World";
        assertTrue(Strings.checkTraits(input, StringTrait.CONTAINS.apply(target)));

    }

    @Test
    public void isEligible_Example_Failure() {

        final String input = "Hello World!";
        final String target = "Planet";
        assertFalse(Strings.checkTraits(input, StringTrait.CONTAINS.apply(target)));

    }

    @Test
    public void isEligible_Beginning_Success() {

        final String input = "Hello World!";
        final String target = "He";
        assertTrue(Strings.checkTraits(input, StringTrait.CONTAINS.apply(target)));

    }

    @Test
    public void isEligible_Ending_Success() {

        final String input = "Hello World!";
        final String target = "d!";
        assertTrue(Strings.checkTraits(input, StringTrait.CONTAINS.apply(target)));

    }

    @Test
    public void isEligible_Equal_Success() {

        final String input = "Hello World!";
        assertTrue(Strings.checkTraits(input, StringTrait.CONTAINS.apply(input)));

    }

}
