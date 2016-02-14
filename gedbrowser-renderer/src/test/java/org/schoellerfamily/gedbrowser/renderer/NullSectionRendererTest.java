package org.schoellerfamily.gedbrowser.renderer;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;

 /**
  * @author Dick Schoeller
  */
public class NullSectionRendererTest {
    /** */
    private transient NullSectionRenderer nsr;

    /** */
    @Before
    public final void init() {
        final DefaultRenderer renderer =
                new DefaultRenderer(new GedObject(null) { },
                        new GedRendererFactory(),
                        RenderingContext.anonymous());
        nsr = (NullSectionRenderer) renderer
                .getSectionRenderer();
    }

    /** */
    @Test
    public final void testNullSectionRendererNullFalse01() {
        final StringBuilder builder = new StringBuilder();
        nsr.renderAsSection(builder, null, false, 0, 1);
        final String string = builder.toString();
        assertEquals("", string);
    }

    /** */
    @Test
    public final void testNullSectionRendererNullTrue01() {
        final StringBuilder builder = new StringBuilder();
        nsr.renderAsSection(builder, null, true, 0, 1);
        final String string = builder.toString();
        assertEquals("", string);
    }

    /** */
    @Test
    public final void testNullSectionRendererOuterFalse21() {
        final StringBuilder builder = new StringBuilder();
        nsr.renderAsSection(builder, new GedRenderer<GedObject>(null,
                new GedRendererFactory(),
                RenderingContext.anonymous()) {
        }, false, 2, 1);
        final String string = builder.toString();
        assertEquals("", string);
    }

    /** */
    @Test
    public final void testNullSectionRendererOuterTrue02() {
        final StringBuilder builder = new StringBuilder();
        nsr.renderAsSection(builder, new GedRenderer<GedObject>(null,
                new GedRendererFactory(),
                RenderingContext.anonymous()) {
        }, true, 0, 2);
        final String string = builder.toString();
        assertEquals("", string);
    }
}
