package org.schoellerfamily.gedbrowser.analytics.order;

import org.joda.time.LocalDate;
import org.schoellerfamily.gedbrowser.datamodel.Attribute;
import org.schoellerfamily.gedbrowser.datamodel.Date;

/**
 * Base class for order analysis.
 *
 * @author Dick Schoeller
 */
public abstract class AbstractOrderAnalyzer {
    /** The accumulated result of the analysis. */
    private final OrderAnalyzerResult result;

    /** Track for the current flow of dates in the analysis. */
    private LocalDate currentDate;

    /** The most recently seen event. */
    private Attribute seenEvent;

    /**
     * @param result the result
     */
    protected AbstractOrderAnalyzer(final OrderAnalyzerResult result) {
        this.result = result;
    }

    /**
     * Do the analysis.
     *
     * @return the current state of the analysis
     */
    public abstract OrderAnalyzerResult analyze();

    /**
     * @return the date of the most recent attribute
     */
    public LocalDate getCurrentDate() {
        return currentDate;
    }

    /**
     * @param currentDate the date of the most recent attribute
     */
    public void setCurrentDate(final LocalDate currentDate) {
        this.currentDate = currentDate;
    }

    /**
     * @return the most recently seen event
     */
    public final Attribute getSeenEvent() {
        return seenEvent;
    }

    /**
     * @param seenEvent the most recently seen event
     */
    public final void setSeenEvent(final Attribute seenEvent) {
        this.seenEvent = seenEvent;
    }

    /**
     * @param attribute the attribute to check
     */
    protected final void basicOrderCheck(final Attribute attribute) {
        final LocalDate newDate = createLocalDate(attribute);
        if (!newDateAfterCurrent(newDate)) {
            getResult().addMismatch(attribute.getString()
                    + onDateString(newDate)
                    + " before event, "
                    + getSeenEvent().getString()
                    + onDateString(getCurrentDate()));
        }
    }

    /**
     * String to insert about the date. If date is defined return
     * " on &lt;date&gt;". Otherwise, return " (undated)".
     *
     * @param newDate the date
     * @return the string
     */
    protected final String onDateString(final LocalDate newDate) {
        if (newDate == null) {
            return " (undated)";
        }
        return " on " + newDate;
    }

    /**
     * Create a local date from the attribute's date.
     *
     * @param attribute the attribute
     * @return the local date
     */
    protected final LocalDate createLocalDate(final Attribute attribute) {
        return createLocalDate(attribute.getDate());
    }

    /**
     * Create a LocalDate from the given date string. Returns null
     * for an empty or null date string.
     *
     * @param dateString the date string
     * @return the new LocalDate
     */
    protected final LocalDate createLocalDate(final String dateString) {
        if (dateString.isEmpty()) {
            return null;
        }
        final Date date = new Date(null, dateString);
        return new LocalDate(date.getEstimateCalendar());
    }

    /**
     * @param newDate the newDate
     * @return true if the order is correct
     */
    private boolean newDateAfterCurrent(final LocalDate newDate) {
        if (getCurrentDate() == null) {
            setCurrentDate(newDate);
            return true;
        }
        if (newDate == null) {
            return true;
        }
        if (newDate.isEqual(getCurrentDate())) {
            return true;
        }
        if (newDate.isAfter(getCurrentDate())) {
            setCurrentDate(newDate);
            return true;
        }
        return false;
    }

    /**
     * @return the result
     */
    public final OrderAnalyzerResult getResult() {
        return result;
    }

    /**
     * Return the earlier of the 2 dates. If either is null return the other.
     *
     * @param date0 a date
     * @param date1 another date
     * @return the earlier of the 2 dates
     */
    public static LocalDate minDate(final LocalDate date0,
            final LocalDate date1) {
        if (date1 == null) {
            return date0;
        }
        if (date0 == null) {
            return date1;
        }
        if (date1.isBefore(date0)) {
            return date1;
        } else {
            return date0;
        }
    }
}
