package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.schoellerfamily.gedbrowser.datamodel.NoteLink;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;
import org.schoellerfamily.gedbrowser.renderer.GedRendererFactory;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.schoellerfamily.gedbrowser.renderer.SimpleAttributeListOpenRenderer;
import org.schoellerfamily.gedbrowser.renderer.NoteLinkListItemRenderer;
import org.schoellerfamily.gedbrowser.renderer.NoteLinkNameIndexRenderer;
import org.schoellerfamily.gedbrowser.renderer.NoteLinkPhraseRenderer;
import org.schoellerfamily.gedbrowser.renderer.NoteLinkRenderer;
import org.schoellerfamily.gedbrowser.renderer.NullNameHtmlRenderer;
import org.schoellerfamily.gedbrowser.renderer.application.ApplicationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;



/**
 * Contains tests for note link renderer.
 *
 * @author Richard Schoeller
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { TestConfiguration.class })
final class NoteLinkRendererTest {
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
        final NoteLinkRenderer renderer = createRenderer();
        assertTrue(
            renderer.getAttributeListOpenRenderer() instanceof SimpleAttributeListOpenRenderer,
            "Wrong renderer type");
    }

    @Test
    void testListItemRenderer() {
        final NoteLinkRenderer renderer = createRenderer();
        assertTrue(renderer.getListItemRenderer() instanceof NoteLinkListItemRenderer,
            "Wrong renderer type");
    }

    @Test
    void testNameHtmlRenderer() {
        final NoteLinkRenderer renderer = createRenderer();
        assertTrue(renderer.getNameHtmlRenderer() instanceof NullNameHtmlRenderer,
            "Wrong renderer type");
    }

    @Test
    void testNameIndexRenderer() {
        final NoteLinkRenderer renderer = createRenderer();
        assertTrue(renderer.getNameIndexRenderer() instanceof NoteLinkNameIndexRenderer,
            "Wrong renderer type");
    }

    @Test
    void testPhraseRenderer() {
        final NoteLinkRenderer renderer = createRenderer();
        assertTrue(renderer.getPhraseRenderer() instanceof NoteLinkPhraseRenderer,
            "Wrong renderer type");
    }

    @Test
    void testUnsetNoteLink() {
        final NoteLinkRenderer renderer = createRenderer();
        assertEquals("", renderer.getIndexName(), "Index name should be empty");
    }

    @Test
    void testNoteLinkCobweb() {
        final NoteLinkRenderer renderer = new NoteLinkRenderer(
            new NoteLink(null, "notelink", new ObjectId("N1")), new GedRendererFactory(),
            anonymousContext);
        assertEquals("N1", renderer.getIndexName(), "Index name mismatch");
    }

    private NoteLinkRenderer createRenderer() {
        return new NoteLinkRenderer(new NoteLink(), new GedRendererFactory(), anonymousContext);
    }
}
