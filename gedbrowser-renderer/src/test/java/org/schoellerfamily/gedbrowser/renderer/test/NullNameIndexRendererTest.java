package org.schoellerfamily.gedbrowser.renderer.test;

import org.junit.Assert;
import org.junit.Test;
import org.schoellerfamily.gedbrowser.renderer.NullNameIndexRenderer;

/**
 * @author Dick Schoeller
 */
public class NullNameIndexRendererTest {
    /** */
    @Test
    public final void testGetNameIndex() {
        final NullNameIndexRenderer renderer = new NullNameIndexRenderer();
        Assert.assertEquals("Expected empty string", "",
                renderer.getIndexName());
    }
}
