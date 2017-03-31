package org.schoellerfamily.gedbrowser.analytics.test;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.schoellerfamily.gedbrowser.analytics.BirthDateFromParentsEstimator;
import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;

/**
 * @author Dick Schoeller
 */
public final class BirthDateFromParentsEstimatorTest {
    /** */
    private transient GedObjectBuilder builder;
    /** */
    private transient Person person2;
    /** */
    private transient Person person3;
    /** */
    private transient Family family1;
    /** */
    private transient BirthDateFromParentsEstimator estimator;

    /** */
    @Before
    public void setUp() {
        builder = new GedObjectBuilder();
        final Person person1 = builder.createPerson1();
        person2 = builder.createPerson2();
        person3 = builder.createPerson3();

        family1 = builder.createFamily("F1");

        builder.addChildToFamily(family1, person1);
        builder.addHusbandToFamily(family1, person2);
        builder.addWifeToFamily(family1, person3);
        estimator = new BirthDateFromParentsEstimator(person1);
    }

    /** */
    @Test
    public void testFromBirthWithOnlyMarriage() {
        builder.createFamilyEvent(family1, "Marriage", "10 MAY 1960");
        assertNull("Should be null because no dates available to use",
                estimator.estimateFromBirth(null));
    }

    /** */
    @Test
    public void testFromBirthWithOnlyFatherBirth() {
        builder.createPersonEvent(person2, "Birth", "1 JAN 1935");
        final LocalDate expected = new LocalDate(1962, 1, 1);
        assertMatch(expected, estimator.estimateFromBirth(null));
    }

    /** */
    @Test
    public void testFromBirthWithOnlyFather() {
        final Person child1 = builder.createPerson1();
        final Person father = builder.createPerson3();
        final Family family = builder.createFamily("F1");
        builder.addChildToFamily(family, child1);
        builder.addHusbandToFamily(family, father);
        builder.createPersonEvent(father, "Birth", "1 JAN 1935");
        final BirthDateFromParentsEstimator e =
                new BirthDateFromParentsEstimator(child1);
        final LocalDate expected = new LocalDate(1962, 1, 1);
        assertMatch(expected, e.estimateFromBirth(null));
    }

    /** */
    @Test
    public void testFromBirthWithOnlyMotherBirth() {
        builder.createPersonEvent(person3, "Birth", "1 JAN 1939");
        final LocalDate expected = new LocalDate(1966, 1, 1);
        assertMatch(expected, estimator.estimateFromBirth(null));
    }

    /** */
    @Test
    public void testFromBirthWithOnlyMother() {
        final Person child1 = builder.createPerson1();
        final Person mother = builder.createPerson4();
        final Family family = builder.createFamily("F1");
        builder.createFamilyEvent(family, "Marriage", "10 MAY 1960");
        builder.addChildToFamily(family, child1);
        builder.addWifeToFamily(family, mother);
        builder.createPersonEvent(mother, "Birth", "1 JAN 1939");
        final BirthDateFromParentsEstimator e =
                new BirthDateFromParentsEstimator(child1);
        final LocalDate expected = new LocalDate(1966, 1, 1);
        assertMatch(expected, e.estimateFromBirth(null));
    }

    /** */
    @Test
    public void testFromBirthFamilyNoParents() {
        final Person child1 = builder.createPerson1();
        final Family family = builder.createFamily("F1");
        builder.addChildToFamily(family, child1);
        final BirthDateFromParentsEstimator e =
                new BirthDateFromParentsEstimator(child1);
        assertNull("Should not get a date without parents",
                e.estimateFromBirth(null));
    }

    /** */
    @Test
    public void testFromBirthWithBothParentsBirth() {
        builder.createPersonEvent(person2, "Birth", "1 JAN 1935");
        builder.createPersonEvent(person3, "Birth", "1 JAN 1939");
        final LocalDate expected = new LocalDate(1962, 1, 1);
        assertMatch(expected, estimator.estimateFromBirth(null));
    }

    /** */
    @Test
    public void testFromBirthWithPreviousDate() {
        builder.createPersonEvent(person2, "Birth", "1 JAN 1935");
        builder.createPersonEvent(person3, "Birth", "1 JAN 1939");
        final LocalDate expected = new LocalDate(1966, 10, 1);
        assertMatch(expected, estimator.estimateFromBirth(expected));
    }

    /** */
    @Test
    public void testFromMarriageWithOnlyMarriage() {
        builder.createFamilyEvent(family1, "Marriage", "10 MAY 1960");
        final LocalDate expected = new LocalDate(1962, 5, 1);
        assertMatch(expected, estimator.estimateFromMarriage(null));
    }

    /** */
    @Test
    public void testFromMarriageWithOlderSibling() {
        final Person child1 = builder.createPerson1();
        final Person child2 = builder.createPerson2();
        final Person father = builder.createPerson3();
        final Person mother = builder.createPerson4();
        final Family family = builder.createFamily("F1");
        builder.createFamilyEvent(family, "Marriage", "10 MAY 1960");
        builder.addChildToFamily(family, child1);
        builder.addChildToFamily(family, child2);
        builder.addHusbandToFamily(family, father);
        builder.addWifeToFamily(family, mother);
        final BirthDateFromParentsEstimator e =
                new BirthDateFromParentsEstimator(child2);
        final LocalDate expected = new LocalDate(1964, 5, 1);
        assertMatch(expected, e.estimateFromMarriage(null));
    }

    /** */
    @Test
    public void testFromMarriageWithYoungerSibling() {
        final Person child1 = builder.createPerson1();
        final Person child2 = builder.createPerson2();
        final Person father = builder.createPerson3();
        final Person mother = builder.createPerson4();
        final Family family = builder.createFamily("F1");
        builder.createFamilyEvent(family, "Marriage", "10 MAY 1960");
        builder.addChildToFamily(family, child1);
        builder.addChildToFamily(family, child2);
        builder.addHusbandToFamily(family, father);
        builder.addWifeToFamily(family, mother);
        final BirthDateFromParentsEstimator e =
                new BirthDateFromParentsEstimator(child1);
        final LocalDate expected = new LocalDate(1962, 5, 1);
        assertMatch(expected, e.estimateFromMarriage(null));
    }

    /** */
    @Test
    public void testFromMarriageWithPreviousDate() {
        builder.createFamilyEvent(family1, "Marriage", "10 MAY 1960");
        final LocalDate expected = new LocalDate(1965, 5, 1);
        assertMatch(expected, estimator.estimateFromMarriage(expected));
    }

    /** */
    @Test
    public void testFromMarriageWithOnlyFatherBirth() {
        builder.createPersonEvent(person2, "Birth", "1 JAN 1935");
        assertNull("Should be null because no dates available to use",
                estimator.estimateFromMarriage(null));
    }

    /** */
    @Test
    public void testFromMarriageWithOnlyMotherBirth() {
        builder.createPersonEvent(person3, "Birth", "1 JAN 1939");
        assertNull("Should be null because no dates available to use",
                estimator.estimateFromMarriage(null));
    }

    /** */
    @Test
    public void testFromMarriageWithBothParentsBirth() {
        builder.createPersonEvent(person2, "Birth", "1 JAN 1935");
        builder.createPersonEvent(person3, "Birth", "1 JAN 1939");
        assertNull("Should be null because no dates available to use",
                estimator.estimateFromMarriage(null));
    }

    /** */
    @Test
    public void testFromMarriageFamilyNoParents() {
        final Person child1 = builder.createPerson1();
        final Family family = builder.createFamily("F1");
        builder.addChildToFamily(family, child1);
        builder.createFamilyEvent(family, "Marriage", "10 MAY 1960");
        final BirthDateFromParentsEstimator e =
                new BirthDateFromParentsEstimator(child1);
        final LocalDate expected = new LocalDate(1962, 5, 1);
        assertMatch(expected, e.estimateFromMarriage(null));
    }

    /**
     * @param expected expected date
     * @param actual actual date
     */
    private void assertMatch(final LocalDate expected, final LocalDate actual) {
        assertTrue(mismatchString(expected, actual),
                expected.isEqual(actual));
    }

    /**
     * @param expected expected date
     * @param actual actual date
     * @return string describing the mismatch
     */
    private String mismatchString(final LocalDate expected,
            final LocalDate actual) {
        return "Don't match! expected: " + expected + ", actual: " + actual;
    }
}
