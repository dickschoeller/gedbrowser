package org.schoellerfamily.gedbrowser.renderer;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.schoellerfamily.gedbrowser.datamodel.Name;

/**
 * @author Dick Schoeller
 */
public class NameNameIndexRendererTest {
    /** */
    private static final String UNEXPECTED_STRING =
            "Unexpected string returned";

    /** */
    @Test
    public final void testRenderSimple() {
        final Name name = new Name(null, "Richard/Schoeller/");
        final NameRenderer nameRenderer = new NameRenderer(name,
                new GedRendererFactory(),
                RenderingContext.anonymous());
        final NameNameIndexRenderer nnir = (NameNameIndexRenderer) nameRenderer
                .getNameIndexRenderer();
        assertEquals(UNEXPECTED_STRING,
                " <span class=\"surname\">Schoeller</span>, Richard",
                nnir.getIndexName());
    }

    /** */
    @Test
    public final void testRenderHarder() {
        final Name name = new Name(null, "Karl Frederick/Schoeller/Jr.");
        final NameRenderer nameRenderer = new NameRenderer(name,
                new GedRendererFactory(),
                RenderingContext.anonymous());
        final NameNameIndexRenderer nnir = (NameNameIndexRenderer) nameRenderer
                .getNameIndexRenderer();
        assertEquals(UNEXPECTED_STRING,
                " <span class=\"surname\">Schoeller</span>,"
                + " Karl Frederick, Jr.",
                nnir.getIndexName());
    }

    /** */
    @Test
    public final void testRenderNoPrefix() {
        final Name name = new Name(null, "/Schoeller/");
        final NameRenderer nameRenderer = new NameRenderer(name,
                new GedRendererFactory(),
                RenderingContext.anonymous());
        final NameNameIndexRenderer nnir = (NameNameIndexRenderer) nameRenderer
                .getNameIndexRenderer();
        assertEquals(UNEXPECTED_STRING,
                " <span class=\"surname\">Schoeller</span>",
                nnir.getIndexName());
    }

    /** */
    @Test
    public final void testRenderEmpty() {
        final Name name = new Name(null, "");
        final NameRenderer nameRenderer = new NameRenderer(name,
                new GedRendererFactory(),
                RenderingContext.anonymous());
        final NameNameIndexRenderer nnir = (NameNameIndexRenderer) nameRenderer
                .getNameIndexRenderer();
        assertEquals(UNEXPECTED_STRING,
                " <span class=\"surname\">?</span>",
                nnir.getIndexName());
    }

    /** */
    @Test
    public final void testRenderNull() {
        final Name name = new Name(null);
        final NameRenderer nameRenderer = new NameRenderer(name,
                new GedRendererFactory(),
                RenderingContext.anonymous());
        final NameNameIndexRenderer nnir = (NameNameIndexRenderer) nameRenderer
                .getNameIndexRenderer();
        assertEquals(UNEXPECTED_STRING,
                " <span class=\"surname\">?</span>",
                nnir.getIndexName());
    }

    /** */
    @Test
    public final void testRenderUnset() {
        final Name name = new Name();
        final NameRenderer nameRenderer = new NameRenderer(name,
                new GedRendererFactory(),
                RenderingContext.anonymous());
        final NameNameIndexRenderer nnir = (NameNameIndexRenderer) nameRenderer
                .getNameIndexRenderer();
        assertEquals(UNEXPECTED_STRING,
                " <span class=\"surname\">?</span>",
                nnir.getIndexName());
    }

    /** */
    @Test
    public final void testRenderPrefixSuffix() {
        final Name name = new Name(null, "Foo//Bar");
        final NameRenderer nameRenderer = new NameRenderer(name,
                new GedRendererFactory(),
                RenderingContext.anonymous());
        final NameNameIndexRenderer nnir = (NameNameIndexRenderer) nameRenderer
                .getNameIndexRenderer();
        assertEquals(UNEXPECTED_STRING,
                " <span class=\"surname\">?</span>, Foo, Bar",
                nnir.getIndexName());
    }
}
