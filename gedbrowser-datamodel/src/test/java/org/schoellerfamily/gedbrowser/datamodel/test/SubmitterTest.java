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
        assertEquals("Richard John/Schoeller/",
                submitter.getName().toString(), "Mismatch name");
    }

    /** */
    @Test
    public void testSubmitterSurname() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Submitter submitter = builder.createSubmitter("S1",
                "Richard John/Schoeller/");
        assertEquals("Schoeller", submitter.getSurname(), "Mismatch surname");
    }

    /** */
    @Test
    public void testSubmitterIndexName() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Submitter submitter = builder.createSubmitter("S1",
                "Richard John/Schoeller/");
        assertEquals("Schoeller, Richard John",
                submitter.getIndexName(), "Mismatch index name");
    }

    /** */
    @Test
    public void testSubmitterNullName() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Submitter submitter2 = builder.createSubmitter("S2");
        assertEquals("", submitter2.getName().toString(), "Expected null name");
    }

    /** */
    @Test
    public void testSubmitterNullSurname() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Submitter submitter2 = builder.createSubmitter("S2");
        assertEquals("?", submitter2.getSurname(), "Expected ? surname");
    }

    /** */
    @Test
    public void testSubmitterNullIndexName() {
        final GedObjectBuilder builder = new GedObjectBuilder(root);
        final Submitter submitter2 = builder.createSubmitter("S2");
        assertEquals("?, ?", submitter2.getIndexName(), "Expected ?s index name");
    }

    /** */
    @Test
    public void testSubmitterNullParent() {
        final Submitter submitter = new Submitter();
        assertNull(submitter.getParent(), "Expected null parent");
    }

    /** */
    @Test
    public void testSubmitterNullString() {
        final Submitter submitter = new Submitter();
        assertEquals("", submitter.getString(), "Expected empty string");
    }

    /** */
    @Test
    public void testSubmitterRootParent() {
        final Submitter submitter = new Submitter(root, new ObjectId("SUB1"));
        assertEquals(root, submitter.getParent(), "Mismatched parent");
    }

    /** */
    @Test
    public void testSubmitterNullNullParent() {
        Submitter submitter;
        submitter = new Submitter(null, null);
        assertEquals("", submitter.getString(), "Expected empty string");
    }

    /** */
    @Test
    public void testSubmitterNullNullString() {
        Submitter submitter;
        submitter = new Submitter(null, null);
        assertEquals("", submitter.getString(), "Expected empty string");
    }

    /** */
    @Test
    public void testSubmitterRootNullParent() {
        Submitter submitter;
        submitter = new Submitter(root, null);
        assertEquals(root, submitter.getParent(), "Mismatched parent");
    }

    /** */
    @Test
    public void testSubmitterRootNullString() {
        Submitter submitter;
        submitter = new Submitter(root, null);
        assertEquals("", submitter.getString(), "Expected empty string");

    }

    /** */
    @Test
    public void testSubmitterNullEmptyParent() {
        Submitter submitter;
        submitter = new Submitter(null, new ObjectId(""));
        assertNull(submitter.getParent(), "Expected null parent");
    }

    /** */
    @Test
    public void testSubmitterNullEmptyString() {
        Submitter submitter;
        submitter = new Submitter(null, new ObjectId(""));
        assertEquals("", submitter.getString(), "Expected empty string");
    }

    /** */
    @Test
    public void testSubmitterRootEmptyParent() {
        Submitter submitter;
        submitter = new Submitter(root, new ObjectId(""));
        assertEquals(root, submitter.getParent(), "Mismatched parent");
    }

    /** */
    @Test
    public void testSubmitterRootEmptyString() {
        Submitter submitter;
        submitter = new Submitter(root, new ObjectId(""));
        assertEquals("", submitter.getString(), "Expected empty string");
    }

    /** */
    @Test
    public void testSubmitterNullStringParent() {
        Submitter submitter;
        submitter = new Submitter(null, new ObjectId(TEST_STRING));
        assertNull(submitter.getParent(), "Expected null parent");
    }

    /** */
    @Test
    public void testSubmitterNullStringString() {
        Submitter submitter;
        submitter = new Submitter(null, new ObjectId(TEST_STRING));
        assertEquals(TEST_STRING, submitter.getString(), "Mismatched string");
    }

    /** */
    @Test
    public void testSubmitterRootStringParent() {
        Submitter submitter;
        submitter = new Submitter(root, new ObjectId(TEST_STRING));
        assertEquals(root, submitter.getParent(), "Mismatched parent");
    }

    /** */
    @Test
    public void testSubmitterRootStringString() {
        Submitter submitter;
        submitter = new Submitter(root, new ObjectId(TEST_STRING));
        assertEquals(TEST_STRING, submitter.getString(), "Mismatched ID");
    }

    /** */
    @Test
    public void testSubmitterNullNullNullParent() {
        final Submitter submitter = new Submitter(null, null);
        assertNull(submitter.getParent(), "Expected null parent");
    }

    /** */
    @Test
    public void testSubmitterNullNullNullString() {
        final Submitter submitter = new Submitter(null, null);
        assertEquals("", submitter.getString(), "Expected empty string");
    }

    /** */
    @Test
    public void testSubmitterRootNullNullParent() {
        final Submitter submitter = new Submitter(root, null);
        assertEquals(root, submitter.getParent(), "Mismatched parent");
    }

    /** */
    @Test
    public void testSubmitterRootNullNullString() {
        final Submitter submitter = new Submitter(root, null);
        assertEquals("", submitter.getString(), "Expected empty string");
    }

    /** */
    @Test
    public void testSubmitterNullEmptyNullParent() {
        final Submitter submitter = new Submitter(null, new ObjectId(""));
        assertNull(submitter.getParent(), "Expected null parent");
    }

    /** */
    @Test
    public void testSubmitterNullEmptyNullString() {
        final Submitter submitter = new Submitter(null, new ObjectId(""));
        assertEquals("", submitter.getString(), "Expected empty string");
    }

    /** */
    @Test
    public void testSubmitterRootEmptyNullParent() {
        final Submitter submitter = new Submitter(root, new ObjectId(""));
        assertEquals(root, submitter.getParent(), "Mismatched parent");
    }

    /** */
    @Test
    public void testSubmitterRootEmptyNullString() {
        final Submitter submitter = new Submitter(root, new ObjectId(""));
        assertEquals("", submitter.getString(), "Expected empty string");
    }

    /** */
    @Test
    public void testSubmitterNullStringNullParent() {
        final Submitter submitter = new Submitter(null,
                new ObjectId(TEST_STRING));
        assertNull(submitter.getParent(), "Expected null parent");
    }

    /** */
    @Test
    public void testSubmitterNullStringNullString() {
        final Submitter submitter = new Submitter(null,
                new ObjectId(TEST_STRING));
        assertEquals(TEST_STRING, submitter.getString(), "Mismatched string");
    }

    /** */
    @Test
    public void testSubmitterRootStringNullParent() {
        final Submitter submitter = new Submitter(root,
                new ObjectId(TEST_STRING));
        assertEquals(root, submitter.getParent(), "Mismatched parent");
    }

    /** */
    @Test
    public void testSubmitterRootStringNullString() {
        final Submitter submitter = new Submitter(root,
                new ObjectId(TEST_STRING));
        assertEquals(TEST_STRING, submitter.getString(), "Mismatched string");
    }

    /** */
    @Test
    public void testSubmitterNullNullEmptyParent() {
        final Submitter submitter = new Submitter(null, null);
        assertNull(submitter.getParent(), "Expected null parent");
    }

    /** */
    @Test
    public void testSubmitterNullNullEmptyString() {
        final Submitter submitter = new Submitter(null, null);
        assertEquals("", submitter.getString(), "Expected empty string");
    }

    /** */
    @Test
    public void testSubmitterRootNullEmptyParent() {
        final Submitter submitter = new Submitter(root, null);
        assertEquals(root, submitter.getParent(), "Mismatched parent");
    }

    /** */
    @Test
    public void testSubmitterRootNullEmptyString() {
        final Submitter submitter = new Submitter(root, null);
        assertEquals("", submitter.getString(), "Expected empty string");
    }

    /** */
    @Test
    public void testSubmitterNullEmptyEmptyParent() {
        final Submitter submitter = new Submitter(null, new ObjectId(""));
        assertNull(submitter.getParent(), "Expected null parent");
    }

    /** */
    @Test
    public void testSubmitterNullEmptyEmptyString() {
        final Submitter submitter = new Submitter(null, new ObjectId(""));
        assertEquals("", submitter.getString(), "Expected empty string");
    }

    /** */
    @Test
    public void testSubmitterRootEmptyEmptyParent() {
        final Submitter submitter = new Submitter(root, new ObjectId(""));
        assertEquals(root, submitter.getParent(), "Mismatched parent");
    }

    /** */
    @Test
    public void testSubmitterRootEmptyEmptyString() {
        final Submitter submitter = new Submitter(root, new ObjectId(""));
        assertEquals("", submitter.getString(), "Expected empty string");
    }

    /** */
    @Test
    public void testSubmitterNullStringEmptyParent() {
        final Submitter submitter = new Submitter(null,
                new ObjectId(TEST_STRING));
        assertNull(submitter.getParent(), "Expected null parent");
    }

    /** */
    @Test
    public void testSubmitterNullStringEmptyString() {
        final Submitter submitter = new Submitter(null,
                new ObjectId(TEST_STRING));
        assertEquals(TEST_STRING, submitter.getString(), "Mismatched string");
    }

    /** */
    @Test
    public void testSubmitterRootStringEmptyParent() {
        final Submitter submitter = new Submitter(root,
                new ObjectId(TEST_STRING));
        assertEquals(root, submitter.getParent(), "Mismatched parent");
    }

    /** */
    @Test
    public void testSubmitterRootStringEmptyString() {
        final Submitter submitter = new Submitter(root,
                new ObjectId(TEST_STRING));
        assertEquals(TEST_STRING, submitter.getString(), "Mismatched string");
    }

    /** */
    @Test
    public void testSubmitterNullNullStringParent() {
        final Submitter submitter = new Submitter(null, null);
        submitter.appendString(TEST_STRUNG);
        assertNull(submitter.getParent(), "Expected null parent");
    }

    /** */
    @Test
    public void testSubmitterNullNullStringString() {
        final Submitter submitter = new Submitter(null, null);
        submitter.appendString(TEST_STRUNG);
        assertEquals(TEST_STRUNG, submitter.getString(), "Mismatched string");
    }

    /** */
    @Test
    public void testSubmitterRootNullStringParent() {
        final Submitter submitter = new Submitter(root, null);
        submitter.appendString(TEST_STRUNG);
        assertEquals(root, submitter.getParent(), "Mismatched parent");
    }

    /** */
    @Test
    public void testSubmitterRootNullStringString() {
        final Submitter submitter = new Submitter(root, null);
        submitter.appendString(TEST_STRUNG);
        assertEquals(TEST_STRUNG, submitter.getString(), "Mismatched string");
    }

    /** */
    @Test
    public void testSubmitterNullEmptyStringParent() {
        final Submitter submitter = new Submitter(null, new ObjectId(""));
        submitter.appendString(TEST_STRUNG);
        assertNull(submitter.getParent(), "Expected null parent");
    }

    /** */
    @Test
    public void testSubmitterNullEmptyStringString() {
        final Submitter submitter = new Submitter(null, new ObjectId(""));
        submitter.appendString(TEST_STRUNG);
        assertEquals(TEST_STRUNG, submitter.getString(), "Mismatched string");
    }

    /** */
    @Test
    public void testSubmitterRootEmptyStringParent() {
        final Submitter submitter = new Submitter(root, new ObjectId(""));
        submitter.appendString(TEST_STRUNG);
        assertEquals(root, submitter.getParent(), "Mismatched parent");
    }

    /** */
    @Test
    public void testSubmitterRootEmptyStringString() {
        final Submitter submitter = new Submitter(root, new ObjectId(""));
        submitter.appendString(TEST_STRUNG);
        assertEquals(TEST_STRUNG, submitter.getString(), "Mismatched string");
    }

    /** */
    @Test
    public void testSubmitterNullStringStringParent() {
        final Submitter submitter = new Submitter(null,
                new ObjectId(TEST_STRING));
        submitter.appendString(TEST_STRUNG);
        assertNull(submitter.getParent(), "Expected null parent");
    }

    /** */
    @Test
    public void testSubmitterNullStringStringString() {
        final Submitter submitter = new Submitter(null,
                new ObjectId(TEST_STRING));
        submitter.appendString(" " + TEST_STRUNG);
        assertEquals("string strung", submitter.getString(), "Mismatched string");
    }

    /** */
    @Test
    public void testSubmitterRootStringStringParent() {
        final Submitter submitter = new Submitter(root,
                new ObjectId(TEST_STRING));
        submitter.appendString(" " + TEST_STRUNG);
        assertEquals(root, submitter.getParent(), "Mismatched parent");
    }

    /** */
    @Test
    public void testSubmitterRootStringStringString() {
        final Submitter submitter = new Submitter(root,
                new ObjectId(TEST_STRING));
        submitter.appendString(" " + TEST_STRUNG);
        assertEquals("string strung", submitter.getString(), "Mismatched string");
    }
}
