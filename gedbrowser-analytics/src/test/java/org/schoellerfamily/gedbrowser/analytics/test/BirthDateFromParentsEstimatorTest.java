package org.schoellerfamily.gedbrowser.analytics.test;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.schoellerfamily.gedbrowser.analytics.BirthDateFromParentsEstimator;
import org.schoellerfamily.gedbrowser.analytics.order.test.AnalyzerTest;
import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.util.FamilyBuilder;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;
import org.schoellerfamily.gedbrowser.datamodel.util.PersonBuilder;

/**
 * @author Dick Schoeller
 */
public final class BirthDateFromParentsEstimatorTest implements AnalyzerTest {
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

    /**
     * {@inheritDoc}
     */
    @Override
    public PersonBuilder personBuilder() {
        return builder.getPersonBuilder();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FamilyBuilder familyBuilder() {
        return builder.getFamilyBuilder();
    }

    /** */
    @Before
    public void setUp() {
        builder = new GedObjectBuilder();
        final Person person1 = createJRandom();
        person2 = createAnonymousSchoeller();
        person3 = createAnonymousJones();

        family1 = familyBuilder().createFamily("F1");
        final Family family3 = family1;

        familyBuilder().addChildToFamily(family3, person1);
        final Family family = family1;
        final Person person = person2;
        familyBuilder().addHusbandToFamily(family, person);
        final Family family2 = family1;
        final Person person4 = person3;
        familyBuilder().addWifeToFamily(family2, person4);
        estimator = new BirthDateFromParentsEstimator(person1);
    }

    /** */
    @Test
    public void testFromBirthWithOnlyMarriage() {
        final Family family = family1;
        familyBuilder().createFamilyEvent(family, "Marriage", "10 MAY 1960");
        assertNull("Should be null because no dates available to use",
                estimator.estimateFromBirth(null));
    }

    /** */
    @Test
    public void testFromBirthWithOnlyFatherBirth() {
        final Person person = person2;
        personBuilder().createPersonEvent(person, "Birth", "1 JAN 1935");
        final LocalDate expected = new LocalDate(1962, 1, 1);
        assertMatch(expected, estimator.estimateFromBirth(null));
    }

    /** */
    @Test
    public void testFromBirthWithOnlyFather() {
        final Person child1 = createJRandom();
        final Person father = createAnonymousJones();
        final Family family = familyBuilder().createFamily("F1");
        familyBuilder().addChildToFamily(family, child1);
        familyBuilder().addHusbandToFamily(family, father);
        personBuilder().createPersonEvent(father, "Birth", "1 JAN 1935");
        final BirthDateFromParentsEstimator e =
                new BirthDateFromParentsEstimator(child1);
        final LocalDate expected = new LocalDate(1962, 1, 1);
        assertMatch(expected, e.estimateFromBirth(null));
    }

    /** */
    @Test
    public void testFromBirthWithOnlyMotherBirth() {
        final Person person = person3;
        personBuilder().createPersonEvent(person, "Birth", "1 JAN 1939");
        final LocalDate expected = new LocalDate(1966, 1, 1);
        assertMatch(expected, estimator.estimateFromBirth(null));
    }

    /** */
    @Test
    public void testFromBirthWithOnlyMother() {
        final Person child1 = createJRandom();
        final Person mother = createTooTall();
        final Family family = familyBuilder().createFamily("F1");
        familyBuilder().createFamilyEvent(family, "Marriage", "10 MAY 1960");
        familyBuilder().addChildToFamily(family, child1);
        familyBuilder().addWifeToFamily(family, mother);
        personBuilder().createPersonEvent(mother, "Birth", "1 JAN 1939");
        final BirthDateFromParentsEstimator e =
                new BirthDateFromParentsEstimator(child1);
        final LocalDate expected = new LocalDate(1966, 1, 1);
        assertMatch(expected, e.estimateFromBirth(null));
    }

    /** */
    @Test
    public void testFromBirthFamilyNoParents() {
        final Person child1 = createJRandom();
        final Family family = familyBuilder().createFamily("F1");
        familyBuilder().addChildToFamily(family, child1);
        final BirthDateFromParentsEstimator e =
                new BirthDateFromParentsEstimator(child1);
        assertNull("Should not get a date without parents",
                e.estimateFromBirth(null));
    }

    /** */
    @Test
    public void testFromBirthWithBothParentsBirth() {
        final Person person = person2;
        personBuilder().createPersonEvent(person, "Birth", "1 JAN 1935");
        final Person person1 = person3;
        personBuilder().createPersonEvent(person1, "Birth", "1 JAN 1939");
        final LocalDate expected = new LocalDate(1962, 1, 1);
        assertMatch(expected, estimator.estimateFromBirth(null));
    }

    /** */
    @Test
    public void testFromBirthWithPreviousDate() {
        final Person person = person2;
        personBuilder().createPersonEvent(person, "Birth", "1 JAN 1935");
        final Person person1 = person3;
        personBuilder().createPersonEvent(person1, "Birth", "1 JAN 1939");
        final LocalDate expected = new LocalDate(1966, 10, 1);
        assertMatch(expected, estimator.estimateFromBirth(expected));
    }

    /** */
    @Test
    public void testFromMarriageWithOnlyMarriage() {
        final Family family = family1;
        familyBuilder().createFamilyEvent(family, "Marriage", "10 MAY 1960");
        final LocalDate expected = new LocalDate(1962, 5, 1);
        assertMatch(expected, estimator.estimateFromMarriage(null));
    }

    /** */
    @Test
    public void testFromMarriageWithOlderSibling() {
        final Person child1 = createJRandom();
        final Person child2 = createAnonymousSchoeller();
        final Person father = createAnonymousJones();
        final Person mother = createTooTall();
        final Family family = familyBuilder().createFamily("F1");
        familyBuilder().createFamilyEvent(family, "Marriage", "10 MAY 1960");
        familyBuilder().addChildToFamily(family, child1);
        familyBuilder().addChildToFamily(family, child2);
        familyBuilder().addHusbandToFamily(family, father);
        familyBuilder().addWifeToFamily(family, mother);
        final BirthDateFromParentsEstimator e =
                new BirthDateFromParentsEstimator(child2);
        final LocalDate expected = new LocalDate(1964, 5, 1);
        assertMatch(expected, e.estimateFromMarriage(null));
    }

    /** */
    @Test
    public void testFromMarriageWithYoungerSibling() {
        final Person child1 = createJRandom();
        final Person child2 = createAnonymousSchoeller();
        final Person father = createAnonymousJones();
        final Person mother = createTooTall();
        final Family family = familyBuilder().createFamily("F1");
        familyBuilder().createFamilyEvent(family, "Marriage", "10 MAY 1960");
        familyBuilder().addChildToFamily(family, child1);
        familyBuilder().addChildToFamily(family, child2);
        familyBuilder().addHusbandToFamily(family, father);
        familyBuilder().addWifeToFamily(family, mother);
        final BirthDateFromParentsEstimator e =
                new BirthDateFromParentsEstimator(child1);
        final LocalDate expected = new LocalDate(1962, 5, 1);
        assertMatch(expected, e.estimateFromMarriage(null));
    }

    /** */
    @Test
    public void testFromMarriageWithPreviousDate() {
        final Family family = family1;
        familyBuilder().createFamilyEvent(family, "Marriage", "10 MAY 1960");
        final LocalDate expected = new LocalDate(1965, 5, 1);
        assertMatch(expected, estimator.estimateFromMarriage(expected));
    }

    /** */
    @Test
    public void testFromMarriageWithOnlyFatherBirth() {
        final Person person = person2;
        personBuilder().createPersonEvent(person, "Birth", "1 JAN 1935");
        assertNull("Should be null because no dates available to use",
                estimator.estimateFromMarriage(null));
    }

    /** */
    @Test
    public void testFromMarriageWithOnlyMotherBirth() {
        final Person person = person3;
        personBuilder().createPersonEvent(person, "Birth", "1 JAN 1939");
        assertNull("Should be null because no dates available to use",
                estimator.estimateFromMarriage(null));
    }

    /** */
    @Test
    public void testFromMarriageWithBothParentsBirth() {
        final Person person = person2;
        personBuilder().createPersonEvent(person, "Birth", "1 JAN 1935");
        final Person person1 = person3;
        personBuilder().createPersonEvent(person1, "Birth", "1 JAN 1939");
        assertNull("Should be null because no dates available to use",
                estimator.estimateFromMarriage(null));
    }

    /** */
    @Test
    public void testFromMarriageFamilyNoParents() {
        final Person child1 = createJRandom();
        final Family family = familyBuilder().createFamily("F1");
        familyBuilder().addChildToFamily(family, child1);
        familyBuilder().createFamilyEvent(family, "Marriage", "10 MAY 1960");
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
