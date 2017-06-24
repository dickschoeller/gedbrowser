package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.schoellerfamily.gedbrowser.datamodel.Name;
import org.schoellerfamily.gedbrowser.renderer.GedRendererFactory;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.schoellerfamily.gedbrowser.renderer.SimpleAttributeListOpenRenderer;
import org.schoellerfamily.gedbrowser.renderer.SimpleNameListItemRenderer;
import org.schoellerfamily.gedbrowser.renderer.SimpleNameNameHtmlRenderer;
import org.schoellerfamily.gedbrowser.renderer.SimpleNameNameIndexRenderer;
import org.schoellerfamily.gedbrowser.renderer.SimpleNamePhraseRenderer;
import org.schoellerfamily.gedbrowser.renderer.SimpleNameRenderer;
import org.schoellerfamily.gedbrowser.renderer.application.ApplicationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Dick Schoeller
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestConfiguration.class })
public final class SimpleNameRendererTest {
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
        final SimpleNameRenderer renderer = new SimpleNameRenderer(
                new Name(null), new GedRendererFactory(), anonymousContext);
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
        final SimpleNameRenderer renderer = new SimpleNameRenderer(
                new Name(null), new GedRendererFactory(), anonymousContext);
        assertTrue("Wrong renderer type",
                renderer.getListItemRenderer()
                instanceof SimpleNameListItemRenderer);
    }

    /**
     * Test that we are using the appropriate sub-renderers.
     * We will test the sub-renderers directly.
     */
    @Test
    public void testNameHtmlRenderer() {
        final SimpleNameRenderer renderer = new SimpleNameRenderer(
                new Name(null), new GedRendererFactory(), anonymousContext);
        assertTrue("Wrong renderer type",
                renderer.getNameHtmlRenderer()
                instanceof SimpleNameNameHtmlRenderer);
    }

    /**
     * Test that we are using the appropriate sub-renderers.
     * We will test the sub-renderers directly.
     */
    @Test
    public void testNameIndexRenderer() {
        final SimpleNameRenderer renderer = new SimpleNameRenderer(
                new Name(null), new GedRendererFactory(), anonymousContext);
        assertTrue("Wrong renderer type",
                renderer.getNameIndexRenderer()
                instanceof SimpleNameNameIndexRenderer);
    }

    /**
     * Test that we are using the appropriate sub-renderers.
     * We will test the sub-renderers directly.
     */
    @Test
    public void testPhraseRenderer() {
        final SimpleNameRenderer renderer = new SimpleNameRenderer(
                new Name(null), new GedRendererFactory(), anonymousContext);
        assertTrue("Wrong renderer type",
                renderer.getPhraseRenderer()
                instanceof SimpleNamePhraseRenderer);
    }
}
