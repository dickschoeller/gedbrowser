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
public final class HusbandTest {
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
    private final transient Husband husband2a = new Husband(family2, HUSB_TAG,
            new ObjectId("@I1@"));
    /** */
    private final transient Husband husband2b = new Husband(family2, HUSB_TAG,
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
        person1.insert(new FamS(person1, "F2"));
        person3.insert(new FamS(person3, "F2"));
        family1.insert(husband1);
        family1.insert(wife1);
        family2.insert(husband2a);
        family2.insert(husband2b);
    }

    /** */
    @Test
    public void testGetFather() {
        assertEquals("Person mismatch", person1, husband1.getFather());
        assertEquals("Person mismatch", person1, husband2a.getFather());
        assertEquals("Person mismatch", person3, husband2b.getFather());
    }

    /** */
    @Test
    public void testGetSpouse() {
        assertTrue("Husband should be set", husband1.getSpouse().isSet());
        assertTrue("Husband should be set", husband2a.getSpouse().isSet());
        assertTrue("Husband should be set", husband2b.getSpouse().isSet());
        assertEquals("Person mismatch", person1, husband1.getSpouse());
        assertEquals("Person mismatch", person1, husband2a.getSpouse());
        assertEquals("Person mismatch", person3, husband2b.getSpouse());
        // TODO should these be null?
        assertEquals("Person mismatch", person1, husband1.getSpouse());
        assertEquals("Person mismatch", person1, husband2a.getSpouse());
        assertEquals("Person mismatch", person3, husband2b.getSpouse());
    }

    /** */
    @Test
    public void testHusbandGedObject() {
        final Root localRoot = new Root(null, ROOT_TAG);
        final Family family = new Family(localRoot, new ObjectId("F1"));
        localRoot.insert("F1", family);
        final Husband husband = new Husband(family);
        assertTrue("Husband string should be empty", husband.getString()
                .isEmpty());
        assertEquals("", husband.getToString());
        assertEquals("F1", husband.getFromString());
        assertFalse(husband.getFather().isSet());
    }

    /** */
    @Test
    public void testHusbandGedObjectString() {
        final Root localRoot = new Root(null, ROOT_TAG);
        final Family family = new Family(localRoot, new ObjectId("F1"));
        localRoot.insert("F1", family);
        final Husband husband = new Husband(family, HUSB_TAG);
        assertEquals("Tag mismatch", HUSB_TAG, husband.getString());
        assertEquals("To string mismatch", "", husband.getToString());
        assertEquals("From string mismatch", "F1", husband.getFromString());
        assertFalse("Person should not be set", husband.getFather().isSet());
    }

    /** */
    @Test
    public void testHusbandGedObjectStringString() {
        final Root localRoot = new Root(null, ROOT_TAG);
        final Family family = new Family(localRoot, new ObjectId("F1"));
        localRoot.insert("F1", family);
        final Husband husband =
                new Husband(family, HUSB_TAG, new ObjectId("@I3@"));
        assertEquals("Tag mismatch", HUSB_TAG, husband.getString());
        assertEquals("To string mismatch", "I3", husband.getToString());
        assertEquals("From string mismatch", "F1", husband.getFromString());
        assertFalse("Person should not be set", husband.getFather().isSet());
    }
}
