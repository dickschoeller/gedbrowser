package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.schoellerfamily.gedbrowser.datamodel.Date;
import org.schoellerfamily.gedbrowser.renderer.DateListItemRenderer;
import org.schoellerfamily.gedbrowser.renderer.DatePhraseRenderer;
import org.schoellerfamily.gedbrowser.renderer.DateRenderer;
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
 * Contains tests for date renderer.
 *
 * @author Richard Schoeller
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { TestConfiguration.class })
final class DateRendererTest {
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
        final DateRenderer renderer = new DateRenderer(new Date(null), new GedRendererFactory(),
            anonymousContext);
        assertTrue(
            renderer.getAttributeListOpenRenderer() instanceof SimpleAttributeListOpenRenderer,
            "Wrong renderer type");
    }

    @Test
    void testListItemRenderer() {
        final DateRenderer renderer = new DateRenderer(new Date(null), new GedRendererFactory(),
            anonymousContext);
        assertTrue(renderer.getListItemRenderer() instanceof DateListItemRenderer,
            "Wrong renderer type");
    }

    @Test
    void testNameHtmlRenderer() {
        final DateRenderer renderer = new DateRenderer(new Date(null), new GedRendererFactory(),
            anonymousContext);
        assertTrue(renderer.getNameHtmlRenderer() instanceof NullNameHtmlRenderer,
            "Wrong renderer type");
    }

    @Test
    void testNameIndexRenderer() {
        final DateRenderer renderer = new DateRenderer(new Date(null), new GedRendererFactory(),
            anonymousContext);
        assertTrue(renderer.getNameIndexRenderer() instanceof NullNameIndexRenderer,
            "Wrong renderer type");
    }

    @Test
    void testPhraseRenderer() {
        final DateRenderer renderer = new DateRenderer(new Date(null), new GedRendererFactory(),
            anonymousContext);
        assertTrue(renderer.getPhraseRenderer() instanceof DatePhraseRenderer,
            "Wrong renderer type");
    }
}
