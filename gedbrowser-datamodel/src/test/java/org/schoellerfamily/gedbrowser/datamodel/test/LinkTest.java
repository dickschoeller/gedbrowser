package org.schoellerfamily.gedbrowser.datamodel.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.Link;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;

/**
 * @author Dick Schoeller
 */
public final class LinkTest {
    /** */
    private static final String LINK_TEST = "Link";
    /** */
    private transient Person person1;

    /** */
    @Before
    public void setUp() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        person1 = builder.createPerson1();
        final Person person2 = builder.createPerson2();
        final Person person3 = builder.createPerson3();
        final Family family = builder.createFamily("F1");
        builder.addChildToFamily(family, person1);
        builder.addHusbandToFamily(family, person2);
        builder.addWifeToFamily(family, person3);
    }

    /** */
    @Test
    public void testInitLink() {
        final Link link = new Link(null);
        link.initLink(new ObjectId("F2"));
        assertMatch(link, "", "F2");
    }

    /** */
    @Test
    public void testSetParentReinitLink() {
        final Link link = new Link(null);
        link.initLink(new ObjectId("F2"));
        link.setParent(person1);
        link.initLink(new ObjectId("F1"));
        assertMatch(link, "I1", "F1");
    }

    /** */
    @Test
    public void testNullifyParentReinitLink() {
        final Link link = new Link(null);
        link.initLink(new ObjectId("F2"));
        link.setParent(person1);
        link.initLink(new ObjectId("F1"));
        link.setParent(null);
        link.initLink(new ObjectId(""));
        assertMatch(link, "", "");
    }

    /** */
    @Test
    public void testInitLinkWithObjectId() {
        final Link link = new Link(null);
        link.initLink(new ObjectId("@F2@"));
        assertMatch(link, "", "F2");
    }

    /** */
    @Test
    public void testInitLinkWithObjectIdSetParent() {
        final Link link = new Link(null);
        link.initLink(new ObjectId("@F2@"));
        link.setParent(person1);
        link.initLink(new ObjectId("@F1@"));
        assertMatch(link, "I1", "F1");
    }

    /** */
    @Test
    public void testSetGetFromStringEmpty() {
        final Link link = new Link(person1, LINK_TEST, new ObjectId("F1"));
        link.setFromString("");
        assertMatch(link, "", "F1");
    }

    /** */
    @Test
    public void testSetGetFromString() {
        final Link link = new Link(person1, LINK_TEST, new ObjectId("F1"));
        link.setFromString("I2");
        assertMatch(link, "I2", "F1");
    }

    /** */
    @Test
    public void testSetGetFromStringToNull() {
        final Link link = new Link(person1, LINK_TEST, new ObjectId("F1"));
        link.setFromString(null);
        assertMatch(link, "", "F1");
    }

    /** */
    @Test
    public void testSetGetToStringEmpty() {
        final Link link = new Link(person1, LINK_TEST, new ObjectId("F1"));
        link.setToString("");
        assertMatch(link, "I1", "");
    }

    /** */
    @Test
    public void testSetGetToString() {
        final Link link = new Link(person1, LINK_TEST, new ObjectId("F1"));
        link.setToString("F2");
        assertMatch(link, "I1", "F2");
    }

    /** */
    @Test
    public void testSetGetToStringToNull() {
        final Link link = new Link(person1, LINK_TEST, new ObjectId("F1"));
        link.setToString(null);
        assertMatch(link, "I1", "");
    }

    /**
     * @param link the link to check
     * @param expectedFromString the expected from string
     * @param expectedToString the expected to string
     */
    private void assertMatch(final Link link, final String expectedFromString,
            final String expectedToString) {
        assertEquals("From string mismatch", expectedFromString,
                link.getFromString());
        assertEquals("To string mismatch", expectedToString,
                link.getToString());
    }
}
