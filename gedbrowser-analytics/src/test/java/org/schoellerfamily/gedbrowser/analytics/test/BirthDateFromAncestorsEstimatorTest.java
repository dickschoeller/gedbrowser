package org.schoellerfamily.gedbrowser.analytics.test;

import static org.junit.Assert.assertTrue;

import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.schoellerfamily.gedbrowser.analytics.BirthDateFromAncestorsEstimator;
import org.schoellerfamily.gedbrowser.analytics.order.test.AnalyzerTest;
import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.util.FamilyBuilder;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;
import org.schoellerfamily.gedbrowser.datamodel.util.PersonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Dick Schoeller
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestConfiguration.class })
public class BirthDateFromAncestorsEstimatorTest implements AnalyzerTest {
    /** */
    @Autowired
    private GedObjectBuilder builder;

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
    @Test
    public final void testFromParentsMarriageWithDate() {
        final Person person1 = createJRandom();
        final Person person2 = createAnonymousSchoeller();
        final Person person3 = createAnonymousJones();

        final Family family1 = builder.getFamilyBuilder().createFamily("F1");

        builder.getFamilyBuilder().addHusbandToFamily(family1, person1);
        builder.getFamilyBuilder().addWifeToFamily(family1, person2);
        builder.getFamilyBuilder().addChildToFamily(family1, person3);

        familyBuilder().createFamilyEvent(family1, "Marriage", "27 MAY 1984");

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

    /** */
    @Test
    public final void testFromParentsMarriageWithoutDate() {
        final Person person1 = createJRandom();
        final Person person2 = createAnonymousSchoeller();
        final Person person3 = createAnonymousJones();

        final Family family1 = familyBuilder().createFamily("F1");

        familyBuilder().addHusbandToFamily(family1, person1);
        familyBuilder().addWifeToFamily(family1, person2);
        familyBuilder().addChildToFamily(family1, person3);

        familyBuilder().createFamilyEvent(family1, "Marriage", "27 MAY 1984");

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

    /** */
    @Test
    public final void testFromGrandparentsMarriageWithoutDate() {
        final Person person1 = createJRandom();
        final Person person2 = createAnonymousSchoeller();
        final Person person3 = createAnonymousJones();
        final Person person4 = createTooTall();

        final Family family1 = familyBuilder().createFamily("F1");
        familyBuilder().addHusbandToFamily(family1, person1);
        familyBuilder().addWifeToFamily(family1, person2);
        familyBuilder().addChildToFamily(family1, person3);
        familyBuilder().createFamilyEvent(family1, "Marriage", "27 MAY 1984");

        final Family family2 = familyBuilder().createFamily("F2");
        familyBuilder().addHusbandToFamily(family2, person3);
        familyBuilder().addChildToFamily(family2, person4);

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

    /** */
    @Test
    public final void testFromParentBirthWithDate() {
        final Person person1 = createJRandom();
        final Person person2 = createAnonymousSchoeller();
        final Person person3 = createAnonymousJones();

        final Family family1 = familyBuilder().createFamily("F1");
        familyBuilder().addHusbandToFamily(family1, person1);
        familyBuilder().addWifeToFamily(family1, person2);
        familyBuilder().addChildToFamily(family1, person3);

        personBuilder().createPersonEvent(person1, "Birth", "2 MAY 1950");

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

    /** */
    @Test
    public final void testFromParentBirthWithoutDate() {
        final Person person1 = createJRandom();
        final Person person2 = createAnonymousSchoeller();
        final Person person3 = createAnonymousJones();

        final Family family1 = familyBuilder().createFamily("F1");
        familyBuilder().addHusbandToFamily(family1, person1);
        familyBuilder().addWifeToFamily(family1, person2);
        familyBuilder().addChildToFamily(family1, person3);

        personBuilder().createPersonEvent(person1, "Birth", "2 MAY 1950");

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

    /** */
    @Test
    public final void testFromMotherBirthWithDateNoFather() {
        final Person person2 = createAnonymousSchoeller();
        final Person person3 = createAnonymousJones();

        final Family family1 = familyBuilder().createFamily("F1");
        familyBuilder().addWifeToFamily(family1, person2);
        familyBuilder().addChildToFamily(family1, person3);

        personBuilder().createPersonEvent(person2, "Birth", "2 MAY 1950");

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

    /** */
    @Test
    public final void testFromMotherBirthWithoutDateNoFather() {
        final Person person2 = createAnonymousSchoeller();
        final Person person3 = createAnonymousJones();

        final Family family1 = familyBuilder().createFamily("F1");
        familyBuilder().addWifeToFamily(family1, person2);
        familyBuilder().addChildToFamily(family1, person3);

        personBuilder().createPersonEvent(person2, "Birth", "2 MAY 1950");

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

    /** */
    @Test
    public final void testFromFatherBirthWithDateNoMother() {
        final Person person1 = createJRandom();
        final Person person3 = createAnonymousJones();

        final Family family1 = familyBuilder().createFamily("F1");
        familyBuilder().addHusbandToFamily(family1, person1);
        familyBuilder().addChildToFamily(family1, person3);

        personBuilder().createPersonEvent(person1, "Birth", "2 MAY 1950");

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

    /** */
    @Test
    public final void testFromFatherBirthWithoutDateNoMother() {
        final Person person1 = createJRandom();
        final Person person3 = createAnonymousJones();

        final Family family1 = familyBuilder().createFamily("F1");
        familyBuilder().addHusbandToFamily(family1, person1);
        familyBuilder().addChildToFamily(family1, person3);

        personBuilder().createPersonEvent(person1, "Birth", "2 MAY 1950");

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

    /** */
    @Test
    public final void testFromParentOtherWithDate() {
        final Person person1 = createJRandom();
        final Person person2 = createAnonymousSchoeller();
        final Person person3 = createAnonymousJones();

        final Family family1 = familyBuilder().createFamily("F1");
        familyBuilder().addHusbandToFamily(family1, person1);
        familyBuilder().addWifeToFamily(family1, person2);
        familyBuilder().addChildToFamily(family1, person3);

        personBuilder().createPersonEvent(person1, "Occupation", "27 MAY 1984");

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

    /** */
    @Test
    public final void testFromParentOtherWithoutDate() {
        final Person person1 = createJRandom();
        final Person person2 = createAnonymousSchoeller();
        final Person person3 = createAnonymousJones();

        final Family family1 = familyBuilder().createFamily("F1");
        familyBuilder().addHusbandToFamily(family1, person1);
        familyBuilder().addWifeToFamily(family1, person2);
        familyBuilder().addChildToFamily(family1, person3);

        personBuilder().createPersonEvent(person1, "Occupation", "27 MAY 1984");

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
