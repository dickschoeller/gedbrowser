package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.schoellerfamily.gedbrowser.datamodel.Place;
import org.schoellerfamily.gedbrowser.renderer.GedRendererFactory;
import org.schoellerfamily.gedbrowser.renderer.NullNameHtmlRenderer;
import org.schoellerfamily.gedbrowser.renderer.NullNameIndexRenderer;
import org.schoellerfamily.gedbrowser.renderer.PlaceListItemRenderer;
import org.schoellerfamily.gedbrowser.renderer.PlacePhraseRenderer;
import org.schoellerfamily.gedbrowser.renderer.PlaceRenderer;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.schoellerfamily.gedbrowser.renderer.SimpleAttributeListOpenRenderer;
import org.schoellerfamily.gedbrowser.renderer.application.ApplicationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author Dick Schoeller
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { TestConfiguration.class })
final class PlaceRendererTest {
    /** */
    @Autowired
    private transient ApplicationInfo appInfo;

    /** */
    private RenderingContext anonymousContext;

    /** */
    @BeforeEach
    void setUp() {
        anonymousContext = RenderingContext.anonymous(appInfo);
    }

    /**
     * Test that we are using the appropriate sub-renderers. We will test the
     * sub-renderers directly.
     */
    @Test
    void testAttributeListOpenRenderer() {
        final PlaceRenderer renderer = new PlaceRenderer(new Place(), new GedRendererFactory(),
            anonymousContext);
        assertTrue(
            renderer.getAttributeListOpenRenderer() instanceof SimpleAttributeListOpenRenderer,
            "Wrong renderer type");
    }

    /**
     * Test that we are using the appropriate sub-renderers. We will test the
     * sub-renderers directly.
     */
    @Test
    void testListItemRenderer() {
        final PlaceRenderer renderer = new PlaceRenderer(new Place(), new GedRendererFactory(),
            anonymousContext);
        assertTrue(renderer.getListItemRenderer() instanceof PlaceListItemRenderer,
            "Wrong renderer type");
    }

    /**
     * Test that we are using the appropriate sub-renderers. We will test the
     * sub-renderers directly.
     */
    @Test
    void testNameHtmlRenderer() {
        final PlaceRenderer renderer = new PlaceRenderer(new Place(), new GedRendererFactory(),
            anonymousContext);
        assertTrue(renderer.getNameHtmlRenderer() instanceof NullNameHtmlRenderer,
            "Wrong renderer type");
    }

    /**
     * Test that we are using the appropriate sub-renderers. We will test the
     * sub-renderers directly.
     */
    @Test
    void testNameIndexRenderer() {
        final PlaceRenderer renderer = new PlaceRenderer(new Place(), new GedRendererFactory(),
            anonymousContext);
        assertTrue(renderer.getNameIndexRenderer() instanceof NullNameIndexRenderer,
            "Wrong renderer type");
    }

    /**
     * Test that we are using the appropriate sub-renderers. We will test the
     * sub-renderers directly.
     */
    @Test
    void testPhraseRenderer() {
        final PlaceRenderer renderer = new PlaceRenderer(new Place(), new GedRendererFactory(),
            anonymousContext);
        assertTrue(renderer.getPhraseRenderer() instanceof PlacePhraseRenderer,
            "Wrong renderer type");
    }
}
