package org.schoellerfamily.gedbrowser.datamodel.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.Husband;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;

/**
 * @author Dick Schoeller
 */
public final class HusbandTest {
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

    /** */
    @Before
    public void setUp() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        person1 = builder.getPersonBuilder().createPerson(
                "I1", "J. Random/Schoeller/");
        final Person person2 = builder.getPersonBuilder().createPerson(
                "I2", "Anonymous/Schoeller/");
        person3 = builder.getPersonBuilder().createPerson(
                "I3", "Anonymous/Jones/");
        final Family family1 = builder.getFamilyBuilder().createFamily("F1");
        final Family family2 = builder.getFamilyBuilder().createFamily("F2");
        final Person person = person1;
        husband1 =
                builder.getFamilyBuilder().addHusbandToFamily(family1, person);
        builder.getFamilyBuilder().addWifeToFamily(family1, person2);
        final Person person4 = person1;
        husband2a =
                builder.getFamilyBuilder().addHusbandToFamily(family2, person4);
        final Person person5 = person3;
        husband2b =
                builder.getFamilyBuilder().addHusbandToFamily(family2, person5);
    }

    /** */
    @Test
    public void testEmptyGetFather() {
        final Husband husband = new Husband();
        assertFalse("Should not be set", husband.getFather().isSet());
    }

    /** */
    @Test
    public void testGetFather1() {
        assertEquals("Person mismatch", person1, husband1.getFather());
    }

    /** */
    @Test
    public void testGetFather2a() {
        assertEquals("Person mismatch", person1, husband2a.getFather());
    }

    /** */
    @Test
    public void testGetFather2b() {
        assertEquals("Person mismatch", person3, husband2b.getFather());
    }

    /** */
    @Test
    public void testGetSpouseIsSet1() {
        assertTrue("Husband should be set", husband1.getSpouse().isSet());
    }

    /** */
    @Test
    public void testGetSpouseIsSet2a() {
        assertTrue("Husband should be set", husband2a.getSpouse().isSet());
    }

    /** */
    @Test
    public void testGetSpouseIsSet2b() {
        assertTrue("Husband should be set", husband2b.getSpouse().isSet());
    }

    /** */
    @Test
    public void testGetSpouse1() {
        assertEquals("Person mismatch", person1, husband1.getSpouse());
    }

    /** */
    @Test
    public void testGetSpouse2a() {
        assertEquals("Person mismatch", person1, husband2a.getSpouse());
    }

    /** */
    @Test
    public void testGetSpouse2b() {
        assertEquals("Person mismatch", person3, husband2b.getSpouse());
    }

    /** */
    @Test
    public void testHusbandGedObjectFatherNotSet() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Family family = builder.getFamilyBuilder().createFamily("F1");
        final Husband husband = new Husband(family, "Husband", null);
        assertFalse("Father should not be set", husband.getFather().isSet());
    }

    /** */
    @Test
    public void testHusbandGedObjectStringFatherNotSet() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Family family = builder.getFamilyBuilder().createFamily("F1");
        final Husband husband = new Husband(family, "Husband", null);
        assertFalse("Father should not be set", husband.getFather().isSet());
    }

    /** */
    @Test
    public void testHusbandGedObjectStringStringFather() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Family family = builder.getFamilyBuilder().createFamily("F1");
        final Husband husband =
                new Husband(family, "Husband", new ObjectId("@I3@"));
        assertFalse("Father should not be set", husband.getFather().isSet());
    }
}
