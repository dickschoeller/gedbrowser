package org.schoellerfamily.gedbrowser.analytics.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.schoellerfamily.gedbrowser.analytics.AgeEstimator;
import org.schoellerfamily.gedbrowser.analytics.CalendarProvider;
import org.schoellerfamily.gedbrowser.analytics.CalendarProviderStub;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;

/**
 * @author Dick Schoeller
 */
public final class AgeEstimatorTest {
    /** The comparison calendar. */
    private CalendarProvider provider;
    /** The builder to construct gedobjects for testing. */
    private GedObjectBuilder builder;

    /**
     * Prepare a comparison calendar at a fixed date. Makes tests predictable.
     */
    @Before
    public void before() {
        provider = new CalendarProviderStub();
        builder = new GedObjectBuilder();
    }

    /**
     * Test against Dick's data.
     */
    @Test
    public void testDick() {
        final Person person = builder.createPerson("I0",
                "J. Random/Schoeller/");
        builder.createPersonEvent(person, "Birth", "14 DEC 1958");

        final int ageAtReferenceDate = 57;
        final AgeEstimator estimator = new AgeEstimator(person, provider);
        assertEquals("estimate does not match expected value of 57",
                ageAtReferenceDate, estimator.estimateInYears());
    }

    /**
     * Test against Lisa's data.
     */
    @Test
    public void testLisa() {
        final Person person = builder.createPerson("I0",
                "J. Random/Schoeller/");
        builder.createPersonEvent(person, "Birth", "09 MAY 1960");

        final int ageAtReferenceDate = 55;
        final AgeEstimator estimator = new AgeEstimator(person, provider);
        assertEquals("estimate does not match expected value of 55",
                ageAtReferenceDate, estimator.estimateInYears());
    }

    /**
     * Test against a January 1st birth.
     */
    @Test
    public void testBeginYear() {
        final Person person = builder.createPerson("I0",
                "J. Random/Schoeller/");
        builder.createPersonEvent(person, "Birth", "01 JAN 1960");

        final int ageAtReferenceDate = 55;
        final AgeEstimator estimator = new AgeEstimator(person, provider);
        assertEquals("estimate does not match expected value of 55",
                ageAtReferenceDate, estimator.estimateInYears());
    }

    /**
     * Test against a December 31st birth.
     */
    @Test
    public void testEndYear() {
        final Person person = builder.createPerson("I0",
                "J. Random/Schoeller/");
        builder.createPersonEvent(person, "Birth", "31 DEC 1960");

        final int ageAtReferenceDate = 54;
        final AgeEstimator estimator = new AgeEstimator(person, provider);
        assertEquals("estimate does not match expected value of 54",
                ageAtReferenceDate, estimator.estimateInYears());
    }

    /**
     * Test a birth date that is just before some year.
     */
    @Test
    public void testBeforeYear() {
        final Person person = builder.createPerson("I0",
                "J. Random/Schoeller/");
        builder.createPersonEvent(person, "Birth", "BEF 1960");

        final int ageAtReferenceDate = 55;
        final AgeEstimator estimator = new AgeEstimator(person, provider);
        assertEquals("estimate does not match expected value of 55",
                ageAtReferenceDate, estimator.estimateInYears());
    }

    /**
     * Test a birth date that is before the same month as the reference.
     */
    @Test
    public void testBeforeMonth() {
        final Person person = builder.createPerson("I0",
                "J. Random/Schoeller/");
        builder.createPersonEvent(person, "Birth", "BEF DEC 1960");

        final int ageAtReferenceDate = 55;
        final AgeEstimator estimator = new AgeEstimator(person, provider);
        assertEquals("estimate does not match expected value of 55",
                ageAtReferenceDate, estimator.estimateInYears());
    }

    /**
     * Test a date that is before the day before the reference.
     */
    @Test
    public void testBeforeTheDayBeforeTheReference() {
        final Person person = builder.createPerson("I0",
                "J. Random/Schoeller/");
        builder.createPersonEvent(person, "Birth", "BEF 13 DEC 1960");

        final int ageAtReferenceDate = 55;
        final AgeEstimator estimator = new AgeEstimator(person, provider);
        assertEquals("estimate does not match expected value of 55",
                ageAtReferenceDate, estimator.estimateInYears());
    }

    /**
     * Test a date that is before the reference date.
     */
    @Test
    public void testBeforeTheReference() {
        final Person person = builder.createPerson("I0",
                "J. Random/Schoeller/");
        builder.createPersonEvent(person, "Birth", "BEF 14 DEC 1960");

        final int ageAtReferenceDate = 55;
        final AgeEstimator estimator = new AgeEstimator(person, provider);
        assertEquals("estimate does not match expected value of 55",
                ageAtReferenceDate, estimator.estimateInYears());
    }

    /**
     * Test a birth date that is before the day after the reference.
     */
    @Test
    public void testBeforeTheDayAfterTheReference() {
        final Person person = builder.createPerson("I0",
                "J. Random/Schoeller/");
        builder.createPersonEvent(person, "Birth", "BEF 15 DEC 1960");

        final int ageAtReferenceDate = 55;
        final AgeEstimator estimator = new AgeEstimator(person, provider);
        assertEquals("estimate does not match expected value of 55",
                ageAtReferenceDate, estimator.estimateInYears());
    }

    /**
     * Test a birth date that is before 2 days after the reference.
     */
    @Test
    public void testBeforeTwoDaysAfterTheReference() {
        final Person person = builder.createPerson("I0",
                "J. Random/Schoeller/");
        builder.createPersonEvent(person, "Birth", "BEF 16 DEC 1960");

        final int ageAtReferenceDate = 54;
        final AgeEstimator estimator = new AgeEstimator(person, provider);
        assertEquals("estimate does not match expected value of 54",
                ageAtReferenceDate, estimator.estimateInYears());
    }

    /**
     * Test a birth date that is after a year.
     */
    @Test
    public void testAfterYear() {
        final Person person = builder.createPerson("I0",
                "J. Random/Schoeller/");
        builder.createPersonEvent(person, "Birth", "AFT 1960");

        final int ageAtReferenceDate = 54;
        final AgeEstimator estimator = new AgeEstimator(person, provider);
        assertEquals("estimate does not match expected value of 54",
                ageAtReferenceDate, estimator.estimateInYears());
    }

    /**
     * Test a birth date that is after the same month as the reference.
     */
    @Test
    public void testAfterReferenceMonth() {
        final Person person = builder.createPerson("I0",
                "J. Random/Schoeller/");
        builder.createPersonEvent(person, "Birth", "AFT DEC 1960");

        final int ageAtReferenceDate = 54;
        final AgeEstimator estimator = new AgeEstimator(person, provider);
        assertEquals("estimate does not match expected value of 54",
                ageAtReferenceDate, estimator.estimateInYears());
    }

    /**
     * Test a date that is after the first month of the year.
     */
    @Test
    public void testAfterJanuary() {
        final Person person = builder.createPerson("I0",
                "J. Random/Schoeller/");
        builder.createPersonEvent(person, "Birth", "AFT JAN 1960");

        final int ageAtReferenceDate = 55;
        final AgeEstimator estimator = new AgeEstimator(person, provider);
        assertEquals("estimate does not match expected value of 55",
                ageAtReferenceDate, estimator.estimateInYears());
    }

    /**
     * Test a birthday after 2 days before the reference date.
     */
    @Test
    public void testAfterTwoDaysBeforeTheReference() {
        final Person person = builder.createPerson("I0",
                "J. Random/Schoeller/");
        builder.createPersonEvent(person, "Birth", "AFT 12 DEC 1960");

        final int ageAtReferenceDate = 55;
        final AgeEstimator estimator = new AgeEstimator(person, provider);
        assertEquals("estimate does not match expected value of 55",
                ageAtReferenceDate, estimator.estimateInYears());
    }

    /**
     * Test a birthday after the day before the reference date.
     */
    @Test
    public void testAfterTheDayBeforeTheReference() {
        final Person person = builder.createPerson("I0",
                "J. Random/Schoeller/");
        builder.createPersonEvent(person, "Birth", "AFT 13 DEC 1960");

        final int ageAtReferenceDate = 55;
        final AgeEstimator estimator = new AgeEstimator(person, provider);
        assertEquals("estimate does not match expected value of 55",
                ageAtReferenceDate, estimator.estimateInYears());
    }

    /**
     *  Test a birthday after the reference date.
     */
    @Test
    public void testAfterTheReference() {
        final Person person = builder.createPerson("I0",
                "J. Random/Schoeller/");
        builder.createPersonEvent(person, "Birth", "AFT 14 DEC 1960");

        final int ageAtReferenceDate = 54;
        final AgeEstimator estimator = new AgeEstimator(person, provider);
        assertEquals("estimate does not match expected value of 55",
                ageAtReferenceDate, estimator.estimateInYears());
    }

    /**
     * Test a birthday after the day after the reference date.
     */
    @Test
    public void testAfterTheDayAfterTheReference() {
        final Person person = builder.createPerson("I0",
                "J. Random/Schoeller/");
        builder.createPersonEvent(person, "Birth", "AFT 15 DEC 1960");

        final int ageAtReferenceDate = 54;
        final AgeEstimator estimator = new AgeEstimator(person, provider);
        assertEquals("estimate does not match expected value of 54",
                ageAtReferenceDate, estimator.estimateInYears());
    }

    /**
     * Test a date whose string has years, no months and a single day.
     */
    @Test
    public void testYearsDay() {
        final Person person = builder.createPerson("I0",
                "J. Random/Schoeller/");
        builder.createPersonEvent(person, "Birth", "13 DEC 1960");

        final AgeEstimator estimator = new AgeEstimator(person, provider);
        assertEquals("estimate string doesn't match",
                "55 years, 1 day", estimator.estimateInYearsMonthsDays());
    }

    /**
     * Test a date whose string has years, months and a single day.
     */
    @Test
    public void testYearsMonthsDay() {
        final Person person = builder.createPerson("I0",
                "J. Random/Schoeller/");
        builder.createPersonEvent(person, "Birth", "13 JAN 1961");

        final AgeEstimator estimator = new AgeEstimator(person, provider);
        assertEquals("estimate string doesn't match",
                "54 years, 11 months, 1 day",
                estimator.estimateInYearsMonthsDays());
    }

    /**
     * Test a date whose string has years, months and days.
     */
    @Test
    public void testYearsMonthsDays() {
        final Person person = builder.createPerson("I0",
                "J. Random/Schoeller/");
        builder.createPersonEvent(person, "Birth", "16 DEC 1960");

        final AgeEstimator estimator = new AgeEstimator(person, provider);
        assertEquals("estimate string doesn't match",
                "54 years, 11 months, 28 days",
                estimator.estimateInYearsMonthsDays());
    }

    /**
     * Test a date whose string has years and months but no days.
     */
    @Test
    public void testYearsMonths() {
        final Person person = builder.createPerson("I0",
                "J. Random/Schoeller/");
        builder.createPersonEvent(person, "Birth", "14 JAN 1961");

        final AgeEstimator estimator = new AgeEstimator(person, provider);
        assertEquals("estimate string doesn't match",
                "54 years, 11 months",
                estimator.estimateInYearsMonthsDays());
    }
}
