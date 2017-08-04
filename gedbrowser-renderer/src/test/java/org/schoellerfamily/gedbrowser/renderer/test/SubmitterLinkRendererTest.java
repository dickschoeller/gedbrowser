package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Dick Schoeller
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestConfiguration.class })
public final class SubmitterLinkRendererTest {
    /** */
    @Autowired
    private transient ApplicationInfo appInfo;

    /** */
    private RenderingContext anonymousContext;

    /** */
    @Before
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
        final SubmitterLinkRenderer renderer = new SubmitterLinkRenderer(
                new SubmitterLink(), new GedRendererFactory(),
                anonymousContext);
        assertTrue("Wrong renderer type",
                renderer.getListItemRenderer()
                instanceof SubmitterLinkListItemRenderer);
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
        assertTrue("Wrong renderer type",
                renderer.getNameHtmlRenderer()
                instanceof SubmitterLinkNameHtmlRenderer);
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
        final SubmitterLinkRenderer renderer = new SubmitterLinkRenderer(
                new SubmitterLink(), new GedRendererFactory(),
                anonymousContext);
        assertTrue("Wrong renderer type",
                renderer.getPhraseRenderer()
                instanceof SubmitterLinkPhraseRenderer);
    }
}
