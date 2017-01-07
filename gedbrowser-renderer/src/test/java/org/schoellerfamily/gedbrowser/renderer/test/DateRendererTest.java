package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.schoellerfamily.gedbrowser.datamodel.Date;
import org.schoellerfamily.gedbrowser.renderer.DateListItemRenderer;
import org.schoellerfamily.gedbrowser.renderer.DatePhraseRenderer;
import org.schoellerfamily.gedbrowser.renderer.DateRenderer;
import org.schoellerfamily.gedbrowser.renderer.GedRendererFactory;
import org.schoellerfamily.gedbrowser.renderer.NullNameHtmlRenderer;
import org.schoellerfamily.gedbrowser.renderer.NullNameIndexRenderer;
import org.schoellerfamily.gedbrowser.renderer.NullSectionRenderer;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.schoellerfamily.gedbrowser.renderer.SimpleAttributeListOpenRenderer;

/**
 * @author Dick Schoeller
 */
public class DateRendererTest {
    /**
     * Test that we are using the appropriate sub-renderers.
     * We will test the sub-renderers directly.
     */
    @Test
    public final void testAttributeListOpenRenderer() {
        final DateRenderer renderer = new DateRenderer(new Date(null),
                new GedRendererFactory(),
                RenderingContext.anonymous());
        assertTrue("Wrong renderer type",
                renderer.getAttributeListOpenRenderer()
                instanceof SimpleAttributeListOpenRenderer);
    }

    /**
     * Test that we are using the appropriate sub-renderers.
     * We will test the sub-renderers directly.
     */
    @Test
    public final void testListItemRenderer() {
        final DateRenderer renderer = new DateRenderer(new Date(null),
                new GedRendererFactory(),
                RenderingContext.anonymous());
        assertTrue("Wrong renderer type",
                renderer.getListItemRenderer()
                instanceof DateListItemRenderer);
    }

    /**
     * Test that we are using the appropriate sub-renderers.
     * We will test the sub-renderers directly.
     */
    @Test
    public final void testNameHtmlRenderer() {
        final DateRenderer renderer = new DateRenderer(new Date(null),
                new GedRendererFactory(),
                RenderingContext.anonymous());
        assertTrue("Wrong renderer type",
                renderer.getNameHtmlRenderer()
                instanceof NullNameHtmlRenderer);
    }

    /**
     * Test that we are using the appropriate sub-renderers.
     * We will test the sub-renderers directly.
     */
    @Test
    public final void testNameIndexRenderer() {
        final DateRenderer renderer = new DateRenderer(new Date(null),
                new GedRendererFactory(),
                RenderingContext.anonymous());
        assertTrue("Wrong renderer type",
                renderer.getNameIndexRenderer()
                instanceof NullNameIndexRenderer);
    }

    /**
     * Test that we are using the appropriate sub-renderers.
     * We will test the sub-renderers directly.
     */
    @Test
    public final void testPhraseRenderer() {
        final DateRenderer renderer = new DateRenderer(new Date(null),
                new GedRendererFactory(),
                RenderingContext.anonymous());
        assertTrue("Wrong renderer type",
                renderer.getPhraseRenderer()
                instanceof DatePhraseRenderer);
    }

    /**
     * Test that we are using the appropriate sub-renderers.
     * We will test the sub-renderers directly.
     */
    @Test
    public final void testSectionRenderer() {
        final DateRenderer renderer = new DateRenderer(new Date(null),
                new GedRendererFactory(),
                RenderingContext.anonymous());
        assertTrue("Wrong renderer type",
                renderer.getSectionRenderer()
                instanceof NullSectionRenderer);
    }
}
