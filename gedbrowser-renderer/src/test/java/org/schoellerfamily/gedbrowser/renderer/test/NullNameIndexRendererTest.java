package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.schoellerfamily.gedbrowser.renderer.NullNameIndexRenderer;

/**
 * @author Dick Schoeller
 */
public final class NullNameIndexRendererTest {
    /** */
    @Test
    public void testGetNameIndex() {
        final NullNameIndexRenderer renderer = new NullNameIndexRenderer();
        assertEquals("Expected empty string", "",
                renderer.getIndexName());
    }
}
