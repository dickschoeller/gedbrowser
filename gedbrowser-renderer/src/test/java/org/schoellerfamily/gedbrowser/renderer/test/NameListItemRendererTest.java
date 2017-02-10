package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.schoellerfamily.gedbrowser.analytics.CalendarProvider;
import org.schoellerfamily.gedbrowser.analytics.CalendarProviderStub;
import org.schoellerfamily.gedbrowser.datamodel.Name;
import org.schoellerfamily.gedbrowser.renderer.ApplicationInfo;
import org.schoellerfamily.gedbrowser.renderer.GedRendererFactory;
import org.schoellerfamily.gedbrowser.renderer.NameListItemRenderer;
import org.schoellerfamily.gedbrowser.renderer.NameRenderer;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;

/**
 * @author Dick Schoeller
 */
public final class NameListItemRendererTest {
    /** */
    private static final String UNEXPECTED_STRING =
            "Unexpected string returned";
    /** */
    private static final String EXPECT_EMPTY = "Expected empty string";

    /** */
    private CalendarProvider provider;

    /** */
    private RenderingContext anonymousContext;

    /** */
    @Before
    public void init() {
        provider = new CalendarProviderStub();
        final ApplicationInfo appInfo = new ApplicationInfoStub();
        anonymousContext = RenderingContext.anonymous(appInfo);
    }

    /** */
    @Test
    public void testRenderSimple() {
        final Name name = new Name(null, "Richard /Schoeller/");
        final NameRenderer nameRenderer = new NameRenderer(name,
                new GedRendererFactory(), anonymousContext, provider);
        final NameListItemRenderer nlir = (NameListItemRenderer) nameRenderer
                .getListItemRenderer();
        final StringBuilder builder = new StringBuilder();
        nlir.renderAsListItem(builder, false, 0);
        assertEquals(UNEXPECTED_STRING, "Richard  Schoeller",
                builder.toString());
    }

    /** */
    @Test
    public void testRenderHarder() {
        final Name name = new Name(null, "Karl Frederick /Schoeller/Jr.");
        final NameRenderer nameRenderer = new NameRenderer(name,
                new GedRendererFactory(), anonymousContext, provider);
        final NameListItemRenderer nlir = (NameListItemRenderer) nameRenderer
                .getListItemRenderer();
        final StringBuilder builder = new StringBuilder();
        nlir.renderAsListItem(builder, false, 0);
        assertEquals(UNEXPECTED_STRING, "Karl Frederick  Schoeller Jr.",
                builder.toString());
    }

    /** */
    @Test
    public void testRenderNonZeroPad() {
        final Name name = new Name(null, "Karl Frederick /Schoeller/Jr.");
        final NameRenderer nameRenderer = new NameRenderer(name,
                new GedRendererFactory(), anonymousContext, provider);
        final NameListItemRenderer nlir = (NameListItemRenderer) nameRenderer
                .getListItemRenderer();
        final StringBuilder builder = new StringBuilder();
        nlir.renderAsListItem(builder, false, 1);
        assertEquals(EXPECT_EMPTY, "", builder.toString());
    }

    /** */
    @Test
    public void testRenderNewLine() {
        final Name name = new Name(null, "Karl Frederick /Schoeller/Jr.");
        final NameRenderer nameRenderer = new NameRenderer(name,
                new GedRendererFactory(), anonymousContext, provider);
        final NameListItemRenderer nlir = (NameListItemRenderer) nameRenderer
                .getListItemRenderer();
        final StringBuilder builder = new StringBuilder();
        nlir.renderAsListItem(builder, true, 0);
        assertEquals(UNEXPECTED_STRING, "\nKarl Frederick  Schoeller Jr.",
                builder.toString());
    }

    /** */
    @Test
    public void testRenderNoPrefix() {
        final Name name = new Name(null, "/Schoeller/");
        final NameRenderer nameRenderer = new NameRenderer(name,
                new GedRendererFactory(), anonymousContext, provider);
        final NameListItemRenderer nlir = (NameListItemRenderer) nameRenderer
                .getListItemRenderer();
        final StringBuilder builder = new StringBuilder();
        nlir.renderAsListItem(builder, false, 0);
        assertEquals(UNEXPECTED_STRING, "Schoeller", builder.toString());
    }

    /** */
    @Test
    public void testRenderEmpty() {
        final Name name = new Name(null, "");
        final NameRenderer nameRenderer = new NameRenderer(name,
                new GedRendererFactory(), anonymousContext, provider);
        final NameListItemRenderer nlir = (NameListItemRenderer) nameRenderer
                .getListItemRenderer();
        final StringBuilder builder = new StringBuilder();
        nlir.renderAsListItem(builder, false, 0);
        assertEquals(UNEXPECTED_STRING, "?", builder.toString());
    }

    /** */
    @Test
    public void testRenderNull() {
        final Name name = new Name(null);
        final NameRenderer nameRenderer = new NameRenderer(name,
                new GedRendererFactory(), anonymousContext, provider);
        final NameListItemRenderer nlir = (NameListItemRenderer) nameRenderer
                .getListItemRenderer();
        final StringBuilder builder = new StringBuilder();
        nlir.renderAsListItem(builder, false, 0);
        assertEquals(UNEXPECTED_STRING, "?", builder.toString());
    }

    /** */
    @Test
    public void testRenderPrefixSuffix() {
        final Name name = new Name(null, "Foo//Bar");
        final NameRenderer nameRenderer = new NameRenderer(name,
                new GedRendererFactory(), anonymousContext, provider);
        final NameListItemRenderer nlir = (NameListItemRenderer) nameRenderer
                .getListItemRenderer();
        final StringBuilder builder = new StringBuilder();
        nlir.renderAsListItem(builder, false, 0);
        assertEquals(UNEXPECTED_STRING, "Foo ? Bar",
                builder.toString());
    }
}
