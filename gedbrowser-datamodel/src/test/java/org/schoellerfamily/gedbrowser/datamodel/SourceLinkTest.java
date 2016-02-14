package org.schoellerfamily.gedbrowser.datamodel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Dick Schoeller
 */
public final class SourceLinkTest {
    /** */
    private static final String SOURCE_TAG = "Source";
    /** */
    private static final String EXPECT_EMPTY = "Expected empty string";
    /** */
    private static final String WRONG_PARENT = "Wrong parent";
    /** */
    private static final String EXPECT_NULL = "Expected null";
    /** */
    private final transient Root root = new Root(null, "Root");
    /** */
    private final transient Person person = new Person(root,
            new ObjectId("I1"));
    /** */
    private final transient Source source = new Source(person,
            new ObjectId("S1"));

    /** */
    @Before
    public void setUp() {
        root.insert(null, person);
        root.insert(null, source);
    }

    /** */
    @Test
    public void testSourceLinkGedObject() {
        SourceLink sourceLink;
        sourceLink = new SourceLink(null);
        assertNull(EXPECT_NULL, sourceLink.getParent());
        assertEquals(EXPECT_EMPTY, "", sourceLink.getString());
        assertEquals(EXPECT_EMPTY, "", sourceLink.getToString());
        assertEquals(EXPECT_EMPTY, "", sourceLink.getFromString());

        sourceLink = new SourceLink(person);
        assertEquals(WRONG_PARENT, person, sourceLink.getParent());
        assertEquals(EXPECT_EMPTY, "", sourceLink.getString());
        assertEquals(EXPECT_EMPTY, "", sourceLink.getToString());
        assertEquals("I1", sourceLink.getFromString());
    }

    /** */
    @Test
    public void testSourceLinkGedObjectString() {
        SourceLink sourceLink;
        sourceLink = new SourceLink(null, null);
        assertNull(EXPECT_NULL, sourceLink.getParent());
        assertEquals(EXPECT_EMPTY, "", sourceLink.getString());
        assertEquals(EXPECT_EMPTY, "", sourceLink.getToString());
        assertEquals(EXPECT_EMPTY, "", sourceLink.getFromString());

        sourceLink = new SourceLink(person, null);
        assertEquals(WRONG_PARENT, person, sourceLink.getParent());
        assertEquals(EXPECT_EMPTY, "", sourceLink.getString());
        assertEquals(EXPECT_EMPTY, "", sourceLink.getToString());
        assertEquals("I1", sourceLink.getFromString());

        sourceLink = new SourceLink(null, "");
        assertNull(EXPECT_NULL, sourceLink.getParent());
        assertEquals(EXPECT_EMPTY, "", sourceLink.getString());
        assertEquals(EXPECT_EMPTY, "", sourceLink.getToString());
        assertEquals(EXPECT_EMPTY, "", sourceLink.getFromString());

        sourceLink = new SourceLink(person, "");
        assertEquals(WRONG_PARENT, person, sourceLink.getParent());
        assertEquals(EXPECT_EMPTY, "", sourceLink.getString());
        assertEquals(EXPECT_EMPTY, "", sourceLink.getToString());
        assertEquals("I1", sourceLink.getFromString());

        sourceLink = new SourceLink(null, SOURCE_TAG);
        assertNull(EXPECT_NULL, sourceLink.getParent());
        assertEquals(SOURCE_TAG, sourceLink.getString());
        assertEquals(EXPECT_EMPTY, "", sourceLink.getToString());
        assertEquals(EXPECT_EMPTY, "", sourceLink.getFromString());

        sourceLink = new SourceLink(person, SOURCE_TAG);
        assertEquals(WRONG_PARENT, person, sourceLink.getParent());
        assertEquals(SOURCE_TAG, sourceLink.getString());
        assertEquals(EXPECT_EMPTY, "", sourceLink.getToString());
        assertEquals("I1", sourceLink.getFromString());
    }

    /** */
    @Test
    public void testSourceLinkGedObjectStringNull() {
        SourceLink sourceLink;
        sourceLink = new SourceLink(null, null, null);
        assertNull(EXPECT_NULL, sourceLink.getParent());
        assertEquals(EXPECT_EMPTY, "", sourceLink.getString());
        assertEquals(EXPECT_EMPTY, "", sourceLink.getToString());
        assertEquals(EXPECT_EMPTY, "", sourceLink.getFromString());

        sourceLink = new SourceLink(person, null, null);
        assertEquals(WRONG_PARENT, person, sourceLink.getParent());
        assertEquals(EXPECT_EMPTY, "", sourceLink.getString());
        assertEquals(EXPECT_EMPTY, "", sourceLink.getToString());
        assertEquals("I1", sourceLink.getFromString());

        sourceLink = new SourceLink(null, "", null);
        assertNull(sourceLink.getParent());
        assertEquals(EXPECT_EMPTY, "", sourceLink.getString());
        assertEquals(EXPECT_EMPTY, "", sourceLink.getToString());
        assertEquals(EXPECT_EMPTY, "", sourceLink.getFromString());

        sourceLink = new SourceLink(person, "", null);
        assertEquals(WRONG_PARENT, person, sourceLink.getParent());
        assertEquals(EXPECT_EMPTY, "", sourceLink.getString());
        assertEquals(EXPECT_EMPTY, "", sourceLink.getToString());
        assertEquals("I1", sourceLink.getFromString());

        sourceLink = new SourceLink(null, SOURCE_TAG, null);
        assertNull(EXPECT_NULL, sourceLink.getParent());
        assertEquals(SOURCE_TAG, sourceLink.getString());
        assertEquals(EXPECT_EMPTY, "", sourceLink.getToString());
        assertEquals(EXPECT_EMPTY, "", sourceLink.getFromString());

        sourceLink = new SourceLink(person, SOURCE_TAG, null);
        assertEquals(WRONG_PARENT, person, sourceLink.getParent());
        assertEquals(SOURCE_TAG, sourceLink.getString());
        assertEquals(EXPECT_EMPTY, "", sourceLink.getToString());
        assertEquals("I1", sourceLink.getFromString());
    }

    /** */
    @Test
    public void testSourceLinkGedObjectStringBlank() {
        SourceLink sourceLink;
        sourceLink = new SourceLink(null, null, new ObjectId(""));
        assertNull(EXPECT_NULL, sourceLink.getParent());
        assertEquals(EXPECT_EMPTY, "", sourceLink.getString());
        assertEquals(EXPECT_EMPTY, "", sourceLink.getToString());
        assertEquals(EXPECT_EMPTY, "", sourceLink.getFromString());

        sourceLink = new SourceLink(person, null, new ObjectId(""));
        assertEquals(WRONG_PARENT, person, sourceLink.getParent());
        assertEquals(EXPECT_EMPTY, "", sourceLink.getString());
        assertEquals(EXPECT_EMPTY, "", sourceLink.getToString());
        assertEquals("I1", sourceLink.getFromString());

        sourceLink = new SourceLink(null, "", new ObjectId(""));
        assertNull(EXPECT_NULL, sourceLink.getParent());
        assertEquals(EXPECT_EMPTY, "", sourceLink.getString());
        assertEquals(EXPECT_EMPTY, "", sourceLink.getToString());
        assertEquals(EXPECT_EMPTY, "", sourceLink.getFromString());

        sourceLink = new SourceLink(person, "", new ObjectId(""));
        assertEquals(WRONG_PARENT, person, sourceLink.getParent());
        assertEquals(EXPECT_EMPTY, "", sourceLink.getString());
        assertEquals(EXPECT_EMPTY, "", sourceLink.getToString());
        assertEquals("I1", sourceLink.getFromString());

        sourceLink = new SourceLink(null, SOURCE_TAG, new ObjectId(""));
        assertNull(EXPECT_NULL, sourceLink.getParent());
        assertEquals(SOURCE_TAG, sourceLink.getString());
        assertEquals(EXPECT_EMPTY, "", sourceLink.getToString());
        assertEquals(EXPECT_EMPTY, "", sourceLink.getFromString());

        sourceLink = new SourceLink(person, SOURCE_TAG, new ObjectId(""));
        assertEquals(WRONG_PARENT, person, sourceLink.getParent());
        assertEquals(SOURCE_TAG, sourceLink.getString());
        assertEquals(EXPECT_EMPTY, "", sourceLink.getToString());
        assertEquals("I1", sourceLink.getFromString());
    }

    /** */
    @Test
    public void testSourceLinkGedObjectStringString() {
        SourceLink sourceLink;
        sourceLink = new SourceLink(null, null, new ObjectId("S1"));
        assertNull(EXPECT_NULL, sourceLink.getParent());
        assertEquals(EXPECT_EMPTY, "", sourceLink.getString());
        assertEquals("S1", sourceLink.getToString());
        assertEquals(EXPECT_EMPTY, "", sourceLink.getFromString());

        sourceLink = new SourceLink(person, null, new ObjectId("S1"));
        assertEquals(WRONG_PARENT, person, sourceLink.getParent());
        assertEquals(EXPECT_EMPTY, "", sourceLink.getString());
        assertEquals("S1", sourceLink.getToString());
        assertEquals("I1", sourceLink.getFromString());

        sourceLink = new SourceLink(null, "", new ObjectId("S1"));
        assertNull(EXPECT_NULL, sourceLink.getParent());
        assertEquals(EXPECT_EMPTY, "", sourceLink.getString());
        assertEquals("S1", sourceLink.getToString());
        assertEquals(EXPECT_EMPTY, "", sourceLink.getFromString());

        sourceLink = new SourceLink(person, "", new ObjectId("S1"));
        assertEquals(WRONG_PARENT, person, sourceLink.getParent());
        assertEquals(EXPECT_EMPTY, "", sourceLink.getString());
        assertEquals("S1", sourceLink.getToString());
        assertEquals("I1", sourceLink.getFromString());

        sourceLink = new SourceLink(null, SOURCE_TAG, new ObjectId("S1"));
        assertNull(EXPECT_NULL, sourceLink.getParent());
        assertEquals(SOURCE_TAG, sourceLink.getString());
        assertEquals("S1", sourceLink.getToString());
        assertEquals(EXPECT_EMPTY, "", sourceLink.getFromString());

        sourceLink = new SourceLink(person, SOURCE_TAG, new ObjectId("S1"));
        assertEquals(WRONG_PARENT, person, sourceLink.getParent());
        assertEquals(SOURCE_TAG, sourceLink.getString());
        assertEquals("S1", sourceLink.getToString());
        assertEquals("I1", sourceLink.getFromString());
    }
}
