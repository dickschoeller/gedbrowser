package org.schoellerfamily.gedbrowser.analytics;

import java.util.Calendar;

import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;
import org.schoellerfamily.gedbrowser.datamodel.DateParser;
import org.schoellerfamily.gedbrowser.datamodel.Person;

/**
 * @author Dick Schoeller
 */
public final class AgeEstimator {
    /** Which person are we estimating. */
    private final Person person;
    /** Relative to which date. */
    private final Calendar relativeTo;

    /**
     * Estimator for the specified person, relative to today.
     *
     * @param person the person whose age we are estimating
     */
    public AgeEstimator(final Person person) {
        this.person = person;
        this.relativeTo = Calendar.getInstance();
    }

    /**
     * Estimator for the specified person, relative to the provided date.
     * This constructor is mostly for testing. Though we could use it later
     * for age at birth of children, etc.
     *
     * @param person the person whose age we are estimating
     * @param relativeTo the date we are comparing against
     */
    public AgeEstimator(final Person person, final Calendar relativeTo) {
        this.person = person;
        this.relativeTo = relativeTo;
    }

    /**
     * @return the estimated difference in years
     */
    public int estimateInYears() {
        final String birthDateString = person.getBirthDate();
        final LocalDate l0 = new LocalDate(relativeTo);
        final DateParser parser = new DateParser(birthDateString);
        final LocalDate l1 = new LocalDate(parser.getEstimateCalendar());
        final Period p = new Period(l1, l0);
        return p.getYears();
    }


    /**
     * @return the estimated difference in years
     */
    public String estimateInYearsMonthsDays() {
        final String birthDateString = person.getBirthDate();
        final LocalDate l0 = new LocalDate(relativeTo);
        final DateParser parser = new DateParser(birthDateString);
        final Calendar estimateCalendar = parser.getEstimateCalendar();
        final LocalDate l1 = new LocalDate(estimateCalendar);
        final Period p = new Period(l1, l0, PeriodType.yearMonthDay());
        final PeriodFormatter ymd = new PeriodFormatterBuilder()
        .printZeroAlways()
        .appendYears()
        .appendSuffix(" year", " years")
        .appendSeparator(", ")
        .printZeroRarelyLast()
        .appendMonths()
        .appendSuffix(" month", " months")
        .appendSeparator(", ")
        .appendDays()
        .appendSuffix(" day", " days")
        .toFormatter();
        return ymd.print(p);
    }
}
