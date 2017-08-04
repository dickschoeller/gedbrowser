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
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Dick Schoeller
 */
@RunWith(SpringJUnit4ClassRunner.class)
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
        final SourceRenderer renderer = createRenderer();
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
        final SourceRenderer renderer = createRenderer();
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
        final SourceRenderer renderer = createRenderer();
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
        final SourceRenderer renderer = createRenderer();
        assertTrue("Wrong renderer type",
                renderer.getNameIndexRenderer()
                instanceof SourceNameIndexRenderer);
    }

    /**
     * Test that we are using the appropriate sub-renderers.
     * We will test the sub-renderers directly.
     */
    @Test
    public void testPhraseRenderer() {
        final SourceRenderer renderer = createRenderer();
        assertTrue("Wrong renderer type",
                renderer.getPhraseRenderer()
                instanceof NullPhraseRenderer);
    }

    /**
     * @return the renderer
     */
    private SourceRenderer createRenderer() {
        return new SourceRenderer(new Source(null, new ObjectId("S1")),
                new GedRendererFactory(), anonymousContext);
    }

    /**
     * @throws IOException because the reader can
     */
    @Test
    public void testTitleString() throws IOException {
        final Root root = reader.readBigTestSource();
        final Source source = (Source) root.find("S3");
        final SourceRenderer renderer = new SourceRenderer(source,
                new GedRendererFactory(),
                anonymousContext);
        assertEquals("Mismatched title string",
                "Schoeller, Richard John, birth certificate",
                renderer.getTitleString());
    }

    /**
     * @throws IOException because the reader can
     */
    @Test
    public void testIdString() throws IOException {
        final Root root = reader.readBigTestSource();
        final Source source = (Source) root.find("S3");
        final SourceRenderer renderer = new SourceRenderer(source,
                new GedRendererFactory(),
                anonymousContext);
        assertEquals("Mismatched source ID",
                "S3",
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
        final Source source = (Source) root.find("S3");
        final SourceRenderer renderer = new SourceRenderer(source,
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
        final Source source = (Source) root.find("S3");
        final SourceRenderer renderer = new SourceRenderer(source,
                new GedRendererFactory(),
                anonymousContext);
        assertEquals("Mismatched index html string",
                "<a href=\"source?db=null&amp;id=S3\" class=\"name\""
                + " id=\"source-S3\">Schoeller, Richard John, birth"
                + " certificate (S3)</a>",
                renderer.getIndexNameHtml());
    }

    /**
     * Test whether the menu items are as expected.
     *
     * @throws IOException if can't read data file
     */
    @Test
    public void testHeaderMenuItem() throws IOException {
        final Root root = reader.readFileTestSource();
        final Collection<Source> sources = root.find(Source.class);
        for (final Source source : sources) {
            final SourceRenderer renderer = createRenderer(source);
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
    public void testIndexMenuItem() throws IOException {
        final Root root = reader.readFileTestSource();
        final Collection<Source> sources = root.find(Source.class);
        for (final Source source : sources) {
            final SourceRenderer renderer = createRenderer(source);
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
        final Collection<Source> sources = root.find(Source.class);
        for (final Source source : sources) {
            final SourceRenderer renderer = createRenderer(source);
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
    public void testSourcesMenuItem() throws IOException {
        final Root root = reader.readFileTestSource();
        final Collection<Source> sources = root.find(Source.class);
        for (final Source source : sources) {
            final SourceRenderer renderer = createRenderer(source);
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
        final Collection<Source> sources = root.find(Source.class);
        for (final Source source : sources) {
            final SourceRenderer renderer = createRenderer(source);
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
        final Collection<Source> sources = root.find(Source.class);
        for (final Source source : sources) {
            final SourceRenderer renderer = createRenderer(source);
            assertEquals("places href mismatch", "places?db=gl120368",
                renderer.getPlacesHref());
        }
    }

    /**
     * @param source the source
     * @return the renderer
     */
    private SourceRenderer createRenderer(final Source source) {
        return new SourceRenderer(source,
                new GedRendererFactory(), anonymousContext);
    }
}
