package com.etdon.commons.test.util;

import com.etdon.commons.util.StringUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StringUtilsTests {

    @Test
    public void applyValues_Example_Success() {

        final String input = "1: {} 2: {}";
        final String output = StringUtils.applyValues(input, 1, 2);
        assertEquals("1: 1 2: 2", output);

    }

}
