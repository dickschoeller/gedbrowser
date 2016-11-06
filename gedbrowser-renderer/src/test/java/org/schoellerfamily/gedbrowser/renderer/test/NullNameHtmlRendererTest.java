package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.schoellerfamily.gedbrowser.renderer.NameHtmlRenderer;
import org.schoellerfamily.gedbrowser.renderer.NullNameHtmlRenderer;

/**
 * @author Dick Schoeller
 */
public class NullNameHtmlRendererTest {
    /** */
    private transient NameHtmlRenderer nameHtmlRenderer;

    /** */
    @Before
    public final void init() {
        nameHtmlRenderer = new NullNameHtmlRenderer();
    }

    /** */
    @Test
    public final void testGetNameHtml() {
        assertEquals("expected empty string", "",
                nameHtmlRenderer.getNameHtml());
    }
}
