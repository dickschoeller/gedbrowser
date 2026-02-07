package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.Collection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.schoellerfamily.gedbrowser.datamodel.Note;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.reader.testreader.TestDataReader;
import org.schoellerfamily.gedbrowser.renderer.GedRenderer;
import org.schoellerfamily.gedbrowser.renderer.GedRendererFactory;
import org.schoellerfamily.gedbrowser.renderer.NoteNameIndexRenderer;
import org.schoellerfamily.gedbrowser.renderer.NoteRenderer;
import org.schoellerfamily.gedbrowser.renderer.NullListItemRenderer;
import org.schoellerfamily.gedbrowser.renderer.NullNameHtmlRenderer;
import org.schoellerfamily.gedbrowser.renderer.NullPhraseRenderer;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.schoellerfamily.gedbrowser.renderer.SimpleAttributeListOpenRenderer;
import org.schoellerfamily.gedbrowser.renderer.SourceLinkRenderer;
import org.schoellerfamily.gedbrowser.renderer.application.ApplicationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author Dick Schoeller
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { TestConfiguration.class })
final class NoteRendererTest {
    /** */
    @Autowired
    private transient TestDataReader reader;
    /** */
    @Autowired
    private transient ApplicationInfo appInfo;

    /** */
    private RenderingContext anonymousContext;

    /** */
    @BeforeEach
    void setUp() {
        anonymousContext = RenderingContext.anonymous(appInfo);
    }

    /**
     * Test that we are using the appropriate sub-renderers. We will test the
     * sub-renderers directly.
     */
    @Test
    void testAttributeListOpenRenderer() {
        final NoteRenderer renderer = createRenderer();
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
        final NoteRenderer renderer = createRenderer();
        assertTrue(renderer.getListItemRenderer() instanceof NullListItemRenderer,
            "Wrong renderer type");
    }

    /**
     * Test that we are using the appropriate sub-renderers. We will test the
     * sub-renderers directly.
     */
    @Test
    void testNameHtmlRenderer() {
        final NoteRenderer renderer = createRenderer();
        assertTrue(renderer.getNameHtmlRenderer() instanceof NullNameHtmlRenderer,
            "Wrong renderer type");
    }

    /**
     * Test that we are using the appropriate sub-renderers. We will test the
     * sub-renderers directly.
     */
    @Test
    void testNameIndexRenderer() {
        final NoteRenderer renderer = createRenderer();
        assertTrue(renderer.getNameIndexRenderer() instanceof NoteNameIndexRenderer,
            "Wrong renderer type");
    }

    /**
     * Test that we are using the appropriate sub-renderers. We will test the
     * sub-renderers directly.
     */
    @Test
    void testPhraseRenderer() {
        final NoteRenderer renderer = createRenderer();
        assertTrue(renderer.getPhraseRenderer() instanceof NullPhraseRenderer,
            "Wrong renderer type");
    }

    /**
     * @return the renderer
     */
    private NoteRenderer createRenderer() {
        return new NoteRenderer(new Note(null, new ObjectId("N1")), new GedRendererFactory(),
            anonymousContext);
    }

    /**
     * @throws IOException because the reader can
     */
    @Test
    void testTitleString() throws IOException {
        final Root root = reader.readBigTestSource();
        final Note note = (Note) root.find("N1");
        final NoteRenderer renderer = new NoteRenderer(note, new GedRendererFactory(),
            anonymousContext);
        assertEquals("This is a note", renderer.getTitleString(), "Mismatched title string");
    }

    /**
     * @throws IOException because the reader can
     */
    @Test
    void testTitleStringLong() throws IOException {
        final Root root = reader.readBigTestSource();
        final Note note = (Note) root.find("N2");
        final NoteRenderer renderer = new NoteRenderer(note, new GedRendererFactory(),
            anonymousContext);
        assertEquals("This is a note with a much longer string, and some",
            renderer.getTitleString(), "Mismatched title string");
    }

    /**
     * @throws IOException because the reader can
     */
    @Test
    void testTitleStringWithAttributesCount() throws IOException {
        final Root root = reader.readBigTestSource();
        final Note note = (Note) root.find("N3");
        final NoteRenderer renderer = new NoteRenderer(note, new GedRendererFactory(),
            anonymousContext);
        assertEquals(1, renderer.getAttributes().size(), "Count is wrong");
    }

    /**
     * @throws IOException because the reader can
     */
    @Test
    void testTitleStringWithAttributesContent() throws IOException {
        final Root root = reader.readBigTestSource();
        final Note note = (Note) root.find("N3");
        final NoteRenderer renderer = new NoteRenderer(note, new GedRendererFactory(),
            anonymousContext);
        for (final GedRenderer<?> aRenderer : renderer.getAttributes()) {
            assertTrue(aRenderer instanceof SourceLinkRenderer, "Wrong type of renderer");
        }
    }

    /**
     * @throws IOException because the reader can
     */
    @Test
    void testIdString() throws IOException {
        final Root root = reader.readBigTestSource();
        final Note note = (Note) root.find("N1");
        final NoteRenderer renderer = new NoteRenderer(note, new GedRendererFactory(),
            anonymousContext);
        assertEquals("N1", renderer.getIdString(), "Mismatched note ID");
    }

    /**
     * @throws IOException because the reader can
     */
    @Test
    void testIdAttributes() throws IOException {
        final String[] expects = {
            "<span class=\"label\">Abbreviation:</span>" + " SchoellerRichardBirthCert",
            "<span class=\"label\">Note:</span>" + " I have a certified copy of this document" };
        final Root root = reader.readBigTestSource();
        final Note note = (Note) root.find("N1");
        final NoteRenderer renderer = new NoteRenderer(note, new GedRendererFactory(),
            anonymousContext);
        int i = 0;
        for (final GedRenderer<?> attribute : renderer.getAttributes()) {
            assertEquals(expects[i++], attribute.getListItemContents(),
                "Rendered html doesn't match expectation");
        }
    }

    /**
     * Test the generating of an HTML name for inclusion in an index.
     *
     * @throws IOException if can't read data file
     */
    @Test
    void testIndexName() throws IOException {
        final Root root = reader.readBigTestSource();
        final Note note = (Note) root.find("N1");
        final NoteRenderer renderer = new NoteRenderer(note, new GedRendererFactory(),
            anonymousContext);
        assertEquals(
            "<a href=\"note?db=null&amp;id=N1\" class=\"name\""
                + " id=\"note-N1\">This is a note (N1)</a>",
            renderer.getIndexNameHtml(), "Mismatched index html string");
    }

    /**
     * Test whether the menu items are as expected.
     *
     * @throws IOException if can't read data file
     */
    @Test
    void testHeadMenuItem() throws IOException {
        final Root root = reader.readFileTestSource();
        final Collection<Note> notes = root.find(Note.class);
        for (final Note note : notes) {
            final NoteRenderer renderer = createRenderer(note);
            assertEquals("head?db=gl120368", renderer.getHeaderHref(), "head href mismatch");
        }
    }

    /**
     * Test whether the menu items are as expected.
     *
     * @throws IOException if can't read data file
     */
    @Test
    void testSaveMenuItem() throws IOException {
        final Root root = reader.readFileTestSource();
        final Collection<Note> notes = root.find(Note.class);
        for (final Note note : notes) {
            final NoteRenderer renderer = createRenderer(note);
            assertEquals("save?db=gl120368", renderer.getSaveHref(), "save href mismatch");
        }
    }

    /**
     * Test whether the menu items are as expected.
     *
     * @throws IOException if can't read data file
     */
    @Test
    void testSaveFilename() throws IOException {
        final Root root = reader.readFileTestSource();
        final Collection<Note> notes = root.find(Note.class);
        for (final Note note : notes) {
            final NoteRenderer renderer = createRenderer(note);
            assertEquals("gl120368.ged", renderer.getSaveFilename(), "save href mismatch");
        }
    }

    /**
     * Test whether the menu items are as expected., "Rendered string does not match
     * expectation".
     *
     * @throws IOException if can't read data file
     */
    @Test
    void testIndexMenuItem() throws IOException {
        final Root root = reader.readFileTestSource();
        final Collection<Note> notes = root.find(Note.class);
        for (final Note note : notes) {
            final NoteRenderer renderer = createRenderer(note);
            assertEquals("surnames?db=gl120368&letter=A", renderer.getIndexHref(),
                "index href mismatch");
        }
    }

    /**
     * Test whether the menu items are as expected.
     *
     * @throws IOException if can't read data file
     */
    @Test
    void testLivingMenuItem() throws IOException {
        final Root root = reader.readFileTestSource();
        final Collection<Note> notes = root.find(Note.class);
        for (final Note note : notes) {
            final NoteRenderer renderer = createRenderer(note);
            assertEquals("living?db=gl120368", renderer.getLivingHref(), "living href mismatch");
        }
    }

    /**
     * Test whether the menu items are as expected.
     *
     * @throws IOException if can't read data file
     */
    @Test
    void testNotesMenuItem() throws IOException {
        final Root root = reader.readFileTestSource();
        final Collection<Note> notes = root.find(Note.class);
        for (final Note note : notes) {
            final NoteRenderer renderer = createRenderer(note);
            assertEquals("sources?db=gl120368", renderer.getSourcesHref(), "sources href mismatch");
        }
    }

    /**
     * Test whether the menu items are as expected.
     *
     * @throws IOException if can't read data file
     */
    @Test
    void testSubmittersMenuItem() throws IOException {
        final Root root = reader.readFileTestSource();
        final Collection<Note> notes = root.find(Note.class);
        for (final Note note : notes) {
            final NoteRenderer renderer = createRenderer(note);
            assertEquals("submitters?db=gl120368", renderer.getSubmittersHref(),
                "submitters href mismatch");
        }
    }

    /**
     * Test whether the menu items are as expected.
     *
     * @throws IOException if can't read data file
     */
    @Test
    void testPlacesMenuItem() throws IOException {
        final Root root = reader.readFileTestSource();
        final Collection<Note> notes = root.find(Note.class);
        for (final Note note : notes) {
            final NoteRenderer renderer = createRenderer(note);
            assertEquals("places?db=gl120368", renderer.getPlacesHref(), "places href mismatch");
        }
    }

    /**
     * @param note the note
     * @return the renderer
     */
    private NoteRenderer createRenderer(final Note note) {
        return new NoteRenderer(note, new GedRendererFactory(), anonymousContext);
    }
}
