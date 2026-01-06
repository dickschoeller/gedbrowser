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
 * @author Dick Schoeller
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { TestConfiguration.class })
public final class NoteLinkRendererTest {
    /** */
    @Autowired
    private transient ApplicationInfo appInfo;

    /** */
    private RenderingContext anonymousContext;

    /** */
    @BeforeEach
    public void init() {
        anonymousContext = RenderingContext.anonymous(appInfo);
    }

    /**
     * Test that we are using the appropriate sub-renderers. We will test the
     * sub-renderers directly.
     */
    @Test
    public void testAttributeListOpenRenderer() {
        final NoteLinkRenderer renderer = createRenderer();
        assertTrue(
            renderer.getAttributeListOpenRenderer() instanceof SimpleAttributeListOpenRenderer,
            "Wrong renderer type");
    }

    /**
     * Test that we are using the appropriate sub-renderers. We will test the
     * sub-renderers directly.
     */
    @Test
    public void testListItemRenderer() {
        final NoteLinkRenderer renderer = createRenderer();
        assertTrue(renderer.getListItemRenderer() instanceof NoteLinkListItemRenderer,
            "Wrong renderer type");
    }

    /**
     * Test that we are using the appropriate sub-renderers. We will test the
     * sub-renderers directly.
     */
    @Test
    public void testNameHtmlRenderer() {
        final NoteLinkRenderer renderer = createRenderer();
        assertTrue(renderer.getNameHtmlRenderer() instanceof NullNameHtmlRenderer,
            "Wrong renderer type");
    }

    /**
     * Test that we are using the appropriate sub-renderers. We will test the
     * sub-renderers directly.
     */
    @Test
    public void testNameIndexRenderer() {
        final NoteLinkRenderer renderer = createRenderer();
        assertTrue(renderer.getNameIndexRenderer() instanceof NoteLinkNameIndexRenderer,
            "Wrong renderer type");
    }

    /**
     * Test that we are using the appropriate sub-renderers. We will test the
     * sub-renderers directly.
     */
    @Test
    public void testPhraseRenderer() {
        final NoteLinkRenderer renderer = createRenderer();
        assertTrue(renderer.getPhraseRenderer() instanceof NoteLinkPhraseRenderer,
            "Wrong renderer type");
    }

    /**
     * Test empty rendering.
     */
    @Test
    public void testUnsetNoteLink() {
        final NoteLinkRenderer renderer = createRenderer();
        assertEquals("", renderer.getIndexName(), "Index name should be empty");
    }

    /**
     * Test empty rendering.
     */
    @Test
    public void testNoteLinkCobweb() {
        final NoteLinkRenderer renderer = new NoteLinkRenderer(
            new NoteLink(null, "notelink", new ObjectId("N1")), new GedRendererFactory(),
            anonymousContext);
        assertEquals("N1", renderer.getIndexName(), "Index name mismatch");
    }

    /**
     * @return the renderer
     */
    private NoteLinkRenderer createRenderer() {
        return new NoteLinkRenderer(new NoteLink(), new GedRendererFactory(), anonymousContext);
    }
}
