package org.schoellerfamily.gedbrowser.datamodel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

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
    public void testGetMother() {
        assertEquals(person2, wife1.getMother());
        assertEquals(person2, wife2a.getMother());
        assertEquals(person3, wife2b.getMother());
    }

    /** */
    @Test
    public void testGetSpouse() {
        assertTrue(wife1.getSpouse().isSet());
        assertTrue(wife2a.getSpouse().isSet());
        assertTrue(wife2b.getSpouse().isSet());
        assertEquals(person2, wife1.getSpouse());
        assertEquals(person2, wife2a.getSpouse());
        assertEquals(person3, wife2b.getSpouse());
        // TODO should these be null?
        assertEquals(person2, wife1.getSpouse());
        assertEquals(person2, wife2a.getSpouse());
        assertEquals(person3, wife2b.getSpouse());
    }

    /** */
    @Test
    public void testWifeGedObject() {
        final Root localRoot = new Root(null, ROOT_TAG);
        final Family family = new Family(localRoot, new ObjectId("F1"));
        localRoot.insert("F1", family);
        final Wife wife = new Wife(family);
        assertTrue("Wife string should be empty", wife.getString().isEmpty());
        assertEquals("", wife.getToString());
        assertEquals("F1", wife.getFromString());
        assertFalse(wife.getMother().isSet());
    }

    /** */
    @Test
    public void testWifeGedObjectString() {
        final Root localRoot = new Root(null, ROOT_TAG);
        final Family family = new Family(localRoot, new ObjectId("F1"));
        localRoot.insert("F1", family);
        final Wife wife = new Wife(family, WIFE_TAG);
        assertEquals(WIFE_TAG, wife.getString());
        assertEquals("", wife.getToString());
        assertEquals("F1", wife.getFromString());
        assertFalse(wife.getMother().isSet());
    }

    /** */
    @Test
    public void testWifeGedObjectStringString() {
        final Root localRoot = new Root(null, ROOT_TAG);
        final Family family = new Family(localRoot, new ObjectId("F1"));
        localRoot.insert("F1", family);
        final Wife wife = new Wife(family, WIFE_TAG, new ObjectId("@I3@"));
        assertEquals(WIFE_TAG, wife.getString());
        assertEquals("I3", wife.getToString());
        assertEquals("F1", wife.getFromString());
        assertFalse(wife.getMother().isSet());
    }
}
