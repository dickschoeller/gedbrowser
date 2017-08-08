package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.schoellerfamily.gedbrowser.datamodel.Name;
import org.schoellerfamily.gedbrowser.renderer.GedRendererFactory;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.schoellerfamily.gedbrowser.renderer.SimpleNameListItemRenderer;
import org.schoellerfamily.gedbrowser.renderer.SimpleNameRenderer;
import org.schoellerfamily.gedbrowser.renderer.application.ApplicationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Dick Schoeller
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestConfiguration.class })
public final class SimpleNameListItemRendererTest {
    /** */
    @Autowired
    private transient ApplicationInfo appInfo;

    /** */
    private static final String UNEXPECTED_STRING =
            "Unexpected string returned";
    /** */
    private static final String EXPECT_EMPTY = "Expected empty string";

    /** */
    private RenderingContext anonymousContext;

    /** */
    @Before
    public void init() {
        anonymousContext = RenderingContext.anonymous(appInfo);
    }

    /** */
    @Test
    public void testRenderSimple() {
        final Name name = new Name(null, "Richard /Schoeller/");
        final SimpleNameRenderer nameRenderer = new SimpleNameRenderer(name,
                new GedRendererFactory(), anonymousContext);
        final SimpleNameListItemRenderer nlir =
                (SimpleNameListItemRenderer) nameRenderer
                    .getListItemRenderer();
        final StringBuilder builder = new StringBuilder();
        nlir.renderAsListItem(builder, false, 0);
        assertEquals(UNEXPECTED_STRING, "Richard Schoeller",
                builder.toString());
    }

    /** */
    @Test
    public void testRenderHarder() {
        final Name name = new Name(null, "Karl Frederick /Schoeller/Jr.");
        final SimpleNameRenderer nameRenderer = new SimpleNameRenderer(name,
                new GedRendererFactory(), anonymousContext);
        final SimpleNameListItemRenderer nlir =
                (SimpleNameListItemRenderer) nameRenderer
                    .getListItemRenderer();
        final StringBuilder builder = new StringBuilder();
        nlir.renderAsListItem(builder, false, 0);
        assertEquals(UNEXPECTED_STRING, "Karl Frederick Schoeller Jr.",
                builder.toString());
    }

    /** */
    @Test
    public void testRenderNonZeroPad() {
        final Name name = new Name(null, "Karl Frederick /Schoeller/Jr.");
        final SimpleNameRenderer nameRenderer = new SimpleNameRenderer(name,
                new GedRendererFactory(), anonymousContext);
        final SimpleNameListItemRenderer nlir =
                (SimpleNameListItemRenderer) nameRenderer
                    .getListItemRenderer();
        final StringBuilder builder = new StringBuilder();
        nlir.renderAsListItem(builder, false, 1);
        assertEquals(EXPECT_EMPTY, "", builder.toString());
    }

    /** */
    @Test
    public void testRenderNewLine() {
        final Name name = new Name(null, "Karl Frederick /Schoeller/Jr.");
        final SimpleNameRenderer nameRenderer = new SimpleNameRenderer(name,
                new GedRendererFactory(), anonymousContext);
        final SimpleNameListItemRenderer nlir =
                (SimpleNameListItemRenderer) nameRenderer
                    .getListItemRenderer();
        final StringBuilder builder = new StringBuilder();
        nlir.renderAsListItem(builder, true, 0);
        assertEquals(UNEXPECTED_STRING, "\nKarl Frederick Schoeller Jr.",
                builder.toString());
    }

    /** */
    @Test
    public void testRenderNoPrefix() {
        final Name name = new Name(null, "/Schoeller/");
        final SimpleNameRenderer nameRenderer = new SimpleNameRenderer(name,
                new GedRendererFactory(), anonymousContext);
        final SimpleNameListItemRenderer nlir =
                (SimpleNameListItemRenderer) nameRenderer
                    .getListItemRenderer();
        final StringBuilder builder = new StringBuilder();
        nlir.renderAsListItem(builder, false, 0);
        assertEquals(UNEXPECTED_STRING, "Schoeller", builder.toString());
    }

    /** */
    @Test
    public void testRenderEmpty() {
        final Name name = new Name(null, "");
        final SimpleNameRenderer nameRenderer = new SimpleNameRenderer(name,
                new GedRendererFactory(), anonymousContext);
        final SimpleNameListItemRenderer nlir =
                (SimpleNameListItemRenderer) nameRenderer
                    .getListItemRenderer();
        final StringBuilder builder = new StringBuilder();
        nlir.renderAsListItem(builder, false, 0);
        assertEquals(UNEXPECTED_STRING, "", builder.toString());
    }

    /** */
    @Test
    public void testRenderNull() {
        final Name name = new Name(null);
        final SimpleNameRenderer nameRenderer = new SimpleNameRenderer(name,
                new GedRendererFactory(), anonymousContext);
        final SimpleNameListItemRenderer nlir =
                (SimpleNameListItemRenderer) nameRenderer
                    .getListItemRenderer();
        final StringBuilder builder = new StringBuilder();
        nlir.renderAsListItem(builder, false, 0);
        assertEquals(UNEXPECTED_STRING, "", builder.toString());
    }

    /** */
    @Test
    public void testRenderPrefixSuffix() {
        final Name name = new Name(null, "Foo//Bar");
        final SimpleNameRenderer nameRenderer = new SimpleNameRenderer(name,
                new GedRendererFactory(), anonymousContext);
        final SimpleNameListItemRenderer nlir =
                (SimpleNameListItemRenderer) nameRenderer
                    .getListItemRenderer();
        final StringBuilder builder = new StringBuilder();
        nlir.renderAsListItem(builder, false, 0);
        assertEquals(UNEXPECTED_STRING, "Foo Bar", builder.toString());
    }

    /** */
    @Test
    public void testListItemContentsSimple() {
        final Name name = new Name(null, "Richard /Schoeller/");
        final SimpleNameRenderer nameRenderer = new SimpleNameRenderer(name,
                new GedRendererFactory(), anonymousContext);
        final SimpleNameListItemRenderer nlir =
                (SimpleNameListItemRenderer) nameRenderer
                    .getListItemRenderer();
        assertEquals(UNEXPECTED_STRING,
                "<span class=\"label\">Name:</span> Richard Schoeller",
                nlir.getListItemContents());
    }

    /** */
    @Test
    public void testListItemContentsHarder() {
        final Name name = new Name(null, "Karl Frederick /Schoeller/Jr.");
        final SimpleNameRenderer nameRenderer = new SimpleNameRenderer(name,
                new GedRendererFactory(), anonymousContext);
        final SimpleNameListItemRenderer nlir =
                (SimpleNameListItemRenderer) nameRenderer
                    .getListItemRenderer();
        assertEquals(UNEXPECTED_STRING,
                "<span class=\"label\">Name:</span>"
                + " Karl Frederick Schoeller Jr.",
                nlir.getListItemContents());
    }

    /** */
    @Test
    public void testListItemContentsNonZeroPad() {
        final Name name = new Name(null, "Karl Frederick /Schoeller/Jr.");
        final SimpleNameRenderer nameRenderer = new SimpleNameRenderer(name,
                new GedRendererFactory(), anonymousContext);
        final SimpleNameListItemRenderer nlir =
                (SimpleNameListItemRenderer) nameRenderer
                    .getListItemRenderer();
        assertEquals(EXPECT_EMPTY,
                "<span class=\"label\">Name:</span>"
                + " Karl Frederick Schoeller Jr.",
                nlir.getListItemContents());
    }

    /** */
    @Test
    public void testListItemContentsNewLine() {
        final Name name = new Name(null, "Karl Frederick /Schoeller/Jr.");
        final SimpleNameRenderer nameRenderer = new SimpleNameRenderer(name,
                new GedRendererFactory(), anonymousContext);
        final SimpleNameListItemRenderer nlir =
                (SimpleNameListItemRenderer) nameRenderer
                    .getListItemRenderer();
        assertEquals(UNEXPECTED_STRING,
                "<span class=\"label\">Name:</span>"
                + " Karl Frederick Schoeller Jr.",
                nlir.getListItemContents());
    }

    /** */
    @Test
    public void testListItemContentsNoPrefix() {
        final Name name = new Name(null, "/Schoeller/");
        final SimpleNameRenderer nameRenderer = new SimpleNameRenderer(name,
                new GedRendererFactory(), anonymousContext);
        final SimpleNameListItemRenderer nlir =
                (SimpleNameListItemRenderer) nameRenderer
                    .getListItemRenderer();
        assertEquals(UNEXPECTED_STRING,
                "<span class=\"label\">Name:</span> Schoeller",
                nlir.getListItemContents());
    }

    /** */
    @Test
    public void testListItemContentsEmpty() {
        final Name name = new Name(null, "");
        final SimpleNameRenderer nameRenderer = new SimpleNameRenderer(name,
                new GedRendererFactory(), anonymousContext);
        final SimpleNameListItemRenderer nlir =
                (SimpleNameListItemRenderer) nameRenderer
                    .getListItemRenderer();
        assertEquals(UNEXPECTED_STRING,
                "<span class=\"label\">Name:</span> ",
                nlir.getListItemContents());
    }

    /** */
    @Test
    public void testListItemContentsNull() {
        final Name name = new Name(null);
        final SimpleNameRenderer nameRenderer = new SimpleNameRenderer(name,
                new GedRendererFactory(), anonymousContext);
        final SimpleNameListItemRenderer nlir =
                (SimpleNameListItemRenderer) nameRenderer
                    .getListItemRenderer();
        assertEquals(UNEXPECTED_STRING,
                "<span class=\"label\">Name:</span> ",
                nlir.getListItemContents());
    }

    /** */
    @Test
    public void testListItemContentsPrefixSuffix() {
        final Name name = new Name(null, "Foo//Bar");
        final SimpleNameRenderer nameRenderer = new SimpleNameRenderer(name,
                new GedRendererFactory(), anonymousContext);
        final SimpleNameListItemRenderer nlir =
                (SimpleNameListItemRenderer) nameRenderer
                    .getListItemRenderer();
        assertEquals(UNEXPECTED_STRING,
                "<span class=\"label\">Name:</span> Foo Bar",
                nlir.getListItemContents());
    }
}
