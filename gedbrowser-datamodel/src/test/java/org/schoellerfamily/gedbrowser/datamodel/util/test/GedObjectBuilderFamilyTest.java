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
import org.schoellerfamily.gedbrowser.datamodel.visitor.GetDateVisitor;

/**
 * @author Dick Schoeller
 */
public final class GedObjectBuilderFamilyTest {
    /** */
    private final GedObjectBuilder builder = new GedObjectBuilder();

    /**
     * A common person creator.
     *
     * @return the person
     */
    private Person createJRandom() {
        return builder.createPerson("I1", "J. Random/Schoeller/");
    }

    // TODO there might be more valid checks of the behaviors of the creators.
    // Check ID on family
    // Check type and date on events
    // Check network in family members
    /** */
    @Test
    public void testAddChildWithNulls() {
        final Child child = builder.addChildToFamily(null, null);
        assertFalse("Should create an empty child", child.isSet());
    }

    /** */
    @Test
    public void testAddChildWithNullPerson() {
        final Family family = builder.createFamily("F1");
        final Child child = builder.addChildToFamily(family, null);
        assertFalse("Should create an empty child", child.isSet());
    }

    /** */
    @Test
    public void testAddChildWithNullFamily() {
        final Person person = createJRandom();
        final Child child = builder.addChildToFamily(null, person);
        assertFalse("Should create an empty child", child.isSet());
    }

    /** */
    @Test
    public void testAddChild() {
        final Family family = builder.createFamily("F1");
        final Person person = createJRandom();
        final Child child = builder.addChildToFamily(family, person);
        assertTrue("Should create a real child", child.isSet());
    }

    /** */
    @Test
    public void testAddHusbandWithNulls() {
        final Husband husband = builder.addHusbandToFamily(null, null);
        assertFalse("Should create an empty husband", husband.isSet());
    }

    /** */
    @Test
    public void testAddHusbandWithNullPerson() {
        final Family family = builder.createFamily("F1");
        final Husband husband = builder.addHusbandToFamily(family, null);
        assertFalse("Should create an empty husband", husband.isSet());
    }

    /** */
    @Test
    public void testAddHusbandWithNullFamily() {
        final Person person = createJRandom();
        final Husband husband = builder .addHusbandToFamily(null, person);
        assertFalse("Should create an empty husband", husband.isSet());
    }

    /** */
    @Test
    public void testAddHusband() {
        final Family family = builder.createFamily("F1");
        final Person person = createJRandom();
        final Husband wife =
                builder.addHusbandToFamily(family, person);
        assertTrue("Should create a real wife", wife.isSet());
    }

    /** */
    @Test
    public void testAddWifeWithNulls() {
        final Wife wife =
                builder.addWifeToFamily(null, null);
        assertFalse("Should create an empty wife", wife.isSet());
    }

    /** */
    @Test
    public void testAddWifeWithNullPerson() {
        final Family family = builder.createFamily("F1");
        final Wife wife =
                builder.addWifeToFamily(family, null);
        assertFalse("Should create an empty wife", wife.isSet());
    }

    /** */
    @Test
    public void testAddWifeWithNullFamily() {
        final Person person = createJRandom();
        final Wife wife =
                builder.addWifeToFamily(null, person);
        assertFalse("Should create an empty wife", wife.isSet());
    }

    /** */
    @Test
    public void testAddWife() {
        final Family family = builder.createFamily("F1");
        final Person person = createJRandom();
        final Wife wife =
                builder.addWifeToFamily(family, person);
        assertTrue("Should create a real wife", wife.isSet());
    }

    /** */
    @Test
    public void testCreateFamilyWithNull() {
        final Family family = builder.createFamily(null);
        assertFalse("Should create empty family", family.isSet());
    }

    /** */
    @Test
    public void testCreateFamily() {
        final Family family = builder.createFamily("STRING");
        assertEquals("Should have the ID provided",
                "STRING", family.getString());
    }

    /** */
    @Test
    public void testCreateFamily1() {
        final Family family = builder.createFamily("F1");
        assertEquals("Should have the ID provided",
                "F1", family.getString());
    }

    /** */
    @Test
    public void testCreateFamily2() {
        final Family family = builder.createFamily("F2");
        assertEquals("Should have the ID provided",
                "F2", family.getString());
    }

    /** */
    @Test
    public void testCreateFamily3() {
        final Family family = builder.createFamily("F3");
        assertEquals("Should have the ID provided",
                "F3", family.getString());
    }

    /** */
    @Test
    public void testFamilyEventWithNulls() {
        final Attribute event =
                builder.createFamilyEvent(null, null);
        assertFalse("Should create empty event", event.isSet());
    }

    /** */
    @Test
    public void testFamilyEventWithNullFamily() {
        final Attribute event = builder.createFamilyEvent(
                null, "Marriage", "21 NOV 2002");
        assertFalse("Should create empty event", event.isSet());
    }

    /** */
    @Test
    public void testFamilyEventWithNullType() {
        final Family family = builder.createFamily("F1");
        final Attribute event = builder.createFamilyEvent(
                family, null, "21 NOV 2002");
        assertFalse("Should create empty event", event.isSet());
    }

    /** */
    @Test
    public void testFamilyEventWithNullDate() {
        final Family family = builder.createFamily("F1");
        final Attribute event = builder.createFamilyEvent(
                family, "Marriage");
        final GetDateVisitor visitor = new GetDateVisitor();
        event.accept(visitor);
        assertTrue("Should create empty event date",
                visitor.getDate().isEmpty());
    }

    /** */
    @Test
    public void testFamilyEventWithBogusDate() {
        final Family family = builder.createFamily("F1");
        final Attribute event = builder.createFamilyEvent(
                family, "Marriage", "HUH?");
        final GetDateVisitor visitor = new GetDateVisitor();
        event.accept(visitor);
        assertEquals("Should create event with this date string",
                "HUH?", visitor.getDate());
    }

    /** */
    @Test
    public void testFamilyEvent() {
        final Family family = builder.createFamily("F1");
        final Attribute event = builder.createFamilyEvent(
                family, "Marriage", "21 NOV 2002");
        assertTrue("Should create real event", event.isSet());
    }
}
