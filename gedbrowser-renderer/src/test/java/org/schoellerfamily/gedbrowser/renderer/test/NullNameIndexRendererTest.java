package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.schoellerfamily.gedbrowser.renderer.NullNameIndexRenderer;

/**
 * @author Dick Schoeller
 */
final class NullNameIndexRendererTest {
    @Test
    void testGetNameIndex() {
        final NullNameIndexRenderer renderer = new NullNameIndexRenderer();
        assertEquals("", renderer.getIndexName(), "Expected empty string");
    }
}
