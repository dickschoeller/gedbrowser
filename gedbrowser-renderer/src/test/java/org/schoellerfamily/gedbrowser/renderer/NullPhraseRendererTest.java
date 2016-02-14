package org.schoellerfamily.gedbrowser.renderer;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;

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
