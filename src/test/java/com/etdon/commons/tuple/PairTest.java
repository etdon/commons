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
    public void equals_NullKeyValueNotEquals_Success() {

        final Pair<String, String> first = Pair.of("key", "value");
        final Pair<String, String> second = Pair.of(null, null);
        assertNotEquals(first, second);

    }

}
