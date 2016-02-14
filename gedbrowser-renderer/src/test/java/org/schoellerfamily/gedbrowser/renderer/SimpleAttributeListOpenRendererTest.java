package org.schoellerfamily.gedbrowser.renderer;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;

/**
 * @author Dick Schoeller
 */
public class SimpleAttributeListOpenRendererTest {
    /** */
    private transient SimpleAttributeListOpenRenderer npr;

    /** */
    private final transient GedObject gob =
            new GedObject(null, "THIS IS A STRING") {
    };

    /** */
    @Before
    public final void init() {
        final DefaultRenderer renderer = new DefaultRenderer(gob,
                new GedRendererFactory(),
                RenderingContext.anonymous());
        npr = (SimpleAttributeListOpenRenderer) renderer
                .getAttributeListOpenRenderer();
    }

    /** */
    @Test
    public final void testRenderAsPhrase() {
        final StringBuilder builder = new StringBuilder();
        npr.renderAttributeListOpen(builder, 0, gob);
        final String string = builder.toString();
        assertEquals("", string);
    }

}
