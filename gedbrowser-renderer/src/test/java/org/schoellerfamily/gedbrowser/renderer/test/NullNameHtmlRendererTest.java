package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.schoellerfamily.gedbrowser.renderer.NameHtmlRenderer;
import org.schoellerfamily.gedbrowser.renderer.NullNameHtmlRenderer;

/**
 * @author Dick Schoeller
 */
public final class NullNameHtmlRendererTest {
    /** */
    private transient NameHtmlRenderer nameHtmlRenderer;

    /** */
    @Before
    public void init() {
        nameHtmlRenderer = new NullNameHtmlRenderer();
    }

    /** */
    @Test
    public void testGetNameHtml() {
        assertEquals("expected empty string", "",
                nameHtmlRenderer.getNameHtml());
    }
}
