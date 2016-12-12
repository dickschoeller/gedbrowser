package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.schoellerfamily.gedbrowser.datamodel.SourceLink;
import org.schoellerfamily.gedbrowser.renderer.GedRendererFactory;
import org.schoellerfamily.gedbrowser.renderer.NullNameHtmlRenderer;
import org.schoellerfamily.gedbrowser.renderer.NullNameIndexRenderer;
import org.schoellerfamily.gedbrowser.renderer.NullSectionRenderer;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.schoellerfamily.gedbrowser.renderer.SimpleAttributeListOpenRenderer;
import org.schoellerfamily.gedbrowser.renderer.SourceLinkListItemRenderer;
import org.schoellerfamily.gedbrowser.renderer.SourceLinkPhraseRenderer;
import org.schoellerfamily.gedbrowser.renderer.SourceLinkRenderer;

/**
 * @author Dick Schoeller
 */
public class SourceLinkRendererTest {
    /**
     * Test that we are using the appropriate sub-renderers.
     * We will test the sub-renderers directly.
     */
    @Test
    public final void testAttributeListOpenRenderer() {
        final SourceLinkRenderer renderer =
                new SourceLinkRenderer(
                        new SourceLink(null), new GedRendererFactory(),
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
        final SourceLinkRenderer renderer =
                new SourceLinkRenderer(
                        new SourceLink(null), new GedRendererFactory(),
                        RenderingContext.anonymous());
        assertTrue(renderer.getListItemRenderer()
                instanceof SourceLinkListItemRenderer);
    }

    /**
     * Test that we are using the appropriate sub-renderers.
     * We will test the sub-renderers directly.
     */
    @Test
    public final void testNameHtmlRenderer() {
        final SourceLinkRenderer renderer =
                new SourceLinkRenderer(
                        new SourceLink(null), new GedRendererFactory(),
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
        final SourceLinkRenderer renderer =
                new SourceLinkRenderer(
                        new SourceLink(null), new GedRendererFactory(),
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
        final SourceLinkRenderer renderer =
                new SourceLinkRenderer(
                        new SourceLink(null), new GedRendererFactory(),
                        RenderingContext.anonymous());
        assertTrue(renderer.getPhraseRenderer()
                instanceof SourceLinkPhraseRenderer);
    }

    /**
     * Test that we are using the appropriate sub-renderers.
     * We will test the sub-renderers directly.
     */
    @Test
    public final void testSectionRenderer() {
        final SourceLinkRenderer renderer =
                new SourceLinkRenderer(
                        new SourceLink(null), new GedRendererFactory(),
                        RenderingContext.anonymous());
        assertTrue(renderer.getSectionRenderer()
                instanceof NullSectionRenderer);
    }
}