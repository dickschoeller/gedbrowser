package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.Collection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.reader.testreader.TestDataReader;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.schoellerfamily.gedbrowser.renderer.SourceRenderer;
import org.schoellerfamily.gedbrowser.renderer.SourcesRenderer;
import org.schoellerfamily.gedbrowser.renderer.application.ApplicationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author Dick Schoeller
 */
@ExtendWith(SpringExtension.class)
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
    @BeforeEach
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
        assertEquals(INDEX_NAMES.length, renderers.size(), "should have only one entry");
        int i = 0;
        for (final SourceRenderer renderer : renderers) {
            final String indexName = INDEX_NAMES[i];
            assertEquals(indexName, renderer.getTitleString(), "Index name mismatch");
            i++;
        }
    }

    /**
     * Test whether the menu items are as expected.
     *
     * @throws IOException if can't read data file
     */
    @Test
    public void testHeadMenuItem() throws IOException {
        final Root root = reader.readFileTestSource();
        final SourcesRenderer renderer = new SourcesRenderer(root,
                anonymousContext);
        assertEquals("head?db=gl120368", renderer.getHeaderHref(), "head href mismatch");
    }

    /**
     * Test whether the menu items are as expected.
     *
     * @throws IOException if can't read data file
     */
    @Test
    public void testSaveMenuItem() throws IOException {
        final Root root = reader.readFileTestSource();
        final SourcesRenderer renderer = new SourcesRenderer(root,
                anonymousContext);
        assertEquals("save?db=gl120368", renderer.getSaveHref(), "save href mismatch");
    }

    /**
     * Test whether the menu items are as expected.
     *
     * @throws IOException if can't read data file
     */
    @Test
    public void testSaveFilename() throws IOException {
        final Root root1 = reader.readFileTestSource();
        final SourcesRenderer renderer = new SourcesRenderer(root1,
                anonymousContext);
        assertEquals("gl120368.ged", renderer.getSaveFilename(), "save href mismatch");
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
        assertEquals("surnames?db=gl120368&letter=A", renderer.getIndexHref(), "index href mismatch");
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
        assertEquals("living?db=gl120368", renderer.getLivingHref(), "living href mismatch");
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
        assertEquals("sources?db=gl120368", renderer.getSourcesHref(), "submitters href mismatch");
    }

    /**
     * Test whether the menu items are as expected.
     *
     * @throws IOException if can't read data file
     */
    @Test
    public void testSubmittersMenuItem() throws IOException {
        final Root root = reader.readFileTestSource();
        final SourcesRenderer renderer = new SourcesRenderer(root,
                anonymousContext);
        assertEquals("submitters?db=gl120368", renderer.getSubmittersHref(), "sources href mismatch");
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
        assertEquals("places?db=gl120368", renderer.getPlacesHref(), "places href mismatch");
    }
}
