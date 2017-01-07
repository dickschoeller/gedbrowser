package org.schoellerfamily.gedbrowser.datamodel.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
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
public final class WifeTest {
    /** */
    private static final String WIFE_TAG = "WIFE";
    /** */
    private static final String HUSB_TAG = "HUSB";
    /** */
    private static final String ROOT_TAG = "Root";
    /** */
    private final transient Root root = new Root(null, ROOT_TAG);
    /** */
    private final transient Family family1 = new Family(root,
            new ObjectId("F1"));
    /** */
    private final transient Family family2 = new Family(root,
            new ObjectId("F2"));
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
    private final transient Husband husband1 = new Husband(family1, HUSB_TAG,
            new ObjectId("@I1@"));
    /** */
    private final transient Wife wife1 = new Wife(family1, WIFE_TAG,
            new ObjectId("@I2@"));
    /** */
    private final transient Wife wife2a = new Wife(family2, "WIFE",
            new ObjectId("@I2@"));
    /** */
    private final transient Wife wife2b = new Wife(family2, "WIFE",
            new ObjectId("@I3@"));

    /** */
    @Before
    public void setUp() {
        root.insert("I1", person1);
        root.insert("I2", person2);
        root.insert("I3", person3);
        root.insert("F1", family1);
        root.insert("F2", family2);
        person1.insert(new FamS(person1, "F1"));
        person2.insert(new FamS(person2, "F1"));
        person1.insert(new FamS(person2, "F2"));
        person3.insert(new FamS(person3, "F2"));
        family1.insert(wife1);
        family1.insert(husband1);
        family2.insert(wife2a);
        family2.insert(wife2b);
    }

    /** */
    @Test
    public void testGetMother1() {
        assertEquals("Person's mother doesn't match", person2,
                wife1.getMother());
    }

    /** */
    @Test
    public void testGetMother2a() {
        assertEquals("Person's mother doesn't match", person2,
                wife2a.getMother());
    }

    /** */
    @Test
    public void testGetMother2b() {
        assertEquals("Person's mother doesn't match", person3,
                wife2b.getMother());
    }

    /** */
    @Test
    public void testGetSpouse1Set() {
        assertTrue("Person's spouse isn't set", wife1.getSpouse().isSet());
    }

    /** */
    @Test
    public void testGetSpouse2aSet() {
        assertTrue("Person's spouse isn't set", wife2a.getSpouse().isSet());
    }

    /** */
    @Test
    public void testGetSpouse2bSet() {
        assertTrue("Person's spouse isn't set", wife2b.getSpouse().isSet());
    }

    /** */
    @Test
    public void testGetSpouse1() {
        assertEquals("Person's spouse doesn't match", person2,
                wife1.getSpouse());
    }

    /** */
    @Test
    public void testGetSpouse2a() {
        assertEquals("Person's spouse doesn't match", person2,
                wife2a.getSpouse());
    }

    /** */
    @Test
    public void testGetSpouse2b() {
        assertEquals("Person's spouse doesn't match", person3,
                wife2b.getSpouse());
    }

    /** */
    @Test
    public void testWifeGedObjectEmptyString() {
        final Root localRoot = new Root(null, ROOT_TAG);
        final Family family = new Family(localRoot, new ObjectId("F1"));
        localRoot.insert("F1", family);
        final Wife wife = new Wife(family);
        assertTrue("Wife string should be empty", wife.getString().isEmpty());
    }

    /** */
    @Test
    public void testWifeGedObjectEmptyToString() {
        final Root localRoot = new Root(null, ROOT_TAG);
        final Family family = new Family(localRoot, new ObjectId("F1"));
        localRoot.insert("F1", family);
        final Wife wife = new Wife(family);
        assertTrue("Wife getToString should be empty",
                wife.getToString().isEmpty());
    }

    /** */
    @Test
    public void testWifeGedObjectFromString() {
        final Root localRoot = new Root(null, ROOT_TAG);
        final Family family = new Family(localRoot, new ObjectId("F1"));
        localRoot.insert("F1", family);
        final Wife wife = new Wife(family);
        assertEquals("Wife fromString doesn't match",
                "F1", wife.getFromString());
    }

    /** */
    @Test
    public void testWifeGedObjectMotherNotSet() {
        final Root localRoot = new Root(null, ROOT_TAG);
        final Family family = new Family(localRoot, new ObjectId("F1"));
        localRoot.insert("F1", family);
        final Wife wife = new Wife(family);
        assertFalse("Mother should not be set", wife.getMother().isSet());
    }

    /** */
    @Test
    public void testWifeGedObjectStringTag() {
        final Root localRoot = new Root(null, ROOT_TAG);
        final Family family = new Family(localRoot, new ObjectId("F1"));
        localRoot.insert("F1", family);
        final Wife wife = new Wife(family, WIFE_TAG);
        assertEquals("Tag doesn't match", WIFE_TAG, wife.getString());
    }

    /** */
    @Test
    public void testWifeGedObjectStringEmptyTo() {
        final Root localRoot = new Root(null, ROOT_TAG);
        final Family family = new Family(localRoot, new ObjectId("F1"));
        localRoot.insert("F1", family);
        final Wife wife = new Wife(family, WIFE_TAG);
        assertTrue("To string should be empty", wife.getToString().isEmpty());
    }

    /** */
    @Test
    public void testWifeGedObjectStringFrom() {
        final Root localRoot = new Root(null, ROOT_TAG);
        final Family family = new Family(localRoot, new ObjectId("F1"));
        localRoot.insert("F1", family);
        final Wife wife = new Wife(family, WIFE_TAG);
        assertEquals("From string doesn't match", "F1", wife.getFromString());
    }

    /** */
    @Test
    public void testWifeGedObjectStringMotherNotSet() {
        final Root localRoot = new Root(null, ROOT_TAG);
        final Family family = new Family(localRoot, new ObjectId("F1"));
        localRoot.insert("F1", family);
        final Wife wife = new Wife(family, WIFE_TAG);
        assertFalse("Mother should not be set", wife.getMother().isSet());
    }

    /** */
    @Test
    public void testWifeGedObjectStringStringTag() {
        final Root localRoot = new Root(null, ROOT_TAG);
        final Family family = new Family(localRoot, new ObjectId("F1"));
        localRoot.insert("F1", family);
        final Wife wife = new Wife(family, WIFE_TAG, new ObjectId("@I3@"));
        assertEquals("Tag doesn't match", WIFE_TAG, wife.getString());
    }

    /** */
    @Test
    public void testWifeGedObjectStringStringTo() {
        final Root localRoot = new Root(null, ROOT_TAG);
        final Family family = new Family(localRoot, new ObjectId("F1"));
        localRoot.insert("F1", family);
        final Wife wife = new Wife(family, WIFE_TAG, new ObjectId("@I3@"));
        assertEquals("To string doesn't match", "I3", wife.getToString());
    }

    /** */
    @Test
    public void testWifeGedObjectStringStringFrom() {
        final Root localRoot = new Root(null, ROOT_TAG);
        final Family family = new Family(localRoot, new ObjectId("F1"));
        localRoot.insert("F1", family);
        final Wife wife = new Wife(family, WIFE_TAG, new ObjectId("@I3@"));
        assertEquals("From string doesn't match", "F1", wife.getFromString());
    }

    /** */
    @Test
    public void testWifeGedObjectStringStringMother() {
        final Root localRoot = new Root(null, ROOT_TAG);
        final Family family = new Family(localRoot, new ObjectId("F1"));
        localRoot.insert("F1", family);
        final Wife wife = new Wife(family, WIFE_TAG, new ObjectId("@I3@"));
        assertFalse("Mother should not be set", wife.getMother().isSet());
    }
}
