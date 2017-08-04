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
import org.schoellerfamily.gedbrowser.renderer.SubmitterRenderer;
import org.schoellerfamily.gedbrowser.renderer.SubmittersRenderer;
import org.schoellerfamily.gedbrowser.renderer.application.ApplicationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Dick Schoeller
 */
@RunWith(SpringJUnit4ClassRunner.class)
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
    @Before
    public void init() {
        anonymousContext = RenderingContext.anonymous(appInfo);
    }

    /**
     * @throws IOException if can't read the data file
     */
    @Test
    public void testSubmitters() throws IOException {
        final Root root = reader.readBigTestSource();
        final SubmittersRenderer renderer = new SubmittersRenderer(root,
                anonymousContext);
        assertSubmittersMatch(renderer.getSubmitters());
    }

    /**
     * Check that the submitters matches the expectation.
     *
     * @param renderers the collection of submitter renderers
     */
    private void assertSubmittersMatch(
            final Collection<SubmitterRenderer> renderers) {
        assertEquals("should have only one entry", 1, renderers.size());
        for (final SubmitterRenderer subRenderer : renderers) {
            assertEquals("Index name mismatch",
                    "<a class=\"name\" href=\"submitter?db=null&amp;id=SUB1\">"
                    + "Richard Schoeller"
                    + " [SUB1]</a>",
                    subRenderer.getIndexName());
            assertEquals("HTML name mismatch",
                    "Richard Schoeller",
                    subRenderer.getNameHtml());
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
        final SubmittersRenderer renderer = new SubmittersRenderer(root,
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
        final SubmittersRenderer renderer = new SubmittersRenderer(root,
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
        final SubmittersRenderer renderer = new SubmittersRenderer(root,
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
        final SubmittersRenderer renderer = new SubmittersRenderer(root,
                anonymousContext);
        assertEquals("submitters href mismatch",
                "sources?db=gl120368", renderer.getSourcesHref());
    }

    /**
     * Test whether the menu items are as expected.
     *
     * @throws IOException if can't read data file
     */
    @Test
    public void testSubmittersMenuItem() throws IOException {
        final Root root = reader.readFileTestSource();
        final SubmittersRenderer renderer = new SubmittersRenderer(root,
                anonymousContext);
        assertEquals("sources href mismatch",
                "submitters?db=gl120368", renderer.getSubmittersHref());
    }
}
