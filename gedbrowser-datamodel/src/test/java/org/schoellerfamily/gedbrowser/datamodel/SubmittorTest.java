package org.schoellerfamily.gedbrowser.datamodel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

/**
 * @author Dick Schoeller
 */
public final class SubmittorTest {
    /** */
    private static final String TEST_STRUNG = "strung";
    /** */
    private static final String TEST_STRING = "string";
    /** */
    private final transient Root root = new Root(null, "Root");

    /** */
    @Test
    public void testSubmittorNames() {
        Submittor submittor = new Submittor(root, "S1");
        final Name name = new Name(submittor, "Richard John/Schoeller/");
        submittor.addAttribute(name);
        assertEquals("Richard John/Schoeller/", submittor.getName().toString());
        assertEquals("Schoeller", submittor.getSurname());
        assertEquals("Schoeller, Richard John", submittor.getIndexName());

        submittor = new Submittor(root, "S2");
        assertEquals("", submittor.getName().toString());
        assertEquals("?", submittor.getSurname());
        assertEquals("?, ?", submittor.getIndexName());
    }

    /** */
    @Test
    public void testSubmittorGedObject() {
        Submittor submittor;
        submittor = new Submittor(null);
        assertNull(submittor.getParent());
        assertEquals("", submittor.getString());
        submittor = new Submittor(root);
        assertEquals(root, submittor.getParent());
        assertEquals("", submittor.getString());
    }

    /** */
    @Test
    public void testSubmittorGedObjectString() {
        Submittor submittor;
        submittor = new Submittor(null, null);
        assertNull(submittor.getParent());
        assertEquals("", submittor.getString());
        submittor = new Submittor(root, null);
        assertEquals(root, submittor.getParent());
        assertEquals("", submittor.getString());

        submittor = new Submittor(null, "");
        assertNull(submittor.getParent());
        assertEquals("", submittor.getString());
        submittor = new Submittor(root, "");
        assertEquals(root, submittor.getParent());
        assertEquals("", submittor.getString());

        submittor = new Submittor(null, TEST_STRING);
        assertNull(submittor.getParent());
        assertEquals(TEST_STRING, submittor.getString());
        submittor = new Submittor(root, TEST_STRING);
        assertEquals(root, submittor.getParent());
        assertEquals(TEST_STRING, submittor.getString());
    }

    /** */
    @Test
    public void testSubmittorGedObjectStringString() {
        Submittor submittor;
        submittor = new Submittor(null, null, null);
        assertNull(submittor.getParent());
        assertEquals("", submittor.getString());
        submittor = new Submittor(root, null, null);
        assertEquals(root, submittor.getParent());
        assertEquals("", submittor.getString());

        submittor = new Submittor(null, "", null);
        assertNull(submittor.getParent());
        assertEquals("", submittor.getString());
        submittor = new Submittor(root, "", null);
        assertEquals(root, submittor.getParent());
        assertEquals("", submittor.getString());

        submittor = new Submittor(null, TEST_STRING, null);
        assertNull(submittor.getParent());
        assertEquals(TEST_STRING, submittor.getString());
        submittor = new Submittor(root, TEST_STRING, null);
        assertEquals(root, submittor.getParent());
        assertEquals(TEST_STRING, submittor.getString());

        submittor = new Submittor(null, null, "");
        assertNull(submittor.getParent());
        assertEquals("", submittor.getString());
        submittor = new Submittor(root, null, "");
        assertEquals(root, submittor.getParent());
        assertEquals("", submittor.getString());

        submittor = new Submittor(null, "", "");
        assertNull(submittor.getParent());
        assertEquals("", submittor.getString());
        submittor = new Submittor(root, "", "");
        assertEquals(root, submittor.getParent());
        assertEquals("", submittor.getString());

        submittor = new Submittor(null, TEST_STRING, "");
        assertNull(submittor.getParent());
        assertEquals(TEST_STRING, submittor.getString());
        submittor = new Submittor(root, TEST_STRING, "");
        assertEquals(root, submittor.getParent());
        assertEquals(TEST_STRING, submittor.getString());

        submittor = new Submittor(null, null, TEST_STRUNG);
        assertNull(submittor.getParent());
        assertEquals(TEST_STRUNG, submittor.getString());
        submittor = new Submittor(root, null, TEST_STRUNG);
        assertEquals(root, submittor.getParent());
        assertEquals(TEST_STRUNG, submittor.getString());

        submittor = new Submittor(null, "", TEST_STRUNG);
        assertNull(submittor.getParent());
        assertEquals(TEST_STRUNG, submittor.getString());
        submittor = new Submittor(root, "", TEST_STRUNG);
        assertEquals(root, submittor.getParent());
        assertEquals(TEST_STRUNG, submittor.getString());

        submittor = new Submittor(null, TEST_STRING, TEST_STRUNG);
        assertNull(submittor.getParent());
        assertEquals("string strung", submittor.getString());
        submittor = new Submittor(root, TEST_STRING, TEST_STRUNG);
        assertEquals(root, submittor.getParent());
        assertEquals("string strung", submittor.getString());
    }
}
