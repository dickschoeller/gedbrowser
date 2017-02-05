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
public final class GedObjectBuilderFamilyTest {
    // TODO there might be more valid checks of the behaviors of the creators.
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
        assertTrue("Should create empty event date", event.getDate().isEmpty());
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
}
