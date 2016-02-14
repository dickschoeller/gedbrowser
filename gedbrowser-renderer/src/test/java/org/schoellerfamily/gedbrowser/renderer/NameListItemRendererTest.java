package org.schoellerfamily.gedbrowser.renderer;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.schoellerfamily.gedbrowser.datamodel.Name;

/**
 * @author Dick Schoeller
 */
public class NameListItemRendererTest {
    /** */
    private static final String UNEXPECTED_STRING =
            "Unexpected string returned";
    /** */
    private static final String EXPECT_EMPTY = "Expected empty string";

    /** */
    @Test
    public final void testRenderSimple() {
        final Name name = new Name(null, "Richard /Schoeller/");
        final NameRenderer nameRenderer = new NameRenderer(name,
                new GedRendererFactory(),
                RenderingContext.anonymous());
        final NameListItemRenderer nlir = (NameListItemRenderer) nameRenderer
                .getListItemRenderer();
        final StringBuilder builder = new StringBuilder();
        nlir.renderAsListItem(builder, false, 0);
        assertEquals(UNEXPECTED_STRING, "Richard  Schoeller",
                builder.toString());
    }

    /** */
    @Test
    public final void testRenderHarder() {
        final Name name = new Name(null, "Karl Frederick /Schoeller/Jr.");
        final NameRenderer nameRenderer = new NameRenderer(name,
                new GedRendererFactory(),
                RenderingContext.anonymous());
        final NameListItemRenderer nlir = (NameListItemRenderer) nameRenderer
                .getListItemRenderer();
        final StringBuilder builder = new StringBuilder();
        nlir.renderAsListItem(builder, false, 0);
        assertEquals(UNEXPECTED_STRING, "Karl Frederick  Schoeller Jr.",
                builder.toString());
    }

    /** */
    @Test
    public final void testRenderNonZeroPad() {
        final Name name = new Name(null, "Karl Frederick /Schoeller/Jr.");
        final NameRenderer nameRenderer = new NameRenderer(name,
                new GedRendererFactory(),
                RenderingContext.anonymous());
        final NameListItemRenderer nlir = (NameListItemRenderer) nameRenderer
                .getListItemRenderer();
        final StringBuilder builder = new StringBuilder();
        nlir.renderAsListItem(builder, false, 1);
        assertEquals(EXPECT_EMPTY, "", builder.toString());
    }

    /** */
    @Test
    public final void testRenderNewLine() {
        final Name name = new Name(null, "Karl Frederick /Schoeller/Jr.");
        final NameRenderer nameRenderer = new NameRenderer(name,
                new GedRendererFactory(),
                RenderingContext.anonymous());
        final NameListItemRenderer nlir = (NameListItemRenderer) nameRenderer
                .getListItemRenderer();
        final StringBuilder builder = new StringBuilder();
        nlir.renderAsListItem(builder, true, 0);
        assertEquals(UNEXPECTED_STRING, "\nKarl Frederick  Schoeller Jr.",
                builder.toString());
    }

    /** */
    @Test
    public final void testRenderNoPrefix() {
        final Name name = new Name(null, "/Schoeller/");
        final NameRenderer nameRenderer = new NameRenderer(name,
                new GedRendererFactory(),
                RenderingContext.anonymous());
        final NameListItemRenderer nlir = (NameListItemRenderer) nameRenderer
                .getListItemRenderer();
        final StringBuilder builder = new StringBuilder();
        nlir.renderAsListItem(builder, false, 0);
        assertEquals(UNEXPECTED_STRING, "Schoeller", builder.toString());
    }

    /** */
    @Test
    public final void testRenderEmpty() {
        final Name name = new Name(null, "");
        final NameRenderer nameRenderer = new NameRenderer(name,
                new GedRendererFactory(),
                RenderingContext.anonymous());
        final NameListItemRenderer nlir = (NameListItemRenderer) nameRenderer
                .getListItemRenderer();
        final StringBuilder builder = new StringBuilder();
        nlir.renderAsListItem(builder, false, 0);
        assertEquals(UNEXPECTED_STRING, "?", builder.toString());
    }

    /** */
    @Test
    public final void testRenderNull() {
        final Name name = new Name(null);
        final NameRenderer nameRenderer = new NameRenderer(name,
                new GedRendererFactory(),
                RenderingContext.anonymous());
        final NameListItemRenderer nlir = (NameListItemRenderer) nameRenderer
                .getListItemRenderer();
        final StringBuilder builder = new StringBuilder();
        nlir.renderAsListItem(builder, false, 0);
        assertEquals(UNEXPECTED_STRING, "?", builder.toString());
    }

    /** */
    @Test
    public final void testRenderPrefixSuffix() {
        final Name name = new Name(null, "Foo//Bar");
        final NameRenderer nameRenderer = new NameRenderer(name,
                new GedRendererFactory(),
                RenderingContext.anonymous());
        final NameListItemRenderer nlir = (NameListItemRenderer) nameRenderer
                .getListItemRenderer();
        final StringBuilder builder = new StringBuilder();
        nlir.renderAsListItem(builder, false, 0);
        assertEquals(UNEXPECTED_STRING, "Foo ? Bar",
                builder.toString());
    }
}
