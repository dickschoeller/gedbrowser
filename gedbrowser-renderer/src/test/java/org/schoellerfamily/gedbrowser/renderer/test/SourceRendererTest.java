package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.Collection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.Source;
import org.schoellerfamily.gedbrowser.reader.testreader.TestDataReader;
import org.schoellerfamily.gedbrowser.renderer.GedRenderer;
import org.schoellerfamily.gedbrowser.renderer.GedRendererFactory;
import org.schoellerfamily.gedbrowser.renderer.NullListItemRenderer;
import org.schoellerfamily.gedbrowser.renderer.NullNameHtmlRenderer;
import org.schoellerfamily.gedbrowser.renderer.NullPhraseRenderer;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.schoellerfamily.gedbrowser.renderer.SimpleAttributeListOpenRenderer;
import org.schoellerfamily.gedbrowser.renderer.SourceNameIndexRenderer;
import org.schoellerfamily.gedbrowser.renderer.SourceRenderer;
import org.schoellerfamily.gedbrowser.renderer.application.ApplicationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author Dick Schoeller
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { TestConfiguration.class })
public final class SourceRendererTest {
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
    public void setUp() {
        anonymousContext = RenderingContext.anonymous(appInfo);
    }

    /**
     * Test that we are using the appropriate sub-renderers. We will test the
     * sub-renderers directly.
     */
    @Test
    void testAttributeListOpenRenderer() {
        final SourceRenderer renderer = createRenderer();
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
        final SourceRenderer renderer = createRenderer();
        assertTrue(renderer.getListItemRenderer() instanceof NullListItemRenderer,
            "Wrong renderer type");
    }

    /**
     * Test that we are using the appropriate sub-renderers. We will test the
     * sub-renderers directly.
     */
    @Test
    void testNameHtmlRenderer() {
        final SourceRenderer renderer = createRenderer();
        assertTrue(renderer.getNameHtmlRenderer() instanceof NullNameHtmlRenderer,
            "Wrong renderer type");
    }

    /**
     * Test that we are using the appropriate sub-renderers. We will test the
     * sub-renderers directly.
     */
    @Test
    void testNameIndexRenderer() {
        final SourceRenderer renderer = createRenderer();
        assertTrue(renderer.getNameIndexRenderer() instanceof SourceNameIndexRenderer,
            "Wrong renderer type");
    }

    /**
     * Test that we are using the appropriate sub-renderers. We will test the
     * sub-renderers directly.
     */
    @Test
    void testPhraseRenderer() {
        final SourceRenderer renderer = createRenderer();
        assertTrue(renderer.getPhraseRenderer() instanceof NullPhraseRenderer,
            "Wrong renderer type");
    }

    /**
     * @return the renderer
     */
    private SourceRenderer createRenderer() {
        return new SourceRenderer(new Source(null, new ObjectId("S1")), new GedRendererFactory(),
            anonymousContext);
    }

    /**
     * @throws IOException because the reader can
     */
    @Test
    void testTitleString() throws IOException {
        final Root root = reader.readBigTestSource();
        final Source source = (Source) root.find("S3");
        final SourceRenderer renderer = new SourceRenderer(source, new GedRendererFactory(),
            anonymousContext);
        assertEquals("Schoeller, Richard John, birth certificate", renderer.getTitleString(),
            "Mismatched title string");
    }

    /**
     * @throws IOException because the reader can
     */
    @Test
    void testIdString() throws IOException {
        final Root root = reader.readBigTestSource();
        final Source source = (Source) root.find("S3");
        final SourceRenderer renderer = new SourceRenderer(source, new GedRendererFactory(),
            anonymousContext);
        assertEquals("S3", renderer.getIdString(), "Mismatched source ID");
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
        final Source source = (Source) root.find("S3");
        final SourceRenderer renderer = new SourceRenderer(source, new GedRendererFactory(),
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
        final Source source = (Source) root.find("S3");
        final SourceRenderer renderer = new SourceRenderer(source, new GedRendererFactory(),
            anonymousContext);
        assertEquals(
            "<a href=\"source?db=null&amp;id=S3\" class=\"name\""
                + " id=\"source-S3\">Schoeller, Richard John, birth" + " certificate (S3)</a>",
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
        final Collection<Source> sources = root.find(Source.class);
        for (final Source source : sources) {
            final SourceRenderer renderer = createRenderer(source);
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
        final Collection<Source> sources = root.find(Source.class);
        for (final Source source : sources) {
            final SourceRenderer renderer = createRenderer(source);
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
        final Collection<Source> sources = root.find(Source.class);
        for (final Source source : sources) {
            final SourceRenderer renderer = createRenderer(source);
            assertEquals("gl120368.ged", renderer.getSaveFilename(), "save href mismatch");
        }
    }

    /**
     * Test whether the menu items are as expected.
     *
     * @throws IOException if can't read data file
     */
    @Test
    void testIndexMenuItem() throws IOException {
        final Root root = reader.readFileTestSource();
        final Collection<Source> sources = root.find(Source.class);
        for (final Source source : sources) {
            final SourceRenderer renderer = createRenderer(source);
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
        final Collection<Source> sources = root.find(Source.class);
        for (final Source source : sources) {
            final SourceRenderer renderer = createRenderer(source);
            assertEquals("living?db=gl120368", renderer.getLivingHref(), "living href mismatch");
        }
    }

    /**
     * Test whether the menu items are as expected.
     *
     * @throws IOException if can't read data file
     */
    @Test
    void testSourcesMenuItem() throws IOException {
        final Root root = reader.readFileTestSource();
        final Collection<Source> sources = root.find(Source.class);
        for (final Source source : sources) {
            final SourceRenderer renderer = createRenderer(source);
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
        final Collection<Source> sources = root.find(Source.class);
        for (final Source source : sources) {
            final SourceRenderer renderer = createRenderer(source);
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
        final Collection<Source> sources = root.find(Source.class);
        for (final Source source : sources) {
            final SourceRenderer renderer = createRenderer(source);
            assertEquals("places?db=gl120368", renderer.getPlacesHref(), "places href mismatch");
        }
    }

    /**
     * @param source the source
     * @return the renderer
     */
    private SourceRenderer createRenderer(final Source source) {
        return new SourceRenderer(source, new GedRendererFactory(), anonymousContext);
    }
}
