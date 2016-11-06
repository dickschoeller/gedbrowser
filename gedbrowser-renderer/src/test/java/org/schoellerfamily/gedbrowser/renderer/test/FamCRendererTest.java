package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.schoellerfamily.gedbrowser.datamodel.FamC;
import org.schoellerfamily.gedbrowser.renderer.FamCRenderer;
import org.schoellerfamily.gedbrowser.renderer.GedRendererFactory;
import org.schoellerfamily.gedbrowser.renderer.NullListItemRenderer;
import org.schoellerfamily.gedbrowser.renderer.NullNameHtmlRenderer;
import org.schoellerfamily.gedbrowser.renderer.NullNameIndexRenderer;
import org.schoellerfamily.gedbrowser.renderer.NullPhraseRenderer;
import org.schoellerfamily.gedbrowser.renderer.NullSectionRenderer;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.schoellerfamily.gedbrowser.renderer.SimpleAttributeListOpenRenderer;

/**
 * @author Dick Schoeller
 */
public class FamCRendererTest {
    /**
     * Test that we are using the appropriate sub-renderers.
     * We will test the sub-renderers directly.
     */
    @Test
    public final void testAttributeListOpenRenderer() {
        final FamCRenderer renderer =
                new FamCRenderer(new FamC(null),
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
        final FamCRenderer renderer =
                new FamCRenderer(new FamC(null),
                        new GedRendererFactory(),
                        RenderingContext.anonymous());
        assertTrue(renderer.getListItemRenderer()
                instanceof NullListItemRenderer);
    }

    /**
     * Test that we are using the appropriate sub-renderers.
     * We will test the sub-renderers directly.
     */
    @Test
    public final void testNameHtmlRenderer() {
        final FamCRenderer renderer =
                new FamCRenderer(new FamC(null),
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
        final FamCRenderer renderer =
                new FamCRenderer(new FamC(null),
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
        final FamCRenderer renderer =
                new FamCRenderer(new FamC(null),
                        new GedRendererFactory(),
                        RenderingContext.anonymous());
        assertTrue(renderer.getPhraseRenderer()
                instanceof NullPhraseRenderer);
    }

    /**
     * Test that we are using the appropriate sub-renderers.
     * We will test the sub-renderers directly.
     */
    @Test
    public final void testSectionRenderer() {
        final FamCRenderer renderer =
                new FamCRenderer(new FamC(null),
                        new GedRendererFactory(),
                        RenderingContext.anonymous());
        assertTrue(renderer.getSectionRenderer()
                instanceof NullSectionRenderer);
    }
}
