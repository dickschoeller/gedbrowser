package org.schoellerfamily.gedbrowser.datamodel.util.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.schoellerfamily.gedbrowser.datamodel.Head;
import org.schoellerfamily.gedbrowser.datamodel.Submission;
import org.schoellerfamily.gedbrowser.datamodel.SubmissionLink;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;

/**
 * @author Dick Schoeller
 */
public class GedObjectBuilderSubmissionTest {
    /** */
    @Test
    public void testCreateSubmissionSimple() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Submission submission = builder.createSubmission("SUB1");
        assertEquals("Mismatched tag", "SUB1", submission.getString());
    }

    /** */
    @Test
    public void testCreateSubmissionSimpleNoId() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Submission submission = builder.createSubmission(null);
        assertEquals("Expected empty string", "", submission.getString());
    }

    /** */
    @Test
    public void testCreateSubmissionLinkSimple() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Submission submission = builder.createSubmission("SUB1");
        final SubmissionLink submissionLink = builder
                .createSubmissionLink(submission);
        assertEquals("Mismatched tag", "SUB1", submissionLink.getToString());
    }

    /** */
    @Test
    public void testCreateSubmissionLinkSimpleNoId() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Submission submission = builder.createSubmission(null);
        final SubmissionLink submissionLink = builder
                .createSubmissionLink(submission);
        assertEquals("Expected empty string", "", submissionLink.getToString());
    }

    /** */
    @Test
    public void testCreateSubmissionLinkSimpleParent() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Submission submission = builder.createSubmission("SUB1");
        final SubmissionLink submissionLink = builder
                .createSubmissionLink(submission);
        assertEquals("Mismatched parent", Head.class,
                submissionLink.getParent().getClass());
    }

    /** */
    @Test
    public void testCreateSubmissionLinkSimpleNoIdParent() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Submission submission = builder.createSubmission(null);
        final SubmissionLink submissionLink = builder
                .createSubmissionLink(submission);
        assertEquals("Mismatched parent", Head.class,
                submissionLink.getParent().getClass());
    }
}
