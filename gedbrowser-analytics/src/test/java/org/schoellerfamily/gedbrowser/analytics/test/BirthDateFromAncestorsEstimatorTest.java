package org.schoellerfamily.gedbrowser.analytics.test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.joda.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.schoellerfamily.gedbrowser.analytics.BirthDateFromAncestorsEstimator;
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
class BirthDateFromAncestorsEstimatorTest implements AnalyzerTest {
    /** */
    @Autowired
    private GedObjectBuilder builder;

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

    @Test
    void testFromParentsMarriageWithDate() {
        final Person person1 = createJRandom();
        final Person person2 = createAnonymousSchoeller();
        final Person person3 = createAnonymousJones();

        final Family family1 = builder.createFamily("F1");

        builder.addHusbandToFamily(family1, person1);
        builder.addWifeToFamily(family1, person2);
        builder.addChildToFamily(family1, person3);

        builder.createFamilyEvent(family1, "Marriage", "27 MAY 1984");

        final BirthDateFromAncestorsEstimator estimator =
                new BirthDateFromAncestorsEstimator(person3);
        final int birthYear = 1988;
        final int birthMonth = 1;
        final int birthDay = 1;
        final LocalDate expected =
                new LocalDate(birthYear, birthMonth, birthDay);
        final LocalDate actual = estimator.estimateFromMarriage(expected);
        assertMatch(expected, actual);
    }

    @Test
    void testFromParentsMarriageWithoutDate() {
        final Person person1 = createJRandom();
        final Person person2 = createAnonymousSchoeller();
        final Person person3 = createAnonymousJones();

        final Family family1 = builder.createFamily("F1");

        builder.addHusbandToFamily(family1, person1);
        builder.addWifeToFamily(family1, person2);
        builder.addChildToFamily(family1, person3);

        builder.createFamilyEvent(family1, "Marriage", "27 MAY 1984");

        final BirthDateFromAncestorsEstimator estimator =
                new BirthDateFromAncestorsEstimator(person3);
        final int birthYear = 1986;
        final int birthMonth = 1;
        final int birthDay = 1;
        final LocalDate expected =
                new LocalDate(birthYear, birthMonth, birthDay);
        final LocalDate actual = estimator.estimateFromMarriage(null);
        assertMatch(expected, actual);
    }

    @Test
    void testFromGrandparentsMarriageWithoutDate() {
        final Person person1 = createJRandom();
        final Person person2 = createAnonymousSchoeller();
        final Person person3 = createAnonymousJones();
        final Person person4 = createTooTall();

        final Family family1 = builder.createFamily("F1");
        builder.addHusbandToFamily(family1, person1);
        builder.addWifeToFamily(family1, person2);
        builder.addChildToFamily(family1, person3);
        builder.createFamilyEvent(family1, "Marriage", "27 MAY 1984");

        final Family family2 = builder.createFamily("F2");
        builder.addHusbandToFamily(family2, person3);
        builder.addChildToFamily(family2, person4);

        final BirthDateFromAncestorsEstimator estimator =
                new BirthDateFromAncestorsEstimator(person4);
        final int birthYear = 2013;
        final int birthMonth = 1;
        final int birthDay = 1;
        final LocalDate expected =
                new LocalDate(birthYear, birthMonth, birthDay);
        final LocalDate actual = estimator.estimateFromMarriage(null);
        assertMatch(expected, actual);
    }

    @Test
    void testFromParentBirthWithDate() {
        final Person person1 = createJRandom();
        final Person person2 = createAnonymousSchoeller();
        final Person person3 = createAnonymousJones();

        final Family family1 = builder.createFamily("F1");
        builder.addHusbandToFamily(family1, person1);
        builder.addWifeToFamily(family1, person2);
        builder.addChildToFamily(family1, person3);

        builder.createPersonEvent(person1, "Birth", "2 MAY 1950");

        final BirthDateFromAncestorsEstimator estimator =
                new BirthDateFromAncestorsEstimator(person3);
        final int birthYear = 1979;
        final int birthMonth = 10;
        final int birthDay = 10;
        final LocalDate expected =
                new LocalDate(birthYear, birthMonth, birthDay);
        final LocalDate actual = estimator.estimate(expected);
        assertMatch(expected, actual);
    }

    @Test
    void testFromParentBirthWithoutDate() {
        final Person person1 = createJRandom();
        final Person person2 = createAnonymousSchoeller();
        final Person person3 = createAnonymousJones();

        final Family family1 = builder.createFamily("F1");
        builder.addHusbandToFamily(family1, person1);
        builder.addWifeToFamily(family1, person2);
        builder.addChildToFamily(family1, person3);

        builder.createPersonEvent(person1, "Birth", "2 MAY 1950");

        final BirthDateFromAncestorsEstimator estimator =
                new BirthDateFromAncestorsEstimator(person3);
        final int birthYear = 1977;
        final int birthMonth = 1;
        final int birthDay = 1;
        final LocalDate expected =
                new LocalDate(birthYear, birthMonth, birthDay);
        final LocalDate actual = estimator.estimate(null);
        assertMatch(expected, actual);
    }

    @Test
    void testFromMotherBirthWithDateNoFather() {
        final Person person2 = createAnonymousSchoeller();
        final Person person3 = createAnonymousJones();

        final Family family1 = builder.createFamily("F1");
        builder.addWifeToFamily(family1, person2);
        builder.addChildToFamily(family1, person3);

        builder.createPersonEvent(person2, "Birth", "2 MAY 1950");

        final BirthDateFromAncestorsEstimator estimator =
                new BirthDateFromAncestorsEstimator(person3);
        final int birthYear = 1979;
        final int birthMonth = 1;
        final int birthDay = 1;
        final LocalDate expected =
                new LocalDate(birthYear, birthMonth, birthDay);
        final LocalDate actual = estimator.estimate(expected);
        assertMatch(expected, actual);
    }

    @Test
    void testFromMotherBirthWithoutDateNoFather() {
        final Person person2 = createAnonymousSchoeller();
        final Person person3 = createAnonymousJones();

        final Family family1 = builder.createFamily("F1");
        builder.addWifeToFamily(family1, person2);
        builder.addChildToFamily(family1, person3);

        builder.createPersonEvent(person2, "Birth", "2 MAY 1950");

        final BirthDateFromAncestorsEstimator estimator =
                new BirthDateFromAncestorsEstimator(person3);
        final int birthYear = 1977;
        final int birthMonth = 1;
        final int birthDay = 1;
        final LocalDate expected =
                new LocalDate(birthYear, birthMonth, birthDay);
        final LocalDate actual = estimator.estimate(null);
        assertMatch(expected, actual);
    }

    @Test
    void testFromFatherBirthWithDateNoMother() {
        final Person person1 = createJRandom();
        final Person person3 = createAnonymousJones();

        final Family family1 = builder.createFamily("F1");
        builder.addHusbandToFamily(family1, person1);
        builder.addChildToFamily(family1, person3);

        builder.createPersonEvent(person1, "Birth", "2 MAY 1950");

        final BirthDateFromAncestorsEstimator estimator =
                new BirthDateFromAncestorsEstimator(person3);
        final int birthYear = 1979;
        final int birthMonth = 1;
        final int birthDay = 1;
        final LocalDate expected =
                new LocalDate(birthYear, birthMonth, birthDay);
        final LocalDate actual = estimator.estimate(expected);
        assertMatch(expected, actual);
    }

    @Test
    void testFromFatherBirthWithoutDateNoMother() {
        final Person person1 = createJRandom();
        final Person person3 = createAnonymousJones();

        final Family family1 = builder.createFamily("F1");
        builder.addHusbandToFamily(family1, person1);
        builder.addChildToFamily(family1, person3);

        builder.createPersonEvent(person1, "Birth", "2 MAY 1950");

        final BirthDateFromAncestorsEstimator estimator =
                new BirthDateFromAncestorsEstimator(person3);
        final int birthYear = 1977;
        final int birthMonth = 1;
        final int birthDay = 1;
        final LocalDate expected =
                new LocalDate(birthYear, birthMonth, birthDay);
        final LocalDate actual = estimator.estimate(null);
        assertMatch(expected, actual);
    }

    @Test
    void testFromParentOtherWithDate() {
        final Person person1 = createJRandom();
        final Person person2 = createAnonymousSchoeller();
        final Person person3 = createAnonymousJones();

        final Family family1 = builder.createFamily("F1");
        builder.addHusbandToFamily(family1, person1);
        builder.addWifeToFamily(family1, person2);
        builder.addChildToFamily(family1, person3);

        builder.createPersonEvent(person1, "Occupation", "27 MAY 1984");

        final BirthDateFromAncestorsEstimator estimator =
                new BirthDateFromAncestorsEstimator(person3);
        final int birthYear = 1988;
        final int birthMonth = 10;
        final int birthDay = 10;
        final LocalDate expected =
                new LocalDate(birthYear, birthMonth, birthDay);
        final LocalDate actual = estimator.estimateFromOtherEvents(expected);
        assertMatch(expected, actual);
    }

    @Test
    void testFromParentOtherWithoutDate() {
        final Person person1 = createJRandom();
        final Person person2 = createAnonymousSchoeller();
        final Person person3 = createAnonymousJones();

        final Family family1 = builder.createFamily("F1");
        builder.addHusbandToFamily(family1, person1);
        builder.addWifeToFamily(family1, person2);
        builder.addChildToFamily(family1, person3);

        builder.createPersonEvent(person1, "Occupation", "27 MAY 1984");

        final BirthDateFromAncestorsEstimator estimator =
                new BirthDateFromAncestorsEstimator(person3);
        final int birthYear = 1986;
        final int birthMonth = 1;
        final int birthDay = 1;
        final LocalDate expected =
                new LocalDate(birthYear, birthMonth, birthDay);
        final LocalDate actual = estimator.estimateFromOtherEvents(null);
        assertMatch(expected, actual);
    }

    private void assertMatch(final LocalDate expected, final LocalDate actual) {
        assertTrue(expected.isEqual(actual), mismatchString(expected, actual));
    }

    private String mismatchString(final LocalDate expected,
            final LocalDate actual) {
        return "Don't match! expected: " + expected + ", actual: " + actual;
    }
}
