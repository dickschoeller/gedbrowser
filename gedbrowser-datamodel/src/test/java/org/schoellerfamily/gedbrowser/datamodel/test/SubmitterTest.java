package org.schoellerfamily.gedbrowser.datamodel.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.Submitter;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;

/**
 * @author Dick Schoeller
 */
@SuppressWarnings({ "PMD.ExcessivePublicCount", "PMD.GodClass" })
public final class SubmitterTest {
    /** */
    private static final String TEST_STRUNG = "strung";
    /** */
    private static final String TEST_STRING = "string";
    /** */
    private final transient Root root = new Root("Root");

    /** */
    @Test
    public void testSubmitterName() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Submitter submitter = builder.createSubmitter("S1",
                "Richard John/Schoeller/");
        assertEquals("Mismatch name", "Richard John/Schoeller/",
                submitter.getName().toString());
    }

    /** */
    @Test
    public void testSubmitterSurname() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Submitter submitter = builder.createSubmitter("S1",
                "Richard John/Schoeller/");
        assertEquals("Mismatch surname", "Schoeller", submitter.getSurname());
    }

    /** */
    @Test
    public void testSubmitterIndexName() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Submitter submitter = builder.createSubmitter("S1",
                "Richard John/Schoeller/");
        assertEquals("Mismatch index name", "Schoeller, Richard John",
                submitter.getIndexName());
    }

    /** */
    @Test
    public void testSubmitterNullName() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Submitter submitter2 = builder.createSubmitter("S2");
        assertEquals("Expected null name", "", submitter2.getName().toString());
    }

    /** */
    @Test
    public void testSubmitterNullSurname() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Submitter submitter2 = builder.createSubmitter("S2");
        assertEquals("Expected ? surname", "?", submitter2.getSurname());
    }

    /** */
    @Test
    public void testSubmitterNullIndexName() {
        final GedObjectBuilder builder = new GedObjectBuilder(root);
        final Submitter submitter2 = builder.createSubmitter("S2");
        assertEquals("Expected ?s index name", "?, ?",
                submitter2.getIndexName());
    }

    /** */
    @Test
    public void testSubmitterNullParent() {
        final Submitter submitter = new Submitter();
        assertNull("Expected null parent", submitter.getParent());
    }

    /** */
    @Test
    public void testSubmitterNullString() {
        final Submitter submitter = new Submitter();
        assertEquals("Expected empty string", "", submitter.getString());
    }

    /** */
    @Test
    public void testSubmitterRootParent() {
        final Submitter submitter = new Submitter(root, new ObjectId("SUB1"));
        assertEquals("Mismatched parent", root, submitter.getParent());
    }

    /** */
    @Test
    public void testSubmitterNullNullParent() {
        Submitter submitter;
        submitter = new Submitter(null, null);
        assertEquals("Expected empty string", "", submitter.getString());
    }

    /** */
    @Test
    public void testSubmitterNullNullString() {
        Submitter submitter;
        submitter = new Submitter(null, null);
        assertEquals("Expected empty string", "", submitter.getString());
    }

    /** */
    @Test
    public void testSubmitterRootNullParent() {
        Submitter submitter;
        submitter = new Submitter(root, null);
        assertEquals("Mismatched parent", root, submitter.getParent());
    }

    /** */
    @Test
    public void testSubmitterRootNullString() {
        Submitter submitter;
        submitter = new Submitter(root, null);
        assertEquals("Expected empty string", "", submitter.getString());

    }

    /** */
    @Test
    public void testSubmitterNullEmptyParent() {
        Submitter submitter;
        submitter = new Submitter(null, new ObjectId(""));
        assertNull("Expected null parent", submitter.getParent());
    }

    /** */
    @Test
    public void testSubmitterNullEmptyString() {
        Submitter submitter;
        submitter = new Submitter(null, new ObjectId(""));
        assertEquals("Expected empty string", "", submitter.getString());
    }

    /** */
    @Test
    public void testSubmitterRootEmptyParent() {
        Submitter submitter;
        submitter = new Submitter(root, new ObjectId(""));
        assertEquals("Mismatched parent", root, submitter.getParent());
    }

    /** */
    @Test
    public void testSubmitterRootEmptyString() {
        Submitter submitter;
        submitter = new Submitter(root, new ObjectId(""));
        assertEquals("Expected empty string", "", submitter.getString());
    }

    /** */
    @Test
    public void testSubmitterNullStringParent() {
        Submitter submitter;
        submitter = new Submitter(null, new ObjectId(TEST_STRING));
        assertNull("Expected null parent", submitter.getParent());
    }

    /** */
    @Test
    public void testSubmitterNullStringString() {
        Submitter submitter;
        submitter = new Submitter(null, new ObjectId(TEST_STRING));
        assertEquals("Mismatched string", TEST_STRING, submitter.getString());
    }

    /** */
    @Test
    public void testSubmitterRootStringParent() {
        Submitter submitter;
        submitter = new Submitter(root, new ObjectId(TEST_STRING));
        assertEquals("Mismatched parent", root, submitter.getParent());
    }

    /** */
    @Test
    public void testSubmitterRootStringString() {
        Submitter submitter;
        submitter = new Submitter(root, new ObjectId(TEST_STRING));
        assertEquals("Mismatched ID", TEST_STRING, submitter.getString());
    }

    /** */
    @Test
    public void testSubmitterNullNullNullParent() {
        final Submitter submitter = new Submitter(null, null);
        assertNull("Expected null parent", submitter.getParent());
    }

    /** */
    @Test
    public void testSubmitterNullNullNullString() {
        final Submitter submitter = new Submitter(null, null);
        assertEquals("Expected empty string", "", submitter.getString());
    }

    /** */
    @Test
    public void testSubmitterRootNullNullParent() {
        final Submitter submitter = new Submitter(root, null);
        assertEquals("Mismatched parent", root, submitter.getParent());
    }

    /** */
    @Test
    public void testSubmitterRootNullNullString() {
        final Submitter submitter = new Submitter(root, null);
        assertEquals("Expected empty string", "", submitter.getString());
    }

    /** */
    @Test
    public void testSubmitterNullEmptyNullParent() {
        final Submitter submitter = new Submitter(null, new ObjectId(""));
        assertNull("Expected null parent", submitter.getParent());
    }

    /** */
    @Test
    public void testSubmitterNullEmptyNullString() {
        final Submitter submitter = new Submitter(null, new ObjectId(""));
        assertEquals("Expected empty string", "", submitter.getString());
    }

    /** */
    @Test
    public void testSubmitterRootEmptyNullParent() {
        final Submitter submitter = new Submitter(root, new ObjectId(""));
        assertEquals("Mismatched parent", root, submitter.getParent());
    }

    /** */
    @Test
    public void testSubmitterRootEmptyNullString() {
        final Submitter submitter = new Submitter(root, new ObjectId(""));
        assertEquals("Expected empty string", "", submitter.getString());
    }

    /** */
    @Test
    public void testSubmitterNullStringNullParent() {
        final Submitter submitter = new Submitter(null,
                new ObjectId(TEST_STRING));
        assertNull("Expected null parent", submitter.getParent());
    }

    /** */
    @Test
    public void testSubmitterNullStringNullString() {
        final Submitter submitter = new Submitter(null,
                new ObjectId(TEST_STRING));
        assertEquals("Mismatched string", TEST_STRING, submitter.getString());
    }

    /** */
    @Test
    public void testSubmitterRootStringNullParent() {
        final Submitter submitter = new Submitter(root,
                new ObjectId(TEST_STRING));
        assertEquals("Mismatched parent", root, submitter.getParent());
    }

    /** */
    @Test
    public void testSubmitterRootStringNullString() {
        final Submitter submitter = new Submitter(root,
                new ObjectId(TEST_STRING));
        assertEquals("Mismatched string", TEST_STRING, submitter.getString());
    }

    /** */
    @Test
    public void testSubmitterNullNullEmptyParent() {
        final Submitter submitter = new Submitter(null, null);
        assertNull("Expected null parent", submitter.getParent());
    }

    /** */
    @Test
    public void testSubmitterNullNullEmptyString() {
        final Submitter submitter = new Submitter(null, null);
        assertEquals("Expected empty string", "", submitter.getString());
    }

    /** */
    @Test
    public void testSubmitterRootNullEmptyParent() {
        final Submitter submitter = new Submitter(root, null);
        assertEquals("Mismatched parent", root, submitter.getParent());
    }

    /** */
    @Test
    public void testSubmitterRootNullEmptyString() {
        final Submitter submitter = new Submitter(root, null);
        assertEquals("Expected empty string", "", submitter.getString());
    }

    /** */
    @Test
    public void testSubmitterNullEmptyEmptyParent() {
        final Submitter submitter = new Submitter(null, new ObjectId(""));
        assertNull("Expected null parent", submitter.getParent());
    }

    /** */
    @Test
    public void testSubmitterNullEmptyEmptyString() {
        final Submitter submitter = new Submitter(null, new ObjectId(""));
        assertEquals("Expected empty string", "", submitter.getString());
    }

    /** */
    @Test
    public void testSubmitterRootEmptyEmptyParent() {
        final Submitter submitter = new Submitter(root, new ObjectId(""));
        assertEquals("Mismatched parent", root, submitter.getParent());
    }

    /** */
    @Test
    public void testSubmitterRootEmptyEmptyString() {
        final Submitter submitter = new Submitter(root, new ObjectId(""));
        assertEquals("Expected empty string", "", submitter.getString());
    }

    /** */
    @Test
    public void testSubmitterNullStringEmptyParent() {
        final Submitter submitter = new Submitter(null,
                new ObjectId(TEST_STRING));
        assertNull("Expected null parent", submitter.getParent());
    }

    /** */
    @Test
    public void testSubmitterNullStringEmptyString() {
        final Submitter submitter = new Submitter(null,
                new ObjectId(TEST_STRING));
        assertEquals("Mismatched string", TEST_STRING, submitter.getString());
    }

    /** */
    @Test
    public void testSubmitterRootStringEmptyParent() {
        final Submitter submitter = new Submitter(root,
                new ObjectId(TEST_STRING));
        assertEquals("Mismatched parent", root, submitter.getParent());
    }

    /** */
    @Test
    public void testSubmitterRootStringEmptyString() {
        final Submitter submitter = new Submitter(root,
                new ObjectId(TEST_STRING));
        assertEquals("Mismatched string", TEST_STRING, submitter.getString());
    }

    /** */
    @Test
    public void testSubmitterNullNullStringParent() {
        final Submitter submitter = new Submitter(null, null);
        submitter.appendString(TEST_STRUNG);
        assertNull("Expected null parent", submitter.getParent());
    }

    /** */
    @Test
    public void testSubmitterNullNullStringString() {
        final Submitter submitter = new Submitter(null, null);
        submitter.appendString(TEST_STRUNG);
        assertEquals("Mismatched string", TEST_STRUNG, submitter.getString());
    }

    /** */
    @Test
    public void testSubmitterRootNullStringParent() {
        final Submitter submitter = new Submitter(root, null);
        submitter.appendString(TEST_STRUNG);
        assertEquals("Mismatched parent", root, submitter.getParent());
    }

    /** */
    @Test
    public void testSubmitterRootNullStringString() {
        final Submitter submitter = new Submitter(root, null);
        submitter.appendString(TEST_STRUNG);
        assertEquals("Mismatched string", TEST_STRUNG, submitter.getString());
    }

    /** */
    @Test
    public void testSubmitterNullEmptyStringParent() {
        final Submitter submitter = new Submitter(null, new ObjectId(""));
        submitter.appendString(TEST_STRUNG);
        assertNull("Expected null parent", submitter.getParent());
    }

    /** */
    @Test
    public void testSubmitterNullEmptyStringString() {
        final Submitter submitter = new Submitter(null, new ObjectId(""));
        submitter.appendString(TEST_STRUNG);
        assertEquals("Mismatched string", TEST_STRUNG, submitter.getString());
    }

    /** */
    @Test
    public void testSubmitterRootEmptyStringParent() {
        final Submitter submitter = new Submitter(root, new ObjectId(""));
        submitter.appendString(TEST_STRUNG);
        assertEquals("Mismatched parent", root, submitter.getParent());
    }

    /** */
    @Test
    public void testSubmitterRootEmptyStringString() {
        final Submitter submitter = new Submitter(root, new ObjectId(""));
        submitter.appendString(TEST_STRUNG);
        assertEquals("Mismatched string", TEST_STRUNG, submitter.getString());
    }

    /** */
    @Test
    public void testSubmitterNullStringStringParent() {
        final Submitter submitter = new Submitter(null,
                new ObjectId(TEST_STRING));
        submitter.appendString(TEST_STRUNG);
        assertNull("Expected null parent", submitter.getParent());
    }

    /** */
    @Test
    public void testSubmitterNullStringStringString() {
        final Submitter submitter = new Submitter(null,
                new ObjectId(TEST_STRING));
        submitter.appendString(" " + TEST_STRUNG);
        assertEquals("Mismatched string", "string strung",
                submitter.getString());
    }

    /** */
    @Test
    public void testSubmitterRootStringStringParent() {
        final Submitter submitter = new Submitter(root,
                new ObjectId(TEST_STRING));
        submitter.appendString(" " + TEST_STRUNG);
        assertEquals("Mismatched parent", root, submitter.getParent());
    }

    /** */
    @Test
    public void testSubmitterRootStringStringString() {
        final Submitter submitter = new Submitter(root,
                new ObjectId(TEST_STRING));
        submitter.appendString(" " + TEST_STRUNG);
        assertEquals("Mismatched string", "string strung",
                submitter.getString());
    }
}
