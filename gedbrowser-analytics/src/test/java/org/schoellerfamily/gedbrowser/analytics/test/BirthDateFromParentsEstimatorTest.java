package org.schoellerfamily.gedbrowser.analytics.test;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.joda.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.schoellerfamily.gedbrowser.analytics.BirthDateFromParentsEstimator;
import org.schoellerfamily.gedbrowser.analytics.order.test.AnalyzerTest;
import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.util.FamilyBuilder;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;
import org.schoellerfamily.gedbrowser.datamodel.util.PersonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author Dick Schoeller
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { TestConfiguration.class })
final class BirthDateFromParentsEstimatorTest implements AnalyzerTest {
    /** */
    @Autowired
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
     * Returns the person builder.
     *
     * @return the resulting person builder
     */
    @Override
    public PersonBuilder personBuilder() {
        return builder;
    }

    /**
     * Returns the family builder.
     *
     * @return the resulting family builder
     */
    @Override
    public FamilyBuilder familyBuilder() {
        return builder;
    }

    @BeforeEach
    void setUp() {
        final Person person1 = createJRandom();
        person2 = createAnonymousSchoeller();
        person3 = createAnonymousJones();

        family1 = builder.createFamily("F1");
        final Family family3 = family1;

        builder.addChildToFamily(family3, person1);
        final Family family = family1;
        final Person person = person2;
        builder.addHusbandToFamily(family, person);
        final Family family2 = family1;
        final Person person4 = person3;
        builder.addWifeToFamily(family2, person4);
        estimator = new BirthDateFromParentsEstimator(person1);
    }

    @Test
    void testFromBirthWithOnlyMarriage() {
        final Family family = family1;
        builder.createFamilyEvent(family, "Marriage", "10 MAY 1960");
        assertNull(estimator.estimateFromBirth(null),
            "Should be null because no dates available to use");
    }

    @Test
    void testFromBirthWithOnlyFatherBirth() {
        final Person person = person2;
        builder.createPersonEvent(person, "Birth", "1 JAN 1935");
        final LocalDate expected = new LocalDate(1962, 1, 1);
        assertMatch(expected, estimator.estimateFromBirth(null));
    }

    @Test
    void testFromBirthWithOnlyFather() {
        final Person child1 = createJRandom();
        final Person father = createAnonymousJones();
        final Family family = builder.createFamily("F1");
        builder.addChildToFamily(family, child1);
        builder.addHusbandToFamily(family, father);
        builder.createPersonEvent(father, "Birth", "1 JAN 1935");
        final BirthDateFromParentsEstimator e = new BirthDateFromParentsEstimator(child1);
        final LocalDate expected = new LocalDate(1962, 1, 1);
        assertMatch(expected, e.estimateFromBirth(null));
    }

    @Test
    void testFromBirthWithOnlyMotherBirth() {
        final Person person = person3;
        builder.createPersonEvent(person, "Birth", "1 JAN 1939");
        final LocalDate expected = new LocalDate(1966, 1, 1);
        assertMatch(expected, estimator.estimateFromBirth(null));
    }

    @Test
    void testFromBirthWithOnlyMother() {
        final Person child1 = createJRandom();
        final Person mother = createTooTall();
        final Family family = builder.createFamily("F1");
        builder.createFamilyEvent(family, "Marriage", "10 MAY 1960");
        builder.addChildToFamily(family, child1);
        builder.addWifeToFamily(family, mother);
        builder.createPersonEvent(mother, "Birth", "1 JAN 1939");
        final BirthDateFromParentsEstimator e = new BirthDateFromParentsEstimator(child1);
        final LocalDate expected = new LocalDate(1966, 1, 1);
        assertMatch(expected, e.estimateFromBirth(null));
    }

    @Test
    void testFromBirthFamilyNoParents() {
        final Person child1 = createJRandom();
        final Family family = builder.createFamily("F1");
        builder.addChildToFamily(family, child1);
        final BirthDateFromParentsEstimator e = new BirthDateFromParentsEstimator(child1);
        assertNull(e.estimateFromBirth(null), "Should not get a date without parents");
    }

    @Test
    void testFromBirthWithBothParentsBirth() {
        final Person person = person2;
        builder.createPersonEvent(person, "Birth", "1 JAN 1935");
        final Person person1 = person3;
        builder.createPersonEvent(person1, "Birth", "1 JAN 1939");
        final LocalDate expected = new LocalDate(1962, 1, 1);
        assertMatch(expected, estimator.estimateFromBirth(null));
    }

    @Test
    void testFromBirthWithPreviousDate() {
        final Person person = person2;
        builder.createPersonEvent(person, "Birth", "1 JAN 1935");
        final Person person1 = person3;
        builder.createPersonEvent(person1, "Birth", "1 JAN 1939");
        final LocalDate expected = new LocalDate(1966, 10, 1);
        assertMatch(expected, estimator.estimateFromBirth(expected));
    }

    @Test
    void testFromMarriageWithOnlyMarriage() {
        final Family family = family1;
        builder.createFamilyEvent(family, "Marriage", "10 MAY 1960");
        final LocalDate expected = new LocalDate(1962, 5, 1);
        assertMatch(expected, estimator.estimateFromMarriage(null));
    }

    @Test
    void testFromMarriageWithOlderSibling() {
        final Person child1 = createJRandom();
        final Person child2 = createAnonymousSchoeller();
        final Person father = createAnonymousJones();
        final Person mother = createTooTall();
        final Family family = builder.createFamily("F1");
        builder.createFamilyEvent(family, "Marriage", "10 MAY 1960");
        builder.addChildToFamily(family, child1);
        builder.addChildToFamily(family, child2);
        builder.addHusbandToFamily(family, father);
        builder.addWifeToFamily(family, mother);
        final BirthDateFromParentsEstimator e = new BirthDateFromParentsEstimator(child2);
        final LocalDate expected = new LocalDate(1964, 5, 1);
        assertMatch(expected, e.estimateFromMarriage(null));
    }

    @Test
    void testFromMarriageWithYoungerSibling() {
        final Person child1 = createJRandom();
        final Person child2 = createAnonymousSchoeller();
        final Person father = createAnonymousJones();
        final Person mother = createTooTall();
        final Family family = builder.createFamily("F1");
        builder.createFamilyEvent(family, "Marriage", "10 MAY 1960");
        builder.addChildToFamily(family, child1);
        builder.addChildToFamily(family, child2);
        builder.addHusbandToFamily(family, father);
        builder.addWifeToFamily(family, mother);
        final BirthDateFromParentsEstimator e = new BirthDateFromParentsEstimator(child1);
        final LocalDate expected = new LocalDate(1962, 5, 1);
        assertMatch(expected, e.estimateFromMarriage(null));
    }

    @Test
    void testFromMarriageWithPreviousDate() {
        final Family family = family1;
        builder.createFamilyEvent(family, "Marriage", "10 MAY 1960");
        final LocalDate expected = new LocalDate(1965, 5, 1);
        assertMatch(expected, estimator.estimateFromMarriage(expected));
    }

    @Test
    void testFromMarriageWithOnlyFatherBirth() {
        final Person person = person2;
        builder.createPersonEvent(person, "Birth", "1 JAN 1935");
        assertNull(estimator.estimateFromMarriage(null),
            "Should be null because no dates available to use");
    }

    @Test
    void testFromMarriageWithOnlyMotherBirth() {
        final Person person = person3;
        builder.createPersonEvent(person, "Birth", "1 JAN 1939");
        assertNull(estimator.estimateFromMarriage(null),
            "Should be null because no dates available to use");
    }

    @Test
    void testFromMarriageWithBothParentsBirth() {
        final Person person = person2;
        builder.createPersonEvent(person, "Birth", "1 JAN 1935");
        final Person person1 = person3;
        builder.createPersonEvent(person1, "Birth", "1 JAN 1939");
        assertNull(estimator.estimateFromMarriage(null),
            "Should be null because no dates available to use");
    }

    @Test
    void testFromMarriageFamilyNoParents() {
        final Person child1 = createJRandom();
        final Family family = builder.createFamily("F1");
        builder.addChildToFamily(family, child1);
        builder.createFamilyEvent(family, "Marriage", "10 MAY 1960");
        final BirthDateFromParentsEstimator e = new BirthDateFromParentsEstimator(child1);
        final LocalDate expected = new LocalDate(1962, 5, 1);
        assertMatch(expected, e.estimateFromMarriage(null));
    }

    private void assertMatch(final LocalDate expected, final LocalDate actual) {
        assertTrue(expected.isEqual(actual), mismatchString(expected, actual));
    }

    private String mismatchString(final LocalDate expected, final LocalDate actual) {
        return "Don't match! expected: " + expected + ", actual: " + actual;
    }
}
