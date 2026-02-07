package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.schoellerfamily.gedbrowser.datamodel.Name;
import org.schoellerfamily.gedbrowser.renderer.GedRendererFactory;
import org.schoellerfamily.gedbrowser.renderer.NameListItemRenderer;
import org.schoellerfamily.gedbrowser.renderer.NameRenderer;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.schoellerfamily.gedbrowser.renderer.application.ApplicationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author Dick Schoeller
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { TestConfiguration.class })
final class NameListItemRendererTest {
    /** */
    @Autowired
    private transient ApplicationInfo appInfo;

    /** */
    private static final String UNEXPECTED_STRING = "Unexpected string returned";
    /** */
    private static final String EXPECT_EMPTY = "Expected empty string";

    /** */
    private RenderingContext anonymousContext;

    /** */
    @BeforeEach
    void setUp() {
        anonymousContext = RenderingContext.anonymous(appInfo);
    }

    /** */
    @Test
    void testRenderSimple() {
        final Name name = new Name(null, "Richard /Schoeller/");
        final NameRenderer nameRenderer = new NameRenderer(name, new GedRendererFactory(),
            anonymousContext);
        final NameListItemRenderer nlir = (NameListItemRenderer) nameRenderer.getListItemRenderer();
        final StringBuilder builder = new StringBuilder();
        nlir.renderAsListItem(builder, false, 0);
        assertEquals("Richard Schoeller", builder.toString(), UNEXPECTED_STRING);
    }

    /** */
    @Test
    void testRenderHarder() {
        final Name name = new Name(null, "Karl Frederick /Schoeller/Jr.");
        final NameRenderer nameRenderer = new NameRenderer(name, new GedRendererFactory(),
            anonymousContext);
        final NameListItemRenderer nlir = (NameListItemRenderer) nameRenderer.getListItemRenderer();
        final StringBuilder builder = new StringBuilder();
        nlir.renderAsListItem(builder, false, 0);
        assertEquals("Karl Frederick Schoeller Jr.", builder.toString(), UNEXPECTED_STRING);
    }

    /** */
    @Test
    void testRenderNonZeroPad() {
        final Name name = new Name(null, "Karl Frederick /Schoeller/Jr.");
        final NameRenderer nameRenderer = new NameRenderer(name, new GedRendererFactory(),
            anonymousContext);
        final NameListItemRenderer nlir = (NameListItemRenderer) nameRenderer.getListItemRenderer();
        final StringBuilder builder = new StringBuilder();
        nlir.renderAsListItem(builder, false, 1);
        assertEquals("", builder.toString(), EXPECT_EMPTY);
    }

    /** */
    @Test
    void testRenderNewLine() {
        final Name name = new Name(null, "Karl Frederick /Schoeller/Jr.");
        final NameRenderer nameRenderer = new NameRenderer(name, new GedRendererFactory(),
            anonymousContext);
        final NameListItemRenderer nlir = (NameListItemRenderer) nameRenderer.getListItemRenderer();
        final StringBuilder builder = new StringBuilder();
        nlir.renderAsListItem(builder, true, 0);
        assertEquals("\nKarl Frederick Schoeller Jr.", builder.toString(), UNEXPECTED_STRING);
    }

    /** */
    @Test
    void testRenderNoPrefix() {
        final Name name = new Name(null, "/Schoeller/");
        final NameRenderer nameRenderer = new NameRenderer(name, new GedRendererFactory(),
            anonymousContext);
        final NameListItemRenderer nlir = (NameListItemRenderer) nameRenderer.getListItemRenderer();
        final StringBuilder builder = new StringBuilder();
        nlir.renderAsListItem(builder, false, 0);
        assertEquals("Schoeller", builder.toString(), UNEXPECTED_STRING);
    }

    /** */
    @Test
    void testRenderEmpty() {
        final Name name = new Name(null, "");
        final NameRenderer nameRenderer = new NameRenderer(name, new GedRendererFactory(),
            anonymousContext);
        final NameListItemRenderer nlir = (NameListItemRenderer) nameRenderer.getListItemRenderer();
        final StringBuilder builder = new StringBuilder();
        nlir.renderAsListItem(builder, false, 0);
        assertEquals("?", builder.toString(), UNEXPECTED_STRING);
    }

    /** */
    @Test
    void testRenderNull() {
        final Name name = new Name(null);
        final NameRenderer nameRenderer = new NameRenderer(name, new GedRendererFactory(),
            anonymousContext);
        final NameListItemRenderer nlir = (NameListItemRenderer) nameRenderer.getListItemRenderer();
        final StringBuilder builder = new StringBuilder();
        nlir.renderAsListItem(builder, false, 0);
        assertEquals("?", builder.toString(), UNEXPECTED_STRING);
    }

    /** */
    @Test
    void testRenderPrefixSuffix() {
        final Name name = new Name(null, "Foo//Bar");
        final NameRenderer nameRenderer = new NameRenderer(name, new GedRendererFactory(),
            anonymousContext);
        final NameListItemRenderer nlir = (NameListItemRenderer) nameRenderer.getListItemRenderer();
        final StringBuilder builder = new StringBuilder();
        nlir.renderAsListItem(builder, false, 0);
        assertEquals("Foo ? Bar", builder.toString(), UNEXPECTED_STRING);
    }
}
