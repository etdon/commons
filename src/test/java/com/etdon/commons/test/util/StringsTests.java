package com.etdon.commons.test.util;

import com.etdon.commons.util.Strings;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StringsTests {

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

        assertEquals("", Strings.combine(null));

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
        assertEquals("", Strings.list(separator, null));

    }

    @Test
    public void list_NullValues_Success() {

        assertEquals("", Strings.list(null, null));

    }

}
