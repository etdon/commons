package com.etdon.commons.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @see Exceptional
 */
public class ExceptionalTest {

    @Test
    public void of_Create_Success() {

        final Exception exception = Exceptional.of(RuntimeException.class, "A: {} B: {}", 1, 2);
        assertNotNull(exception);
        assertEquals("A: 1 B: 2", exception.getMessage());

    }

    @SuppressWarnings("DataFlowIssue")
    @Test
    public void of_NullTypeAndMessage_Throws() {

        assertThrows(NullPointerException.class, () -> Exceptional.of(null, (Object) null));

    }

    @Test
    public void of_NullValues_Throws() {

        assertThrows(NullPointerException.class, () -> Exceptional.of(RuntimeException.class, "", (Object[]) null));

    }

}
