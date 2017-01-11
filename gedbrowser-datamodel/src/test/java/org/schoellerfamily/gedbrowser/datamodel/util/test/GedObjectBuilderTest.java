package org.schoellerfamily.gedbrowser.datamodel.util.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.schoellerfamily.gedbrowser.datamodel.Attribute;
import org.schoellerfamily.gedbrowser.datamodel.Child;
import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.Husband;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Wife;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;

/**
 * @author Dick Schoeller
 */
public class GedObjectBuilderTest {
    // TODO there might be more valid checks of the behaviors of the creators.
    // Check name and ID on person
    // Check ID on family
    // Check type and date on events
    // Check network in family members

    /** */
    @Test
    public void testAddChildWithNulls() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Child child = builder.addChildToFamily(null, null);
        assertFalse("Should create an empty child", child.isSet());
    }

    /** */
    @Test
    public void testAddChildWithNullPerson() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Family family = builder.createFamily1();
        final Child child = builder.addChildToFamily(family, null);
        assertFalse("Should create an empty child", child.isSet());
    }

    /** */
    @Test
    public void testAddChildWithNullFamily() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson1();
        final Child child = builder.addChildToFamily(null, person);
        assertFalse("Should create an empty child", child.isSet());
    }

    /** */
    @Test
    public void testAddChild() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Family family = builder.createFamily1();
        final Person person = builder.createPerson1();
        final Child child = builder.addChildToFamily(family, person);
        assertTrue("Should create a real child", child.isSet());
    }

    /** */
    @Test
    public void testAddHusbandWithNulls() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Husband husband = builder.addHusbandToFamily(null, null);
        assertFalse("Should create an empty husband", husband.isSet());
    }

    /** */
    @Test
    public void testAddHusbandWithNullPerson() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Family family = builder.createFamily1();
        final Husband husband = builder.addHusbandToFamily(family, null);
        assertFalse("Should create an empty husband", husband.isSet());
    }

    /** */
    @Test
    public void testAddHusbandWithNullFamily() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson1();
        final Husband husband = builder.addHusbandToFamily(null, person);
        assertFalse("Should create an empty husband", husband.isSet());
    }

    /** */
    @Test
    public void testAddHusband() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Family family = builder.createFamily1();
        final Person person = builder.createPerson1();
        final Husband wife = builder.addHusbandToFamily(family, person);
        assertTrue("Should create a real wife", wife.isSet());
    }

    /** */
    @Test
    public void testAddWifeWithNulls() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Wife wife = builder.addWifeToFamily(null, null);
        assertFalse("Should create an empty wife", wife.isSet());
    }

    /** */
    @Test
    public void testAddWifeWithNullPerson() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Family family = builder.createFamily1();
        final Wife wife = builder.addWifeToFamily(family, null);
        assertFalse("Should create an empty wife", wife.isSet());
    }

    /** */
    @Test
    public void testAddWifeWithNullFamily() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson1();
        final Wife wife = builder.addWifeToFamily(null, person);
        assertFalse("Should create an empty wife", wife.isSet());
    }

    /** */
    @Test
    public void testAddWife() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Family family = builder.createFamily1();
        final Person person = builder.createPerson1();
        final Wife wife = builder.addWifeToFamily(family, person);
        assertTrue("Should create a real wife", wife.isSet());
    }

    /** */
    @Test
    public void testCreateFamilyWithNull() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Family family = builder.createFamily(null);
        assertFalse("Should create empty family", family.isSet());
    }

    /** */
    @Test
    public void testCreateFamily() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Family family = builder.createFamily("STRING");
        assertEquals("Should have the ID provided",
                "STRING", family.getString());
    }

    /** */
    @Test
    public void testCreateFamily1() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Family family = builder.createFamily1();
        assertEquals("Should have the ID provided",
                "F1", family.getString());
    }

    /** */
    @Test
    public void testCreateFamily2() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Family family = builder.createFamily2();
        assertEquals("Should have the ID provided",
                "F2", family.getString());
    }

    /** */
    @Test
    public void testCreateFamily3() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Family family = builder.createFamily3();
        assertEquals("Should have the ID provided",
                "F3", family.getString());
    }

    /** */
    @Test
    public void testFamilyEventWithNulls() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Attribute event = builder.createFamilyEvent(null, null);
        assertFalse("Should create empty event", event.isSet());
    }

    /** */
    @Test
    public void testFamilyEventWithNullFamily() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Attribute event =
                builder.createFamilyEvent(null, "Marriage", "21 NOV 2002");
        assertFalse("Should create empty event", event.isSet());
    }

    /** */
    @Test
    public void testFamilyEventWithNullType() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Family family = builder.createFamily1();
        final Attribute event =
                builder.createFamilyEvent(family, null, "21 NOV 2002");
        assertFalse("Should create empty event", event.isSet());
    }

    /** */
    @Test
    public void testFamilyEventWithNullDate() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Family family = builder.createFamily1();
        final Attribute event =
                builder.createFamilyEvent(family, "Marriage");
        assertFalse("Should create empty event", event.isSet());
    }

    /** */
    @Test
    public void testFamilyEventWithBogusDate() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Family family = builder.createFamily1();
        final Attribute event =
                builder.createFamilyEvent(family, "Marriage", "HUH?");
        assertEquals("Should create event with this date string",
                "HUH?", event.getDate());
    }

    /** */
    @Test
    public void testFamilyEvent() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Family family = builder.createFamily1();
        final Attribute event =
                builder.createFamilyEvent(family, "Marriage", "21 NOV 2002");
        assertTrue("Should create real event", event.isSet());
    }

    /** */
    @Test
    public void testWithNulls() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson(null, null);
        assertFalse("Should create empty person", person.isSet());
    }

    /** */
    @Test
    public void testPersonWithNullId() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson(null, "Name/Me/");
        assertFalse("Should create empty person", person.isSet());
    }

    /** */
    @Test
    public void testPersonWithNullName() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson("I1", null);
        assertFalse("Should create empty person", person.isSet());
    }

    /** */
    @Test
    public void testPerson() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson("I1", "Name/Me/");
        assertTrue("Should create real person", person.isSet());
    }

    /** */
    @Test
    public void testPerson1() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson1();
        assertEquals("Should create real person",
                "I1", person.getString());
    }

    /** */
    @Test
    public void testPerson2() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson2();
        assertEquals("Should create real person",
                "I2", person.getString());
    }

    /** */
    @Test
    public void testPerson3() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson3();
        assertEquals("Should create real person",
                "I3", person.getString());
    }

    /** */
    @Test
    public void testPerson4() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson4();
        assertEquals("Should create real person",
                "I4", person.getString());
    }

    /** */
    @Test
    public void testPerson5() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson5();
        assertEquals("Should create real person",
                "I5", person.getString());
    }

    /** */
    @Test
    public void testPerson1Name() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson1();
        assertEquals("Should create real person",
                "J. Random/Schoeller/", person.getName().getString());
    }

    /** */
    @Test
    public void testPerson2Name() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson2();
        assertEquals("Should create real person",
                "Anonymous/Schoeller/", person.getName().getString());
    }

    /** */
    @Test
    public void testPerson3Name() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson3();
        assertEquals("Should create real person",
                "Anonymous/Jones/", person.getName().getString());
    }

    /** */
    @Test
    public void testPerson4Name() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson4();
        assertEquals("Should create real person",
                "Too Tall/Jones/", person.getName().getString());
    }

    /** */
    @Test
    public void testPerson5Name() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson5();
        assertEquals("Should create real person",
                "Anonyma/Schoeller/", person.getName().getString());
    }

    /** */
    @Test
    public void testPersonEventWithNulls() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Attribute event = builder.createPersonEvent(null, null);
        assertFalse("Should create empty event", event.isSet());
    }

    /** */
    @Test
    public void testPersonEventWithNullPerson() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Attribute event =
                builder.createPersonEvent(null, "Birth", "10 NOV 2000");
        assertFalse("Should create empty event", event.isSet());
    }

    /** */
    @Test
    public void testPersonEventWithNullType() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson1();
        final Attribute event =
                builder.createPersonEvent(person, null, "10 NOV 2000");
        assertFalse("Should create empty event", event.isSet());
    }

    /** */
    @Test
    public void testPersonEventWithNullDate() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson1();
        final Attribute event =
                builder.createPersonEvent(person, "Birth");
        assertTrue("Should create undated event", event.getDate().isEmpty());
    }

    /** */
    @Test
    public void testPersonEventWithBogusDate() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson1();
        final Attribute event =
                builder.createPersonEvent(person, "Birth",  "HUH?");
        assertEquals("Should create event with this date string",
                "HUH?", event.getDate());
    }

    /** */
    @Test
    public void testPersonEvent() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson1();
        final Attribute event =
                builder.createPersonEvent(person, "Birth",  "10 NOV 2000");
        assertTrue("Should create real event", event.isSet());
    }
}
