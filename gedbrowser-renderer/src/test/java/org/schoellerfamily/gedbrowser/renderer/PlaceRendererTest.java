package org.schoellerfamily.gedbrowser.renderer;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.schoellerfamily.gedbrowser.datamodel.Place;

/**
 * @author Dick Schoeller
 */
public class PlaceRendererTest {
    /**
     * Test that we are using the appropriate sub-renderers.
     * We will test the sub-renderers directly.
     */
    @Test
    public final void testAttributeListOpenRenderer() {
        final PlaceRenderer renderer = new PlaceRenderer(new Place(null),
                new GedRendererFactory(),
                RenderingContext.anonymous());
        assertTrue(renderer.getAttributeListOpenRenderer()
                instanceof SimpleAttributeListOpenRenderer);
    }

    /**
     * Test that we are using the appropriate sub-renderers.
     * We will test the sub-renderers directly.
     */
    @Test
    public final void testListItemRenderer() {
        final PlaceRenderer renderer = new PlaceRenderer(new Place(null),
                new GedRendererFactory(),
                RenderingContext.anonymous());
        assertTrue(renderer.getListItemRenderer()
                instanceof PlaceListItemRenderer);
    }

    /**
     * Test that we are using the appropriate sub-renderers.
     * We will test the sub-renderers directly.
     */
    @Test
    public final void testNameHtmlRenderer() {
        final PlaceRenderer renderer = new PlaceRenderer(new Place(null),
                new GedRendererFactory(),
                RenderingContext.anonymous());
        assertTrue(renderer.getNameHtmlRenderer()
                instanceof NullNameHtmlRenderer);
    }

    /**
     * Test that we are using the appropriate sub-renderers.
     * We will test the sub-renderers directly.
     */
    @Test
    public final void testNameIndexRenderer() {
        final PlaceRenderer renderer = new PlaceRenderer(new Place(null),
                new GedRendererFactory(),
                RenderingContext.anonymous());
        assertTrue(renderer.getNameIndexRenderer()
                instanceof NullNameIndexRenderer);
    }

    /**
     * Test that we are using the appropriate sub-renderers.
     * We will test the sub-renderers directly.
     */
    @Test
    public final void testPhraseRenderer() {
        final PlaceRenderer renderer = new PlaceRenderer(new Place(null),
                new GedRendererFactory(),
                RenderingContext.anonymous());
        assertTrue(renderer.getPhraseRenderer()
                instanceof PlacePhraseRenderer);
    }

    /**
     * Test that we are using the appropriate sub-renderers.
     * We will test the sub-renderers directly.
     */
    @Test
    public final void testSectionRenderer() {
        final PlaceRenderer renderer = new PlaceRenderer(new Place(null),
                new GedRendererFactory(),
                RenderingContext.anonymous());
        assertTrue(renderer.getSectionRenderer()
                instanceof NullSectionRenderer);
    }
}
