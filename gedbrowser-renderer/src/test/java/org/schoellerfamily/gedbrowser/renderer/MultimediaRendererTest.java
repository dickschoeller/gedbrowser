package org.schoellerfamily.gedbrowser.renderer;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.schoellerfamily.gedbrowser.datamodel.Multimedia;

/**
 * @author Dick Schoeller
 */
public class MultimediaRendererTest {
    /**
     * Test that we are using the appropriate sub-renderers.
     * We will test the sub-renderers directly.
     */
    @Test
    public final void testMultimediaListItemRenderer() {
        final MultimediaRenderer renderer =
                new MultimediaRenderer(
                        new Multimedia(null), new GedRendererFactory(),
                        RenderingContext.anonymous());
        assertTrue(renderer.getListItemRenderer()
                instanceof MultimediaListItemRenderer);
    }

    /**
     * Test that we are using the appropriate sub-renderers.
     * We will test the sub-renderers directly.
     */
    @Test
    public final void testNameHtmlRenderer() {
        final MultimediaRenderer renderer =
                new MultimediaRenderer(
                        new Multimedia(null), new GedRendererFactory(),
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
        final MultimediaRenderer renderer =
                new MultimediaRenderer(
                        new Multimedia(null), new GedRendererFactory(),
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
        final MultimediaRenderer renderer =
                new MultimediaRenderer(
                        new Multimedia(null), new GedRendererFactory(),
                        RenderingContext.anonymous());
        assertTrue(renderer.getPhraseRenderer()
                instanceof MultimediaPhraseRenderer);
    }

    /**
     * Test that we are using the appropriate sub-renderers.
     * We will test the sub-renderers directly.
     */
    @Test
    public final void testSectionRenderer() {
        final MultimediaRenderer renderer =
                new MultimediaRenderer(
                        new Multimedia(null), new GedRendererFactory(),
                        RenderingContext.anonymous());
        assertTrue(renderer.getSectionRenderer()
                instanceof MultimediaSectionRenderer);
    }
    // TODO test render as page and renderAsListItem
}
