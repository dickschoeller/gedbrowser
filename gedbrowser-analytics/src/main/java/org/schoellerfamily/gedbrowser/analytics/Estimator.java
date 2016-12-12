package org.schoellerfamily.gedbrowser.analytics;

import java.util.List;

import org.joda.time.LocalDate;
import org.schoellerfamily.gedbrowser.datamodel.Attribute;
import org.schoellerfamily.gedbrowser.datamodel.Date;
import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Person;

/**
 * Base class for estimators. Primarily contains useful methods to avoid
 * Demeter.
 *
 * @author Dick Schoeller
 */
@SuppressWarnings("PMD.AbstractClassWithoutAbstractMethod")
public abstract class Estimator {
    /**
     * Get the list of children from a family.
     *
     * @param family the family
     * @return the children
     */
    protected final List<Person> getChildren(final Family family) {
        return family.getChildren();
    }

    /**
     * Get the father from a family.
     *
     * @param family the family
     * @return the father
     */
    protected final Person getFather(final Family family) {
        return family.getFather();
    }

    /**
     * Get the mother from a family.
     *
     * @param family the family
     * @return the mother
     */
    protected final Person getMother(final Family family) {
        return family.getMother();
    }

    /**
     * Get the birth date string of a person.
     *
     * @param person the person
     * @return the birth date
     */
    protected final String getBirthDate(final Person person) {
        return person.getBirthDate();
    }

    /**
     * Get the date string for an attribute.
     *
     * @param attr the attribute
     * @return the date
     */
    protected final String getDate(final Attribute attr) {
        return attr.getDate();
    }

    /**
     * Get the string from any GED object.
     *
     * @param gob the GED object
     * @return the string
     */
    protected final String getString(final GedObject gob) {
        return gob.getString();
    }

    /**
     * Validate a date string. Really just checking not empty.
     *
     * @param dateString the date string
     * @return true if valid
     */
    protected final boolean validDateString(final String dateString) {
        return dateString != null && !dateString.isEmpty();
    }

    /**
     * @param date primary date
     * @param compareDate date to compare to
     * @return true if date is before compareDate
     */
    protected final boolean isBefore(final LocalDate date,
            final LocalDate compareDate) {
        return date.isBefore(compareDate);
    }

    /**
     * Add years to the date and then adjust to the beginning of the resultant
     * year.
     *
     * @param date input date
     * @param years years to add
     * @return new date
     */
    protected final LocalDate plusYearsAdjustToBegin(final LocalDate date,
            final int years) {
        final LocalDate adjustedYears = plusYears(date, years);
        final LocalDate firstMonth = firstMonth(adjustedYears);
        return firstDayOfMonth(firstMonth);
    }

    /**
     * Subtract years from the date and then adjust to the beginning of the
     * resultant year.
     *
     * @param date input date
     * @param years years to add
     * @return new date
     */
    protected final LocalDate minusYearsAdjustToBegin(final LocalDate date,
            final int years) {
        final LocalDate adjustedYears = minusYears(date, years);
        final LocalDate firstMonth = firstMonth(adjustedYears);
        return firstDayOfMonth(firstMonth);
    }

    /**
     * Adjust the input date to the beginning of the month.
     *
     * @param date the input date
     * @return new date
     */
    protected final LocalDate firstDayOfMonth(final LocalDate date) {
        return date.withDayOfMonth(1);
    }

    /**
     * Adjust the input date to the first month of the year.
     *
     * @param date the input date
     * @return new date
     */
    protected final LocalDate firstMonth(final LocalDate date) {
        return date.withMonthOfYear(1);
    }

    /**
     * Increment the date by a number of years.
     *
     * @param date the input date
     * @param years the number of years
     * @return new date
     */
    protected final LocalDate plusYears(final LocalDate date, final int years) {
        return date.plusYears(years);
    }

    /**
     * Decrement the date by a number of years.
     *
     * @param date the input date
     * @param years the number of years
     * @return new date
     */
    protected final LocalDate minusYears(final LocalDate date,
            final int years) {
        return date.minusYears(years);
    }


    /**
     * Create a LocalDate from the given date string. Returns null
     * for an empty or null date string.
     *
     * @param dateString the date string
     * @return the new LocalDate
     */
    protected final LocalDate createLocalDate(final String dateString) {
        if (dateString == null || dateString.isEmpty()) {
            return null;
        }
        final Date date = new Date(null, dateString);
        return new LocalDate(date.getEstimateCalendar());
    }

    /**
     * Avoid warnings about new in loops.
     *
     * @param p the person to estimate
     * @return the estimator
     */
    protected final BirthDateEstimator createEstimator(final Person p) {
        return new BirthDateEstimator(p);
    }

    /**
     * @param date the current best date
     * @param family the family being processed
     * @return the date we got out of this family
     */
    protected final LocalDate processMarriageDate(final LocalDate date,
            final Family family) {
        LocalDate returnDate = date;
        for (final GedObject gob : family.getAttributes()) {
            if (!(gob instanceof Attribute)) {
                continue;
            }
            if (!"Marriage".equals(getString(gob))) {
                continue;
            }
            final Attribute attr = (Attribute) gob;
            final String datestring = getDate(attr);
            final LocalDate marriageLocalDate = createLocalDate(datestring);
            if (marriageLocalDate == null) {
                continue;
            }
            if (returnDate == null) {
                returnDate = marriageLocalDate;
            } else {
                if (isBefore(marriageLocalDate, returnDate)) {
                    returnDate = marriageLocalDate;
                }
            }
        }
        return returnDate;
    }
}
