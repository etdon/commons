package com.etdon.commons.tuple;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @see ImmutablePair
 */
public class ImmutablePairTest {

    @Test
    public void init_NullKeyValue_Success() {

        final ImmutablePair<String, String> pair = ImmutablePair.of(null, null);
        assertNull(pair.getKey());
        assertNull(pair.getValue());

    }

    @Test
    public void setKey_Any_Throws() {

        final ImmutablePair<String, String> pair = ImmutablePair.of("key", "value");
        assertThrows(UnsupportedOperationException.class, () -> pair.setKey(null));

    }

    @Test
    public void setValue_Any_Throws() {

        final ImmutablePair<String, String> pair = ImmutablePair.of("key", "value");
        assertThrows(UnsupportedOperationException.class, () -> pair.setValue(null));

    }

}
