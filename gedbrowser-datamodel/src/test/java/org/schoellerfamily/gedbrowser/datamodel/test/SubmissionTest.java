package org.schoellerfamily.gedbrowser.datamodel.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Submission;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;

/**
 * @author Dick Schoeller
 */
public class SubmissionTest {
    /** */
    private GedObjectBuilder builder;

    /** */
    @BeforeEach
    public void setUp() {
        builder = new GedObjectBuilder();
    }

    /** */
    @Test
    void testSubmissionGedObjectCompare() {
        final Root root = builder.getRoot();
        final Submission submission =
                new Submission(root, new ObjectId("SUBN1"));
        root.insert(submission);
        final GedObject gob = root.find("SUBN1");
        assertEquals(submission, gob, "Found wrong submission");
    }

    /** */
    @Test
    void testSubmissionGedObjectGetString() {
        final Submission submission = new Submission();
        assertTrue(submission.getString().isEmpty(), "Expected empty string");
    }

    /** */
    @Test
    void testSubmissionGedObjectStringGetString() {
        final Root root = builder.getRoot();
        final Submission submission =
                new Submission(root, new ObjectId("SUBN1"));
        root.insert(submission);
        assertEquals("SUBN1", submission.getString(), "Mismatched tag");
    }
}
