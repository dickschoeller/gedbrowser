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
import org.schoellerfamily.gedbrowser.renderer.SubmittorRenderer;
import org.schoellerfamily.gedbrowser.renderer.SubmittorsRenderer;
import org.schoellerfamily.gedbrowser.renderer.application.ApplicationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Dick Schoeller
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestConfiguration.class })
public final class SubmittorsRendererTest {
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
    public void testSubmittors() throws IOException {
        final Root root = reader.readBigTestSource();
        final SubmittorsRenderer renderer = new SubmittorsRenderer(root,
                anonymousContext);
        assertSubmittorsMatch(renderer.getSubmittors());
    }

    /**
     * Check that the submittors matches the expectation.
     *
     * @param renderers the collection of submittor renderers
     */
    private void assertSubmittorsMatch(
            final Collection<SubmittorRenderer> renderers) {
        assertEquals("should have only one entry", 1, renderers.size());
        for (final SubmittorRenderer subRenderer : renderers) {
            assertEquals("Index name mismatch",
                    "<a class=\"name\" href=\"submittor?db=null&amp;id=SUB1\">"
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
        final SubmittorsRenderer renderer = new SubmittorsRenderer(root,
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
        final SubmittorsRenderer renderer = new SubmittorsRenderer(root,
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
        final SubmittorsRenderer renderer = new SubmittorsRenderer(root,
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
        final SubmittorsRenderer renderer = new SubmittorsRenderer(root,
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
        final SubmittorsRenderer renderer = new SubmittorsRenderer(root,
                anonymousContext);
        assertEquals("sources href mismatch",
                "submittors?db=gl120368", renderer.getSubmittorsHref());
    }
}
