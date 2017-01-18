package org.schoellerfamily.gedbrowser.datamodel.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.schoellerfamily.gedbrowser.datamodel.Attribute;
import org.schoellerfamily.gedbrowser.datamodel.Child;
import org.schoellerfamily.gedbrowser.datamodel.Date;
import org.schoellerfamily.gedbrowser.datamodel.FamC;
import org.schoellerfamily.gedbrowser.datamodel.FamS;
import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.Husband;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.Wife;

/**
 * @author Dick Schoeller
 */
public final class AttributeTest {
    /** */
    private static final String DUMMY = "Dummy";
    /** */
    private static final String TEST_STRUNG = "strung";
    /** */
    private static final String TEST_STRING = "string";
    /** */
    private static final String HUNDRED_DAY = "31 July 2090";
    /** */
    private static final String POTTER_DAY = "31 July 1990";
    /** */
    private static final String SHOULD_BE_EMPTY = "Should be empty";
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
        root.insert(person1);
        root.insert(person2);
        root.insert(person3);
        root.insert(family);

        family.insert(child);
        family.insert(husband);
        family.insert(wife);

        person1.insert(famC);
        person2.insert(famS2);
        person3.insert(famS3);
    }

    /** */
    @Test
    public void testString() {
        final Attribute job = new Attribute(person1, "Job", "Worked at SAP");
        assertEquals("In should match out", "Worked at SAP", job.getTail());
    }

    /** */
    @Test
    public void testAppendString() {
        final Attribute job = new Attribute(person1, "Job", "Worked at SAP");
        job.appendString(" for 5 years");
        assertEquals("Should have concatenated the segments",
                "Worked at SAP for 5 years", job.getTail());
    }

    /** */
    @Test
    public void testNoBirthNoDate() {
        final Attribute dummy = new Attribute(person1, DUMMY);
        final Date dummyDate = new Date(dummy, POTTER_DAY);
        dummy.insert(dummyDate);
        assertEquals("Expected empty string", "", dummy.getBirthDate());
    }

    /** */
    @Test
    public void testGetBirthDateNotInserted() {
        final Attribute dummy = new Attribute(person1, DUMMY);
        final Date dummyDate = new Date(dummy, POTTER_DAY);
        dummy.insert(dummyDate);
        final Attribute birth = new Attribute(person1, "Birth");
        new Date(birth, POTTER_DAY);
        assertEquals("Expected empty string", "", birth.getBirthDate());
    }

    /** */
    @Test
    public void testGetBirthDate() {
        final Attribute dummy = new Attribute(person1, DUMMY);
        final Date dummyDate = new Date(dummy, POTTER_DAY);
        dummy.insert(dummyDate);
        final Attribute birth = new Attribute(person1, "Birth");
        final Date date = new Date(birth, POTTER_DAY);
        birth.insert(date);
        assertEquals("Date's filled in. Should match", POTTER_DAY,
                birth.getBirthDate());
    }

    /** */
    @Test
    public void testNoDeathNoDate() {
        final Attribute dummy = new Attribute(person1, DUMMY);
        final Date dummyDate = new Date(dummy, POTTER_DAY);
        dummy.insert(dummyDate);
        assertEquals("Expected empty string", "", dummy.getDeathDate());
    }

    /** */
    @Test
    public void testGetDeathDateNotInserted() {
        final Attribute dummy = new Attribute(person1, DUMMY);
        final Date dummyDate = new Date(dummy, POTTER_DAY);
        dummy.insert(dummyDate);
        final Attribute death = new Attribute(person1, "Death");
        assertEquals("Expected empty string", "", death.getDeathDate());
    }

    /** */
    @Test
    public void testGetDeathDate() {
        final Attribute dummy = new Attribute(person1, DUMMY);
        final Date dummyDate = new Date(dummy, POTTER_DAY);
        dummy.insert(dummyDate);
        final Attribute death = new Attribute(person1, "Death");
        final Date date = new Date(death, HUNDRED_DAY);
        death.insert(date);
        assertEquals("Date's filled in. Should match",
                HUNDRED_DAY, death.getDeathDate());
    }

    /** */
    @Test
    public void testGetDate() {
        final Attribute dummy = new Attribute(person1, DUMMY);
        final Date dummyDate = new Date(dummy, POTTER_DAY);
        // TODO this should become unnecessary if I can further restrict the
        // children of an attribute.
        dummy.insert(new Person());
        dummy.insert(dummyDate);
        assertEquals(POTTER_DAY, dummy.getDate());

        final Attribute dummy1 = new Attribute(person1, DUMMY);
        final Date dummyDate1 = new Date(dummy, null);
        dummy1.insert(dummyDate1);
        assertEquals("", dummy1.getDate());

        final Attribute death = new Attribute(person1, "Death");
        assertEquals("", death.getDate());

        final Date date = new Date(death, HUNDRED_DAY);
        death.insert(date);
        assertEquals(HUNDRED_DAY, death.getDate());
    }

    /** */
    @Test
    public void testAttributeGedObject() {
        Attribute attribute;
        attribute = new Attribute(null);
        assertEquals(null, attribute.getParent());
        assertEquals(SHOULD_BE_EMPTY, "", attribute.getString());
        assertEquals(SHOULD_BE_EMPTY, "", attribute.getTail());

        attribute = new Attribute(person1);
        assertEquals(person1, attribute.getParent());
        assertEquals(SHOULD_BE_EMPTY, "", attribute.getString());
        assertEquals(SHOULD_BE_EMPTY, "", attribute.getTail());
    }

    /** */
    @Test
    public void testAttributeGedObjectString() {
        Attribute attribute;
        attribute = new Attribute(null, null);
        assertEquals(null, attribute.getParent());
        assertEquals(SHOULD_BE_EMPTY, "", attribute.getString());
        assertEquals(SHOULD_BE_EMPTY, "", attribute.getTail());

        attribute = new Attribute(person1, null);
        assertEquals(person1, attribute.getParent());
        assertEquals(SHOULD_BE_EMPTY, "", attribute.getString());
        assertEquals(SHOULD_BE_EMPTY, "", attribute.getTail());

        attribute = new Attribute(null, "");
        assertEquals(null, attribute.getParent());
        assertEquals(SHOULD_BE_EMPTY, "", attribute.getString());
        assertEquals(SHOULD_BE_EMPTY, "", attribute.getTail());

        attribute = new Attribute(person1, "");
        assertEquals(person1, attribute.getParent());
        assertEquals(SHOULD_BE_EMPTY, "", attribute.getString());
        assertEquals("", attribute.getTail());

        attribute = new Attribute(null, TEST_STRING);
        assertEquals(null, attribute.getParent());
        assertEquals(TEST_STRING, attribute.getString());
        assertEquals(SHOULD_BE_EMPTY, "", attribute.getTail());

        attribute = new Attribute(person1, TEST_STRING);
        assertEquals(person1, attribute.getParent());
        assertEquals(TEST_STRING, attribute.getString());
        assertEquals(SHOULD_BE_EMPTY, "", attribute.getTail());
    }

    /** */
    @Test
    public void testAttributeGedObjectStringString() {
        Attribute attribute;
        attribute = new Attribute(null, null, null);
        assertEquals(null, attribute.getParent());
        assertEquals(SHOULD_BE_EMPTY, "", attribute.getString());
        assertEquals(SHOULD_BE_EMPTY, "", attribute.getTail());

        attribute = new Attribute(person1, null, null);
        assertEquals(person1, attribute.getParent());
        assertEquals(SHOULD_BE_EMPTY, "", attribute.getString());
        assertEquals(SHOULD_BE_EMPTY, "", attribute.getTail());

        attribute = new Attribute(null, "", null);
        assertEquals(null, attribute.getParent());
        assertEquals(SHOULD_BE_EMPTY, "", attribute.getString());
        assertEquals(SHOULD_BE_EMPTY, "", attribute.getTail());

        attribute = new Attribute(person1, "", null);
        assertEquals(person1, attribute.getParent());
        assertEquals(SHOULD_BE_EMPTY, "", attribute.getString());
        assertEquals(SHOULD_BE_EMPTY, "", attribute.getTail());

        attribute = new Attribute(null, TEST_STRING, null);
        assertEquals(null, attribute.getParent());
        assertEquals(TEST_STRING, attribute.getString());
        assertEquals(SHOULD_BE_EMPTY, "", attribute.getTail());

        attribute = new Attribute(person1, TEST_STRING, null);
        assertEquals(person1, attribute.getParent());
        assertEquals(TEST_STRING, attribute.getString());
        assertEquals(SHOULD_BE_EMPTY, "", attribute.getTail());

        // ///////////////////

        attribute = new Attribute(null, null, "");
        assertEquals(null, attribute.getParent());
        assertEquals(SHOULD_BE_EMPTY, "", attribute.getString());
        assertEquals(SHOULD_BE_EMPTY, "", attribute.getTail());

        attribute = new Attribute(person1, null, "");
        assertEquals(person1, attribute.getParent());
        assertEquals(SHOULD_BE_EMPTY, "", attribute.getString());
        assertEquals(SHOULD_BE_EMPTY, "", attribute.getTail());

        attribute = new Attribute(null, "", "");
        assertEquals(null, attribute.getParent());
        assertEquals(SHOULD_BE_EMPTY, "", attribute.getString());
        assertEquals(SHOULD_BE_EMPTY, "", attribute.getTail());

        attribute = new Attribute(person1, "", "");
        assertEquals(person1, attribute.getParent());
        assertEquals(SHOULD_BE_EMPTY, "", attribute.getString());
        assertEquals(SHOULD_BE_EMPTY, "", attribute.getTail());

        attribute = new Attribute(null, TEST_STRING, "");
        assertEquals(null, attribute.getParent());
        assertEquals(TEST_STRING, attribute.getString());
        assertEquals(SHOULD_BE_EMPTY, "", attribute.getTail());

        attribute = new Attribute(person1, TEST_STRING, "");
        assertEquals(person1, attribute.getParent());
        assertEquals(TEST_STRING, attribute.getString());
        assertEquals(SHOULD_BE_EMPTY, "", attribute.getTail());

        // ///////////////////////

        attribute = new Attribute(null, null, TEST_STRUNG);
        assertEquals(null, attribute.getParent());
        assertEquals(SHOULD_BE_EMPTY, "", attribute.getString());
        assertEquals(TEST_STRUNG, attribute.getTail());

        attribute = new Attribute(person1, null, TEST_STRUNG);
        assertEquals(person1, attribute.getParent());
        assertEquals(SHOULD_BE_EMPTY, "", attribute.getString());
        assertEquals(TEST_STRUNG, attribute.getTail());

        attribute = new Attribute(null, "", TEST_STRUNG);
        assertEquals(null, attribute.getParent());
        assertEquals(SHOULD_BE_EMPTY, "", attribute.getString());
        assertEquals(TEST_STRUNG, attribute.getTail());

        attribute = new Attribute(person1, "", TEST_STRUNG);
        assertEquals(person1, attribute.getParent());
        assertEquals(SHOULD_BE_EMPTY, "", attribute.getString());
        assertEquals(TEST_STRUNG, attribute.getTail());

        attribute = new Attribute(null, TEST_STRING, TEST_STRUNG);
        assertEquals(null, attribute.getParent());
        assertEquals(TEST_STRING, attribute.getString());
        assertEquals(TEST_STRUNG, attribute.getTail());

        attribute = new Attribute(person1, TEST_STRING, TEST_STRUNG);
        assertEquals(person1, attribute.getParent());
        assertEquals(TEST_STRING, attribute.getString());
        assertEquals(TEST_STRUNG, attribute.getTail());
    }

    /** */
    @Test
    public void testSetGetTail() {
        Attribute attribute;
        attribute = new Attribute(null, null, null);
        assertEquals(null, attribute.getParent());
        assertEquals(SHOULD_BE_EMPTY, "", attribute.getString());
        assertEquals(SHOULD_BE_EMPTY, "", attribute.getTail());

        attribute.setTail("test 1");
        assertEquals(null, attribute.getParent());
        assertEquals(SHOULD_BE_EMPTY, "", attribute.getString());
        assertEquals("test 1", attribute.getTail());

        attribute.setTail(null);
        assertEquals(null, attribute.getParent());
        assertEquals(SHOULD_BE_EMPTY, "", attribute.getString());
        assertEquals(SHOULD_BE_EMPTY, "", attribute.getTail());

        attribute.setTail("test 2");
        assertEquals(null, attribute.getParent());
        assertEquals(SHOULD_BE_EMPTY, "", attribute.getString());
        assertEquals("test 2", attribute.getTail());

        attribute.setTail("");
        assertEquals(null, attribute.getParent());
        assertEquals(SHOULD_BE_EMPTY, "", attribute.getString());
        assertEquals(SHOULD_BE_EMPTY, "", attribute.getTail());
    }
}
