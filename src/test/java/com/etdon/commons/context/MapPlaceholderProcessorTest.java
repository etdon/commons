package com.etdon.commons.context;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Certain unit tests check for a {@link RuntimeException} instead of a {@link NullPointerException} thrown by
 * {@link com.etdon.commons.conditional.Preconditions} to avoid collision with the {@link org.jetbrains.annotations.NotNull}
 * null check that throws a {@link IllegalArgumentException} instead, which is not relevant after compilation.
 * @see MapPlaceholderProcessor
 */
public class MapPlaceholderProcessorTest {

    @Test
    public void init_ConstructorDirtyMapEntries_Throws() {

        final Map<String, String> dirty = new HashMap<>();
        dirty.put("identifier", null);
        dirty.put(null, "value");
        assertThrows(RuntimeException.class, () -> new MapPlaceholderProcessor(dirty));

    }

    @Test
    public void init_ConstructorNoMapOwnership_Success() {

        final Map<String, String> owned = new HashMap<>();
        owned.put("identifier", "value");
        final MapPlaceholderProcessor mapPlaceholderProcessor = new MapPlaceholderProcessor(owned);
        assertEquals(1, mapPlaceholderProcessor.getPlaceholderCount());
        owned.put("key", "value");
        assertEquals(1, mapPlaceholderProcessor.getPlaceholderCount());

    }

    @Test
    public void process_Example_Success() {

        final MapPlaceholderProcessor mapPlaceholderProcessor = new MapPlaceholderProcessor();
        mapPlaceholderProcessor.registerPlaceholder("identifier", "value");
        assertEquals("value", mapPlaceholderProcessor.process("${identifier}"));

    }

    @Test
    public void process_InputNoPlaceholder_Success() {

        final MapPlaceholderProcessor mapPlaceholderProcessor = new MapPlaceholderProcessor();
        mapPlaceholderProcessor.registerPlaceholder("identifier", "value");
        assertEquals("input", mapPlaceholderProcessor.process("input"));

    }

    @Test
    public void process_ValueSupplierNonCaching_Success() {

        final Deque<String> queue = new ArrayDeque<>();
        queue.add("first");
        queue.add("second");

        final MapPlaceholderProcessor mapPlaceholderProcessor = new MapPlaceholderProcessor();
        mapPlaceholderProcessor.registerPlaceholder("identifier", queue::pop);
        assertEquals("first second", mapPlaceholderProcessor.process("${identifier} ${identifier}"));

    }

    @Test
    public void process_UnusualFormat_Success() {

        final MapPlaceholderProcessor mapPlaceholderProcessor = new MapPlaceholderProcessor();
        mapPlaceholderProcessor.registerPlaceholder("identifier", "value");
        assertEquals("${$}}$$}{}", mapPlaceholderProcessor.process("${$}}$$}{}"));

    }

    @Test
    public void process_Nested_Success() {

        final MapPlaceholderProcessor mapPlaceholderProcessor = new MapPlaceholderProcessor();
        mapPlaceholderProcessor.registerPlaceholder("identifier", "value");
        assertEquals("${${${${}}}}", mapPlaceholderProcessor.process("${${${${}}}}"));

    }

    @Test
    public void process_EscapeEndIdentifier_Success() {

        final MapPlaceholderProcessor mapPlaceholderProcessor = new MapPlaceholderProcessor();
        mapPlaceholderProcessor.registerPlaceholder("identifier", "value");
        assertEquals("${}}}}", mapPlaceholderProcessor.process("${\\}\\}\\}}"));

    }

    @Test
    public void registerPlaceholder_NullIdentifierOrValue_Throws() {

        final MapPlaceholderProcessor mapPlaceholderProcessor = new MapPlaceholderProcessor();
        assertThrows(RuntimeException.class, () -> mapPlaceholderProcessor.registerPlaceholder(null, "value"));
        assertThrows(RuntimeException.class, () -> mapPlaceholderProcessor.registerPlaceholder("identifier", (String) null));

    }

    @Test
    public void unregisterPlaceholder_Example_Success() {

        final MapPlaceholderProcessor mapPlaceholderProcessor = new MapPlaceholderProcessor();
        mapPlaceholderProcessor.registerPlaceholder("identifier", "value");
        assertEquals("value", mapPlaceholderProcessor.unregisterPlaceholder("identifier"));

    }

    @Test
    public void getEntries_DefensiveCopy_Success() {

        final MapPlaceholderProcessor mapPlaceholderProcessor = new MapPlaceholderProcessor();
        mapPlaceholderProcessor.registerPlaceholder("identifier", "value");
        assertEquals(1, mapPlaceholderProcessor.getPlaceholderCount());
        final Map<String, String> copy = mapPlaceholderProcessor.getEntries();
        copy.remove("identifier");
        assertTrue(copy.isEmpty());
        assertEquals(1, mapPlaceholderProcessor.getPlaceholderCount());

    }

}
