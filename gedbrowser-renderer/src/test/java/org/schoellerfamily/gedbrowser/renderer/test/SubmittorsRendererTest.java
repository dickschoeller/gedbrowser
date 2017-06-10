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
                    + "Richard <span class=\"surname\">Schoeller</span>"
                    + " [SUB1]</a>",
                    subRenderer.getIndexName());
            assertEquals("HTML name mismatch",
                    "Richard <span class=\"surname\">Schoeller</span>",
                    subRenderer.getNameHtml());
        }
    }
}
