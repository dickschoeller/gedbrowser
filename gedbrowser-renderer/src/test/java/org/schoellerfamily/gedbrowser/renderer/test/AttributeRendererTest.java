package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.schoellerfamily.gedbrowser.datamodel.Attribute;
import org.schoellerfamily.gedbrowser.renderer.AttributeListItemRenderer;
import org.schoellerfamily.gedbrowser.renderer.AttributePhraseRenderer;
import org.schoellerfamily.gedbrowser.renderer.AttributeRenderer;
import org.schoellerfamily.gedbrowser.renderer.AttributeSectionRenderer;
import org.schoellerfamily.gedbrowser.renderer.GedRendererFactory;
import org.schoellerfamily.gedbrowser.renderer.NullNameHtmlRenderer;
import org.schoellerfamily.gedbrowser.renderer.NullNameIndexRenderer;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.schoellerfamily.gedbrowser.renderer.SimpleAttributeListOpenRenderer;

/**
 * @author Dick Schoeller
 */
public class AttributeRendererTest {
    /**
     * Test that we are using the appropriate sub-renderers.
     * We will test the sub-renderers directly.
     */
    @Test
    public final void testAttributeListOpenRenderer() {
        final AttributeRenderer renderer =
                new AttributeRenderer(
                        new Attribute(null),
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
    public final void testAttributeListItemRenderer() {
        final AttributeRenderer renderer =
                new AttributeRenderer(
                        new Attribute(null),
                        new GedRendererFactory(),
                        RenderingContext.anonymous());
        assertTrue(renderer.getListItemRenderer()
                instanceof AttributeListItemRenderer);
    }

    /**
     * Test that we are using the appropriate sub-renderers.
     * We will test the sub-renderers directly.
     */
    @Test
    public final void testNameHtmlRenderer() {
        final AttributeRenderer renderer =
                new AttributeRenderer(
                        new Attribute(null),
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
        final AttributeRenderer renderer =
                new AttributeRenderer(
                        new Attribute(null),
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
        final AttributeRenderer renderer =
                new AttributeRenderer(
                        new Attribute(null),
                        new GedRendererFactory(),
                        RenderingContext.anonymous());
        assertTrue(renderer.getPhraseRenderer()
                instanceof AttributePhraseRenderer);
    }

    /**
     * Test that we are using the appropriate sub-renderers.
     * We will test the sub-renderers directly.
     */
    @Test
    public final void testSectionRenderer() {
        final AttributeRenderer renderer =
                new AttributeRenderer(
                        new Attribute(null),
                        new GedRendererFactory(),
                        RenderingContext.anonymous());
        assertTrue(renderer.getSectionRenderer()
                instanceof AttributeSectionRenderer);
    }
    // TODO test render as page and renderAsListItem
}
