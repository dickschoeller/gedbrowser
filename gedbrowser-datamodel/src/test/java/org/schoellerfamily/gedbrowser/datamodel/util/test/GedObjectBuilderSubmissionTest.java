package org.schoellerfamily.gedbrowser.datamodel.util.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
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
        assertEquals("SUB1", submission.getString(), "Mismatched tag");
    }

    /** */
    @Test
    public void testCreateSubmissionSimpleNoId() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Submission submission = builder.createSubmission(null);
        assertEquals("", submission.getString(), "Expected empty string");
    }

    /** */
    @Test
    public void testCreateSubmissionLinkSimple() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Submission submission = builder.createSubmission("SUB1");
        final SubmissionLink submissionLink = builder
                .createSubmissionLink(submission);
        assertEquals("SUB1", submissionLink.getToString(), "Mismatched tag");
    }

    /** */
    @Test
    public void testCreateSubmissionLinkSimpleNoId() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Submission submission = builder.createSubmission(null);
        final SubmissionLink submissionLink = builder
                .createSubmissionLink(submission);
        assertEquals("", submissionLink.getToString(), "Expected empty string");
    }

    /** */
    @Test
    public void testCreateSubmissionLinkSimpleParent() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Submission submission = builder.createSubmission("SUB1");
        final SubmissionLink submissionLink = builder
                .createSubmissionLink(submission);
        assertEquals(Head.class, submissionLink.getParent().getClass(),
                "Mismatched parent");
    }

    /** */
    @Test
    public void testCreateSubmissionLinkSimpleNoIdParent() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Submission submission = builder.createSubmission(null);
        final SubmissionLink submissionLink = builder
                .createSubmissionLink(submission);
        assertEquals(Head.class, submissionLink.getParent().getClass(),
                "Mismatched parent");
    }
}