package org.schoellerfamily.gedbrowser.renderer.test;

import org.junit.Assert;
import org.junit.Test;
import org.schoellerfamily.gedbrowser.datamodel.Place;
import org.schoellerfamily.gedbrowser.renderer.GedRendererFactory;
import org.schoellerfamily.gedbrowser.renderer.NullNameHtmlRenderer;
import org.schoellerfamily.gedbrowser.renderer.NullNameIndexRenderer;
import org.schoellerfamily.gedbrowser.renderer.NullSectionRenderer;
import org.schoellerfamily.gedbrowser.renderer.PlaceListItemRenderer;
import org.schoellerfamily.gedbrowser.renderer.PlacePhraseRenderer;
import org.schoellerfamily.gedbrowser.renderer.PlaceRenderer;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.schoellerfamily.gedbrowser.renderer.SimpleAttributeListOpenRenderer;

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
        Assert.assertTrue("Wrong renderer type",
                renderer.getAttributeListOpenRenderer()
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
        Assert.assertTrue("Wrong renderer type",
                renderer.getListItemRenderer()
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
        Assert.assertTrue("Wrong renderer type",
                renderer.getNameHtmlRenderer()
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
        Assert.assertTrue("Wrong renderer type",
                renderer.getNameIndexRenderer()
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
        Assert.assertTrue("Wrong renderer type",
                renderer.getPhraseRenderer()
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
        Assert.assertTrue("Wrong renderer type",
                renderer.getSectionRenderer()
                instanceof NullSectionRenderer);
    }
}
