package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.schoellerfamily.gedbrowser.renderer.NullNameIndexRenderer;

/**
 * Contains tests for null name index renderer.
 *
 * @author Richard Schoeller
 */
final class NullNameIndexRendererTest {
    @Test
    void testGetNameIndex() {
        final NullNameIndexRenderer renderer = new NullNameIndexRenderer();
        assertEquals("", renderer.getIndexName(), "Expected empty string");
    }
}
