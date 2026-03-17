package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.schoellerfamily.gedbrowser.datamodel.Attribute;
import org.schoellerfamily.gedbrowser.renderer.AttributeListItemRenderer;
import org.schoellerfamily.gedbrowser.renderer.AttributePhraseRenderer;
import org.schoellerfamily.gedbrowser.renderer.AttributeRenderer;
import org.schoellerfamily.gedbrowser.renderer.GedRendererFactory;
import org.schoellerfamily.gedbrowser.renderer.NullNameHtmlRenderer;
import org.schoellerfamily.gedbrowser.renderer.NullNameIndexRenderer;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.schoellerfamily.gedbrowser.renderer.SimpleAttributeListOpenRenderer;
import org.schoellerfamily.gedbrowser.renderer.application.ApplicationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;



/**
 * Contains tests for attribute renderer.
 *
 * @author Richard Schoeller
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { TestConfiguration.class })
final class AttributeRendererTest {
    /** */
    @Autowired
    private transient ApplicationInfo appInfo;

    /** */
    private RenderingContext anonymousContext;

    @BeforeEach
    void setUp() {
        anonymousContext = RenderingContext.anonymous(appInfo);
    }

    @Test
    void testAttributeListOpenRenderer() {
        final AttributeRenderer renderer = new AttributeRenderer(new Attribute(null),
            new GedRendererFactory(), anonymousContext);
        assertTrue(
            renderer.getAttributeListOpenRenderer() instanceof SimpleAttributeListOpenRenderer,
            "Wrong renderer type");
    }

    @Test
    void testAttributeListItemRenderer() {
        final AttributeRenderer renderer = new AttributeRenderer(new Attribute(null),
            new GedRendererFactory(), anonymousContext);
        assertTrue(renderer.getListItemRenderer() instanceof AttributeListItemRenderer,
            "Wrong renderer type");
    }

    @Test
    void testNameHtmlRenderer() {
        final AttributeRenderer renderer = new AttributeRenderer(new Attribute(null),
            new GedRendererFactory(), anonymousContext);
        assertTrue(renderer.getNameHtmlRenderer() instanceof NullNameHtmlRenderer,
            "Wrong renderer type");
    }

    @Test
    void testNameIndexRenderer() {
        final AttributeRenderer renderer = new AttributeRenderer(new Attribute(null),
            new GedRendererFactory(), anonymousContext);
        assertTrue(renderer.getNameIndexRenderer() instanceof NullNameIndexRenderer,
            "Wrong renderer type");
    }

    @Test
    void testPhraseRenderer() {
        final AttributeRenderer renderer = new AttributeRenderer(new Attribute(null),
            new GedRendererFactory(), anonymousContext);
        assertTrue(renderer.getPhraseRenderer() instanceof AttributePhraseRenderer,
            "Wrong renderer type");
    }
    // TODO test render as page and renderAsListItem
}
