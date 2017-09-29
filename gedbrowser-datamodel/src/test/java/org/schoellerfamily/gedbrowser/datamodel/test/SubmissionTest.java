package org.schoellerfamily.gedbrowser.datamodel.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
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
    @Before
    public void setUp() {
        builder = new GedObjectBuilder();
    }

    /** */
    @Test
    public void testSubmissionGedObjectCompare() {
        final Root root = builder.getRoot();
        final Submission submission =
                new Submission(root, new ObjectId("SUBN1"));
        root.insert(submission);
        final GedObject gob = root.find("SUBN1");
        assertEquals("Found wrong submission", submission, gob);
    }

    /** */
    @Test
    public void testSubmissionGedObjectGetString() {
        final Submission submission = new Submission();
        assertTrue("Submission string should be empty", submission.getString()
                .isEmpty());
    }

    /** */
    @Test
    public void testSubmissionGedObjectStringGetString() {
        final Root root = builder.getRoot();
        final Submission submission =
                new Submission(root, new ObjectId("SUBN1"));
        root.insert(submission);
        assertEquals("Expected submission 1", "SUBN1", submission.getString());
    }
}
