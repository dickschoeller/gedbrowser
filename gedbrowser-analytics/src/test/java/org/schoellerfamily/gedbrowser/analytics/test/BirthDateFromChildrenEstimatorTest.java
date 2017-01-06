package org.schoellerfamily.gedbrowser.analytics.test;

import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;
import org.schoellerfamily.gedbrowser.analytics.BirthDateFromChildrenEstimator;
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
public class BirthDateFromChildrenEstimatorTest {
    /** */
    @Test
    public final void testFromChildBirth() {
        final Root root = new Root(null);

        final Person person1 = new Person(root, new ObjectId("I1"));
        person1.insert(new Name(person1, "J. Random/Schoeller/"));

        final Person person3 = new Person(root, new ObjectId("I3"));
        final Attribute birth3 = new Attribute(person3, "Birth");
        person3.insert(new Name(person3, "Anonymous/Jones/"));
        person3.insert(birth3);
        birth3.insert(new Date(birth3, "11 JUL 1960"));

        final ObjectId xrefFam1 = new ObjectId("F1");
        final Family family1 = new Family(root, xrefFam1);
        final FamS famS1 = new FamS(person1, "FAMS", xrefFam1);
        final FamC famC3 = new FamC(person3, "FAMC", xrefFam1);

        final Husband husband1 = new Husband(family1, "Husband",
                new ObjectId("I1"));
        final Child child1 = new Child(family1, "Child", new ObjectId("I3"));

        root.insert(family1);
        root.insert(person1);
        root.insert(person3);

        family1.insert(husband1);
        family1.insert(child1);
        person1.insert(famS1);
        person3.insert(famC3);

        final BirthDateFromChildrenEstimator estimator =
                new BirthDateFromChildrenEstimator(person1);
        final int birthYear = 1933;
        final int birthMonth = 7;
        final int birthDay = 1;
        final LocalDate expected =
                new LocalDate(birthYear, birthMonth, birthDay);
        final LocalDate actual = estimator.estimate(null);
        assertMatch(expected, actual);
    }

    /** */
    @Test
    public final void testFromChildAdultEvent() {
        final Root root = new Root(null);

        final Person person1 = new Person(root, new ObjectId("I1"));
        person1.insert(new Name(person1, "J. Random/Schoeller/"));

        final Person person3 = new Person(root, new ObjectId("I3"));
        person3.insert(new Name(person3, "Anonymous/Jones/"));
        final Attribute event3 = new Attribute(person3, "Occupation");
        person3.insert(event3);
        event3.insert(new Date(event3, "11 JUL 1985"));

        final ObjectId xrefFam1 = new ObjectId("F1");
        final Family family1 = new Family(root, xrefFam1);
        final FamS famS1 = new FamS(person1, "FAMS", xrefFam1);
        final FamC famC3 = new FamC(person3, "FAMC", xrefFam1);

        final Husband husband1 = new Husband(family1, "Husband",
                new ObjectId("I1"));
        final Child child1 = new Child(family1, "Child", new ObjectId("I3"));

        root.insert(family1);
        root.insert(person1);
        root.insert(person3);

        family1.insert(husband1);
        family1.insert(child1);
        person1.insert(famS1);
        person3.insert(famC3);

        final BirthDateFromChildrenEstimator estimator =
                new BirthDateFromChildrenEstimator(person1);
        final int birthYear = 1933;
        final int birthMonth = 1;
        final int birthDay = 1;
        final LocalDate expected =
                new LocalDate(birthYear, birthMonth, birthDay);
        final LocalDate actual = estimator.estimate(null);
        assertMatch(expected, actual);
    }

    /** */
    @Test
    public final void testFromSecondChildBirth() {
        final Root root = new Root(null);

        final Person person1 = new Person(root, new ObjectId("I1"));
        person1.insert(new Name(person1, "J. Random/Schoeller/"));

        final Person person2 = new Person(root, new ObjectId("I2"));
        person2.insert(new Name(person2, "Anonyma/Jones/"));

        final Person person3 = new Person(root, new ObjectId("I3"));
        final Attribute birth3 = new Attribute(person3, "Birth");
        person3.insert(new Name(person3, "Anonymous/Jones/"));
        person3.insert(birth3);
        birth3.insert(new Date(birth3, "11 JUL 1962"));

        final ObjectId xrefFam1 = new ObjectId("F1");
        final Family family1 = new Family(root, xrefFam1);
        final FamS famS1 = new FamS(person1, "FAMS", xrefFam1);
        final FamC famC2 = new FamC(person2, "FAMC", xrefFam1);
        final FamC famC3 = new FamC(person3, "FAMC", xrefFam1);

        final Husband husband1 = new Husband(family1, "Husband",
                new ObjectId("I1"));
        final Child child2 = new Child(family1, "Child", new ObjectId("I2"));
        final Child child3 = new Child(family1, "Child", new ObjectId("I3"));

        root.insert(family1);
        root.insert(person1);
        root.insert(person2);
        root.insert(person3);

        family1.insert(husband1);
        family1.insert(child2);
        family1.insert(child3);
        person1.insert(famS1);
        person2.insert(famC2);
        person3.insert(famC3);

        final BirthDateFromChildrenEstimator estimator =
                new BirthDateFromChildrenEstimator(person1);
        final int birthYear = 1933;
        final int birthMonth = 7;
        final int birthDay = 1;
        final LocalDate expected =
                new LocalDate(birthYear, birthMonth, birthDay);
        final LocalDate actual = estimator.estimate(null);
        assertMatch(expected, actual);
    }

    /** */
    @Test
    public final void testFromSpouseParent() {
        final Root root = new Root(null);

        final Person person1 = new Person(root, new ObjectId("I1"));
        person1.insert(new Name(person1, "J. Random/Schoeller/"));

        final Person person2a = new Person(root, new ObjectId("I2a"));
        person2a.insert(new Name(person1, "Anonyma/Schoeller/"));

        final Person person2 = new Person(root, new ObjectId("I2"));
        person2.insert(new Name(person1, "Anonymous/Schoeller/"));

        final Person person3 = new Person(root, new ObjectId("I3"));
        person3.insert(new Name(person3, "Anonyma/Jones/"));

        final Person person4 = new Person(root, new ObjectId("I4"));
        person4.insert(new Name(person4, "Too Tall/Jones/"));
        final Attribute birth4 = new Attribute(person4, "Birth");
        birth4.insert(new Date(birth4, "11 JUL 1960"));
        person4.insert(birth4);

        final ObjectId xrefFam1 = new ObjectId("F1");
        final Family family1 = new Family(root, xrefFam1);
        final FamS famS1 = new FamS(person1, "FAMS", xrefFam1);
        final FamC famC2a = new FamC(person2a, "FAMC", xrefFam1);
        final FamC famC2 = new FamC(person2, "FAMC", xrefFam1);

        final Husband husband1 =
                new Husband(family1, "Husband", new ObjectId("I1"));
        final Child child2a = new Child(family1, "Child", new ObjectId("I2a"));
        final Child child2 = new Child(family1, "Child", new ObjectId("I2"));

        final ObjectId xrefFam2 = new ObjectId("F2");
        final Family family2 = new Family(root, xrefFam2);
        final FamS famS4 = new FamS(person4, "FAMS", xrefFam2);
        final FamC famC3 = new FamC(person3, "FAMC", xrefFam2);

        final Husband husband4 =
                new Husband(family2, "Husband", new ObjectId("I4"));
        final Child child3 = new Child(family2, "Child", new ObjectId("I3"));

        final ObjectId xrefFam3 = new ObjectId("F3");
        final Family family3 = new Family(root, xrefFam3);
        final FamS famS2 = new FamS(person2, "FAMS", xrefFam3);
        final FamS famS3 = new FamS(person3, "FAMS", xrefFam3);
        final Husband husband2 =
                new Husband(family3, "Husband", new ObjectId("I2"));
        final Wife wife3 =
                new Wife(family3, "Wife", new ObjectId("I3"));

        root.insert(family1);
        root.insert(family2);
        root.insert(family3);
        root.insert(person1);
        root.insert(person2);
        root.insert(person2a);
        root.insert(person3);
        root.insert(person4);

        family1.insert(husband1);
        family1.insert(child2a);
        family1.insert(child2);

        family2.insert(husband4);
        family2.insert(child3);

        family3.insert(husband2);
        family3.insert(wife3);

        person1.insert(famS1);
        person2.insert(famC2);
        person2a.insert(famC2a);
        person2.insert(famS2);
        person3.insert(famC3);
        person3.insert(famS3);
        person4.insert(famS4);

        final BirthDateFromChildrenEstimator estimator =
                new BirthDateFromChildrenEstimator(person1);
        final int birthYear = 1960;
        final int birthMonth = 1;
        final int birthDay = 1;
        final LocalDate expected =
                new LocalDate(birthYear, birthMonth, birthDay);
        final LocalDate actual = estimator.estimateFromSpousesAncestors(null);
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
