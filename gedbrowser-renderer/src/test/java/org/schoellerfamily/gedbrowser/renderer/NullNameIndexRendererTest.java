package org.schoellerfamily.gedbrowser.renderer;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * @author Dick Schoeller
 */
public class NullNameIndexRendererTest {
    /** */
    @Test
    public final void testGetNameIndex() {
        final NullNameIndexRenderer renderer = new NullNameIndexRenderer();
        assertEquals("Expected empty name", "", renderer.getIndexName());
    }
}
