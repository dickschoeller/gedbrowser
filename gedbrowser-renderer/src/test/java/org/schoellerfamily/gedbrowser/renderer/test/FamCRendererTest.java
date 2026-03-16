package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.schoellerfamily.gedbrowser.datamodel.FamC;
import org.schoellerfamily.gedbrowser.renderer.FamCRenderer;
import org.schoellerfamily.gedbrowser.renderer.GedRendererFactory;
import org.schoellerfamily.gedbrowser.renderer.NullListItemRenderer;
import org.schoellerfamily.gedbrowser.renderer.NullNameHtmlRenderer;
import org.schoellerfamily.gedbrowser.renderer.NullNameIndexRenderer;
import org.schoellerfamily.gedbrowser.renderer.NullPhraseRenderer;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.schoellerfamily.gedbrowser.renderer.SimpleAttributeListOpenRenderer;
import org.schoellerfamily.gedbrowser.renderer.application.ApplicationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;



/**
 * Contains tests for fam c renderer.
 *
 * @author Richard Schoeller
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { TestConfiguration.class })
final class FamCRendererTest {
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
        final FamCRenderer renderer = new FamCRenderer(new FamC(), new GedRendererFactory(),
            anonymousContext);
        assertTrue(
            renderer.getAttributeListOpenRenderer() instanceof SimpleAttributeListOpenRenderer,
            "Wrong renderer type");
    }

    @Test
    void testListItemRenderer() {
        final FamCRenderer renderer = new FamCRenderer(new FamC(), new GedRendererFactory(),
            anonymousContext);
        assertTrue(renderer.getListItemRenderer() instanceof NullListItemRenderer,
            "Wrong renderer type");
    }

    @Test
    void testNameHtmlRenderer() {
        final FamCRenderer renderer = new FamCRenderer(new FamC(), new GedRendererFactory(),
            anonymousContext);
        assertTrue(renderer.getNameHtmlRenderer() instanceof NullNameHtmlRenderer,
            "Wrong renderer type");
    }

    @Test
    void testNameIndexRenderer() {
        final FamCRenderer renderer = new FamCRenderer(new FamC(), new GedRendererFactory(),
            anonymousContext);
        assertTrue(renderer.getNameIndexRenderer() instanceof NullNameIndexRenderer,
            "Wrong renderer type");
    }

    @Test
    void testPhraseRenderer() {
        final FamCRenderer renderer = new FamCRenderer(new FamC(), new GedRendererFactory(),
            anonymousContext);
        assertTrue(renderer.getPhraseRenderer() instanceof NullPhraseRenderer,
            "Wrong renderer type");
    }
}
