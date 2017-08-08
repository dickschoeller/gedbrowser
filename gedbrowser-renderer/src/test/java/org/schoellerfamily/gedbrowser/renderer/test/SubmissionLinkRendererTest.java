package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.schoellerfamily.gedbrowser.datamodel.SubmissionLink;
import org.schoellerfamily.gedbrowser.renderer.GedRendererFactory;
import org.schoellerfamily.gedbrowser.renderer.NullNameIndexRenderer;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.schoellerfamily.gedbrowser.renderer.SimpleAttributeListOpenRenderer;
import org.schoellerfamily.gedbrowser.renderer.SubmissionLinkListItemRenderer;
import org.schoellerfamily.gedbrowser.renderer.SubmissionLinkNameHtmlRenderer;
import org.schoellerfamily.gedbrowser.renderer.SubmissionLinkPhraseRenderer;
import org.schoellerfamily.gedbrowser.renderer.SubmissionLinkRenderer;
import org.schoellerfamily.gedbrowser.renderer.application.ApplicationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Dick Schoeller
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestConfiguration.class })
public final class SubmissionLinkRendererTest {
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
        final SubmissionLinkRenderer renderer = new SubmissionLinkRenderer(
                new SubmissionLink(), new GedRendererFactory(),
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
        final SubmissionLinkRenderer renderer = new SubmissionLinkRenderer(
                new SubmissionLink(), new GedRendererFactory(),
                anonymousContext);
        assertTrue("Wrong renderer type",
                renderer.getListItemRenderer()
                instanceof SubmissionLinkListItemRenderer);
    }

    /**
     * Test that we are using the appropriate sub-renderers.
     * We will test the sub-renderers directly.
     */
    @Test
    public void testNameHtmlRenderer() {
        final SubmissionLinkRenderer renderer = new SubmissionLinkRenderer(
                new SubmissionLink(), new GedRendererFactory(),
                anonymousContext);
        assertTrue("Wrong renderer type",
                renderer.getNameHtmlRenderer()
                instanceof SubmissionLinkNameHtmlRenderer);
    }

    /**
     * Test that we are using the appropriate sub-renderers.
     * We will test the sub-renderers directly.
     */
    @Test
    public void testNameIndeRenderer() {
        final SubmissionLinkRenderer renderer = new SubmissionLinkRenderer(
                new SubmissionLink(), new GedRendererFactory(),
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
        final SubmissionLinkRenderer renderer = new SubmissionLinkRenderer(
                new SubmissionLink(), new GedRendererFactory(),
                anonymousContext);
        assertTrue("Wrong renderer type",
                renderer.getPhraseRenderer()
                instanceof SubmissionLinkPhraseRenderer);
    }
}
