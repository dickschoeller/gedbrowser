package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.renderer.DefaultRenderer;
import org.schoellerfamily.gedbrowser.renderer.GedRendererFactory;
import org.schoellerfamily.gedbrowser.renderer.NullListItemRenderer;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;

/**
 * @author Dick Schoeller
 */
public class NullListItemRendererTest {
    /** */
    private transient NullListItemRenderer nsr;

    /** */
    @Before
    public final void init() {
        final DefaultRenderer renderer =
                new DefaultRenderer(new GedObject(null) { },
                        new GedRendererFactory(),
                        RenderingContext.anonymous());
        nsr = (NullListItemRenderer) renderer
                .getListItemRenderer();
    }

    /** */
    @Test
    public final void testRenderAsListItemFalse0() {
        final StringBuilder builder = new StringBuilder();
        nsr.renderAsListItem(builder, false, 0);
        final String string = builder.toString();
        assertEquals("", string);
    }

    /** */
    @Test
    public final void testRenderAsListItemFalse2() {
        final StringBuilder builder = new StringBuilder();
        nsr.renderAsListItem(builder, false, 2);
        final String string = builder.toString();
        assertEquals("", string);
    }

    /** */
    @Test
    public final void testRenderAsListItemTrue0() {
        final StringBuilder builder = new StringBuilder();
        nsr.renderAsListItem(builder, true, 0);
        final String string = builder.toString();
        assertEquals("", string);
    }

    /** */
    @Test
    public final void testRenderAsListItemTrue2() {
        final StringBuilder builder = new StringBuilder();
        nsr.renderAsListItem(builder, true, 2);
        final String string = builder.toString();
        assertEquals("", string);
    }
}
