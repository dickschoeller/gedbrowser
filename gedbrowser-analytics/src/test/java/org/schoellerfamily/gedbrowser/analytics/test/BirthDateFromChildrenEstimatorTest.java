package org.schoellerfamily.gedbrowser.analytics.test;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.joda.time.LocalDate;
import org.junit.Test;
import org.schoellerfamily.gedbrowser.analytics.BirthDateFromChildrenEstimator;
import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;

/**
 * @author Dick Schoeller
 */
public class BirthDateFromChildrenEstimatorTest {
    /** */
    @Test
    public final void testFromChildBirth() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person1 = builder.createPerson1();
        final Person person3 = builder.createPerson3();

        builder.createPersonEvent(person3, "Birth", "11 JUL 1960");

        final Family family1 = builder.createFamily1();

        builder.addHusbandToFamily(family1, person1);
        builder.addChildToFamily(family1, person3);

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
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person1 = builder.createPerson1();
        final Person person3 = builder.createPerson3();

        builder.createPersonEvent(person3, "Occupation", "11 JUL 1985");

        final Family family1 = builder.createFamily1();

        builder.addHusbandToFamily(family1, person1);
        builder.addChildToFamily(family1, person3);

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
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person1 = builder.createPerson1();
        final Person person2 = builder.createPerson2();
        final Person person3 = builder.createPerson3();

        builder.createPersonEvent(person3, "Birth", "11 JUL 1962");

        final Family family1 = builder.createFamily1();

        builder.addHusbandToFamily(family1, person1);
        builder.addChildToFamily(family1, person2);
        builder.addChildToFamily(family1, person3);

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
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person1 = builder.createPerson1();
        final Person person2 = builder.createPerson2();
        final Person person3 = builder.createPerson3();
        final Person person4 = builder.createPerson4();
        final Person person5 = builder.createPerson5();

        builder.createPersonEvent(person4, "Birth", "11 JUL 1960");

        final Family family1 = builder.createFamily1();

        builder.addHusbandToFamily(family1, person1);
        builder.addChildToFamily(family1, person5);
        builder.addChildToFamily(family1, person2);

        final Family family2 = builder.createFamily2();

        builder.addHusbandToFamily(family2, person4);
        builder.addChildToFamily(family2, person3);

        final Family family3 = builder.createFamily3();

        builder.addHusbandToFamily(family3, person2);
        builder.addWifeToFamily(family3, person3);

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

    /** */
    @Test
    public final void testFromNada() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person1 = builder.createPerson1();
        final BirthDateFromChildrenEstimator estimator =
                new BirthDateFromChildrenEstimator(person1);
        final LocalDate actual = estimator.estimateFromSpousesAncestors(null);
        assertNull("Expect a null when given this little", actual);
    }

    /** */
    @Test
    public final void testFromNadaWithDate() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person1 = builder.createPerson1();
        final BirthDateFromChildrenEstimator estimator =
                new BirthDateFromChildrenEstimator(person1);
        final int birthYear = 1960;
        final int birthMonth = 1;
        final int birthDay = 1;
        final LocalDate expected =
                new LocalDate(birthYear, birthMonth, birthDay);
        final LocalDate actual =
                estimator.estimateFromSpousesAncestors(expected);
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
