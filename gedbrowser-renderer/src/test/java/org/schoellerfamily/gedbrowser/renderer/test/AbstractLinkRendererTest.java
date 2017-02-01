package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.schoellerfamily.gedbrowser.analytics.CalendarProvider;
import org.schoellerfamily.gedbrowser.analytics.CalendarProviderStub;
import org.schoellerfamily.gedbrowser.datamodel.AbstractLink;
import org.schoellerfamily.gedbrowser.renderer.AbstractLinkRenderer;
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
public final class AbstractLinkRendererTest {
    /** */
    private CalendarProvider provider;

    /** */
    @Before
    public void setUp() {
        provider = new CalendarProviderStub();
    }

    /**
     * Test that we are using the appropriate sub-renderers.
     * We will test the sub-renderers directly.
     */
    @Test
    public void testAttributeListOpenRenderer() {
        final AbstractLinkRenderer<?> renderer =
                new AbstractLinkRenderer<AbstractLink>(new AbstractLink(null) {
                }, new GedRendererFactory(), RenderingContext.anonymous(),
                        provider) {
        };
        assertTrue("renderer is not of the right type",
                renderer.getAttributeListOpenRenderer()
                instanceof SimpleAttributeListOpenRenderer);
    }

    /**
     * Test that we are using the appropriate sub-renderers.
     * We will test the sub-renderers directly.
     */
    @Test
    public void testListItemRenderer() {
        final AbstractLinkRenderer<?> renderer =
                new AbstractLinkRenderer<AbstractLink>(new AbstractLink(null) {
                }, new GedRendererFactory(), RenderingContext.anonymous(),
                        provider) {
        };
        assertTrue("renderer is not of the right type",
                renderer.getListItemRenderer()
                instanceof NullListItemRenderer);
    }

    /**
     * Test that we are using the appropriate sub-renderers.
     * We will test the sub-renderers directly.
     */
    @Test
    public void testNameHtmlRenderer() {
        final AbstractLinkRenderer<?> renderer =
                new AbstractLinkRenderer<AbstractLink>(new AbstractLink(null) {
                }, new GedRendererFactory(), RenderingContext.anonymous(),
                        provider) {
        };
        assertTrue("renderer is not of the right type",
                renderer.getNameHtmlRenderer()
                instanceof NullNameHtmlRenderer);
    }

    /**
     * Test that we are using the appropriate sub-renderers.
     * We will test the sub-renderers directly.
     */
    @Test
    public void testNameIndexRenderer() {
        final AbstractLinkRenderer<?> renderer =
                new AbstractLinkRenderer<AbstractLink>(new AbstractLink(null) {
                }, new GedRendererFactory(), RenderingContext.anonymous(),
                        provider) {
        };
        assertTrue("renderer is not of the right type",
                renderer.getNameIndexRenderer()
                instanceof NullNameIndexRenderer);
    }

    /**
     * Test that we are using the appropriate sub-renderers.
     * We will test the sub-renderers directly.
     */
    @Test
    public void testPhraseRenderer() {
        final AbstractLinkRenderer<?> renderer =
                new AbstractLinkRenderer<AbstractLink>(new AbstractLink(null) {
                }, new GedRendererFactory(), RenderingContext.anonymous(),
                        provider) {
        };
        assertTrue("renderer is not of the right type",
                renderer.getPhraseRenderer()
                instanceof NullPhraseRenderer);
    }

    /**
     * Test that we are using the appropriate sub-renderers.
     * We will test the sub-renderers directly.
     */
    @Test
    public void testSectionRenderer() {
        final AbstractLinkRenderer<?> renderer =
                new AbstractLinkRenderer<AbstractLink>(new AbstractLink(null) {
                }, new GedRendererFactory(), RenderingContext.anonymous(),
                        provider) {
        };
        assertTrue("renderer is not of the right type",
                renderer.getSectionRenderer()
                instanceof NullSectionRenderer);
    }
}
