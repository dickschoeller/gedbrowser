package org.schoellerfamily.gedbrowser.datamodel.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.Wife;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;

/**
 * @author Dick Schoeller
 */
public final class WifeTest {
    /** */
    private static final String WIFE_TAG = "WIFE";
    /** */
    private static final String ROOT_TAG = "Root";
    /** */
    private transient Person person2;
    /** */
    private transient Person person3;
    /** */
    private transient Wife wife1;
    /** */
    private transient Wife wife2a;
    /** */
    private transient Wife wife2b;

    /** */
    @Before
    public void setUp() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person1 = builder.createPerson("I1");
        person2 = builder.createPerson("I2");
        person3 = builder.createPerson("I3");
        final Family family1 = builder.createFamily("F1");
        builder.addHusbandToFamily(family1, person1);
        wife1 = builder.addWifeToFamily(family1,  person2);

        final Family family2 = builder.createFamily("F2");
        wife2a = builder.addWifeToFamily(family2, person2);
        wife2b = builder.addWifeToFamily(family2, person3);
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
