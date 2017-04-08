package org.schoellerfamily.gedbrowser.datamodel.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Wife;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;

/**
 * @author Dick Schoeller
 */
public final class WifeTest {
    /** */
    private static final String WIFE_TAG = "WIFE";
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
        final Person person1 = builder.getPersonBuilder().createPerson("I1");
        person2 = builder.getPersonBuilder().createPerson("I2");
        person3 = builder.getPersonBuilder().createPerson("I3");
        final Family family1 = builder.getFamilyBuilder().createFamily("F1");
        builder.getFamilyBuilder().addHusbandToFamily(family1, person1);
        final Person person = person2;
        wife1 = builder.getFamilyBuilder().addWifeToFamily(family1, person);

        final Family family2 = builder.getFamilyBuilder().createFamily("F2");
        final Person person4 = person2;
        wife2a = builder.getFamilyBuilder().addWifeToFamily(family2, person4);
        final Person person5 = person3;
        wife2b = builder.getFamilyBuilder().addWifeToFamily(family2, person5);
    }

    /** */
    @Test
    public void testEmptyGetMother() {
        final Wife wife = new Wife();
        assertFalse("Should not be set", wife.getMother().isSet());
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
    public void testWifeGedObjectMotherNotSet() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Family family = builder.getFamilyBuilder().createFamily("F1");
        final Wife wife = new Wife(family, "Wife", null);
        assertFalse("Mother should not be set", wife.getMother().isSet());
    }

    /** */
    @Test
    public void testWifeGedObjectStringMotherNotSet() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Family family = builder.getFamilyBuilder().createFamily("F1");
        final Wife wife = new Wife(family, WIFE_TAG, null);
        assertFalse("Mother should not be set", wife.getMother().isSet());
    }

    /** */
    @Test
    public void testWifeGedObjectStringStringMother() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Family family = builder.getFamilyBuilder().createFamily("F1");
        final Wife wife = new Wife(family, WIFE_TAG, new ObjectId("@I3@"));
        assertFalse("Mother should not be set", wife.getMother().isSet());
    }
}
