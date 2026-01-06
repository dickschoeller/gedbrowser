package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.Collection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.Submission;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;
import org.schoellerfamily.gedbrowser.reader.testreader.TestDataReader;
import org.schoellerfamily.gedbrowser.renderer.GedRendererFactory;
import org.schoellerfamily.gedbrowser.renderer.NullListItemRenderer;
import org.schoellerfamily.gedbrowser.renderer.NullNameHtmlRenderer;
import org.schoellerfamily.gedbrowser.renderer.NullNameIndexRenderer;
import org.schoellerfamily.gedbrowser.renderer.NullPhraseRenderer;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.schoellerfamily.gedbrowser.renderer.SimpleAttributeListOpenRenderer;
import org.schoellerfamily.gedbrowser.renderer.SubmissionRenderer;
import org.schoellerfamily.gedbrowser.renderer.application.ApplicationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author Dick Schoeller
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { TestConfiguration.class })
public final class SubmissionRendererTest {
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
        final SubmissionRenderer renderer = createRenderer();
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
        final SubmissionRenderer renderer = createRenderer();
        assertTrue(renderer.getListItemRenderer() instanceof NullListItemRenderer,
            "Wrong renderer type");
    }

    /**
     * Test that we are using the appropriate sub-renderers. We will test the
     * sub-renderers directly.
     */
    @Test
    void testNameHtmlRenderer() {
        final SubmissionRenderer renderer = createRenderer();
        assertTrue(renderer.getNameHtmlRenderer() instanceof NullNameHtmlRenderer,
            "Wrong renderer type");
    }

    /**
     * Test that we are using the appropriate sub-renderers. We will test the
     * sub-renderers directly.
     */
    @Test
    void testNameIndexRenderer() {
        final SubmissionRenderer renderer = createRenderer();
        assertTrue(renderer.getNameIndexRenderer() instanceof NullNameIndexRenderer,
            "Wrong renderer type");
    }

    /**
     * Test that we are using the appropriate sub-renderers. We will test the
     * sub-renderers directly.
     */
    @Test
    void testPhraseRenderer() {
        final SubmissionRenderer renderer = createRenderer();
        assertTrue(renderer.getPhraseRenderer() instanceof NullPhraseRenderer,
            "Wrong renderer type");
    }

    /**
     * Test that we are using the appropriate sub-renderers. We will test the
     * sub-renderers directly.
     */
    @Test
    void testIdString() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Submission submission = builder.createSubmission("S1");
        final SubmissionRenderer renderer = createRenderer(submission);
        assertEquals("S1", renderer.getIdString(), "Submission ID mismatch");
    }

    /**
     * Test that we are using the appropriate sub-renderers. We will test the
     * sub-renderers directly.
     */
    @Test
    void testTitleString() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Submission submission = builder.createSubmission("S1");
        final SubmissionRenderer renderer = createRenderer(submission);
        assertEquals("S1", renderer.getTitleString(), "Submission ID mismatch");
    }

    /**
     * Test that we are using the appropriate sub-renderers. We will test the
     * sub-renderers directly.
     */
    @Test
    void testNullIdString() {
        final SubmissionRenderer renderer = createRenderer();
        assertEquals("", renderer.getIdString(), "Expected empty submission ID");
    }

    /**
     * @return the renderer
     */
    private SubmissionRenderer createRenderer() {
        return new SubmissionRenderer(new Submission(), new GedRendererFactory(), anonymousContext);
    }

    /**
     * Test whether the menu items are as expected.
     *
     * @throws IOException if can't read data file
     */
    @Test
    void testHeadMenuItem() throws IOException {
        final Root root = reader.readFileTestSource();
        final Collection<Submission> submissions = root.find(Submission.class);
        for (final Submission submission : submissions) {
            final SubmissionRenderer renderer = createRenderer(submission);
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
        final Collection<Submission> submissions = root.find(Submission.class);
        for (final Submission submission : submissions) {
            final SubmissionRenderer renderer = createRenderer(submission);
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
        final Collection<Submission> submissions = root.find(Submission.class);
        for (final Submission submission : submissions) {
            final SubmissionRenderer renderer = createRenderer(submission);
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
        final Collection<Submission> submissions = root.find(Submission.class);
        for (final Submission submission : submissions) {
            final SubmissionRenderer renderer = createRenderer(submission);
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
        final Collection<Submission> submissions = root.find(Submission.class);
        for (final Submission submission : submissions) {
            final SubmissionRenderer renderer = createRenderer(submission);
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
        final Collection<Submission> submissions = root.find(Submission.class);
        for (final Submission submission : submissions) {
            final SubmissionRenderer renderer = createRenderer(submission);
            assertEquals("sources?db=gl120368", renderer.getSourcesHref(), "sources href mismatch");
        }
    }

    /**
     * Test whether the menu items are as expected.
     *
     * @throws IOException if can't read data file
     */
    @Test
    void testSubmissionsMenuItem() throws IOException {
        final Root root = reader.readFileTestSource();
        final Collection<Submission> submissions = root.find(Submission.class);
        for (final Submission submission : submissions) {
            final SubmissionRenderer renderer = createRenderer(submission);
            assertEquals("submitters?db=gl120368", renderer.getSubmittersHref(),
                "submitters href mismatch");
        }
    }

    /**
     * @param submission the submission
     * @return the renderer
     */
    private SubmissionRenderer createRenderer(final Submission submission) {
        return new SubmissionRenderer(submission, new GedRendererFactory(), anonymousContext);
    }
}
