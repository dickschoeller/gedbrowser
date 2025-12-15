package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.schoellerfamily.gedbrowser.datamodel.SubmitterLink;
import org.schoellerfamily.gedbrowser.renderer.GedRendererFactory;
import org.schoellerfamily.gedbrowser.renderer.NullNameIndexRenderer;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.schoellerfamily.gedbrowser.renderer.SimpleAttributeListOpenRenderer;
import org.schoellerfamily.gedbrowser.renderer.SubmitterLinkListItemRenderer;
import org.schoellerfamily.gedbrowser.renderer.SubmitterLinkNameHtmlRenderer;
import org.schoellerfamily.gedbrowser.renderer.SubmitterLinkPhraseRenderer;
import org.schoellerfamily.gedbrowser.renderer.SubmitterLinkRenderer;
import org.schoellerfamily.gedbrowser.renderer.application.ApplicationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author Dick Schoeller
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { TestConfiguration.class })
public final class SubmitterLinkRendererTest {
    /** */
    @Autowired
    private transient ApplicationInfo appInfo;

    /** */
    private RenderingContext anonymousContext;

    /** */
    @BeforeEach
    public void init() {
        anonymousContext = RenderingContext.anonymous(appInfo);
    }

    /**
     * Test that we are using the appropriate sub-renderers.
     * We will test the sub-renderers directly.
     */
    @Test
    public void testAttributeListOpenRenderer() {
        final SubmitterLinkRenderer renderer = new SubmitterLinkRenderer(
                new SubmitterLink(), new GedRendererFactory(),
                anonymousContext);
        assertTrue(renderer.getAttributeListOpenRenderer() instanceof SimpleAttributeListOpenRenderer, "Wrong renderer type");
    }

    /**
     * Test that we are using the appropriate sub-renderers.
     * We will test the sub-renderers directly.
     */
    @Test
    public void testListItemRenderer() {
        final SubmitterLinkRenderer renderer = new SubmitterLinkRenderer(
                new SubmitterLink(), new GedRendererFactory(),
                anonymousContext);
        assertTrue(renderer.getListItemRenderer() instanceof SubmitterLinkListItemRenderer, "Wrong renderer type");
    }

    /**
     * Test that we are using the appropriate sub-renderers.
     * We will test the sub-renderers directly.
     */
    @Test
    public void testNameHtmlRenderer() {
        final SubmitterLinkRenderer renderer = new SubmitterLinkRenderer(
                new SubmitterLink(), new GedRendererFactory(),
                anonymousContext);
        assertTrue(renderer.getNameHtmlRenderer() instanceof SubmitterLinkNameHtmlRenderer, "Wrong renderer type");
    }

    /**
     * Test that we are using the appropriate sub-renderers.
     * We will test the sub-renderers directly.
     */
    @Test
    public void testNameIndeRenderer() {
        final SubmitterLinkRenderer renderer = new SubmitterLinkRenderer(
                new SubmitterLink(), new GedRendererFactory(),
                anonymousContext);
        assertTrue(renderer.getNameIndexRenderer() instanceof NullNameIndexRenderer, "Wrong renderer type");
    }

    /**
     * Test that we are using the appropriate sub-renderers.
     * We will test the sub-renderers directly.
     */
    @Test
    public void testPhraseRenderer() {
        final SubmitterLinkRenderer renderer = new SubmitterLinkRenderer(
                new SubmitterLink(), new GedRendererFactory(),
                anonymousContext);
        assertTrue(renderer.getPhraseRenderer() instanceof SubmitterLinkPhraseRenderer, "Wrong renderer type");
    }
}
