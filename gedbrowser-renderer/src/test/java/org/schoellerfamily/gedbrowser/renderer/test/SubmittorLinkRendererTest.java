package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.schoellerfamily.gedbrowser.analytics.CalendarProvider;
import org.schoellerfamily.gedbrowser.analytics.CalendarProviderStub;
import org.schoellerfamily.gedbrowser.datamodel.SubmittorLink;
import org.schoellerfamily.gedbrowser.renderer.GedRendererFactory;
import org.schoellerfamily.gedbrowser.renderer.NullNameHtmlRenderer;
import org.schoellerfamily.gedbrowser.renderer.NullNameIndexRenderer;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.schoellerfamily.gedbrowser.renderer.SimpleAttributeListOpenRenderer;
import org.schoellerfamily.gedbrowser.renderer.SubmittorLinkListItemRenderer;
import org.schoellerfamily.gedbrowser.renderer.SubmittorLinkPhraseRenderer;
import org.schoellerfamily.gedbrowser.renderer.SubmittorLinkRenderer;
import org.schoellerfamily.gedbrowser.renderer.SubmittorLinkSectionRenderer;

/**
 * @author Dick Schoeller
 */
public final class SubmittorLinkRendererTest {
    /** */
    private CalendarProvider provider;

    /** */
    @Before
    public void init() {
        provider = new CalendarProviderStub();
    }

    /**
     * Test that we are using the appropriate sub-renderers.
     * We will test the sub-renderers directly.
     */
    @Test
    public void testAttributeListOpenRenderer() {
        final SubmittorLinkRenderer renderer = new SubmittorLinkRenderer(
                new SubmittorLink(null), new GedRendererFactory(),
                RenderingContext.anonymous(), provider);
        assertTrue("Wrong renderer type",
                renderer.getAttributeListOpenRenderer()
                instanceof SimpleAttributeListOpenRenderer);
    }

    /**
     * Test that we are using the appropriate sub-renderers.
     * We will test the sub-renderers directly.
     */
    @Test
    public void testListItemRenderer() {
        final SubmittorLinkRenderer renderer = new SubmittorLinkRenderer(
                new SubmittorLink(null), new GedRendererFactory(),
                RenderingContext.anonymous(), provider);
        assertTrue("Wrong renderer type",
                renderer.getListItemRenderer()
                instanceof SubmittorLinkListItemRenderer);
    }

    /**
     * Test that we are using the appropriate sub-renderers.
     * We will test the sub-renderers directly.
     */
    @Test
    public void testNameHtmlRenderer() {
        final SubmittorLinkRenderer renderer = new SubmittorLinkRenderer(
                new SubmittorLink(null), new GedRendererFactory(),
                RenderingContext.anonymous(), provider);
        assertTrue("Wrong renderer type",
                renderer.getNameHtmlRenderer()
                instanceof NullNameHtmlRenderer);
    }

    /**
     * Test that we are using the appropriate sub-renderers.
     * We will test the sub-renderers directly.
     */
    @Test
    public void testNameIndeRenderer() {
        final SubmittorLinkRenderer renderer = new SubmittorLinkRenderer(
                new SubmittorLink(null), new GedRendererFactory(),
                RenderingContext.anonymous(), provider);
        assertTrue("Wrong renderer type",
                renderer.getNameIndexRenderer()
                instanceof NullNameIndexRenderer);
    }

    /**
     * Test that we are using the appropriate sub-renderers.
     * We will test the sub-renderers directly.
     */
    @Test
    public void testPhraseRenderer() {
        final SubmittorLinkRenderer renderer = new SubmittorLinkRenderer(
                new SubmittorLink(null), new GedRendererFactory(),
                RenderingContext.anonymous(), provider);
        assertTrue("Wrong renderer type",
                renderer.getPhraseRenderer()
                instanceof SubmittorLinkPhraseRenderer);
    }

    /**
     * Test that we are using the appropriate sub-renderers.
     * We will test the sub-renderers directly.
     */
    @Test
    public void testSectionRenderer() {
        final SubmittorLinkRenderer renderer = new SubmittorLinkRenderer(
                new SubmittorLink(null), new GedRendererFactory(),
                RenderingContext.anonymous(), provider);
        assertTrue("Wrong renderer type",
                renderer.getSectionRenderer()
                instanceof SubmittorLinkSectionRenderer);
    }
}
