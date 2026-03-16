package org.schoellerfamily.gedbrowser.datamodel.util.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.schoellerfamily.gedbrowser.datamodel.Attribute;
import org.schoellerfamily.gedbrowser.datamodel.Child;
import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.Husband;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Wife;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;
import org.schoellerfamily.gedbrowser.datamodel.visitor.GetDateVisitor;

/**
 * Contains tests for ged object builder family.
 *
 * @author Richard Schoeller
 */
final class GedObjectBuilderFamilyTest {
    /** */
    private final GedObjectBuilder builder = new GedObjectBuilder();

    private Person createJRandom() {
        return builder.createPerson("I1", "J. Random/Schoeller/");
    }

    // TODO there might be more valid checks of the behaviors of the creators.
    // Check ID on family
    // Check type and date on events
    // Check network in family members
    @Test
    void testAddChildWithNulls() {
        final Child child = builder.addChildToFamily(null, null);
        assertFalse(child.isSet(), "Should create an empty child");
    }

    @Test
    void testAddChildWithNullPerson() {
        final Family family = builder.createFamily("F1");
        final Child child = builder.addChildToFamily(family, null);
        assertFalse(child.isSet(), "Should create an empty child");
    }

    @Test
    void testAddChildWithNullFamily() {
        final Person person = createJRandom();
        final Child child = builder.addChildToFamily(null, person);
        assertFalse(child.isSet(), "Should create an empty child");
    }

    @Test
    void testAddChild() {
        final Family family = builder.createFamily("F1");
        final Person person = createJRandom();
        final Child child = builder.addChildToFamily(family, person);
        assertTrue(child.isSet(), "Should create a real child");
    }

    @Test
    void testAddHusbandWithNulls() {
        final Husband husband = builder.addHusbandToFamily(null, null);
        assertFalse(husband.isSet(), "Should create an empty husband");
    }

    @Test
    void testAddHusbandWithNullPerson() {
        final Family family = builder.createFamily("F1");
        final Husband husband = builder.addHusbandToFamily(family, null);
        assertFalse(husband.isSet(), "Should create an empty husband");
    }

    @Test
    void testAddHusbandWithNullFamily() {
        final Person person = createJRandom();
        final Husband husband = builder.addHusbandToFamily(null, person);
        assertFalse(husband.isSet(), "Should create an empty husband");
    }

    @Test
    void testAddHusband() {
        final Family family = builder.createFamily("F1");
        final Person person = createJRandom();
        final Husband wife = builder.addHusbandToFamily(family, person);
        assertTrue(wife.isSet(), "Should create a real wife");
    }

    @Test
    void testAddWifeWithNulls() {
        final Wife wife = builder.addWifeToFamily(null, null);
        assertFalse(wife.isSet(), "Should create an empty wife");
    }

    @Test
    void testAddWifeWithNullPerson() {
        final Family family = builder.createFamily("F1");
        final Wife wife = builder.addWifeToFamily(family, null);
        assertFalse(wife.isSet(), "Should create an empty wife");
    }

    @Test
    void testAddWifeWithNullFamily() {
        final Person person = createJRandom();
        final Wife wife = builder.addWifeToFamily(null, person);
        assertFalse(wife.isSet(), "Should create an empty wife");
    }

    @Test
    void testAddWife() {
        final Family family = builder.createFamily("F1");
        final Person person = createJRandom();
        final Wife wife = builder.addWifeToFamily(family, person);
        assertTrue(wife.isSet(), "Should create a real wife");
    }

    @Test
    void testCreateFamilyWithNull() {
        final Family family = builder.createFamily(null);
        assertFalse(family.isSet(), "Should create empty family");
    }

    @Test
    void testCreateFamily() {
        final Family family = builder.createFamily("STRING");
        assertEquals("STRING", family.getString(), "Should have the ID provided");
    }

    @Test
    void testCreateFamily1() {
        final Family family = builder.createFamily("F1");
        assertEquals("F1", family.getString(), "Should have the ID provided");
    }

    @Test
    void testCreateFamily2() {
        final Family family = builder.createFamily("F2");
        assertEquals("F2", family.getString(), "Should have the ID provided");
    }

    @Test
    void testCreateFamily3() {
        final Family family = builder.createFamily("F3");
        assertEquals("F3", family.getString(), "Should have the ID provided");
    }

    @Test
    void testFamilyEventWithNulls() {
        final Attribute event = builder.createFamilyEvent(null, null);
        assertFalse(event.isSet(), "Should create empty event");
    }

    @Test
    void testFamilyEventWithNullFamily() {
        final Attribute event = builder.createFamilyEvent(null, "Marriage", "21 NOV 2002");
        assertFalse(event.isSet(), "Should create empty event");
    }

    @Test
    void testFamilyEventWithNullType() {
        final Family family = builder.createFamily("F1");
        final Attribute event = builder.createFamilyEvent(family, null, "21 NOV 2002");
        assertFalse(event.isSet(), "Should create empty event");
    }

    @Test
    void testFamilyEventWithNullDate() {
        final Family family = builder.createFamily("F1");
        final Attribute event = builder.createFamilyEvent(family, "Marriage");
        final GetDateVisitor visitor = new GetDateVisitor();
        event.accept(visitor);
        assertTrue(visitor.getDate().isEmpty(), "Should create empty event date");
    }

    @Test
    void testFamilyEventWithBogusDate() {
        final Family family = builder.createFamily("F1");
        final Attribute event = builder.createFamilyEvent(family, "Marriage", "HUH?");
        final GetDateVisitor visitor = new GetDateVisitor();
        event.accept(visitor);
        assertEquals("HUH?", visitor.getDate(), "Should create event with this date string");
    }

    @Test
    void testFamilyEvent() {
        final Family family = builder.createFamily("F1");
        final Attribute event = builder.createFamilyEvent(family, "Marriage", "21 NOV 2002");
        assertTrue(event.isSet(), "Should create real event");
    }
}
