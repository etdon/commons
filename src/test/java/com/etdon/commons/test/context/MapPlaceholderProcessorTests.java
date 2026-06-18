package com.etdon.commons.test.context;

import com.etdon.commons.context.MapPlaceholderProcessor;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class MapPlaceholderProcessorTests {

    @Test
    public void initialize_ConstructorDirtyMapEntries_Throws() {

        final Map<String, String> dirty = new HashMap<>();
        dirty.put("identifier", null);
        dirty.put(null, "value");
        assertThrows(IllegalArgumentException.class, () -> new MapPlaceholderProcessor(dirty));

    }

    @Test
    public void initialize_ConstructorNoMapOwnership_Success() {

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
    public void registerPlaceholder_NullIdentifierOrValue_Throws() {

        final MapPlaceholderProcessor mapPlaceholderProcessor = new MapPlaceholderProcessor();
        assertThrows(IllegalArgumentException.class, () -> mapPlaceholderProcessor.registerPlaceholder(null, "value"));
        assertThrows(IllegalArgumentException.class, () -> mapPlaceholderProcessor.registerPlaceholder("identifier", null));

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
