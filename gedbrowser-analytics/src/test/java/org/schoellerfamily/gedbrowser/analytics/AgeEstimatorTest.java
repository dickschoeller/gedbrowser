package org.schoellerfamily.gedbrowser.analytics;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;
import org.schoellerfamily.gedbrowser.datamodel.Attribute;
import org.schoellerfamily.gedbrowser.datamodel.Date;
import org.schoellerfamily.gedbrowser.datamodel.Name;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Root;

/**
 * @author Dick Schoeller
 */
public final class AgeEstimatorTest {
    /** The comparison calendar. */
    private final Calendar referenceCalendar = Calendar.getInstance(Locale.US);

    /**
     * Prepare a comparison calendar at a fixed date. Makes tests predictable.
     */
    @Before
    public void before() {
        final int birthYear = 2015;
        final int birthDay = 14;
        referenceCalendar.set(Calendar.YEAR, birthYear);
        referenceCalendar.set(Calendar.MONTH, Calendar.DECEMBER);
        referenceCalendar.set(Calendar.DAY_OF_MONTH, birthDay);
    }

    /**
     * Test against Dick's data.
     */
    @Test
    public void testDick() {
        final Root root = new Root(null);
        final Person person = new Person(root);
        final Name name = new Name(person, "Richard John/Schoeller/");
        final Attribute birth = new Attribute(person, "Birth");
        final Date birthDate = new Date(birth, "14 DEC 1958");

        root.insert("I0", person);
        person.insert(name);
        person.insert(birth);
        birth.insert(birthDate);

        final int ageAtReferenceDate = 57;
        final AgeEstimator estimator =
                new AgeEstimator(person, referenceCalendar);
        assertEquals(ageAtReferenceDate, estimator.estimateInYears());
    }

    /**
     * Test against Lisa's data.
     */
    @Test
    public void testLisa() {
        final Root root = new Root(null);
        final Person person = new Person(root);
        final Name name = new Name(person, "Lisa Hope/Robinson/");
        final Attribute birth = new Attribute(person, "Birth");
        final Date birthDate = new Date(birth, "09 MAY 1960");

        root.insert("I0", person);
        person.insert(name);
        person.insert(birth);
        birth.insert(birthDate);

        final int ageAtReferenceDate = 55;
        final AgeEstimator estimator =
                new AgeEstimator(person, referenceCalendar);
        assertEquals(ageAtReferenceDate, estimator.estimateInYears());
    }

    /**
     * Test against a January 1st birth.
     */
    @Test
    public void testBeginYear() {
        final Root root = new Root(null);
        final Person person = new Person(root);
        final Name name = new Name(person, "J. Random/Schoeller/");
        final Attribute birth = new Attribute(person, "Birth");
        final Date birthDate = new Date(birth, "01 JAN 1960");

        root.insert("I0", person);
        person.insert(name);
        person.insert(birth);
        birth.insert(birthDate);

        final int ageAtReferenceDate = 55;
        final AgeEstimator estimator =
                new AgeEstimator(person, referenceCalendar);
        assertEquals(ageAtReferenceDate, estimator.estimateInYears());
    }

    /**
     * Test against a December 31st birth.
     */
    @Test
    public void testEndYear() {
        final Root root = new Root(null);
        final Person person = new Person(root);
        final Name name = new Name(person, "J. Random/Schoeller/");
        final Attribute birth = new Attribute(person, "Birth");
        final Date birthDate = new Date(birth, "31 DEC 1960");

        root.insert("I0", person);
        person.insert(name);
        person.insert(birth);
        birth.insert(birthDate);

        final int ageAtReferenceDate = 54;
        final AgeEstimator estimator =
                new AgeEstimator(person, referenceCalendar);
        assertEquals(ageAtReferenceDate, estimator.estimateInYears());
    }

    /**
     * Test a birth date that is just before some year.
     */
    @Test
    public void testBeforeYear() {
        final Root root = new Root(null);
        final Person person = new Person(root);
        final Name name = new Name(person, "J. Random/Schoeller/");
        final Attribute birth = new Attribute(person, "Birth");
        final Date birthDate = new Date(birth, "BEF 1960");

        root.insert("I0", person);
        person.insert(name);
        person.insert(birth);
        birth.insert(birthDate);

        final int ageAtReferenceDate = 55;
        final AgeEstimator estimator =
                new AgeEstimator(person, referenceCalendar);
        assertEquals(ageAtReferenceDate, estimator.estimateInYears());
    }

    /**
     * Test a birth date that is before the same month as the reference.
     */
    @Test
    public void testBeforeMonth() {
        final Root root = new Root(null);
        final Person person = new Person(root);
        final Name name = new Name(person, "J. Random/Schoeller/");
        final Attribute birth = new Attribute(person, "Birth");
        final Date birthDate = new Date(birth, "BEF DEC 1960");

        root.insert("I0", person);
        person.insert(name);
        person.insert(birth);
        birth.insert(birthDate);

        final int ageAtReferenceDate = 55;
        final AgeEstimator estimator =
                new AgeEstimator(person, referenceCalendar);
        assertEquals(ageAtReferenceDate, estimator.estimateInYears());
    }

    /**
     * Test a date that is before the day before the reference.
     */
    @Test
    public void testBeforeTheDayBeforeTheReference() {
        final Root root = new Root(null);
        final Person person = new Person(root);
        final Name name = new Name(person, "J. Random/Schoeller/");
        final Attribute birth = new Attribute(person, "Birth");
        final Date birthDate = new Date(birth, "BEF 13 DEC 1960");

        root.insert("I0", person);
        person.insert(name);
        person.insert(birth);
        birth.insert(birthDate);

        final int ageAtReferenceDate = 55;
        final AgeEstimator estimator =
                new AgeEstimator(person, referenceCalendar);
        assertEquals(ageAtReferenceDate, estimator.estimateInYears());
    }

    /**
     * Test a date that is before the reference date.
     */
    @Test
    public void testBeforeTheReference() {
        final Root root = new Root(null);
        final Person person = new Person(root);
        final Name name = new Name(person, "J. Random/Schoeller/");
        final Attribute birth = new Attribute(person, "Birth");
        final Date birthDate = new Date(birth, "BEF 14 DEC 1960");

        root.insert("I0", person);
        person.insert(name);
        person.insert(birth);
        birth.insert(birthDate);

        final int ageAtReferenceDate = 55;
        final AgeEstimator estimator =
                new AgeEstimator(person, referenceCalendar);
        assertEquals(ageAtReferenceDate, estimator.estimateInYears());
    }

    /**
     * Test a birth date that is before the day after the reference.
     */
    @Test
    public void testBeforeTheDayAfterTheReference() {
        final Root root = new Root(null);
        final Person person = new Person(root);
        final Name name = new Name(person, "J. Random/Schoeller/");
        final Attribute birth = new Attribute(person, "Birth");
        final Date birthDate = new Date(birth, "BEF 15 DEC 1960");

        root.insert("I0", person);
        person.insert(name);
        person.insert(birth);
        birth.insert(birthDate);

        final int ageAtReferenceDate = 55;
        final AgeEstimator estimator =
                new AgeEstimator(person, referenceCalendar);
        assertEquals(ageAtReferenceDate, estimator.estimateInYears());
    }

    /**
     * Test a birth date that is before 2 days after the reference.
     */
    @Test
    public void testBeforeTwoDaysAfterTheReference() {
        final Root root = new Root(null);
        final Person person = new Person(root);
        final Name name = new Name(person, "J. Random/Schoeller/");
        final Attribute birth = new Attribute(person, "Birth");
        final Date birthDate = new Date(birth, "BEF 16 DEC 1960");

        root.insert("I0", person);
        person.insert(name);
        person.insert(birth);
        birth.insert(birthDate);

        final int ageAtReferenceDate = 54;
        final AgeEstimator estimator =
                new AgeEstimator(person, referenceCalendar);
        assertEquals(ageAtReferenceDate, estimator.estimateInYears());
    }

    /**
     * Test a birth date that is after a year.
     */
    @Test
    public void testAfterYear() {
        final Root root = new Root(null);
        final Person person = new Person(root);
        final Name name = new Name(person, "J. Random/Schoeller/");
        final Attribute birth = new Attribute(person, "Birth");
        final Date birthDate = new Date(birth, "AFT 1960");

        root.insert("I0", person);
        person.insert(name);
        person.insert(birth);
        birth.insert(birthDate);

        final int ageAtReferenceDate = 54;
        final AgeEstimator estimator =
                new AgeEstimator(person, referenceCalendar);
        assertEquals(ageAtReferenceDate, estimator.estimateInYears());
    }

    /**
     * Test a birth date that is after the same month as the reference.
     */
    @Test
    public void testAfterReferenceMonth() {
        final Root root = new Root(null);
        final Person person = new Person(root);
        final Name name = new Name(person, "J. Random/Schoeller/");
        final Attribute birth = new Attribute(person, "Birth");
        final Date birthDate = new Date(birth, "AFT DEC 1960");

        root.insert("I0", person);
        person.insert(name);
        person.insert(birth);
        birth.insert(birthDate);

        final int ageAtReferenceDate = 54;
        final AgeEstimator estimator =
                new AgeEstimator(person, referenceCalendar);
        assertEquals(ageAtReferenceDate, estimator.estimateInYears());
    }

    /**
     * Test a date that is after the first month of the year.
     */
    @Test
    public void testAfterJanuary() {
        final Root root = new Root(null);
        final Person person = new Person(root);
        final Name name = new Name(person, "J. Random/Schoeller/");
        final Attribute birth = new Attribute(person, "Birth");
        final Date birthDate = new Date(birth, "AFT JAN 1960");

        root.insert("I0", person);
        person.insert(name);
        person.insert(birth);
        birth.insert(birthDate);

        final int ageAtReferenceDate = 55;
        final AgeEstimator estimator =
                new AgeEstimator(person, referenceCalendar);
        assertEquals(ageAtReferenceDate, estimator.estimateInYears());
    }

    /**
     * Test a birthday after 2 days before the reference date.
     */
    @Test
    public void testAfterTwoDaysBeforeTheReference() {
        final Root root = new Root(null);
        final Person person = new Person(root);
        final Name name = new Name(person, "J. Random/Schoeller/");
        final Attribute birth = new Attribute(person, "Birth");
        final Date birthDate = new Date(birth, "AFT 12 DEC 1960");

        root.insert("I0", person);
        person.insert(name);
        person.insert(birth);
        birth.insert(birthDate);

        final int ageAtReferenceDate = 55;
        final AgeEstimator estimator =
                new AgeEstimator(person, referenceCalendar);
        assertEquals(ageAtReferenceDate, estimator.estimateInYears());
    }

    /**
     * Test a birthday after the day before the reference date.
     */
    @Test
    public void testAfterTheDayBeforeTheReference() {
        final Root root = new Root(null);
        final Person person = new Person(root);
        final Name name = new Name(person, "J. Random/Schoeller/");
        final Attribute birth = new Attribute(person, "Birth");
        final Date birthDate = new Date(birth, "AFT 13 DEC 1960");

        root.insert("I0", person);
        person.insert(name);
        person.insert(birth);
        birth.insert(birthDate);

        final int ageAtReferenceDate = 55;
        final AgeEstimator estimator =
                new AgeEstimator(person, referenceCalendar);
        assertEquals(ageAtReferenceDate, estimator.estimateInYears());
    }

    /**
     *  Test a birthday after the reference date.
     */
    @Test
    public void testAfterTheReference() {
        final Root root = new Root(null);
        final Person person = new Person(root);
        final Name name = new Name(person, "J. Random/Schoeller/");
        final Attribute birth = new Attribute(person, "Birth");
        final Date birthDate = new Date(birth, "AFT 14 DEC 1960");

        root.insert("I0", person);
        person.insert(name);
        person.insert(birth);
        birth.insert(birthDate);

        final int ageAtReferenceDate = 54;
        final AgeEstimator estimator =
                new AgeEstimator(person, referenceCalendar);
        assertEquals(ageAtReferenceDate, estimator.estimateInYears());
    }

    /**
     * Test a birthday after the day after the reference date.
     */
    @Test
    public void testAfterTheDayAfterTheReference() {
        final Root root = new Root(null);
        final Person person = new Person(root);
        final Name name = new Name(person, "J. Random/Schoeller/");
        final Attribute birth = new Attribute(person, "Birth");
        final Date birthDate = new Date(birth, "AFT 15 DEC 1960");

        root.insert("I0", person);
        person.insert(name);
        person.insert(birth);
        birth.insert(birthDate);

        final int ageAtReferenceDate = 54;
        final AgeEstimator estimator =
                new AgeEstimator(person, referenceCalendar);
        assertEquals(ageAtReferenceDate, estimator.estimateInYears());
    }

    /**
     * Test a date whose string has years, no months and a single day.
     */
    @Test
    public void testYearsDay() {
        final Root root = new Root(null);
        final Person person = new Person(root);
        final Name name = new Name(person, "J. Random/Schoeller/");
        final Attribute birth = new Attribute(person, "Birth");
        final Date birthDate = new Date(birth, "13 DEC 1960");

        root.insert("I0", person);
        person.insert(name);
        person.insert(birth);
        birth.insert(birthDate);

        AgeEstimator estimator = new AgeEstimator(person, referenceCalendar);
        assertEquals("55 years, 1 day", estimator.estimateInYearsMonthsDays());
    }

    /**
     * Test a date whose string has years, months and a single day.
     */
    @Test
    public void testYearsMonthsDay() {
        final Root root = new Root(null);
        final Person person = new Person(root);
        final Name name = new Name(person, "J. Random/Schoeller/");
        final Attribute birth = new Attribute(person, "Birth");
        final Date birthDate = new Date(birth, "13 JAN 1961");

        root.insert("I0", person);
        person.insert(name);
        person.insert(birth);
        birth.insert(birthDate);

        final AgeEstimator estimator = new AgeEstimator(person,
                referenceCalendar);
        assertEquals("54 years, 11 months, 1 day",
                estimator.estimateInYearsMonthsDays());
    }

    /**
     * Test a date whose string has years, months and days.
     */
    @Test
    public void testYearsMonthsDays() {
        final Root root = new Root(null);
        final Person person = new Person(root);
        final Name name = new Name(person, "J. Random/Schoeller/");
        final Attribute birth = new Attribute(person, "Birth");
        final Date birthDate = new Date(birth, "16 DEC 1960");

        root.insert("I0", person);
        person.insert(name);
        person.insert(birth);
        birth.insert(birthDate);

        final AgeEstimator estimator = new AgeEstimator(person,
                referenceCalendar);
        assertEquals("54 years, 11 months, 28 days",
                estimator.estimateInYearsMonthsDays());
    }

    /**
     * Test a date whose string has years and months but no days.
     */
    @Test
    public void testYearsMonths() {
        final Root root = new Root(null);
        final Person person = new Person(root);
        final Name name = new Name(person, "J. Random/Schoeller/");
        final Attribute birth = new Attribute(person, "Birth");
        final Date birthDate = new Date(birth, "14 JAN 1961");

        root.insert("I0", person);
        person.insert(name);
        person.insert(birth);
        birth.insert(birthDate);

        final AgeEstimator estimator = new AgeEstimator(person,
                referenceCalendar);
        assertEquals("54 years, 11 months",
                estimator.estimateInYearsMonthsDays());
    }
}
