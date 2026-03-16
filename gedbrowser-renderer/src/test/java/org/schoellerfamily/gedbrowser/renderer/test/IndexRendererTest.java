package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.Collection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.reader.testreader.TestDataReader;
import org.schoellerfamily.gedbrowser.renderer.IndexRenderer;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.schoellerfamily.gedbrowser.renderer.application.ApplicationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;



/**
 * Contains tests for index renderer.
 *
 * @author Richard Schoeller
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { TestConfiguration.class })
final class IndexRendererTest {
    /** */
    @Autowired
    private transient TestDataReader reader;
    /** */
    @Autowired
    private transient ApplicationInfo appInfo;

    /** */
    private RenderingContext anonymousContext;

    /** */
    private RenderingContext userContext;

    @BeforeEach
    void setUp() {
        anonymousContext = RenderingContext.anonymous(appInfo);
        userContext = RenderingContext.user(appInfo);
    }

    private String letterExpectString(final String letter) {
        return "<a id=\"letter-" + letter + "\" href=\"surnames?db=null&amp;letter=" + letter
            + "\" class=\"name\">[" + letter + "]</a>";
    }

    @Test
    void testLetters() throws IOException {
        @SuppressWarnings("checkstyle:nowhitespaceafter")
        final String[] expects = { letterExpectString("F"), letterExpectString("H"),
            letterExpectString("K"), letterExpectString("L"), letterExpectString("R"),
            letterExpectString("S"), };
        final Root root = reader.readBigTestSource();
        final IndexRenderer ir = new IndexRenderer(root, "S", anonymousContext);
        final Collection<String> letters = ir.getLetters();
        int i = 0;
        for (final String letter : letters) {
            assertEquals(expects[i++], letter, "Mismatch index letter");
        }
    }

    @Test
    void testSurnames() throws IOException {
        @SuppressWarnings("checkstyle:nowhitespaceafter")
        final String[] expects = { "Sacerdote", "Schoeller", };
        final Root root = reader.readBigTestSource();
        final IndexRenderer ir = new IndexRenderer(root, "S", anonymousContext);
        final Collection<String> surnames = ir.getSurnames();
        int i = 0;
        for (final String surname : surnames) {
            assertEquals(expects[i++], surname, "Mismatch surname");
        }
    }

    private String indexExpectString(final String id, final String surname, final String prefix,
        final String dateRange) {
        return "<li id=\"" + id + "\"><a href=\"person?db=null&amp;id=" + id
            + "\" class=\"name\"> <span class=\"surname\">" + surname + "</span>, " + prefix
            + dateRange + " (" + id + ")</a></li>";
    }

    @Test
    void testIndexNameHtmlSchoeller() throws IOException {
        @SuppressWarnings("checkstyle:nowhitespaceafter")
        final String[] expects = { indexExpectString("I4", "Schoeller", "John Vincent", " (1934-)"),
            indexExpectString("I1", "Schoeller", "Melissa Robinson", ""),
            indexExpectString("I2", "Schoeller", "Richard John", " (1958-)"),
            indexExpectString("I5", "Schoeller", "Vivian Grace", " (1960-)"), };
        final Root root = reader.readBigTestSource();
        final IndexRenderer ir = new IndexRenderer(root, "S", userContext);
        final Collection<String> indexNames = ir.getIndexNameHtmls("Schoeller");
        int i = 0;
        for (final String indexName : indexNames) {
            assertEquals(expects[i++], indexName, "Mismatch index name");
        }
    }

    @Test
    void testIndexNameHtmlSchoellerAnonymous() throws IOException {
        final Root root = reader.readBigTestSource();
        final IndexRenderer ir = new IndexRenderer(root, "S", anonymousContext);
        final Collection<String> indexNames = ir.getIndexNameHtmls("Schoeller");
        assertEquals(0, indexNames.size(), "Expect empty");
    }

    @Test
    void testIndexBase() throws IOException {
        final Root root = reader.readBigTestSource();
        final IndexRenderer ir = new IndexRenderer(root, "S", anonymousContext);
        assertEquals("S", ir.getBase(), "Mismatch index letter");
    }

    @Test
    void testHeadMenuItem() throws IOException {
        final Root root = reader.readFileTestSource();
        final IndexRenderer renderer = new IndexRenderer(root, "A", anonymousContext);
        assertEquals("head?db=gl120368", renderer.getHeaderHref(), "head href mismatch");
    }

    @Test
    void testSaveFilename() throws IOException {
        final Root root = reader.readFileTestSource();
        final IndexRenderer renderer = new IndexRenderer(root, "A", anonymousContext);
        assertEquals("gl120368.ged", renderer.getSaveFilename(), "save href mismatch");
    }

    @Test
    void testSaveMenuItem() throws IOException {
        final Root root = reader.readFileTestSource();
        final IndexRenderer renderer = new IndexRenderer(root, "A", anonymousContext);
        assertEquals("save?db=gl120368", renderer.getSaveHref(), "save href mismatch");
    }

    @Test
    void testIndexMenuItem() throws IOException {
        final Root root = reader.readFileTestSource();
        final IndexRenderer renderer = new IndexRenderer(root, "A", anonymousContext);
        assertEquals("surnames?db=gl120368&letter=A", renderer.getIndexHref(),
            "index href mismatch");
    }

    @Test
    void testLivingMenuItem() throws IOException {
        final Root root = reader.readFileTestSource();
        final IndexRenderer renderer = new IndexRenderer(root, "A", anonymousContext);
        assertEquals("living?db=gl120368", renderer.getLivingHref());
    }

    @Test
    void testSourcesMenuItem() throws IOException {
        final Root root = reader.readFileTestSource();
        final IndexRenderer renderer = new IndexRenderer(root, "A", anonymousContext);
        assertEquals("sources?db=gl120368", renderer.getSourcesHref(), "submitters href mismatch");
    }

    @Test
    void testSubmittersMenuItem() throws IOException {
        final Root root = reader.readFileTestSource();
        final IndexRenderer renderer = new IndexRenderer(root, "A", anonymousContext);
        assertEquals("submitters?db=gl120368", renderer.getSubmittersHref(),
            "sources href mismatch");
    }

    @Test
    void testPlacesMenuItem() throws IOException {
        final Root root = reader.readFileTestSource();
        final IndexRenderer renderer = new IndexRenderer(root, "A", anonymousContext);
        assertEquals("places?db=gl120368", renderer.getPlacesHref(), "places href mismatch");
    }
}
