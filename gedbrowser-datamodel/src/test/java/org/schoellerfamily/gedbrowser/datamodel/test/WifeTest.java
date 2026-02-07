package org.schoellerfamily.gedbrowser.datamodel.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Wife;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;

/**
 * @author Dick Schoeller
 */
final class WifeTest {
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
    @BeforeEach
    void setUp() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person1 = builder.createPerson("I1");
        person2 = builder.createPerson("I2");
        person3 = builder.createPerson("I3");
        final Family family1 = builder.createFamily("F1");
        builder.addHusbandToFamily(family1, person1);
        final Person person = person2;
        wife1 = builder.addWifeToFamily(family1, person);

        final Family family2 = builder.createFamily("F2");
        final Person person4 = person2;
        wife2a = builder.addWifeToFamily(family2, person4);
        final Person person5 = person3;
        wife2b = builder.addWifeToFamily(family2, person5);
    }

    /** */
    @Test
    void testEmptyGetMother() {
        final Wife wife = new Wife();
        assertFalse(wife.getMother().isSet(), "Should not be set");
    }

    /** */
    @Test
    void testGetMother1() {
        assertEquals(person2, wife1.getMother(), "Person's mother doesn't match");
    }

    /** */
    @Test
    void testGetMother2a() {
        assertEquals(person2, wife2a.getMother(), "Person's mother doesn't match");
    }

    /** */
    @Test
    void testGetMother2b() {
        assertEquals(person3, wife2b.getMother(), "Person's mother doesn't match");
    }

    /** */
    @Test
    void testGetSpouse1Set() {
        assertTrue(wife1.getSpouse().isSet(), "Person's spouse isn't set");
    }

    /** */
    @Test
    void testGetSpouse2aSet() {
        assertTrue(wife2a.getSpouse().isSet(), "Person's spouse isn't set");
    }

    /** */
    @Test
    void testGetSpouse2bSet() {
        assertTrue(wife2b.getSpouse().isSet(), "Person's spouse isn't set");
    }

    /** */
    @Test
    void testGetSpouse1() {
        assertEquals(person2, wife1.getSpouse(), "Person's spouse doesn't match");
    }

    /** */
    @Test
    void testGetSpouse2a() {
        assertEquals(person2, wife2a.getSpouse(), "Person's spouse doesn't match");
    }

    /** */
    @Test
    void testGetSpouse2b() {
        assertEquals(person3, wife2b.getSpouse(), "Person's spouse doesn't match");
    }

    /** */
    @Test
    void testWifeGedObjectMotherNotSet() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Family family = builder.createFamily("F1");
        final Wife wife = new Wife(family, "Wife", null);
        assertFalse(wife.getMother().isSet(), "Mother should not be set");
    }

    /** */
    @Test
    void testWifeGedObjectStringMotherNotSet() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Family family = builder.createFamily("F1");
        final Wife wife = new Wife(family, WIFE_TAG, null);
        assertFalse(wife.getMother().isSet(), "Mother should not be set");
    }

    /** */
    @Test
    void testWifeGedObjectStringStringMother() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Family family = builder.createFamily("F1");
        final Wife wife = new Wife(family, WIFE_TAG, new ObjectId("@I3@"));
        assertFalse(wife.getMother().isSet(), "Mother should not be set");
    }
}
