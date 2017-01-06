package org.schoellerfamily.gedbrowser.analytics.test;

import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;
import org.schoellerfamily.gedbrowser.analytics.BirthDateFromAncestorsEstimator;
import org.schoellerfamily.gedbrowser.datamodel.Attribute;
import org.schoellerfamily.gedbrowser.datamodel.Child;
import org.schoellerfamily.gedbrowser.datamodel.Date;
import org.schoellerfamily.gedbrowser.datamodel.FamC;
import org.schoellerfamily.gedbrowser.datamodel.FamS;
import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.Husband;
import org.schoellerfamily.gedbrowser.datamodel.Name;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.Wife;

/**
 * @author Dick Schoeller
 */
@SuppressWarnings("PMD.TooManyMethods")
public class BirthDateFromAncestorsEstimatorTest {
    /** */
    @Test
    public final void testFromParentsMarriageWithDate() {
        final Root root = new Root(null);

        final Person person1 = new Person(root, new ObjectId("I1"));
        person1.insert(new Name(person1, "J. Random/Schoeller/"));

        final Person person2 = new Person(root, new ObjectId("I2"));
        person2.insert(new Name(person2, "Anonymous/Smith/"));

        final Person person3 = new Person(root, new ObjectId("I3"));
        person3.insert(new Name(person3, "Anonymous/Jones/"));

        final ObjectId xrefFam1 = new ObjectId("F1");
        final Family family1 = new Family(root, xrefFam1);
        final FamS famS1 = new FamS(person1, "FAMS", xrefFam1);
        final FamS famS2 = new FamS(person2, "FAMS", xrefFam1);
        final FamC famC3 = new FamC(person3, "FAMC", xrefFam1);

        final Husband husband1 = new Husband(family1, "Husband",
                new ObjectId("I1"));
        final Wife wife1 = new Wife(family1, "Wife", new ObjectId("I2"));
        final Child child1 = new Child(family1, "Child", new ObjectId("I3"));

        final Attribute marriage1 = new Attribute(family1, "Marriage");
        family1.insert(marriage1);
        final Date marriageDate1 = new Date(marriage1, "27 MAY 1984");
        marriage1.insert(marriageDate1);

        root.insert(family1);
        root.insert(person1);
        root.insert(person2);
        root.insert(person3);

        family1.insert(husband1);
        family1.insert(wife1);
        family1.insert(child1);
        person1.insert(famS1);
        person2.insert(famS2);
        person3.insert(famC3);

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
        final Root root = new Root(null);

        final Person person1 = new Person(root, new ObjectId("I1"));
        person1.insert(new Name(person1, "J. Random/Schoeller/"));

        final Person person2 = new Person(root, new ObjectId("I2"));
        person2.insert(new Name(person2, "Anonymous/Smith/"));

        final Person person3 = new Person(root, new ObjectId("I3"));
        person3.insert(new Name(person3, "Anonymous/Jones/"));

        final ObjectId xrefFam1 = new ObjectId("F1");
        final Family family1 = new Family(root, xrefFam1);
        final FamS famS1 = new FamS(person1, "FAMS", xrefFam1);
        final FamS famS2 = new FamS(person2, "FAMS", xrefFam1);
        final FamC famC3 = new FamC(person3, "FAMC", xrefFam1);

        final Husband husband1 = new Husband(family1, "Husband",
                new ObjectId("I1"));
        final Wife wife1 = new Wife(family1, "Wife", new ObjectId("I2"));
        final Child child1 = new Child(family1, "Child", new ObjectId("I3"));

        final Attribute marriage1 = new Attribute(family1, "Marriage");
        family1.insert(marriage1);
        final Date marriageDate1 = new Date(marriage1, "27 MAY 1984");
        marriage1.insert(marriageDate1);

        root.insert(family1);
        root.insert(person1);
        root.insert(person2);
        root.insert(person3);

        family1.insert(husband1);
        family1.insert(wife1);
        family1.insert(child1);
        person1.insert(famS1);
        person2.insert(famS2);
        person3.insert(famC3);

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
        final Root root = new Root(null);

        final Person person0 = new Person(root, new ObjectId("I0"));
        person0.insert(new Name(person0, "Anonymous/Jones/Jr."));

        final Person person1 = new Person(root, new ObjectId("I1"));
        person1.insert(new Name(person1, "J. Random/Schoeller/"));

        final Person person2 = new Person(root, new ObjectId("I2"));
        person2.insert(new Name(person2, "Anonymous/Smith/"));

        final Person person3 = new Person(root, new ObjectId("I3"));
        person3.insert(new Name(person3, "Anonymous/Jones/"));

        final ObjectId xrefFam1 = new ObjectId("F1");
        final Family family1 = new Family(root, xrefFam1);
        final FamS famS1 = new FamS(person1, "FAMS", xrefFam1);
        final FamS famS2 = new FamS(person2, "FAMS", xrefFam1);
        final FamC famC3 = new FamC(person3, "FAMC", xrefFam1);

        final ObjectId xrefFam0 = new ObjectId("F0");
        final Family family0 = new Family(root, xrefFam0);
        final FamS famS0 = new FamS(person3, "FAMS", xrefFam0);
        final FamC famC0 = new FamC(person0, "FAMC", xrefFam0);

        final Husband husband1 = new Husband(family1, "Husband",
                new ObjectId("I1"));
        final Wife wife1 = new Wife(family1, "Wife", new ObjectId("I2"));
        final Child child1 = new Child(family1, "Child", new ObjectId("I3"));

        final Husband husband0 = new Husband(family0, "Husband",
                new ObjectId("I3"));
        final Child child0 = new Child(family0, "Child", new ObjectId("I0"));

        final Attribute marriage1 = new Attribute(family1, "Marriage");
        family1.insert(marriage1);
        final Date marriageDate1 = new Date(marriage1, "27 MAY 1984");
        marriage1.insert(marriageDate1);

        root.insert(family0);
        root.insert(family1);
        root.insert(person0);
        root.insert(person1);
        root.insert(person2);
        root.insert(person3);

        family0.insert(husband0);
        family0.insert(child0);
        family1.insert(husband1);
        family1.insert(wife1);
        family1.insert(child1);
        person1.insert(famS1);
        person2.insert(famS2);
        person3.insert(famC3);
        person3.insert(famS0);
        person0.insert(famC0);

        final BirthDateFromAncestorsEstimator estimator =
                new BirthDateFromAncestorsEstimator(person0);
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
        final Root root = new Root(null);

        final Person person1 = new Person(root, new ObjectId("I1"));
        person1.insert(new Name(person1, "J. Random/Schoeller/"));

        final Person person2 = new Person(root, new ObjectId("I2"));
        person2.insert(new Name(person2, "Anonymous/Smith/"));

        final Person person3 = new Person(root, new ObjectId("I3"));
        person3.insert(new Name(person3, "Anonymous/Jones/"));

        final ObjectId xrefFam1 = new ObjectId("F1");
        final Family family1 = new Family(root, xrefFam1);
        final FamS famS1 = new FamS(person1, "FAMS", xrefFam1);
        final FamS famS2 = new FamS(person2, "FAMS", xrefFam1);
        final FamC famC3 = new FamC(person3, "FAMC", xrefFam1);

        final Husband husband1 = new Husband(family1, "Husband",
                new ObjectId("I1"));
        final Wife wife1 = new Wife(family1, "Wife", new ObjectId("I2"));
        final Child child1 = new Child(family1, "Child", new ObjectId("I3"));

        final Attribute birth1 = new Attribute(person1, "Birth");
        person1.insert(birth1);
        final Date birthDate1 = new Date(birth1, "1 MAY 1950");
        birth1.insert(birthDate1);

        root.insert(family1);
        root.insert(person1);
        root.insert(person2);
        root.insert(person3);

        family1.insert(husband1);
        family1.insert(wife1);
        family1.insert(child1);
        person1.insert(famS1);
        person2.insert(famS2);
        person3.insert(famC3);

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
    public final void testFromParentBirthWithoutDate() {
        final Root root = new Root(null);

        final Person person1 = new Person(root, new ObjectId("I1"));
        person1.insert(new Name(person1, "J. Random/Schoeller/"));

        final Person person2 = new Person(root, new ObjectId("I2"));
        person2.insert(new Name(person2, "Anonymous/Smith/"));

        final Person person3 = new Person(root, new ObjectId("I3"));
        person3.insert(new Name(person3, "Anonymous/Jones/"));

        final ObjectId xrefFam1 = new ObjectId("F1");
        final Family family1 = new Family(root, xrefFam1);
        final FamS famS1 = new FamS(person1, "FAMS", xrefFam1);
        final FamS famS2 = new FamS(person2, "FAMS", xrefFam1);
        final FamC famC3 = new FamC(person3, "FAMC", xrefFam1);

        final Husband husband1 = new Husband(family1, "Husband",
                new ObjectId("I1"));
        final Wife wife1 = new Wife(family1, "Wife", new ObjectId("I2"));
        final Child child1 = new Child(family1, "Child", new ObjectId("I3"));

        final Attribute birth1 = new Attribute(person1, "Birth");
        person1.insert(birth1);
        final Date birthDate1 = new Date(birth1, "1 MAY 1950");
        birth1.insert(birthDate1);

        root.insert(family1);
        root.insert(person1);
        root.insert(person2);
        root.insert(person3);

        family1.insert(husband1);
        family1.insert(wife1);
        family1.insert(child1);
        person1.insert(famS1);
        person2.insert(famS2);
        person3.insert(famC3);

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
        final Root root = new Root(null);

        final Person person2 = new Person(root, new ObjectId("I2"));
        person2.insert(new Name(person2, "Anonymous/Smith/"));

        final Person person3 = new Person(root, new ObjectId("I3"));
        person3.insert(new Name(person3, "Anonymous/Jones/"));

        final ObjectId xrefFam1 = new ObjectId("F1");
        final Family family1 = new Family(root, xrefFam1);
        final FamS famS2 = new FamS(person2, "FAMS", xrefFam1);
        final FamC famC3 = new FamC(person3, "FAMC", xrefFam1);

        final Wife wife1 = new Wife(family1, "Wife", new ObjectId("I2"));
        final Child child1 = new Child(family1, "Child", new ObjectId("I3"));

        final Attribute birth1 = new Attribute(person2, "Birth");
        person2.insert(birth1);
        final Date birthDate1 = new Date(birth1, "1 MAY 1950");
        birth1.insert(birthDate1);

        root.insert(family1);
        root.insert(person2);
        root.insert(person3);

        family1.insert(wife1);
        family1.insert(child1);
        person2.insert(famS2);
        person3.insert(famC3);

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
        final Root root = new Root(null);

        final Person person2 = new Person(root, new ObjectId("I2"));
        person2.insert(new Name(person2, "Anonymous/Smith/"));

        final Person person3 = new Person(root, new ObjectId("I3"));
        person3.insert(new Name(person3, "Anonymous/Jones/"));

        final ObjectId xrefFam1 = new ObjectId("F1");
        final Family family1 = new Family(root, xrefFam1);
        final FamS famS2 = new FamS(person2, "FAMS", xrefFam1);
        final FamC famC3 = new FamC(person3, "FAMC", xrefFam1);

        final Wife wife1 = new Wife(family1, "Wife", new ObjectId("I2"));
        final Child child1 = new Child(family1, "Child", new ObjectId("I3"));

        final Attribute birth1 = new Attribute(person2, "Birth");
        person2.insert(birth1);
        final Date birthDate1 = new Date(birth1, "1 MAY 1950");
        birth1.insert(birthDate1);

        root.insert(family1);
        root.insert(person2);
        root.insert(person3);

        family1.insert(wife1);
        family1.insert(child1);
        person2.insert(famS2);
        person3.insert(famC3);

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
        final Root root = new Root(null);

        final Person person1 = new Person(root, new ObjectId("I1"));
        person1.insert(new Name(person1, "J. Random/Schoeller/"));

        final Person person3 = new Person(root, new ObjectId("I3"));
        person3.insert(new Name(person3, "Anonymous/Jones/"));

        final ObjectId xrefFam1 = new ObjectId("F1");
        final Family family1 = new Family(root, xrefFam1);
        final FamS famS1 = new FamS(person1, "FAMS", xrefFam1);
        final FamC famC3 = new FamC(person3, "FAMC", xrefFam1);

        final Husband husband1 = new Husband(family1, "Husband",
                new ObjectId("I1"));
        final Child child1 = new Child(family1, "Child", new ObjectId("I3"));

        final Attribute birth1 = new Attribute(person1, "Birth");
        person1.insert(birth1);
        final Date birthDate1 = new Date(birth1, "1 MAY 1950");
        birth1.insert(birthDate1);

        root.insert(family1);
        root.insert(person1);
        root.insert(person3);

        family1.insert(husband1);
        family1.insert(child1);
        person1.insert(famS1);
        person3.insert(famC3);

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
        final Root root = new Root(null);

        final Person person1 = new Person(root, new ObjectId("I1"));
        person1.insert(new Name(person1, "J. Random/Schoeller/"));

        final Person person3 = new Person(root, new ObjectId("I3"));
        person3.insert(new Name(person3, "Anonymous/Jones/"));

        final ObjectId xrefFam1 = new ObjectId("F1");
        final Family family1 = new Family(root, xrefFam1);
        final FamS famS1 = new FamS(person1, "FAMS", xrefFam1);
        final FamC famC3 = new FamC(person3, "FAMC", xrefFam1);

        final Husband husband1 = new Husband(family1, "Husband",
                new ObjectId("I1"));
        final Child child1 = new Child(family1, "Child", new ObjectId("I3"));

        final Attribute birth1 = new Attribute(person1, "Birth");
        person1.insert(birth1);
        final Date birthDate1 = new Date(birth1, "1 MAY 1950");
        birth1.insert(birthDate1);

        root.insert(family1);
        root.insert(person1);
        root.insert(person3);

        family1.insert(husband1);
        family1.insert(child1);
        person1.insert(famS1);
        person3.insert(famC3);

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
        final Root root = new Root(null);

        final Person person1 = new Person(root, new ObjectId("I1"));
        person1.insert(new Name(person1, "J. Random/Schoeller/"));

        final Person person2 = new Person(root, new ObjectId("I2"));
        person2.insert(new Name(person2, "Anonymous/Smith/"));

        final Person person3 = new Person(root, new ObjectId("I3"));
        person3.insert(new Name(person3, "Anonymous/Jones/"));

        final ObjectId xrefFam1 = new ObjectId("F1");
        final Family family1 = new Family(root, xrefFam1);
        final FamS famS1 = new FamS(person1, "FAMS", xrefFam1);
        final FamS famS2 = new FamS(person2, "FAMS", xrefFam1);
        final FamC famC3 = new FamC(person3, "FAMC", xrefFam1);

        final Husband husband1 = new Husband(family1, "Husband",
                new ObjectId("I1"));
        final Wife wife1 = new Wife(family1, "Wife", new ObjectId("I2"));
        final Child child1 = new Child(family1, "Child", new ObjectId("I3"));

        final Attribute event1 = new Attribute(person1, "Occupation");
        person1.insert(event1);
        final Date eventDate1 = new Date(event1, "27 MAY 1984");
        event1.insert(eventDate1);

        root.insert(family1);
        root.insert(person1);
        root.insert(person2);
        root.insert(person3);

        family1.insert(husband1);
        family1.insert(wife1);
        family1.insert(child1);
        person1.insert(famS1);
        person2.insert(famS2);
        person3.insert(famC3);

        final BirthDateFromAncestorsEstimator estimator =
                new BirthDateFromAncestorsEstimator(person3);
        final int birthYear = 1988;
        final int birthMonth = 1;
        final int birthDay = 1;
        final LocalDate expected =
                new LocalDate(birthYear, birthMonth, birthDay);
        final LocalDate actual = estimator.estimateFromOtherEvents(expected);
        assertMatch(expected, actual);
    }

    /** */
    @Test
    public final void testFromParentOtherWithoutDate() {
        final Root root = new Root(null);

        final Person person1 = new Person(root, new ObjectId("I1"));
        person1.insert(new Name(person1, "J. Random/Schoeller/"));

        final Person person2 = new Person(root, new ObjectId("I2"));
        person2.insert(new Name(person2, "Anonymous/Smith/"));

        final Person person3 = new Person(root, new ObjectId("I3"));
        person3.insert(new Name(person3, "Anonymous/Jones/"));

        final ObjectId xrefFam1 = new ObjectId("F1");
        final Family family1 = new Family(root, xrefFam1);
        final FamS famS1 = new FamS(person1, "FAMS", xrefFam1);
        final FamS famS2 = new FamS(person2, "FAMS", xrefFam1);
        final FamC famC3 = new FamC(person3, "FAMC", xrefFam1);

        final Husband husband1 = new Husband(family1, "Husband",
                new ObjectId("I1"));
        final Wife wife1 = new Wife(family1, "Wife", new ObjectId("I2"));
        final Child child1 = new Child(family1, "Child", new ObjectId("I3"));

        final Attribute event1 = new Attribute(person1, "Occupation");
        person1.insert(event1);
        final Date eventDate1 = new Date(event1, "27 MAY 1984");
        event1.insert(eventDate1);

        root.insert(family1);
        root.insert(person1);
        root.insert(person2);
        root.insert(person3);

        family1.insert(husband1);
        family1.insert(wife1);
        family1.insert(child1);
        person1.insert(famS1);
        person2.insert(famS2);
        person3.insert(famC3);

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
