package org.schoellerfamily.gedbrowser.analytics.test;

import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;
import org.schoellerfamily.gedbrowser.analytics.BirthDateFromAncestorsEstimator;
import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;

/**
 * @author Dick Schoeller
 */
public class BirthDateFromAncestorsEstimatorTest {
    /** */
    @Test
    public final void testFromParentsMarriageWithDate() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person1 = builder.createPerson1();
        final Person person2 = builder.createPerson2();
        final Person person3 = builder.createPerson3();

        final Family family1 = builder.createFamily1();

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

    /** */
    @Test
    public final void testFromParentsMarriageWithoutDate() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person1 = builder.createPerson1();
        final Person person2 = builder.createPerson2();
        final Person person3 = builder.createPerson3();

        final Family family1 = builder.createFamily1();

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

    /** */
    @Test
    public final void testFromGrandparentsMarriageWithoutDate() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person1 = builder.createPerson1();
        final Person person2 = builder.createPerson2();
        final Person person3 = builder.createPerson3();
        final Person person4 = builder.createPerson4();

        final Family family1 = builder.createFamily1();
        builder.addHusbandToFamily(family1, person1);
        builder.addWifeToFamily(family1, person2);
        builder.addChildToFamily(family1, person3);
        builder.createFamilyEvent(family1, "Marriage", "27 MAY 1984");

        final Family family2 = builder.createFamily2();
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

    /** */
    @Test
    public final void testFromParentBirthWithDate() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person1 = builder.createPerson1();
        final Person person2 = builder.createPerson2();
        final Person person3 = builder.createPerson3();

        final Family family1 = builder.createFamily1();
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

    /** */
    @Test
    public final void testFromParentBirthWithoutDate() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person1 = builder.createPerson1();
        final Person person2 = builder.createPerson2();
        final Person person3 = builder.createPerson3();

        final Family family1 = builder.createFamily1();
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

    /** */
    @Test
    public final void testFromMotherBirthWithDateNoFather() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person2 = builder.createPerson2();
        final Person person3 = builder.createPerson3();

        final Family family1 = builder.createFamily1();
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

    /** */
    @Test
    public final void testFromMotherBirthWithoutDateNoFather() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person2 = builder.createPerson2();
        final Person person3 = builder.createPerson3();

        final Family family1 = builder.createFamily1();
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

    /** */
    @Test
    public final void testFromFatherBirthWithDateNoMother() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person1 = builder.createPerson1();
        final Person person3 = builder.createPerson3();

        final Family family1 = builder.createFamily1();
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

    /** */
    @Test
    public final void testFromFatherBirthWithoutDateNoMother() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person1 = builder.createPerson1();
        final Person person3 = builder.createPerson3();

        final Family family1 = builder.createFamily1();
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

    /** */
    @Test
    public final void testFromParentOtherWithDate() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person1 = builder.createPerson1();
        final Person person2 = builder.createPerson2();
        final Person person3 = builder.createPerson3();

        final Family family1 = builder.createFamily1();
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

    /** */
    @Test
    public final void testFromParentOtherWithoutDate() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person1 = builder.createPerson1();
        final Person person2 = builder.createPerson2();
        final Person person3 = builder.createPerson3();

        final Family family1 = builder.createFamily1();
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

    /**
     * @param expected expected date
     * @param actual actual date
     */
    private void assertMatch(final LocalDate expected, final LocalDate actual) {
        Assert.assertTrue(mismatchString(expected, actual),
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
