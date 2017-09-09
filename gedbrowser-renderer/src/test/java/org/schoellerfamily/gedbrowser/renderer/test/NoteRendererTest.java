package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.Note;
import org.schoellerfamily.gedbrowser.reader.testreader.TestDataReader;
import org.schoellerfamily.gedbrowser.renderer.GedRenderer;
import org.schoellerfamily.gedbrowser.renderer.GedRendererFactory;
import org.schoellerfamily.gedbrowser.renderer.NullListItemRenderer;
import org.schoellerfamily.gedbrowser.renderer.NullNameHtmlRenderer;
import org.schoellerfamily.gedbrowser.renderer.NullPhraseRenderer;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.schoellerfamily.gedbrowser.renderer.SimpleAttributeListOpenRenderer;
import org.schoellerfamily.gedbrowser.renderer.SourceLinkRenderer;
import org.schoellerfamily.gedbrowser.renderer.NoteNameIndexRenderer;
import org.schoellerfamily.gedbrowser.renderer.NoteRenderer;
import org.schoellerfamily.gedbrowser.renderer.application.ApplicationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Dick Schoeller
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestConfiguration.class })
public final class NoteRendererTest {
    /** */
    @Autowired
    private transient TestDataReader reader;
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
        final NoteRenderer renderer = createRenderer();
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
        final NoteRenderer renderer = createRenderer();
        assertTrue("Wrong renderer type",
                renderer.getListItemRenderer()
                instanceof NullListItemRenderer);
    }

    /**
     * Test that we are using the appropriate sub-renderers.
     * We will test the sub-renderers directly.
     */
    @Test
    public void testNameHtmlRenderer() {
        final NoteRenderer renderer = createRenderer();
        assertTrue("Wrong renderer type",
                renderer.getNameHtmlRenderer()
                instanceof NullNameHtmlRenderer);
    }

    /**
     * Test that we are using the appropriate sub-renderers.
     * We will test the sub-renderers directly.
     */
    @Test
    public void testNameIndexRenderer() {
        final NoteRenderer renderer = createRenderer();
        assertTrue("Wrong renderer type",
                renderer.getNameIndexRenderer()
                instanceof NoteNameIndexRenderer);
    }

    /**
     * Test that we are using the appropriate sub-renderers.
     * We will test the sub-renderers directly.
     */
    @Test
    public void testPhraseRenderer() {
        final NoteRenderer renderer = createRenderer();
        assertTrue("Wrong renderer type",
                renderer.getPhraseRenderer()
                instanceof NullPhraseRenderer);
    }

    /**
     * @return the renderer
     */
    private NoteRenderer createRenderer() {
        return new NoteRenderer(new Note(null, new ObjectId("N1")),
                new GedRendererFactory(), anonymousContext);
    }

    /**
     * @throws IOException because the reader can
     */
    @Test
    public void testTitleString() throws IOException {
        final Root root = reader.readBigTestSource();
        final Note note = (Note) root.find("N1");
        final NoteRenderer renderer = new NoteRenderer(note,
                new GedRendererFactory(),
                anonymousContext);
        assertEquals("Mismatched title string",
                "This is a note",
                renderer.getTitleString());
    }

    /**
     * @throws IOException because the reader can
     */
    @Test
    public void testTitleStringLong() throws IOException {
        final Root root = reader.readBigTestSource();
        final Note note = (Note) root.find("N2");
        final NoteRenderer renderer = new NoteRenderer(note,
                new GedRendererFactory(),
                anonymousContext);
        assertEquals("Mismatched title string",
                "This is a note with a much longer string, and some",
                renderer.getTitleString());
    }

    /**
     * @throws IOException because the reader can
     */
    @Test
    public void testTitleStringWithAttributesCount() throws IOException {
        final Root root = reader.readBigTestSource();
        final Note note = (Note) root.find("N3");
        final NoteRenderer renderer = new NoteRenderer(note,
                new GedRendererFactory(),
                anonymousContext);
        assertEquals("Count is wrong", 1, renderer.getAttributes().size());
    }

    /**
     * @throws IOException because the reader can
     */
    @Test
    public void testTitleStringWithAttributesContent() throws IOException {
        final Root root = reader.readBigTestSource();
        final Note note = (Note) root.find("N3");
        final NoteRenderer renderer = new NoteRenderer(note,
                new GedRendererFactory(),
                anonymousContext);
        for (final GedRenderer<?> aRenderer : renderer.getAttributes()) {
            assertTrue("Wrong type of renderer",
                    aRenderer instanceof SourceLinkRenderer);
        }
    }

    /**
     * @throws IOException because the reader can
     */
    @Test
    public void testIdString() throws IOException {
        final Root root = reader.readBigTestSource();
        final Note note = (Note) root.find("N1");
        final NoteRenderer renderer = new NoteRenderer(note,
                new GedRendererFactory(),
                anonymousContext);
        assertEquals("Mismatched note ID",
                "N1",
                renderer.getIdString());
    }

    /**
     * @throws IOException because the reader can
     */
    @Test
    public void testIdAttributes() throws IOException {
        final String[] expects = {
                "<span class=\"label\">Abbreviation:</span>"
                + " SchoellerRichardBirthCert",
                "<span class=\"label\">Note:</span>"
                + " I have a certified copy of this document"
        };
        final Root root = reader.readBigTestSource();
        final Note note = (Note) root.find("N1");
        final NoteRenderer renderer = new NoteRenderer(note,
                new GedRendererFactory(),
                anonymousContext);
        int i = 0;
        for (final GedRenderer<?> attribute : renderer.getAttributes()) {
            assertEquals("Rendered html doesn't match expectation",
                    expects[i++], attribute.getListItemContents());
        }
    }

    /**
     * Test the generating of an HTML name for inclusion in an index.
     *
     * @throws IOException if can't read data file
     */
    @Test
    public void testIndexName() throws IOException {
        final Root root = reader.readBigTestSource();
        final Note note = (Note) root.find("N1");
        final NoteRenderer renderer = new NoteRenderer(note,
                new GedRendererFactory(),
                anonymousContext);
        assertEquals("Mismatched index html string",
                "<a href=\"note?db=null&amp;id=N1\" class=\"name\""
                + " id=\"note-N1\">This is a note (N1)</a>",
                renderer.getIndexNameHtml());
    }

    /**
     * Test whether the menu items are as expected.
     *
     * @throws IOException if can't read data file
     */
    @Test
    public void testHeadMenuItem() throws IOException {
        final Root root = reader.readFileTestSource();
        final Collection<Note> notes = root.find(Note.class);
        for (final Note note : notes) {
            final NoteRenderer renderer = createRenderer(note);
            assertEquals("head href mismatch",
                    "head?db=gl120368", renderer.getHeaderHref());
        }
    }

    /**
     * Test whether the menu items are as expected.
     *
     * @throws IOException if can't read data file
     */
    @Test
    public void testSaveMenuItem() throws IOException {
        final Root root = reader.readFileTestSource();
        final Collection<Note> notes = root.find(Note.class);
        for (final Note note : notes) {
            final NoteRenderer renderer = createRenderer(note);
            assertEquals("save href mismatch",
                    "save?db=gl120368", renderer.getSaveHref());
        }
    }

    /**
     * Test whether the menu items are as expected.
     *
     * @throws IOException if can't read data file
     */
    @Test
    public void testIndexMenuItem() throws IOException {
        final Root root = reader.readFileTestSource();
        final Collection<Note> notes = root.find(Note.class);
        for (final Note note : notes) {
            final NoteRenderer renderer = createRenderer(note);
            assertEquals("index href mismatch",
                    "surnames?db=gl120368&letter=A", renderer.getIndexHref());
        }
    }

    /**
     * Test whether the menu items are as expected.
     *
     * @throws IOException if can't read data file
     */
    @Test
    public void testLivingMenuItem() throws IOException {
        final Root root = reader.readFileTestSource();
        final Collection<Note> notes = root.find(Note.class);
        for (final Note note : notes) {
            final NoteRenderer renderer = createRenderer(note);
            assertEquals("living href mismatch",
                    "living?db=gl120368", renderer.getLivingHref());
        }
    }

    /**
     * Test whether the menu items are as expected.
     *
     * @throws IOException if can't read data file
     */
    @Test
    public void testNotesMenuItem() throws IOException {
        final Root root = reader.readFileTestSource();
        final Collection<Note> notes = root.find(Note.class);
        for (final Note note : notes) {
            final NoteRenderer renderer = createRenderer(note);
            assertEquals("sources href mismatch",
                    "sources?db=gl120368", renderer.getSourcesHref());
        }
    }

    /**
     * Test whether the menu items are as expected.
     *
     * @throws IOException if can't read data file
     */
    @Test
    public void testSubmittersMenuItem() throws IOException {
        final Root root = reader.readFileTestSource();
        final Collection<Note> notes = root.find(Note.class);
        for (final Note note : notes) {
            final NoteRenderer renderer = createRenderer(note);
            assertEquals("submitters href mismatch",
                    "submitters?db=gl120368", renderer.getSubmittersHref());
        }
    }

    /**
     * Test whether the menu items are as expected.
     *
     * @throws IOException if can't read data file
     */
    @Test
    public void testPlacesMenuItem() throws IOException {
        final Root root = reader.readFileTestSource();
        final Collection<Note> notes = root.find(Note.class);
        for (final Note note : notes) {
            final NoteRenderer renderer = createRenderer(note);
            assertEquals("places href mismatch", "places?db=gl120368",
                renderer.getPlacesHref());
        }
    }

    /**
     * @param note the note
     * @return the renderer
     */
    private NoteRenderer createRenderer(final Note note) {
        return new NoteRenderer(note,
                new GedRendererFactory(), anonymousContext);
    }
}
