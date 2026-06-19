package com.etdon.commons.tuple;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * @see Pair
 */
public class PairTest {

    @Test
    public void init_NullKeyValue_Success() {

        final Pair<String, String> pair = Pair.of(null, null);
        assertNull(pair.getKey());
        assertNull(pair.getValue());

    }

    @Test
    public void setKey_NullKey_Success() {

        final Pair<String, String> pair = Pair.of("key", "value");
        pair.setKey(null);
        assertNull(pair.getKey());

    }

    @Test
    public void setValue_NullValue_Success() {

        final Pair<String, String> pair = Pair.of("key", "value");
        pair.setValue(null);
        assertNull(pair.getValue());

    }

}
