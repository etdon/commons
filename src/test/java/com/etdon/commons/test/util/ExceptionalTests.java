package com.etdon.commons.test.util;

import com.etdon.commons.util.Exceptional;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ExceptionalTests {

    @Test
    public void of_Create_Success() {

        final Exception exception = Exceptional.of(RuntimeException.class, "A: {} B: {}", 1, 2);
        assertNotNull(exception);
        assertEquals("A: 1 B: 2", exception.getMessage());

    }

    @SuppressWarnings("DataFlowIssue")
    @Test
    public void of_NullTypeAndMessage_Throws() {

        assertThrows(NullPointerException.class, () -> Exceptional.of(null, null));

    }

    @Test
    public void of_NullValues_Throws() {

        assertThrows(NullPointerException.class, () -> Exceptional.of(RuntimeException.class, "", (Object[]) null));

    }

}
