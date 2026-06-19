package com.etdon.commons.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @see StringUtils
 */
public class StringUtilsTest {

    @Test
    public void applyValues_Example_Success() {

        final String input = "1: {} 2: {}";
        final String output = StringUtils.applyValues(input, 1, 2);
        assertEquals("1: 1 2: 2", output);

    }

    @Test
    public void toChunks_Example_Success() {

        final String input = "0A0F100503";
        assertEquals("0A 0F 10 05 03", StringUtils.toChunks(input, 2));

    }

    @Test
    public void toLines_Example_Success() {

        final String input = "123456789";
        assertEquals("123" + System.lineSeparator() + "456" + System.lineSeparator() + "789", StringUtils.toLines(input, 3));

    }

}
