package org.schoellerfamily.gedbrowser.datamodel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Dick Schoeller
 */
public final class SubmittorLinkTest {
    /** */
    private static final String WRONG_PARENT = "Wrong parent";
    /** */
    private static final String EXPECT_SUBMITTOR = "Expected submittor tag";
    /** */
    private static final String SUBMITTOR_TAG = "Submittor";
    /** */
    private static final String HEAD_TAG = "Head";
    /** */
    private static final String EXPECT_EMPTY = "Expected empty string";
    /** */
    private static final String EXPECT_NULL = "Expected null parent";
    /** */
    private final transient Root root = new Root(null, "Root");
    /** */
    private final transient Head head = new Head(root, HEAD_TAG);

    /** */
    @Before
    public void setUp() {
        root.insert(null, head);
    }

    /** */
    @Test
    public void testSubmittorLinkGedObject() {
        SubmittorLink submittorLink;
        submittorLink = new SubmittorLink(null);
        assertNull(EXPECT_NULL, submittorLink.getParent());
        assertEquals(EXPECT_EMPTY, "", submittorLink.getString());
        assertEquals(EXPECT_EMPTY, "", submittorLink.getToString());
        assertEquals(EXPECT_EMPTY, "", submittorLink.getFromString());

        submittorLink = new SubmittorLink(head);
        assertEquals(WRONG_PARENT, head, submittorLink.getParent());
        assertEquals(EXPECT_EMPTY, "", submittorLink.getString());
        assertEquals(EXPECT_EMPTY, "", submittorLink.getToString());
        assertEquals(HEAD_TAG, submittorLink.getFromString());
    }

    /** */
    @Test
    public void testSubmittorLinkGedObjectString() {
        SubmittorLink submittorLink;
        submittorLink = new SubmittorLink(null, null);
        assertNull(EXPECT_NULL, submittorLink.getParent());
        assertEquals(EXPECT_EMPTY, "", submittorLink.getString());
        assertEquals(EXPECT_EMPTY, "", submittorLink.getToString());
        assertEquals(EXPECT_EMPTY, "", submittorLink.getFromString());

        submittorLink = new SubmittorLink(head, null);
        assertEquals(WRONG_PARENT, head, submittorLink.getParent());
        assertEquals(EXPECT_EMPTY, "", submittorLink.getString());
        assertEquals(EXPECT_EMPTY, "", submittorLink.getToString());
        assertEquals(HEAD_TAG, submittorLink.getFromString());

        submittorLink = new SubmittorLink(null, "");
        assertNull(EXPECT_NULL, submittorLink.getParent());
        assertEquals(EXPECT_EMPTY, "", submittorLink.getString());
        assertEquals(EXPECT_EMPTY, "", submittorLink.getToString());
        assertEquals(EXPECT_EMPTY, "", submittorLink.getFromString());

        submittorLink = new SubmittorLink(head, "");
        assertEquals(WRONG_PARENT, head, submittorLink.getParent());
        assertEquals(EXPECT_EMPTY, "", submittorLink.getString());
        assertEquals(EXPECT_EMPTY, "", submittorLink.getToString());
        assertEquals(HEAD_TAG, submittorLink.getFromString());

        submittorLink = new SubmittorLink(null, SUBMITTOR_TAG);
        assertNull(EXPECT_NULL, submittorLink.getParent());
        assertEquals(EXPECT_SUBMITTOR, SUBMITTOR_TAG,
                submittorLink.getString());
        assertEquals(EXPECT_EMPTY, "", submittorLink.getToString());
        assertEquals(EXPECT_EMPTY, "", submittorLink.getFromString());

        submittorLink = new SubmittorLink(head, SUBMITTOR_TAG);
        assertEquals(WRONG_PARENT, head, submittorLink.getParent());
        assertEquals(EXPECT_SUBMITTOR, SUBMITTOR_TAG,
                submittorLink.getString());
        assertEquals(EXPECT_EMPTY, "", submittorLink.getToString());
        assertEquals(HEAD_TAG, submittorLink.getFromString());
    }

    /** */
    @Test
    public void testSubmittorLinkGedObjectStringNull() {
        SubmittorLink submittorLink;
        submittorLink = new SubmittorLink(null, null, null);
        assertNull(EXPECT_NULL, submittorLink.getParent());
        assertEquals(EXPECT_EMPTY, "", submittorLink.getString());
        assertEquals(EXPECT_EMPTY, "", submittorLink.getToString());
        assertEquals(EXPECT_EMPTY, "", submittorLink.getFromString());

        submittorLink = new SubmittorLink(head, null, null);
        assertEquals(WRONG_PARENT, head, submittorLink.getParent());
        assertEquals(EXPECT_EMPTY, "", submittorLink.getString());
        assertEquals(EXPECT_EMPTY, "", submittorLink.getToString());
        assertEquals(HEAD_TAG, submittorLink.getFromString());

        submittorLink = new SubmittorLink(null, "", null);
        assertNull(EXPECT_NULL, submittorLink.getParent());
        assertEquals(EXPECT_EMPTY, "", submittorLink.getString());
        assertEquals(EXPECT_EMPTY, "", submittorLink.getToString());
        assertEquals(EXPECT_EMPTY, "", submittorLink.getFromString());

        submittorLink = new SubmittorLink(head, "", null);
        assertEquals(WRONG_PARENT, head, submittorLink.getParent());
        assertEquals(EXPECT_EMPTY, "", submittorLink.getString());
        assertEquals(EXPECT_EMPTY, "", submittorLink.getToString());
        assertEquals(HEAD_TAG, submittorLink.getFromString());

        submittorLink = new SubmittorLink(null, SUBMITTOR_TAG, null);
        assertNull(EXPECT_NULL, submittorLink.getParent());
        assertEquals(EXPECT_SUBMITTOR, SUBMITTOR_TAG,
                submittorLink.getString());
        assertEquals(EXPECT_EMPTY, "", submittorLink.getToString());
        assertEquals(EXPECT_EMPTY, "", submittorLink.getFromString());

        submittorLink = new SubmittorLink(head, SUBMITTOR_TAG, null);
        assertEquals(head, submittorLink.getParent());
        assertEquals(EXPECT_SUBMITTOR, SUBMITTOR_TAG,
                submittorLink.getString());
        assertEquals(EXPECT_EMPTY, "", submittorLink.getToString());
        assertEquals(HEAD_TAG, submittorLink.getFromString());
    }

    /** */
    @Test
    public void testSubmittorLinkGedObjectStringBlank() {
        SubmittorLink submittorLink;
        submittorLink = new SubmittorLink(null, null, new ObjectId(""));
        assertNull(EXPECT_NULL, submittorLink.getParent());
        assertEquals(EXPECT_EMPTY, "", submittorLink.getString());
        assertEquals(EXPECT_EMPTY, "", submittorLink.getToString());
        assertEquals(EXPECT_EMPTY, "", submittorLink.getFromString());

        submittorLink = new SubmittorLink(head, null, new ObjectId(""));
        assertEquals(WRONG_PARENT, head, submittorLink.getParent());
        assertEquals(EXPECT_EMPTY, "", submittorLink.getString());
        assertEquals(EXPECT_EMPTY, "", submittorLink.getToString());
        assertEquals(HEAD_TAG, submittorLink.getFromString());

        submittorLink = new SubmittorLink(null, "", new ObjectId(""));
        assertNull(EXPECT_NULL, submittorLink.getParent());
        assertEquals(EXPECT_EMPTY, "", submittorLink.getString());
        assertEquals(EXPECT_EMPTY, "", submittorLink.getToString());
        assertEquals(EXPECT_EMPTY, "", submittorLink.getFromString());

        submittorLink = new SubmittorLink(head, "", new ObjectId(""));
        assertEquals(WRONG_PARENT, head, submittorLink.getParent());
        assertEquals(EXPECT_EMPTY, "", submittorLink.getString());
        assertEquals(EXPECT_EMPTY, "", submittorLink.getToString());
        assertEquals(HEAD_TAG, submittorLink.getFromString());

        submittorLink = new SubmittorLink(null, SUBMITTOR_TAG,
                new ObjectId(""));
        assertNull(EXPECT_NULL, submittorLink.getParent());
        assertEquals(EXPECT_SUBMITTOR, SUBMITTOR_TAG,
                submittorLink.getString());
        assertEquals(EXPECT_EMPTY, "", submittorLink.getToString());
        assertEquals(EXPECT_EMPTY, "", submittorLink.getFromString());

        submittorLink = new SubmittorLink(head, SUBMITTOR_TAG,
                new ObjectId(""));
        assertEquals(WRONG_PARENT, head, submittorLink.getParent());
        assertEquals(EXPECT_SUBMITTOR, SUBMITTOR_TAG,
                submittorLink.getString());
        assertEquals(EXPECT_EMPTY, "", submittorLink.getToString());
        assertEquals(HEAD_TAG, submittorLink.getFromString());
    }

    /** */
    @Test
    public void testSubmittorLinkGedObjectStringString() {
        SubmittorLink submittorLink;
        submittorLink = new SubmittorLink(null, null, new ObjectId("S1"));
        assertNull(EXPECT_NULL, submittorLink.getParent());
        assertEquals(EXPECT_EMPTY, "", submittorLink.getString());
        assertEquals("S1", submittorLink.getToString());
        assertEquals(EXPECT_EMPTY, "", submittorLink.getFromString());

        submittorLink = new SubmittorLink(head, null, new ObjectId("S1"));
        assertEquals(head, submittorLink.getParent());
        assertEquals(EXPECT_EMPTY, "", submittorLink.getString());
        assertEquals("S1", submittorLink.getToString());
        assertEquals(HEAD_TAG, submittorLink.getFromString());

        submittorLink = new SubmittorLink(null, "", new ObjectId("S1"));
        assertNull(EXPECT_NULL, submittorLink.getParent());
        assertEquals(EXPECT_EMPTY, "", submittorLink.getString());
        assertEquals("S1", submittorLink.getToString());
        assertEquals(EXPECT_EMPTY, "", submittorLink.getFromString());

        submittorLink = new SubmittorLink(head, "", new ObjectId("S1"));
        assertEquals(head, submittorLink.getParent());
        assertEquals(EXPECT_EMPTY, "", submittorLink.getString());
        assertEquals("S1", submittorLink.getToString());
        assertEquals(HEAD_TAG, submittorLink.getFromString());

        submittorLink = new SubmittorLink(null, SUBMITTOR_TAG,
                new ObjectId("S1"));
        assertNull(EXPECT_NULL, submittorLink.getParent());
        assertEquals(EXPECT_SUBMITTOR, SUBMITTOR_TAG,
                submittorLink.getString());
        assertEquals("S1", submittorLink.getToString());
        assertEquals(EXPECT_EMPTY, "", submittorLink.getFromString());

        submittorLink = new SubmittorLink(head, SUBMITTOR_TAG,
                new ObjectId("S1"));
        assertEquals(head, submittorLink.getParent());
        assertEquals(EXPECT_SUBMITTOR, SUBMITTOR_TAG,
                submittorLink.getString());
        assertEquals("S1", submittorLink.getToString());
        assertEquals(HEAD_TAG, submittorLink.getFromString());
    }
}
