package org.schoellerfamily.gedbrowser.analytics.order;

import java.util.Calendar;

import org.joda.time.LocalDate;
import org.schoellerfamily.gedbrowser.analytics.visitor.PersonAnalysisVisitor;
import org.schoellerfamily.gedbrowser.datamodel.Attribute;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.util.DateParser;
import org.schoellerfamily.gedbrowser.datamodel.visitor.GetDateVisitor;

/**
 * Analyzes abstract order data and ordering behavior.
 *
 * @author Richard Schoeller
 */
public abstract class AbstractOrderAnalyzer {
    /** The accumulated result of the analysis. */
    private final OrderAnalyzerResult result;

    /** Track for the current flow of dates in the analysis. */
    private LocalDate currentDate;

    /** The most recently seen event. */
    private Attribute seenEvent;

    /**
     * Executes abstract order analyzer.
     *
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
     * Get the current date.
     *
     * @return the date of the most recent attribute
     */
    public final LocalDate getCurrentDate() {
        return currentDate;
    }

    /**
     * Set the current date.
     *
     * @param currentDate the date of the most recent attribute
     */
    public final void setCurrentDate(final LocalDate currentDate) {
        this.currentDate = currentDate;
    }

    /**
     * Get the most recently seen event.
     *
     * @return the most recently seen event
     */
    public final Attribute getSeenEvent() {
        return seenEvent;
    }

    /**
     * Set the most recently seen event.
     *
     * @param seenEvent the most recently seen event
     */
    public final void setSeenEvent(final Attribute seenEvent) {
        this.seenEvent = seenEvent;
    }

    /**
     * Perform a basic order check on the given attribute.
     *
     * @param attribute the attribute to check
     */
    protected final void basicOrderCheck(final Attribute attribute) {
        final LocalDate newDate = createLocalDate(attribute);
        if (!newDateAfterCurrent(newDate)) {
            final String message = String.format(
                    "Date order: %s dated %s occurs after %s dated %s",
                    attribute.getString(), onDateString(newDate),
                    getSeenEvent().getString(),
                    onDateString(getCurrentDate()));
            getResult().addMismatch(message);
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
        final GetDateVisitor visitor = new GetDateVisitor();
        attribute.accept(visitor);
        return createLocalDate(visitor.getDate());
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
        final DateParser parser = new DateParser(dateString);
        final Calendar estimateCalendar = parser.getEstimateCalendar();
        if (estimateCalendar == null) {
            return null;
        }
        return new LocalDate(estimateCalendar);
    }

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
     * Get the result.
     *
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


    /**
     * Is the given attribute a birth related event?
     *
     * @param attribute the attribute to check if it's a birth related event
     * @return true if this is a birth related event
     */
    protected final boolean isBirthRelatedEvent(final Attribute attribute) {
        return isNamingEvent(attribute) || isBirthEvent(attribute);
    }

    /**
     * Is the given attribute a naming related event?
     *
     * @param attribute the attribute to check if it's a naming event
     * @return true if this is a naming related event
     */
    protected final boolean isNamingEvent(final Attribute attribute) {
        final String type = attribute.getString();
        return "Baptism".equals(type) || "Christening".equals(type) || "Naming".equals(type);
    }

    /**
     * Is the given attribute a birth event?
     *
     * @param attribute the attribute to check if it's a birth event
     * @return true if this is a birth event
     */
    protected final boolean isBirthEvent(final Attribute attribute) {
        return "Birth".equals(attribute.getString());
    }

    /**
     * Estimate the best birth date from near birth events, like christenings.
     *
     * @param person0 the person whose events we are looking at
     * @return the best birth date from near birth events
     */
    protected final LocalDate getNearBirthEventDate(final Person person0) {
        final GetDateVisitor dateVisitor = new GetDateVisitor("Birth");
        person0.accept(dateVisitor);
        LocalDate birthDate = createLocalDate(dateVisitor.getDate());
        if (birthDate == null) {
            final PersonAnalysisVisitor personVisitor =
                    new PersonAnalysisVisitor();
            person0.accept(personVisitor);
            for (final Attribute attribute : personVisitor.getAttributes()) {
                if (isNamingEvent(attribute)) {
                    birthDate = minDate(birthDate, createLocalDate(attribute));
                }
            }
        }
        return birthDate;
    }
}
