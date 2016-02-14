package org.schoellerfamily.gedbrowser.renderer;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.schoellerfamily.gedbrowser.datamodel.AbstractLink;

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
        assertTrue(renderer.getAttributeListOpenRenderer()
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
        assertTrue(renderer.getListItemRenderer()
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
        assertTrue(renderer.getNameHtmlRenderer()
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
        assertTrue(renderer.getNameIndexRenderer()
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
        assertTrue(renderer.getPhraseRenderer()
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
        assertTrue(renderer.getSectionRenderer()
                instanceof NullSectionRenderer);
    }
}
