package org.schoellerfamily.gedbrowser.datamodel.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.Submittor;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;

/**
 * @author Dick Schoeller
 */
@SuppressWarnings({ "PMD.ExcessivePublicCount", "PMD.GodClass" })
public final class SubmittorTest {
    /** */
    private static final String TEST_STRUNG = "strung";
    /** */
    private static final String TEST_STRING = "string";
    /** */
    private final transient Root root = new Root("Root");

    /** */
    @Test
    public void testSubmittorName() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Submittor submittor =
                builder.createSubmittor("S1", "Richard John/Schoeller/");
        assertEquals("Mismatch name",
                "Richard John/Schoeller/", submittor.getName().toString());
    }

    /** */
    @Test
    public void testSubmittorSurname() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Submittor submittor =
                builder.createSubmittor("S1", "Richard John/Schoeller/");
        assertEquals("Mismatch surname",
                "Schoeller", submittor.getSurname());
    }

    /** */
    @Test
    public void testSubmittorIndexName() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Submittor submittor =
                builder.createSubmittor("S1", "Richard John/Schoeller/");
        assertEquals("Mismatch index name",
                "Schoeller, Richard John", submittor.getIndexName());
    }

    /** */
    @Test
    public void testSubmittorNullName() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Submittor submittor2 = builder.createSubmittor("S2");
        assertEquals("Expected null name",
                "", submittor2.getName().toString());
    }

    /** */
    @Test
    public void testSubmittorNullSurname() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Submittor submittor2 = builder.createSubmittor("S2");
        assertEquals("Expected ? surname",
                "?", submittor2.getSurname());
    }

    /** */
    @Test
    public void testSubmittorNullIndexName() {
        final GedObjectBuilder builder = new GedObjectBuilder(root);
        final Submittor submittor2 = builder.createSubmittor("S2");
        assertEquals("Expected ?s index name",
                "?, ?", submittor2.getIndexName());
    }

    /** */
    @Test
    public void testSubmittorNullParent() {
        final Submittor submittor = new Submittor();
        assertNull("Expected null parent", submittor.getParent());
    }

    /** */
    @Test
    public void testSubmittorNullString() {
        final Submittor submittor = new Submittor();
        assertEquals("Expected empty string", "", submittor.getString());
    }

    /** */
    @Test
    public void testSubmittorRootParent() {
        final Submittor submittor = new Submittor(root, "SUB1");
        assertEquals("Mismatched parent", root, submittor.getParent());
    }

    /** */
    @Test
    public void testSubmittorNullNullParent() {
        Submittor submittor;
        submittor = new Submittor(null, null);
        assertEquals("Expected empty string", "", submittor.getString());
    }

    /** */
    @Test
    public void testSubmittorNullNullString() {
        Submittor submittor;
        submittor = new Submittor(null, null);
        assertEquals("Expected empty string", "", submittor.getString());
    }

    /** */
    @Test
    public void testSubmittorRootNullParent() {
        Submittor submittor;
        submittor = new Submittor(root, null);
        assertEquals("Mismatched parent", root, submittor.getParent());
    }

    /** */
    @Test
    public void testSubmittorRootNullString() {
        Submittor submittor;
        submittor = new Submittor(root, null);
        assertEquals("Expected empty string", "", submittor.getString());

    }

    /** */
    @Test
    public void testSubmittorNullEmptyParent() {
        Submittor submittor;
        submittor = new Submittor(null, "");
        assertNull("Expected null parent", submittor.getParent());
    }

    /** */
    @Test
    public void testSubmittorNullEmptyString() {
        Submittor submittor;
        submittor = new Submittor(null, "");
        assertEquals("Expected empty string", "", submittor.getString());
    }

    /** */
    @Test
    public void testSubmittorRootEmptyParent() {
        Submittor submittor;
        submittor = new Submittor(root, "");
        assertEquals("Mismatched parent", root, submittor.getParent());
    }

    /** */
    @Test
    public void testSubmittorRootEmptyString() {
        Submittor submittor;
        submittor = new Submittor(root, "");
        assertEquals("Expected empty string", "", submittor.getString());
    }

    /** */
    @Test
    public void testSubmittorNullStringParent() {
        Submittor submittor;
        submittor = new Submittor(null, TEST_STRING);
        assertNull("Expected null parent", submittor.getParent());
    }

    /** */
    @Test
    public void testSubmittorNullStringString() {
        Submittor submittor;
        submittor = new Submittor(null, TEST_STRING);
        assertEquals("Mismatched string", TEST_STRING, submittor.getString());
    }

    /** */
    @Test
    public void testSubmittorRootStringParent() {
        Submittor submittor;
        submittor = new Submittor(root, TEST_STRING);
        assertEquals("Mismatched parent", root, submittor.getParent());
    }

    /** */
    @Test
    public void testSubmittorRootStringString() {
        Submittor submittor;
        submittor = new Submittor(root, TEST_STRING);
        assertEquals("Mismatched ID", TEST_STRING, submittor.getString());
    }

    /** */
    @Test
    public void testSubmittorNullNullNullParent() {
        final Submittor submittor = new Submittor(null, null, null);
        assertNull("Expected null parent", submittor.getParent());
    }

    /** */
    @Test
    public void testSubmittorNullNullNullString() {
        final Submittor submittor = new Submittor(null, null, null);
        assertEquals("Expected empty string",
                "", submittor.getString());
    }

    /** */
    @Test
    public void testSubmittorRootNullNullParent() {
        final Submittor submittor = new Submittor(root, null, null);
        assertEquals("Mismatched parent", root, submittor.getParent());
    }

    /** */
    @Test
    public void testSubmittorRootNullNullString() {
        final Submittor submittor = new Submittor(root, null, null);
        assertEquals("Expected empty string",
                "", submittor.getString());
    }

    /** */
    @Test
    public void testSubmittorNullEmptyNullParent() {
        final Submittor submittor = new Submittor(null, "", null);
        assertNull("Expected null parent", submittor.getParent());
    }

    /** */
    @Test
    public void testSubmittorNullEmptyNullString() {
        final Submittor submittor = new Submittor(null, "", null);
        assertEquals("Expected empty string",
                "", submittor.getString());
    }

    /** */
    @Test
    public void testSubmittorRootEmptyNullParent() {
        final Submittor submittor = new Submittor(root, "", null);
        assertEquals("Mismatched parent", root, submittor.getParent());
    }

    /** */
    @Test
    public void testSubmittorRootEmptyNullString() {
        final Submittor submittor = new Submittor(root, "", null);
        assertEquals("Expected empty string",
                "", submittor.getString());
    }

    /** */
    @Test
    public void testSubmittorNullStringNullParent() {
        final Submittor submittor = new Submittor(null, TEST_STRING, null);
        assertNull("Expected null parent", submittor.getParent());
    }

    /** */
    @Test
    public void testSubmittorNullStringNullString() {
        final Submittor submittor = new Submittor(null, TEST_STRING, null);
        assertEquals("Mismatched string",
                TEST_STRING, submittor.getString());
    }

    /** */
    @Test
    public void testSubmittorRootStringNullParent() {
        final Submittor submittor = new Submittor(root, TEST_STRING, null);
        assertEquals("Mismatched parent", root, submittor.getParent());
    }

    /** */
    @Test
    public void testSubmittorRootStringNullString() {
        final Submittor submittor = new Submittor(root, TEST_STRING, null);
        assertEquals("Mismatched string",
                TEST_STRING, submittor.getString());
    }

    /** */
    @Test
    public void testSubmittorNullNullEmptyParent() {
        final Submittor submittor = new Submittor(null, null, "");
        assertNull("Expected null parent", submittor.getParent());
    }

    /** */
    @Test
    public void testSubmittorNullNullEmptyString() {
        final Submittor submittor = new Submittor(null, null, "");
        assertEquals("Expected empty string",
                "", submittor.getString());
    }

    /** */
    @Test
    public void testSubmittorRootNullEmptyParent() {
        final Submittor submittor = new Submittor(root, null, "");
        assertEquals("Mismatched parent", root, submittor.getParent());
    }

    /** */
    @Test
    public void testSubmittorRootNullEmptyString() {
        final Submittor submittor = new Submittor(root, null, "");
        assertEquals("Expected empty string",
                "", submittor.getString());
    }

    /** */
    @Test
    public void testSubmittorNullEmptyEmptyParent() {
        final Submittor submittor = new Submittor(null, "", "");
        assertNull("Expected null parent", submittor.getParent());
    }

    /** */
    @Test
    public void testSubmittorNullEmptyEmptyString() {
        final Submittor submittor = new Submittor(null, "", "");
        assertEquals("Expected empty string",
                "", submittor.getString());
    }

    /** */
    @Test
    public void testSubmittorRootEmptyEmptyParent() {
        final Submittor submittor = new Submittor(root, "", "");
        assertEquals("Mismatched parent", root, submittor.getParent());
    }

    /** */
    @Test
    public void testSubmittorRootEmptyEmptyString() {
        final Submittor submittor = new Submittor(root, "", "");
        assertEquals("Expected empty string",
                "", submittor.getString());
    }

    /** */
    @Test
    public void testSubmittorNullStringEmptyParent() {
        final Submittor submittor = new Submittor(null, TEST_STRING, "");
        assertNull("Expected null parent", submittor.getParent());
    }

    /** */
    @Test
    public void testSubmittorNullStringEmptyString() {
        final Submittor submittor = new Submittor(null, TEST_STRING, "");
        assertEquals("Mismatched string",
                TEST_STRING, submittor.getString());
    }

    /** */
    @Test
    public void testSubmittorRootStringEmptyParent() {
        final Submittor submittor = new Submittor(root, TEST_STRING, "");
        assertEquals("Mismatched parent", root, submittor.getParent());
    }

    /** */
    @Test
    public void testSubmittorRootStringEmptyString() {
        final Submittor submittor = new Submittor(root, TEST_STRING, "");
        assertEquals("Mismatched string",
                TEST_STRING, submittor.getString());
    }

    /** */
    @Test
    public void testSubmittorNullNullStringParent() {
        final Submittor submittor = new Submittor(null, null, TEST_STRUNG);
        assertNull("Expected null parent", submittor.getParent());
    }

    /** */
    @Test
    public void testSubmittorNullNullStringString() {
        final Submittor submittor = new Submittor(null, null, TEST_STRUNG);
        assertEquals("Mismatched string",
                TEST_STRUNG, submittor.getString());
    }

    /** */
    @Test
    public void testSubmittorRootNullStringParent() {
        final Submittor submittor = new Submittor(root, null, TEST_STRUNG);
        assertEquals("Mismatched parent", root, submittor.getParent());
    }

    /** */
    @Test
    public void testSubmittorRootNullStringString() {
        final Submittor submittor = new Submittor(root, null, TEST_STRUNG);
        assertEquals("Mismatched string",
                TEST_STRUNG, submittor.getString());
    }

    /** */
    @Test
    public void testSubmittorNullEmptyStringParent() {
        final Submittor submittor = new Submittor(null, "", TEST_STRUNG);
        assertNull("Expected null parent", submittor.getParent());
    }

    /** */
    @Test
    public void testSubmittorNullEmptyStringString() {
        final Submittor submittor = new Submittor(null, "", TEST_STRUNG);
        assertEquals("Mismatched string",
                TEST_STRUNG, submittor.getString());
    }

    /** */
    @Test
    public void testSubmittorRootEmptyStringParent() {
        final Submittor submittor = new Submittor(root, "", TEST_STRUNG);
        assertEquals("Mismatched parent", root, submittor.getParent());
    }

    /** */
    @Test
    public void testSubmittorRootEmptyStringString() {
        final Submittor submittor = new Submittor(root, "", TEST_STRUNG);
        assertEquals("Mismatched string",
                TEST_STRUNG, submittor.getString());
    }

    /** */
    @Test
    public void testSubmittorNullStringStringParent() {
        final Submittor submittor =
                new Submittor(null, TEST_STRING, TEST_STRUNG);
        assertNull("Expected null parent", submittor.getParent());
    }

    /** */
    @Test
    public void testSubmittorNullStringStringString() {
        final Submittor submittor =
                new Submittor(null, TEST_STRING, TEST_STRUNG);
        assertEquals("Mismatched string",
                "string strung", submittor.getString());
    }

    /** */
    @Test
    public void testSubmittorRootStringStringParent() {
        final Submittor submittor =
                new Submittor(root, TEST_STRING, TEST_STRUNG);
        assertEquals("Mismatched parent", root, submittor.getParent());
    }

    /** */
    @Test
    public void testSubmittorRootStringStringString() {
        final Submittor submittor =
                new Submittor(root, TEST_STRING, TEST_STRUNG);
        assertEquals("Mismatched string",
                "string strung", submittor.getString());
    }
}
