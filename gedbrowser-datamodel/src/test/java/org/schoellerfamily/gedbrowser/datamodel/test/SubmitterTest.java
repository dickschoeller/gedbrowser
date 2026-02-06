package org.schoellerfamily.gedbrowser.datamodel.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.Submitter;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;

/**
 * @author Dick Schoeller
 */
@SuppressWarnings({ "PMD.ExcessivePublicCount", "PMD.GodClass" })
final class SubmitterTest {
    /** */
    private static final String TEST_STRUNG = "strung";
    /** */
    private static final String TEST_STRING = "string";
    /** */
    private final transient Root root = new Root("Root");

    /** */
    @Test
    void testSubmitterName() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Submitter submitter = builder.createSubmitter("S1",
                "Richard John/Schoeller/");
        assertEquals("Richard John/Schoeller/",
                submitter.getName().toString(), "Mismatch name");
    }

    /** */
    @Test
    void testSubmitterSurname() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Submitter submitter = builder.createSubmitter("S1",
                "Richard John/Schoeller/");
        assertEquals("Schoeller", submitter.getSurname(), "Mismatch surname");
    }

    /** */
    @Test
    void testSubmitterIndexName() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Submitter submitter = builder.createSubmitter("S1",
                "Richard John/Schoeller/");
        assertEquals("Schoeller, Richard John",
                submitter.getIndexName(), "Mismatch index name");
    }

    /** */
    @Test
    void testSubmitterNullName() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Submitter submitter2 = builder.createSubmitter("S2");
        assertEquals("", submitter2.getName().toString(), "Expected null name");
    }

    /** */
    @Test
    void testSubmitterNullSurname() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Submitter submitter2 = builder.createSubmitter("S2");
        assertEquals("?", submitter2.getSurname(), "Expected ? surname");
    }

    /** */
    @Test
    void testSubmitterNullIndexName() {
        final GedObjectBuilder builder = new GedObjectBuilder(root);
        final Submitter submitter2 = builder.createSubmitter("S2");
        assertEquals("?, ?", submitter2.getIndexName(), "Expected ?s index name");
    }

    /** */
    @Test
    void testSubmitterNullParent() {
        final Submitter submitter = new Submitter();
        assertNull(submitter.getParent(), "Expected null parent");
    }

    /** */
    @Test
    void testSubmitterNullString() {
        final Submitter submitter = new Submitter();
        assertEquals("", submitter.getString(), "Expected empty string");
    }

    /** */
    @Test
    void testSubmitterRootParent() {
        final Submitter submitter = new Submitter(root, new ObjectId("SUB1"));
        assertEquals(root, submitter.getParent(), "Mismatched parent");
    }

    /** */
    @Test
    void testSubmitterNullNullParent() {
        Submitter submitter;
        submitter = new Submitter(null, null);
        assertEquals("", submitter.getString(), "Expected empty string");
    }

    /** */
    @Test
    void testSubmitterNullNullString() {
        Submitter submitter;
        submitter = new Submitter(null, null);
        assertEquals("", submitter.getString(), "Expected empty string");
    }

    /** */
    @Test
    void testSubmitterRootNullParent() {
        Submitter submitter;
        submitter = new Submitter(root, null);
        assertEquals(root, submitter.getParent(), "Mismatched parent");
    }

    /** */
    @Test
    void testSubmitterRootNullString() {
        Submitter submitter;
        submitter = new Submitter(root, null);
        assertEquals("", submitter.getString(), "Expected empty string");

    }

    /** */
    @Test
    void testSubmitterNullEmptyParent() {
        Submitter submitter;
        submitter = new Submitter(null, new ObjectId(""));
        assertNull(submitter.getParent(), "Expected null parent");
    }

    /** */
    @Test
    void testSubmitterNullEmptyString() {
        Submitter submitter;
        submitter = new Submitter(null, new ObjectId(""));
        assertEquals("", submitter.getString(), "Expected empty string");
    }

    /** */
    @Test
    void testSubmitterRootEmptyParent() {
        Submitter submitter;
        submitter = new Submitter(root, new ObjectId(""));
        assertEquals(root, submitter.getParent(), "Mismatched parent");
    }

    /** */
    @Test
    void testSubmitterRootEmptyString() {
        Submitter submitter;
        submitter = new Submitter(root, new ObjectId(""));
        assertEquals("", submitter.getString(), "Expected empty string");
    }

    /** */
    @Test
    void testSubmitterNullStringParent() {
        Submitter submitter;
        submitter = new Submitter(null, new ObjectId(TEST_STRING));
        assertNull(submitter.getParent(), "Expected null parent");
    }

    /** */
    @Test
    void testSubmitterNullStringString() {
        Submitter submitter;
        submitter = new Submitter(null, new ObjectId(TEST_STRING));
        assertEquals(TEST_STRING, submitter.getString(), "Mismatched string");
    }

    /** */
    @Test
    void testSubmitterRootStringParent() {
        Submitter submitter;
        submitter = new Submitter(root, new ObjectId(TEST_STRING));
        assertEquals(root, submitter.getParent(), "Mismatched parent");
    }

    /** */
    @Test
    void testSubmitterRootStringString() {
        Submitter submitter;
        submitter = new Submitter(root, new ObjectId(TEST_STRING));
        assertEquals(TEST_STRING, submitter.getString(), "Mismatched ID");
    }

    /** */
    @Test
    void testSubmitterNullNullNullParent() {
        final Submitter submitter = new Submitter(null, null);
        assertNull(submitter.getParent(), "Expected null parent");
    }

    /** */
    @Test
    void testSubmitterNullNullNullString() {
        final Submitter submitter = new Submitter(null, null);
        assertEquals("", submitter.getString(), "Expected empty string");
    }

    /** */
    @Test
    void testSubmitterRootNullNullParent() {
        final Submitter submitter = new Submitter(root, null);
        assertEquals(root, submitter.getParent(), "Mismatched parent");
    }

    /** */
    @Test
    void testSubmitterRootNullNullString() {
        final Submitter submitter = new Submitter(root, null);
        assertEquals("", submitter.getString(), "Expected empty string");
    }

    /** */
    @Test
    void testSubmitterNullEmptyNullParent() {
        final Submitter submitter = new Submitter(null, new ObjectId(""));
        assertNull(submitter.getParent(), "Expected null parent");
    }

    /** */
    @Test
    void testSubmitterNullEmptyNullString() {
        final Submitter submitter = new Submitter(null, new ObjectId(""));
        assertEquals("", submitter.getString(), "Expected empty string");
    }

    /** */
    @Test
    void testSubmitterRootEmptyNullParent() {
        final Submitter submitter = new Submitter(root, new ObjectId(""));
        assertEquals(root, submitter.getParent(), "Mismatched parent");
    }

    /** */
    @Test
    void testSubmitterRootEmptyNullString() {
        final Submitter submitter = new Submitter(root, new ObjectId(""));
        assertEquals("", submitter.getString(), "Expected empty string");
    }

    /** */
    @Test
    void testSubmitterNullStringNullParent() {
        final Submitter submitter = new Submitter(null,
                new ObjectId(TEST_STRING));
        assertNull(submitter.getParent(), "Expected null parent");
    }

    /** */
    @Test
    void testSubmitterNullStringNullString() {
        final Submitter submitter = new Submitter(null,
                new ObjectId(TEST_STRING));
        assertEquals(TEST_STRING, submitter.getString(), "Mismatched string");
    }

    /** */
    @Test
    void testSubmitterRootStringNullParent() {
        final Submitter submitter = new Submitter(root,
                new ObjectId(TEST_STRING));
        assertEquals(root, submitter.getParent(), "Mismatched parent");
    }

    /** */
    @Test
    void testSubmitterRootStringNullString() {
        final Submitter submitter = new Submitter(root,
                new ObjectId(TEST_STRING));
        assertEquals(TEST_STRING, submitter.getString(), "Mismatched string");
    }

    /** */
    @Test
    void testSubmitterNullNullEmptyParent() {
        final Submitter submitter = new Submitter(null, null);
        assertNull(submitter.getParent(), "Expected null parent");
    }

    /** */
    @Test
    void testSubmitterNullNullEmptyString() {
        final Submitter submitter = new Submitter(null, null);
        assertEquals("", submitter.getString(), "Expected empty string");
    }

    /** */
    @Test
    void testSubmitterRootNullEmptyParent() {
        final Submitter submitter = new Submitter(root, null);
        assertEquals(root, submitter.getParent(), "Mismatched parent");
    }

    /** */
    @Test
    void testSubmitterRootNullEmptyString() {
        final Submitter submitter = new Submitter(root, null);
        assertEquals("", submitter.getString(), "Expected empty string");
    }

    /** */
    @Test
    void testSubmitterNullEmptyEmptyParent() {
        final Submitter submitter = new Submitter(null, new ObjectId(""));
        assertNull(submitter.getParent(), "Expected null parent");
    }

    /** */
    @Test
    void testSubmitterNullEmptyEmptyString() {
        final Submitter submitter = new Submitter(null, new ObjectId(""));
        assertEquals("", submitter.getString(), "Expected empty string");
    }

    /** */
    @Test
    void testSubmitterRootEmptyEmptyParent() {
        final Submitter submitter = new Submitter(root, new ObjectId(""));
        assertEquals(root, submitter.getParent(), "Mismatched parent");
    }

    /** */
    @Test
    void testSubmitterRootEmptyEmptyString() {
        final Submitter submitter = new Submitter(root, new ObjectId(""));
        assertEquals("", submitter.getString(), "Expected empty string");
    }

    /** */
    @Test
    void testSubmitterNullStringEmptyParent() {
        final Submitter submitter = new Submitter(null,
                new ObjectId(TEST_STRING));
        assertNull(submitter.getParent(), "Expected null parent");
    }

    /** */
    @Test
    void testSubmitterNullStringEmptyString() {
        final Submitter submitter = new Submitter(null,
                new ObjectId(TEST_STRING));
        assertEquals(TEST_STRING, submitter.getString(), "Mismatched string");
    }

    /** */
    @Test
    void testSubmitterRootStringEmptyParent() {
        final Submitter submitter = new Submitter(root,
                new ObjectId(TEST_STRING));
        assertEquals(root, submitter.getParent(), "Mismatched parent");
    }

    /** */
    @Test
    void testSubmitterRootStringEmptyString() {
        final Submitter submitter = new Submitter(root,
                new ObjectId(TEST_STRING));
        assertEquals(TEST_STRING, submitter.getString(), "Mismatched string");
    }

    /** */
    @Test
    void testSubmitterNullNullStringParent() {
        final Submitter submitter = new Submitter(null, null);
        submitter.appendString(TEST_STRUNG);
        assertNull(submitter.getParent(), "Expected null parent");
    }

    /** */
    @Test
    void testSubmitterNullNullStringString() {
        final Submitter submitter = new Submitter(null, null);
        submitter.appendString(TEST_STRUNG);
        assertEquals(TEST_STRUNG, submitter.getString(), "Mismatched string");
    }

    /** */
    @Test
    void testSubmitterRootNullStringParent() {
        final Submitter submitter = new Submitter(root, null);
        submitter.appendString(TEST_STRUNG);
        assertEquals(root, submitter.getParent(), "Mismatched parent");
    }

    /** */
    @Test
    void testSubmitterRootNullStringString() {
        final Submitter submitter = new Submitter(root, null);
        submitter.appendString(TEST_STRUNG);
        assertEquals(TEST_STRUNG, submitter.getString(), "Mismatched string");
    }

    /** */
    @Test
    void testSubmitterNullEmptyStringParent() {
        final Submitter submitter = new Submitter(null, new ObjectId(""));
        submitter.appendString(TEST_STRUNG);
        assertNull(submitter.getParent(), "Expected null parent");
    }

    /** */
    @Test
    void testSubmitterNullEmptyStringString() {
        final Submitter submitter = new Submitter(null, new ObjectId(""));
        submitter.appendString(TEST_STRUNG);
        assertEquals(TEST_STRUNG, submitter.getString(), "Mismatched string");
    }

    /** */
    @Test
    void testSubmitterRootEmptyStringParent() {
        final Submitter submitter = new Submitter(root, new ObjectId(""));
        submitter.appendString(TEST_STRUNG);
        assertEquals(root, submitter.getParent(), "Mismatched parent");
    }

    /** */
    @Test
    void testSubmitterRootEmptyStringString() {
        final Submitter submitter = new Submitter(root, new ObjectId(""));
        submitter.appendString(TEST_STRUNG);
        assertEquals(TEST_STRUNG, submitter.getString(), "Mismatched string");
    }

    /** */
    @Test
    void testSubmitterNullStringStringParent() {
        final Submitter submitter = new Submitter(null,
                new ObjectId(TEST_STRING));
        submitter.appendString(TEST_STRUNG);
        assertNull(submitter.getParent(), "Expected null parent");
    }

    /** */
    @Test
    void testSubmitterNullStringStringString() {
        final Submitter submitter = new Submitter(null,
                new ObjectId(TEST_STRING));
        submitter.appendString(" " + TEST_STRUNG);
        assertEquals("string strung", submitter.getString(), "Mismatched string");
    }

    /** */
    @Test
    void testSubmitterRootStringStringParent() {
        final Submitter submitter = new Submitter(root,
                new ObjectId(TEST_STRING));
        submitter.appendString(" " + TEST_STRUNG);
        assertEquals(root, submitter.getParent(), "Mismatched parent");
    }

    /** */
    @Test
    void testSubmitterRootStringStringString() {
        final Submitter submitter = new Submitter(root,
                new ObjectId(TEST_STRING));
        submitter.appendString(" " + TEST_STRUNG);
        assertEquals("string strung", submitter.getString(), "Mismatched string");
    }
}
