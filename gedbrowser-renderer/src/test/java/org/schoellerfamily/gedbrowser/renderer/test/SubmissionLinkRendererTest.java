package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;



/**
 * Contains tests for submission link renderer.
 *
 * @author Richard Schoeller
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { TestConfiguration.class })
final class SubmissionLinkRendererTest {
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
        final SubmissionLinkRenderer renderer = new SubmissionLinkRenderer(new SubmissionLink(),
            new GedRendererFactory(), anonymousContext);
        assertTrue(
            renderer.getAttributeListOpenRenderer() instanceof SimpleAttributeListOpenRenderer,
            "Wrong renderer type");
    }

    @Test
    void testListItemRenderer() {
        final SubmissionLinkRenderer renderer = new SubmissionLinkRenderer(new SubmissionLink(),
            new GedRendererFactory(), anonymousContext);
        assertTrue(renderer.getListItemRenderer() instanceof SubmissionLinkListItemRenderer,
            "Wrong renderer type");
    }

    @Test
    void testNameHtmlRenderer() {
        final SubmissionLinkRenderer renderer = new SubmissionLinkRenderer(new SubmissionLink(),
            new GedRendererFactory(), anonymousContext);
        assertTrue(renderer.getNameHtmlRenderer() instanceof SubmissionLinkNameHtmlRenderer,
            "Wrong renderer type");
    }

    @Test
    void testNameIndeRenderer() {
        final SubmissionLinkRenderer renderer = new SubmissionLinkRenderer(new SubmissionLink(),
            new GedRendererFactory(), anonymousContext);
        assertTrue(renderer.getNameIndexRenderer() instanceof NullNameIndexRenderer,
            "Wrong renderer type");
    }

    @Test
    void testPhraseRenderer() {
        final SubmissionLinkRenderer renderer = new SubmissionLinkRenderer(new SubmissionLink(),
            new GedRendererFactory(), anonymousContext);
        assertTrue(renderer.getPhraseRenderer() instanceof SubmissionLinkPhraseRenderer,
            "Wrong renderer type");
    }
}
