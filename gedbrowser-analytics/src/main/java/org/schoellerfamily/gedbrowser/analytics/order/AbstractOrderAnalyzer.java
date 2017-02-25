package org.schoellerfamily.gedbrowser.analytics.order;

import java.util.Calendar;

import org.joda.time.LocalDate;
import org.schoellerfamily.gedbrowser.datamodel.Attribute;
import org.schoellerfamily.gedbrowser.datamodel.DateParser;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.visitor.GetDateVisitor;

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
    public final LocalDate getCurrentDate() {
        return currentDate;
    }

    /**
     * @param currentDate the date of the most recent attribute
     */
    public final void setCurrentDate(final LocalDate currentDate) {
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


    /**
     * @param attribute the attribute to check if it's a birth event
     * @return true if this is a birth related event
     */
    protected final boolean isBirthRelatedEvent(final Attribute attribute) {
        return isNamingEvent(attribute) || isBirthEvent(attribute);
    }

    /**
     * @param attribute the attribute to check if it's a birth event
     * @return true if this is a naming related event
     */
    protected final boolean isNamingEvent(final Attribute attribute) {
        final String type = attribute.getString();
        if ("Baptism".equals(type)) {
            return true;
        }
        if ("Christening".equals(type)) {
            return true;
        }
        return ("Naming".equals(type));
    }

    /**
     * @param attribute the attribute to check if it's a birth event
     * @return true if this is a birth event
     */
    protected final boolean isBirthEvent(final Attribute attribute) {
        return "Birth".equals(attribute.getString());
    }

    /**
     * @param person0 the person whose events we are looking at
     * @return the best birth date from near birth events
     */
    protected final LocalDate getNearBirthEventDate(final Person person0) {
        final GetDateVisitor visitor = new GetDateVisitor("Birth");
        person0.accept(visitor);
        LocalDate birthDate = createLocalDate(visitor.getDate());
        if (birthDate == null) {
            for (final GedObject gob : person0.getAttributes()) {
                if (!(gob instanceof Attribute)) {
                    continue;
                }
                final Attribute attribute = (Attribute) gob;
                if (isNamingEvent(attribute)) {
                    birthDate = minDate(birthDate, createLocalDate(attribute));
                }
            }
        }
        return birthDate;
    }

    /**
     * Certain events have no time basis on the person.
     *
     * @param event the event
     * @return true if it is non-time event
     */
    protected final boolean ignoreable(final Attribute event) {
        // Layed out like this because it is easier to understand
        // coverage. No performance differences expected compared
        // to tighter layout.
        if ("Sex".equals(event.getString())) {
            return true;
        }
        if ("Changed".equals(event.getString())) {
            return true;
        }
        if ("Ancestral File Number".equals(event.getString())) {
            return true;
        }
        if ("Title".equals(event.getString())) {
            return true;
        }
        if ("Attribute".equals(event.getString())) {
            // Only care about random attributes if they are dated
            final GetDateVisitor visitor = new GetDateVisitor();
            event.accept(visitor);
            return "".equals(visitor.getDate());
        }
        if ("Note".equals(event.getString())) {
            // Only care about notes if they are dated
            final GetDateVisitor visitor = new GetDateVisitor();
            event.accept(visitor);
            return "".equals(visitor.getDate());
        }
        return "Reference Number".equals(event.getString());
    }
}
