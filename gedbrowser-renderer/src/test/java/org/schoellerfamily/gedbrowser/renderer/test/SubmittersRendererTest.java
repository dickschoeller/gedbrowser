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
import org.schoellerfamily.gedbrowser.renderer.SubmitterRenderer;
import org.schoellerfamily.gedbrowser.renderer.SubmittersRenderer;
import org.schoellerfamily.gedbrowser.renderer.application.ApplicationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author Dick Schoeller
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { TestConfiguration.class })
public final class SubmittersRendererTest {
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
    public void testSubmitters() throws IOException {
        final Root root = reader.readBigTestSource();
        final SubmittersRenderer renderer = new SubmittersRenderer(root, anonymousContext);
        assertSubmittersMatch(renderer.getSubmitters());
    }

    /**
     * Check that the submitters matches the expectation.
     *
     * @param renderers the collection of submitter renderers
     */
    private void assertSubmittersMatch(final Collection<SubmitterRenderer> renderers) {
        assertEquals(1, renderers.size(), "should have only one entry");
        for (final SubmitterRenderer subRenderer : renderers) {
            assertEquals("<a class=\"name\" href=\"submitter?db=null&amp;id=SUB1\">"
                + "Richard Schoeller" + " [SUB1]</a>", subRenderer.getIndexName(),
                "Index name mismatch");
            assertEquals("Richard Schoeller", subRenderer.getNameHtml(), "HTML name mismatch");
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
        final SubmittersRenderer renderer = new SubmittersRenderer(root, anonymousContext);
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
        final SubmittersRenderer renderer = new SubmittersRenderer(root, anonymousContext);
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
        final SubmittersRenderer renderer = new SubmittersRenderer(root1, anonymousContext);
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
        final SubmittersRenderer renderer = new SubmittersRenderer(root, anonymousContext);
        assertEquals("surnames?db=gl120368&letter=A", renderer.getIndexHref(),
            "index href mismatch");
    }

    /**
     * Test whether the menu items are as expected.
     *
     * @throws IOException if can't read data file
     */
    @Test
    public void testLivingMenuItem() throws IOException {
        final Root root = reader.readFileTestSource();
        final SubmittersRenderer renderer = new SubmittersRenderer(root, anonymousContext);
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
        final SubmittersRenderer renderer = new SubmittersRenderer(root, anonymousContext);
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
        final SubmittersRenderer renderer = new SubmittersRenderer(root, anonymousContext);
        assertEquals("submitters?db=gl120368", renderer.getSubmittersHref(),
            "sources href mismatch");
    }
}
