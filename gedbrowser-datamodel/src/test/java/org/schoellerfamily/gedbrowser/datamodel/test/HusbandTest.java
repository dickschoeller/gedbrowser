package org.schoellerfamily.gedbrowser.datamodel.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.Husband;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;

/**
 * Contains tests for husband.
 *
 * @author Richard Schoeller
 */
final class HusbandTest {
    /** */
    private transient Person person1;
    /** */
    private transient Person person3;
    /** */
    private transient Husband husband1;
    /** */
    private transient Husband husband2a;
    /** */
    private transient Husband husband2b;

    @BeforeEach
    void setUp() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        person1 = builder.createPerson(
                "I1", "J. Random/Schoeller/");
        final Person person2 = builder.createPerson(
                "I2", "Anonymous/Schoeller/");
        person3 = builder.createPerson(
                "I3", "Anonymous/Jones/");
        final Family family1 = builder.createFamily("F1");
        final Family family2 = builder.createFamily("F2");
        final Person person = person1;
        husband1 =
                builder.addHusbandToFamily(family1, person);
        builder.addWifeToFamily(family1, person2);
        final Person person4 = person1;
        husband2a =
                builder.addHusbandToFamily(family2, person4);
        final Person person5 = person3;
        husband2b =
                builder.addHusbandToFamily(family2, person5);
    }

    @Test
    void testEmptyGetFather() {
        final Husband husband = new Husband();
        assertFalse(husband.getFather().isSet(), "Should not be set");
    }

    @Test
    void testGetFather1() {
        assertEquals(person1, husband1.getFather(), "Person mismatch");
    }

    @Test
    void testGetFather2a() {
        assertEquals(person1, husband2a.getFather(), "Person mismatch");
    }

    @Test
    void testGetFather2b() {
        assertEquals(person3, husband2b.getFather(), "Person mismatch");
    }

    @Test
    void testGetSpouseIsSet1() {
        assertTrue(husband1.getSpouse().isSet(), "Husband should be set");
    }

    @Test
    void testGetSpouseIsSet2a() {
        assertTrue(husband2a.getSpouse().isSet(), "Husband should be set");
    }

    @Test
    void testGetSpouseIsSet2b() {
        assertTrue(husband2b.getSpouse().isSet(), "Husband should be set");
    }

    @Test
    void testGetSpouse1() {
        assertEquals(person1, husband1.getSpouse(), "Person mismatch");
    }

    @Test
    void testGetSpouse2a() {
        assertEquals(person1, husband2a.getSpouse(), "Person mismatch");
    }

    @Test
    void testGetSpouse2b() {
        assertEquals(person3, husband2b.getSpouse(), "Person mismatch");
    }

    @Test
    void testHusbandGedObjectFatherNotSet() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Family family = builder.createFamily("F1");
        final Husband husband = new Husband(family, "Husband", null);
        assertFalse(husband.getFather().isSet(), "Father should not be set");
    }

    @Test
    void testHusbandGedObjectStringStringFather() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Family family = builder.createFamily("F1");
        final Husband husband =
                new Husband(family, "Husband", new ObjectId("@I3@"));
        assertFalse(husband.getFather().isSet(), "Father should not be set");
    }
}
