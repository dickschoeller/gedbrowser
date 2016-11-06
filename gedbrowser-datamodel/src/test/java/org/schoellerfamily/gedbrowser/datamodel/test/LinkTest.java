package org.schoellerfamily.gedbrowser.datamodel.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;
import org.schoellerfamily.gedbrowser.datamodel.Child;
import org.schoellerfamily.gedbrowser.datamodel.FamC;
import org.schoellerfamily.gedbrowser.datamodel.FamS;
import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.Husband;
import org.schoellerfamily.gedbrowser.datamodel.Link;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.Wife;

/**
 * @author Dick Schoeller
 */
public final class LinkTest {
    /** */
    private static final String PASSED_PARENT =
            "Parent should be the passed in object";
    /** */
    private static final String PARENT_ID =
            "FROM string should be the parent ID string";
    /** */
    private static final String EMPTY_FROM =
            "FROM string should be empty";
    /** */
    private static final String EMPTY_TO = "TO string should be empty";
    /** */
    private static final String EXPECT_EMPTY = "String should be empty";
    /** */
    private static final String NULL_PARENT = "Parent should be null";

    /** */
    private static final String LUNK_TEST = "Lunk";
    /** */
    private static final String LINK_TEST = "Link";
    /** */
    private final transient Root root = new Root(null, "Root");
    /** */
    private final transient Person person1 = new Person(root,
            new ObjectId("I1"));
    /** */
    private final transient Person person2 = new Person(root,
            new ObjectId("I2"));
    /** */
    private final transient Person person3 = new Person(root,
            new ObjectId("I3"));
    /** */
    private final transient Family family = new Family(root,
            new ObjectId("F1"));
    /** */
    private final transient FamC famC = new FamC(person1, "FAMC",
            new ObjectId("F1"));
    /** */
    private final transient FamS famS2 = new FamS(person2, "FAMS",
            new ObjectId("F1"));
    /** */
    private final transient FamS famS3 = new FamS(person3, "FAMS",
            new ObjectId("F1"));
    /** */
    private final transient Child child = new Child(family, "Child",
            new ObjectId("I1"));
    /** */
    private final transient Husband husband = new Husband(family, "Husband",
            new ObjectId("I2"));
    /** */
    private final transient Wife wife = new Wife(family, "Wife",
            new ObjectId("I3"));

    /** */
    @Before
    public void setUp() {
        root.insert(null, person1);
        root.insert(null, person2);
        root.insert(null, person3);
        root.insert(null, family);

        family.insert(child);
        family.insert(husband);
        family.insert(wife);

        person1.insert(famC);
        person2.insert(famS2);
        person3.insert(famS3);
    }

    /** */
    @Test
    public void testLinkGedObject() {
        Link link1;
        link1 = new Link(null);
        assertNull(NULL_PARENT, link1.getParent());
        assertEquals(EXPECT_EMPTY, "", link1.getString());
        assertEquals(EMPTY_TO, "", link1.getToString());
        assertEquals(EMPTY_FROM, "", link1.getFromString());

        link1 = new Link(person1);
        assertEquals(PASSED_PARENT, person1, link1.getParent());
        assertEquals(EXPECT_EMPTY, "", link1.getString());
        assertEquals(EMPTY_TO, "", link1.getToString());
        assertEquals(PARENT_ID, "I1", link1.getFromString());
    }

    /** */
    @Test
    public void testLinkGedObjectString() {
        Link link1;
        link1 = new Link(null, null);
        assertNull(NULL_PARENT, link1.getParent());
        assertEquals(EXPECT_EMPTY, "", link1.getString());
        assertEquals(EMPTY_TO, "", link1.getToString());
        assertEquals(EMPTY_FROM, "", link1.getFromString());

        link1 = new Link(person1, null);
        assertEquals(PASSED_PARENT, person1, link1.getParent());
        assertEquals(EXPECT_EMPTY, "", link1.getString());
        assertEquals(EMPTY_TO, "", link1.getToString());
        assertEquals(PARENT_ID, "I1", link1.getFromString());

        link1 = new Link(null, "");
        assertNull(NULL_PARENT, link1.getParent());
        assertEquals(EXPECT_EMPTY, "", link1.getString());
        assertEquals(EMPTY_TO, "", link1.getToString());
        assertEquals(EMPTY_FROM, "", link1.getFromString());

        link1 = new Link(person1, "");
        assertEquals(PASSED_PARENT, person1, link1.getParent());
        assertEquals(EXPECT_EMPTY, "", link1.getString());
        assertEquals(EMPTY_TO, "", link1.getToString());
        assertEquals(PARENT_ID, "I1", link1.getFromString());

        link1 = new Link(null, LINK_TEST);
        assertNull(link1.getParent());
        assertEquals(LINK_TEST, link1.getString());
        assertEquals(EMPTY_TO, "", link1.getToString());
        assertEquals(EMPTY_FROM, "", link1.getFromString());

        link1 = new Link(person1, LUNK_TEST);
        assertEquals(person1, link1.getParent());
        assertEquals(LUNK_TEST, link1.getString());
        assertEquals(EMPTY_TO, "", link1.getToString());
        assertEquals(PARENT_ID, "I1", link1.getFromString());
    }

    /** */
    @Test
    public void testLinkGedObjectStringNul() {
        Link link1;
        link1 = new Link(null, null, null);
        assertNull(NULL_PARENT, link1.getParent());
        assertEquals(EXPECT_EMPTY, "", link1.getString());
        assertEquals(EMPTY_TO, "", link1.getToString());
        assertEquals(EMPTY_FROM, "", link1.getFromString());

        link1 = new Link(person1, null, null);
        assertEquals(PASSED_PARENT, person1, link1.getParent());
        assertEquals(EXPECT_EMPTY, "", link1.getString());
        assertEquals(EMPTY_TO, "", link1.getToString());
        assertEquals(PARENT_ID, "I1", link1.getFromString());

        link1 = new Link(null, "", null);
        assertNull(NULL_PARENT, link1.getParent());
        assertEquals(EXPECT_EMPTY, "", link1.getString());
        assertEquals(EMPTY_TO, "", link1.getToString());
        assertEquals(EMPTY_FROM, "", link1.getFromString());

        link1 = new Link(person1, "", null);
        assertEquals(PASSED_PARENT, person1, link1.getParent());
        assertEquals(EXPECT_EMPTY, "", link1.getString());
        assertEquals(EMPTY_TO, "", link1.getToString());
        assertEquals(PARENT_ID, "I1", link1.getFromString());

        link1 = new Link(null, LINK_TEST, null);
        assertNull(link1.getParent());
        assertEquals(LINK_TEST, link1.getString());
        assertEquals("", link1.getToString());
        assertEquals("", link1.getFromString());

        link1 = new Link(person1, LUNK_TEST, null);
        assertEquals(person1, link1.getParent());
        assertEquals(LUNK_TEST, link1.getString());
        assertEquals("", link1.getToString());
        assertEquals("I1", link1.getFromString());
    }

    /** */
    @Test
    public void testLinkGedObjectStringBlank() {
        Link link1;
        link1 = new Link(null, null, new ObjectId(""));
        assertNull(link1.getParent());
        assertEquals("", link1.getString());
        assertEquals("", link1.getToString());
        assertEquals("", link1.getFromString());

        link1 = new Link(person1, null, new ObjectId(""));
        assertEquals(person1, link1.getParent());
        assertEquals("", link1.getString());
        assertEquals("", link1.getToString());
        assertEquals("I1", link1.getFromString());

        link1 = new Link(null, "", new ObjectId(""));
        assertNull(link1.getParent());
        assertEquals("", link1.getString());
        assertEquals("", link1.getToString());
        assertEquals("", link1.getFromString());

        link1 = new Link(person1, "", new ObjectId(""));
        assertEquals(person1, link1.getParent());
        assertEquals("", link1.getString());
        assertEquals("", link1.getToString());
        assertEquals("I1", link1.getFromString());

        link1 = new Link(null, LINK_TEST, new ObjectId(""));
        assertNull(link1.getParent());
        assertEquals(LINK_TEST, link1.getString());
        assertEquals("", link1.getToString());
        assertEquals("", link1.getFromString());

        link1 = new Link(person1, LUNK_TEST, new ObjectId(""));
        assertEquals(person1, link1.getParent());
        assertEquals(LUNK_TEST, link1.getString());
        assertEquals("", link1.getToString());
        assertEquals("I1", link1.getFromString());
    }

    /** */
    @Test
    public void testLinkGedObjectStringString() {
        Link link1;
        link1 = new Link(null, null, new ObjectId("I2"));
        assertNull(link1.getParent());
        assertEquals("", link1.getString());
        assertEquals("I2", link1.getToString());
        assertEquals("", link1.getFromString());

        link1 = new Link(person1, null, new ObjectId("I3"));
        assertEquals(person1, link1.getParent());
        assertEquals("", link1.getString());
        assertEquals("I3", link1.getToString());
        assertEquals("I1", link1.getFromString());

        link1 = new Link(null, "", new ObjectId("F1"));
        assertNull(link1.getParent());
        assertEquals("", link1.getString());
        assertEquals("F1", link1.getToString());
        assertEquals("", link1.getFromString());

        link1 = new Link(person1, "", new ObjectId("I2"));
        assertEquals(person1, link1.getParent());
        assertEquals("", link1.getString());
        assertEquals("I2", link1.getToString());
        assertEquals("I1", link1.getFromString());

        link1 = new Link(null, LINK_TEST, new ObjectId("I3"));
        assertNull(link1.getParent());
        assertEquals(LINK_TEST, link1.getString());
        assertEquals("I3", link1.getToString());
        assertEquals("", link1.getFromString());

        link1 = new Link(person1, LUNK_TEST, new ObjectId("F1"));
        assertEquals(person1, link1.getParent());
        assertEquals(LUNK_TEST, link1.getString());
        assertEquals("F1", link1.getToString());
        assertEquals("I1", link1.getFromString());
    }

    /** */
    @Test
    public void testInitLink() {
        final Link link = new Link(null);
        assertEquals("", link.getFromString());
        assertEquals("", link.getToString());

        link.initLink(new ObjectId("F2"));
        assertEquals("", link.getFromString());
        assertEquals("F2", link.getToString());

        link.setParent(person1);
        link.initLink(new ObjectId("F1"));
        assertEquals("I1", link.getFromString());
        assertEquals("F1", link.getToString());

        link.setParent(null);
        link.initLink(new ObjectId(""));
        assertEquals("", link.getFromString());
        assertEquals("", link.getToString());

        link.initLink(new ObjectId("@F2@"));
        assertEquals("", link.getFromString());
        assertEquals("F2", link.getToString());

        link.setParent(person1);
        link.initLink(new ObjectId("@F1@"));
        assertEquals("I1", link.getFromString());
        assertEquals("F1", link.getToString());
    }

    /** */
    @Test
    public void testSetGetFromString() {
        final Link link = new Link(person1, LINK_TEST, new ObjectId("F1"));
        assertEquals("I1", link.getFromString());
        assertEquals("F1", link.getToString());

        link.setFromString("");
        assertEquals("", link.getFromString());
        assertEquals("F1", link.getToString());

        link.setFromString("I2");
        assertEquals("I2", link.getFromString());
        assertEquals("F1", link.getToString());

        link.setFromString(null);
        assertEquals("", link.getFromString());
        assertEquals("F1", link.getToString());
    }

    /** */
    @Test
    public void testSetGetToString() {
        final Link link = new Link(person1, LINK_TEST, new ObjectId("F1"));
        assertEquals("I1", link.getFromString());
        assertEquals("F1", link.getToString());

        link.setToString("");
        assertEquals("I1", link.getFromString());
        assertEquals("", link.getToString());

        link.setToString("F2");
        assertEquals("I1", link.getFromString());
        assertEquals("F2", link.getToString());

        link.setToString(null);
        assertEquals("I1", link.getFromString());
        assertEquals("", link.getToString());
    }
}
