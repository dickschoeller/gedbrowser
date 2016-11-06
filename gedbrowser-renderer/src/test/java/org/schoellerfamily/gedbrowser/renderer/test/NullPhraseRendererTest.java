package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.renderer.DefaultRenderer;
import org.schoellerfamily.gedbrowser.renderer.GedRendererFactory;
import org.schoellerfamily.gedbrowser.renderer.NullPhraseRenderer;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;

/**
 * @author Dick Schoeller
 */
public class NullPhraseRendererTest {
    /** */
    private transient NullPhraseRenderer npr;

    /** */
    @Before
    public final void init() {
        final DefaultRenderer renderer = new DefaultRenderer(new GedObject(null,
                "THIS IS A STRING") {
        }, new GedRendererFactory(),
        RenderingContext.anonymous());
        npr = (NullPhraseRenderer) renderer.getPhraseRenderer();
    }

    /** */
    @Test
    public final void testRenderAsPhrase() {
        assertEquals("Expected empty phrase", "", npr.renderAsPhrase());
    }
}
