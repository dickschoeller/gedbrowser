package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.schoellerfamily.gedbrowser.datamodel.Child;
import org.schoellerfamily.gedbrowser.renderer.ChildRenderer;
import org.schoellerfamily.gedbrowser.renderer.GedRendererFactory;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.schoellerfamily.gedbrowser.renderer.application.ApplicationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author Dick Schoeller
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { TestConfiguration.class })
public final class ChildRendererTest {
    /** */
    @Autowired
    private transient ApplicationInfo appInfo;

    /** */
    private ChildRenderer renderer;

    /**
     * Set up the renderer to test.
     */
    @BeforeEach
    public void init() {
        renderer = new ChildRenderer(new Child(), new GedRendererFactory(),
            RenderingContext.anonymous(appInfo));
    }

    /**
     * Test that we are using the appropriate sub-renderers.
     * We will test the sub-renderers directly.
     */
    @Test
    public void testAttributeListOpenRenderer() {
        assertTrue(renderer.getAttributeListOpenRenderer()
                instanceof org.schoellerfamily.gedbrowser.renderer.SimpleAttributeListOpenRenderer,
                "Wrong renderer type");
    }

    /**
     * Test that we are using the appropriate sub-renderers.
     * We will test the sub-renderers directly.
     */
    @Test
    public void testListItemRenderer() {
        assertTrue(renderer.getListItemRenderer() instanceof org.schoellerfamily.gedbrowser.renderer.NullListItemRenderer,
                "Wrong renderer type");
    }

    /**
     * Test that we are using the appropriate sub-renderers.
     * We will test the sub-renderers directly.
     */
    @Test
    public void testNameHtmlRenderer() {
        assertTrue(renderer.getNameHtmlRenderer() instanceof org.schoellerfamily.gedbrowser.renderer.NullNameHtmlRenderer,
                "Wrong renderer type");
    }

    /**
     * Test that we are using the appropriate sub-renderers.
     * We will test the sub-renderers directly.
     */
    @Test
    public void testNameIndeRenderer() {
        assertTrue(renderer.getNameIndexRenderer() instanceof org.schoellerfamily.gedbrowser.renderer.NullNameIndexRenderer,
                "Wrong renderer type");
    }

    /**
     * Test that we are using the appropriate sub-renderers.
     * We will test the sub-renderers directly.
     */
    @Test
    public void testPhraseRenderer() {
        assertTrue(renderer.getPhraseRenderer() instanceof org.schoellerfamily.gedbrowser.renderer.NullPhraseRenderer,
                "Wrong renderer type");
    }
}