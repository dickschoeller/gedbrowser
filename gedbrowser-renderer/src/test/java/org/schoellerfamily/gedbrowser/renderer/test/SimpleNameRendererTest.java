package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author Dick Schoeller
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { TestConfiguration.class })
public final class SimpleNameRendererTest {
    /** */
    @Autowired
    private transient ApplicationInfo appInfo;

    /** */
    private RenderingContext anonymousContext;

    /** */
    @BeforeEach
    public void setUp() {
        anonymousContext = RenderingContext.anonymous(appInfo);
    }

    /**
     * Test that we are using the appropriate sub-renderers. We will test the
     * sub-renderers directly.
     */
    @Test
    void testAttributeListOpenRenderer() {
        final SimpleNameRenderer renderer = new SimpleNameRenderer(new Name(null),
            new GedRendererFactory(), anonymousContext);
        assertTrue(
            renderer.getAttributeListOpenRenderer() instanceof SimpleAttributeListOpenRenderer,
            "Wrong renderer type");
    }

    /**
     * Test that we are using the appropriate sub-renderers. We will test the
     * sub-renderers directly.
     */
    @Test
    void testListItemRenderer() {
        final SimpleNameRenderer renderer = new SimpleNameRenderer(new Name(null),
            new GedRendererFactory(), anonymousContext);
        assertTrue(renderer.getListItemRenderer() instanceof SimpleNameListItemRenderer,
            "Wrong renderer type");
    }

    /**
     * Test that we are using the appropriate sub-renderers. We will test the
     * sub-renderers directly.
     */
    @Test
    void testNameHtmlRenderer() {
        final SimpleNameRenderer renderer = new SimpleNameRenderer(new Name(null),
            new GedRendererFactory(), anonymousContext);
        assertTrue(renderer.getNameHtmlRenderer() instanceof SimpleNameNameHtmlRenderer,
            "Wrong renderer type");
    }

    /**
     * Test that we are using the appropriate sub-renderers. We will test the
     * sub-renderers directly.
     */
    @Test
    void testNameIndexRenderer() {
        final SimpleNameRenderer renderer = new SimpleNameRenderer(new Name(null),
            new GedRendererFactory(), anonymousContext);
        assertTrue(renderer.getNameIndexRenderer() instanceof SimpleNameNameIndexRenderer,
            "Wrong renderer type");
    }

    /**
     * Test that we are using the appropriate sub-renderers. We will test the
     * sub-renderers directly.
     */
    @Test
    void testPhraseRenderer() {
        final SimpleNameRenderer renderer = new SimpleNameRenderer(new Name(null),
            new GedRendererFactory(), anonymousContext);
        assertTrue(renderer.getPhraseRenderer() instanceof SimpleNamePhraseRenderer,
            "Wrong renderer type");
    }
}
