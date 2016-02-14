package org.schoellerfamily.gedbrowser.datamodel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

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
        assertEquals(person1, husband1.getFather());
        assertEquals(person1, husband2a.getFather());
        assertEquals(person3, husband2b.getFather());
    }

    /** */
    @Test
    public void testGetSpouse() {
        assertTrue(husband1.getSpouse().isSet());
        assertTrue(husband2a.getSpouse().isSet());
        assertTrue(husband2b.getSpouse().isSet());
        assertEquals(person1, husband1.getSpouse());
        assertEquals(person1, husband2a.getSpouse());
        assertEquals(person3, husband2b.getSpouse());
        // TODO should these be null?
        assertEquals(person1, husband1.getSpouse());
        assertEquals(person1, husband2a.getSpouse());
        assertEquals(person3, husband2b.getSpouse());
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
        assertEquals(HUSB_TAG, husband.getString());
        assertEquals("", husband.getToString());
        assertEquals("F1", husband.getFromString());
        assertFalse(husband.getFather().isSet());
    }

    /** */
    @Test
    public void testHusbandGedObjectStringString() {
        final Root localRoot = new Root(null, ROOT_TAG);
        final Family family = new Family(localRoot, new ObjectId("F1"));
        localRoot.insert("F1", family);
        final Husband husband =
                new Husband(family, HUSB_TAG, new ObjectId("@I3@"));
        assertEquals(HUSB_TAG, husband.getString());
        assertEquals("I3", husband.getToString());
        assertEquals("F1", husband.getFromString());
        assertFalse(husband.getFather().isSet());
    }
}
