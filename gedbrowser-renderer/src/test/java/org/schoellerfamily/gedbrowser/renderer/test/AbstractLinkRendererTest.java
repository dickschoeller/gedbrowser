package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
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
public class AbstractLinkRendererTest {
    /**
     * Test that we are using the appropriate sub-renderers.
     * We will test the sub-renderers directly.
     */
    @Test
    public final void testAttributeListOpenRenderer() {
        final AbstractLinkRenderer<?> renderer =
                new AbstractLinkRenderer<AbstractLink>(new AbstractLink(null) {
                }, new GedRendererFactory(), RenderingContext.anonymous()) {
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
    public final void testListItemRenderer() {
        final AbstractLinkRenderer<?> renderer =
                new AbstractLinkRenderer<AbstractLink>(new AbstractLink(null) {
                }, new GedRendererFactory(), RenderingContext.anonymous()) {
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
    public final void testNameHtmlRenderer() {
        final AbstractLinkRenderer<?> renderer =
                new AbstractLinkRenderer<AbstractLink>(new AbstractLink(null) {
                }, new GedRendererFactory(), RenderingContext.anonymous()) {
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
    public final void testNameIndexRenderer() {
        final AbstractLinkRenderer<?> renderer =
                new AbstractLinkRenderer<AbstractLink>(new AbstractLink(null) {
                }, new GedRendererFactory(), RenderingContext.anonymous()) {
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
    public final void testPhraseRenderer() {
        final AbstractLinkRenderer<?> renderer =
                new AbstractLinkRenderer<AbstractLink>(new AbstractLink(null) {
                }, new GedRendererFactory(), RenderingContext.anonymous()) {
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
    public final void testSectionRenderer() {
        final AbstractLinkRenderer<?> renderer =
                new AbstractLinkRenderer<AbstractLink>(new AbstractLink(null) {
                }, new GedRendererFactory(), RenderingContext.anonymous()) {
        };
        assertTrue("renderer is not of the right type",
                renderer.getSectionRenderer()
                instanceof NullSectionRenderer);
    }
}
