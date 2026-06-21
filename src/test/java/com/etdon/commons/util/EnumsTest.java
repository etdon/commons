package com.etdon.commons.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EnumsTest {

    @Test
    public void findNextValue_Example_Success() {

        assertEquals(TestEnum.SECOND, Enums.findNextValue(TestEnum.class, TestEnum.FIRST));

    }

    @Test
    public void findNextValue_LastToFirstValue_Success() {

        assertEquals(TestEnum.FIRST, Enums.findNextValue(TestEnum.class, TestEnum.THIRD));

    }

    @Test
    public void findNextValue_SingleValueEnum_Success() {

        assertEquals(SingleEnum.SINGLE, Enums.findNextValue(SingleEnum.class, SingleEnum.SINGLE));

    }

    enum TestEnum {

        FIRST,
        SECOND,
        THIRD

    }

    enum SingleEnum {

        SINGLE

    }

}
