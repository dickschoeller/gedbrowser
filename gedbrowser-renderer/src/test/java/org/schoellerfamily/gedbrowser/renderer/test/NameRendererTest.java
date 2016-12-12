package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.schoellerfamily.gedbrowser.datamodel.Name;
import org.schoellerfamily.gedbrowser.renderer.GedRendererFactory;
import org.schoellerfamily.gedbrowser.renderer.NameListItemRenderer;
import org.schoellerfamily.gedbrowser.renderer.NameNameHtmlRenderer;
import org.schoellerfamily.gedbrowser.renderer.NameNameIndexRenderer;
import org.schoellerfamily.gedbrowser.renderer.NamePhraseRenderer;
import org.schoellerfamily.gedbrowser.renderer.NameRenderer;
import org.schoellerfamily.gedbrowser.renderer.NullSectionRenderer;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.schoellerfamily.gedbrowser.renderer.SimpleAttributeListOpenRenderer;

/**
 * @author Dick Schoeller
 */
public class NameRendererTest {
    /**
     * Test that we are using the appropriate sub-renderers.
     * We will test the sub-renderers directly.
     */
    @Test
    public final void testAttributeListOpenRenderer() {
        final NameRenderer renderer = new NameRenderer(new Name(null),
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
        final NameRenderer renderer = new NameRenderer(new Name(null),
                new GedRendererFactory(),
                RenderingContext.anonymous());
        assertTrue(renderer.getListItemRenderer()
                instanceof NameListItemRenderer);
    }

    /**
     * Test that we are using the appropriate sub-renderers.
     * We will test the sub-renderers directly.
     */
    @Test
    public final void testNameHtmlRenderer() {
        final NameRenderer renderer = new NameRenderer(new Name(null),
                new GedRendererFactory(),
                RenderingContext.anonymous());
        assertTrue(renderer.getNameHtmlRenderer()
                instanceof NameNameHtmlRenderer);
    }

    /**
     * Test that we are using the appropriate sub-renderers.
     * We will test the sub-renderers directly.
     */
    @Test
    public final void testNameIndexRenderer() {
        final NameRenderer renderer = new NameRenderer(new Name(null),
                new GedRendererFactory(),
                RenderingContext.anonymous());
        assertTrue(renderer.getNameIndexRenderer()
                instanceof NameNameIndexRenderer);
    }

    /**
     * Test that we are using the appropriate sub-renderers.
     * We will test the sub-renderers directly.
     */
    @Test
    public final void testPhraseRenderer() {
        final NameRenderer renderer = new NameRenderer(new Name(null),
                new GedRendererFactory(),
                RenderingContext.anonymous());
        assertTrue(renderer.getPhraseRenderer()
                instanceof NamePhraseRenderer);
    }

    /**
     * Test that we are using the appropriate sub-renderers.
     * We will test the sub-renderers directly.
     */
    @Test
    public final void testSectionRenderer() {
        final NameRenderer renderer = new NameRenderer(new Name(null),
                new GedRendererFactory(),
                RenderingContext.anonymous());
        assertTrue(renderer.getSectionRenderer()
                instanceof NullSectionRenderer);
    }
}