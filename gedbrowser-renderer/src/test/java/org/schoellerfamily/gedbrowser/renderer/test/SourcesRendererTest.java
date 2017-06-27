package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.reader.testreader.TestDataReader;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.schoellerfamily.gedbrowser.renderer.SourceRenderer;
import org.schoellerfamily.gedbrowser.renderer.SourcesRenderer;
import org.schoellerfamily.gedbrowser.renderer.application.ApplicationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Dick Schoeller
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestConfiguration.class })
public class SourcesRendererTest {
    /**
     * Collection of expected values.
     */
    private static final String[] INDEX_NAMES = {
            "birth certificate",
            "Figliuolo Sabino, interview",
            "Figliuolo, Cecilia Katerina, birth announcement",
            "Figliuolo, Vivian (Schoeller), telephone interviews",
            "Interviews with Estelle (Liberman Robinson) Klemer",
            "Robinson, Lisa Hope and Schoeller, Richard John, certificate of\n"
                    + "marriage",
            "Schoeller, Melissa Robinson, birth certificate",
            "Schoeller, Richard John, birth certificate",
            "Schoeller, Richard, first hand information",
    };

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
     * @throws IOException if can't read the data file
     */
    @Test
    public void testSources() throws IOException {
        final Root root = reader.readBigTestSource();
        final SourcesRenderer renderer = new SourcesRenderer(root,
                anonymousContext);
        assertSourcesMatch(renderer.getSources());
    }

    /**
     * Check that the sources matches the expectation.
     *
     * @param renderers the collection of source renderers
     */
    private void assertSourcesMatch(
            final Collection<SourceRenderer> renderers) {
        assertEquals("should have only one entry",
                INDEX_NAMES.length, renderers.size());
        int i = 0;
        for (final SourceRenderer renderer : renderers) {
            final String indexName = INDEX_NAMES[i];
            assertEquals("Index name mismatch",
                    indexName, renderer.getTitleString());
            i++;
        }
    }

    /**
     * Test whether the menu items are as expected.
     *
     * @throws IOException if can't read data file
     */
    @Test
    public void testHeaderMenuItem() throws IOException {
        final Root root = reader.readFileTestSource();
        final SourcesRenderer renderer = new SourcesRenderer(root,
                anonymousContext);
        assertEquals("head href mismatch",
                "head?db=gl120368", renderer.getHeaderHref());
    }

    /**
     * Test whether the menu items are as expected.
     *
     * @throws IOException if can't read data file
     */
    @Test
    public void testIndexMenuItem() throws IOException {
        final Root root = reader.readFileTestSource();
        final SourcesRenderer renderer = new SourcesRenderer(root,
                anonymousContext);
        assertEquals("index href mismatch",
                "surnames?db=gl120368&letter=A", renderer.getIndexHref());
    }

    /**
     * Test whether the menu items are as expected.
     *
     * @throws IOException if can't read data file
     */
    @Test
    public void testLivingMenuItem() throws IOException {
        final Root root = reader.readFileTestSource();
        final SourcesRenderer renderer = new SourcesRenderer(root,
                anonymousContext);
        assertEquals("living href mismatch",
                "living?db=gl120368", renderer.getLivingHref());
    }

    /**
     * Test whether the menu items are as expected.
     *
     * @throws IOException if can't read data file
     */
    @Test
    public void testSourcesMenuItem() throws IOException {
        final Root root = reader.readFileTestSource();
        final SourcesRenderer renderer = new SourcesRenderer(root,
                anonymousContext);
        assertEquals("submittors href mismatch",
                "sources?db=gl120368", renderer.getSourcesHref());
    }

    /**
     * Test whether the menu items are as expected.
     *
     * @throws IOException if can't read data file
     */
    @Test
    public void testSubmittorsMenuItem() throws IOException {
        final Root root = reader.readFileTestSource();
        final SourcesRenderer renderer = new SourcesRenderer(root,
                anonymousContext);
        assertEquals("sources href mismatch",
                "submittors?db=gl120368", renderer.getSubmittorsHref());
    }

    /**
     * Test whether the menu items are as expected.
     *
     * @throws IOException if can't read data file
     */
    @Test
    public void testPlacesMenuItem() throws IOException {
        final Root root1 = reader.readFileTestSource();
        final SourcesRenderer renderer = new SourcesRenderer(root1,
                anonymousContext);
        assertEquals("places href mismatch", "places?db=gl120368",
                renderer.getPlacesHref());
    }
}
