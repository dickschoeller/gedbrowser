package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;
import org.schoellerfamily.gedbrowser.datamodel.SourceLink;
import org.schoellerfamily.gedbrowser.renderer.GedRendererFactory;
import org.schoellerfamily.gedbrowser.renderer.NullNameIndexRenderer;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.schoellerfamily.gedbrowser.renderer.SimpleAttributeListOpenRenderer;
import org.schoellerfamily.gedbrowser.renderer.SourceLinkListItemRenderer;
import org.schoellerfamily.gedbrowser.renderer.SourceLinkNameHtmlRenderer;
import org.schoellerfamily.gedbrowser.renderer.SourceLinkPhraseRenderer;
import org.schoellerfamily.gedbrowser.renderer.SourceLinkRenderer;
import org.schoellerfamily.gedbrowser.renderer.application.ApplicationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;



/**
 * Contains tests for source link renderer.
 *
 * @author Richard Schoeller
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { TestConfiguration.class })
final class SourceLinkRendererTest {
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
        final SourceLinkRenderer renderer = createRenderer();
        assertTrue(
            renderer.getAttributeListOpenRenderer() instanceof SimpleAttributeListOpenRenderer,
            "Wrong renderer type");
    }

    @Test
    void testListItemRenderer() {
        final SourceLinkRenderer renderer = createRenderer();
        assertTrue(renderer.getListItemRenderer() instanceof SourceLinkListItemRenderer,
            "Wrong renderer type");
    }

    @Test
    void testNameHtmlRenderer() {
        final SourceLinkRenderer renderer = createRenderer();
        assertTrue(renderer.getNameHtmlRenderer() instanceof SourceLinkNameHtmlRenderer,
            "Wrong renderer type");
    }

    @Test
    void testNameIndexRenderer() {
        final SourceLinkRenderer renderer = createRenderer();
        assertTrue(renderer.getNameIndexRenderer() instanceof NullNameIndexRenderer,
            "Wrong renderer type");
    }

    @Test
    void testPhraseRenderer() {
        final SourceLinkRenderer renderer = createRenderer();
        assertTrue(renderer.getPhraseRenderer() instanceof SourceLinkPhraseRenderer,
            "Wrong renderer type");
    }

    @Test
    void testUnsetSourceLink() {
        final SourceLinkRenderer renderer = createRenderer();
        assertEquals("", renderer.getIndexName(), "Index name should be empty");
    }

    @Test
    void testUnsetSourceHtmlLink() {
        final SourceLinkRenderer renderer = createRenderer();
        assertEquals("", renderer.getNameHtml(), "Index name should be empty");
    }

    @Test
    void testSourceLinkCobweb() {
        final SourceLinkRenderer renderer = new SourceLinkRenderer(
            new SourceLink(null, "sourceLink", new ObjectId("S1")), new GedRendererFactory(),
            anonymousContext);
        assertEquals("S1", renderer.getNameHtml(), "Index name mismatch");
    }

    private SourceLinkRenderer createRenderer() {
        return new SourceLinkRenderer(new SourceLink(), new GedRendererFactory(), anonymousContext);
    }
}
