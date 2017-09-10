package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Dick Schoeller
 */
@RunWith(SpringJUnit4ClassRunner.class)
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
        final SubmissionRenderer renderer = createRenderer();
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
        final SubmissionRenderer renderer = createRenderer();
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
        final SubmissionRenderer renderer = createRenderer();
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
        final SubmissionRenderer renderer = createRenderer();
        assertTrue("Wrong renderer type",
                renderer.getNameIndexRenderer()
                instanceof NullNameIndexRenderer);
    }

    /**
     * Test that we are using the appropriate sub-renderers.
     * We will test the sub-renderers directly.
     */
    @Test
    public void testPhraseRenderer() {
        final SubmissionRenderer renderer = createRenderer();
        assertTrue("Wrong renderer type",
                renderer.getPhraseRenderer()
                instanceof NullPhraseRenderer);
    }

    /**
     * Test that we are using the appropriate sub-renderers.
     * We will test the sub-renderers directly.
     */
    @Test
    public void testIdString() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Submission submission = builder.createSubmission("S1");
        final SubmissionRenderer renderer = createRenderer(submission);
        assertEquals("Submission ID mismatch", "S1", renderer.getIdString());
    }

    /**
     * Test that we are using the appropriate sub-renderers.
     * We will test the sub-renderers directly.
     */
    @Test
    public void testTitleString() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Submission submission = builder.createSubmission("S1");
        final SubmissionRenderer renderer = createRenderer(submission);
        assertEquals("Submission ID mismatch", "S1",
                renderer.getTitleString());
    }

    /**
     * Test that we are using the appropriate sub-renderers.
     * We will test the sub-renderers directly.
     */
    @Test
    public void testNullIdString() {
        final SubmissionRenderer renderer = createRenderer();
        assertEquals("Expected empty submission ID", "",
                renderer.getIdString());
    }

    /**
     * @return the renderer
     */
    private SubmissionRenderer createRenderer() {
        return new SubmissionRenderer(new Submission(),
                new GedRendererFactory(), anonymousContext);
    }

    /**
     * Test whether the menu items are as expected.
     *
     * @throws IOException if can't read data file
     */
    @Test
    public void testHeadMenuItem() throws IOException {
        final Root root = reader.readFileTestSource();
        final Collection<Submission> submissions = root.find(Submission.class);
        for (final Submission submission : submissions) {
            final SubmissionRenderer renderer = createRenderer(submission);
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
    public void testSaveMenuItem() throws IOException {
        final Root root = reader.readFileTestSource();
        final Collection<Submission> submissions = root.find(Submission.class);
        for (final Submission submission : submissions) {
            final SubmissionRenderer renderer = createRenderer(submission);
            assertEquals("save href mismatch",
                    "save?db=gl120368", renderer.getSaveHref());
        }
    }

    /**
     * Test whether the menu items are as expected.
     *
     * @throws IOException if can't read data file
     */
    @Test
    public void testSaveFilename() throws IOException {
        final Root root = reader.readFileTestSource();
        final Collection<Submission> submissions = root.find(Submission.class);
        for (final Submission submission : submissions) {
            final SubmissionRenderer renderer = createRenderer(submission);
            assertEquals("save href mismatch",
                    "gl120368.ged", renderer.getSaveFilename());
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
        final Collection<Submission> submissions = root.find(Submission.class);
        for (final Submission submission : submissions) {
            final SubmissionRenderer renderer = createRenderer(submission);
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
        final Collection<Submission> submissions = root.find(Submission.class);
        for (final Submission submission : submissions) {
            final SubmissionRenderer renderer = createRenderer(submission);
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
        final Collection<Submission> submissions = root.find(Submission.class);
        for (final Submission submission : submissions) {
            final SubmissionRenderer renderer = createRenderer(submission);
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
    public void testSubmissionsMenuItem() throws IOException {
        final Root root = reader.readFileTestSource();
        final Collection<Submission> submissions = root.find(Submission.class);
        for (final Submission submission : submissions) {
            final SubmissionRenderer renderer = createRenderer(submission);
            assertEquals("submitters href mismatch",
                    "submitters?db=gl120368", renderer.getSubmittersHref());
        }
    }

    /**
     * @param submission the submission
     * @return the renderer
     */
    private SubmissionRenderer createRenderer(final Submission submission) {
        return new SubmissionRenderer(submission,
                new GedRendererFactory(), anonymousContext);
    }
}
