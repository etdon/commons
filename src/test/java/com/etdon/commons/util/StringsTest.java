package com.etdon.commons.util;

import com.etdon.commons.trait.impl.string.StringTrait;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @see Strings
 */
public class StringsTest {

    @Test
    public void applyValues_Example_Success() {

        final String input = "1: {} 2: {}";
        final String output = Strings.applyValues(input, 1, 2);
        assertEquals("1: 1 2: 2", output);

    }

    @Test
    public void toChunks_Example_Success() {

        final String input = "0A0F100503";
        assertEquals("0A 0F 10 05 03", Strings.toChunks(input, 2));

    }

    @Test
    public void toLines_Example_Success() {

        final String input = "123456789";
        assertEquals("123" + System.lineSeparator() + "456" + System.lineSeparator() + "789", Strings.toLines(input, 3));

    }

    @Test
    public void surround_Example_Success() {

        final String affix = "[#]";
        final String input = "Example";
        assertEquals("[#]Example[#]", Strings.surround(affix, input));

    }

    @Test
    public void surround_NullAffix_Success() {

        final String input = "Example";
        assertEquals("Example", Strings.surround(null, input));

    }

    @Test
    public void surround_NullInput_Success() {

        final String affix = "[#]";
        assertEquals("[#][#]", Strings.surround(affix, null));

    }

    @Test
    public void surround_NullValues_Success() {

        assertEquals("", Strings.surround(null, null));

    }

    @Test
    public void combine_Example_Success() {

        final String[] inputs = new String[]{"first", "second", "third"};
        assertEquals("firstsecondthird", Strings.combine(inputs));

    }

    @Test
    public void combine_NullValues_Success() {

        assertEquals("", Strings.combine((String) null));

    }

    @Test
    public void list_Example_Success() {

        final String separator = ", ";
        final String[] inputs = new String[]{"first", "second", "third"};
        assertEquals("first, second, third", Strings.list(separator, inputs));

    }

    @Test
    public void list_NullSeparator_Success() {

        final String[] inputs = new String[]{"first", "second", "third"};
        assertEquals("firstsecondthird", Strings.list(null, inputs));

    }

    @Test
    public void list_NullInputs_Success() {

        final String separator = ", ";
        assertEquals("", Strings.list(separator, (String) null));

    }

    @Test
    public void list_NullValues_Success() {

        assertEquals("", Strings.list(null, (String) null));

    }

    @Test
    public void checkTraits_Example_Success() {

        final String input = "ExAmPlE";
        assertTrue(Strings.checkTraits(input, StringTrait.NOT_EMPTY, StringTrait.MIXED_CASE));

    }

}
