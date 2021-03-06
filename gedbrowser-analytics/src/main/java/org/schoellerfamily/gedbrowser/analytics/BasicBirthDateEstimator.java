package org.schoellerfamily.gedbrowser.analytics;

import java.util.List;

import org.joda.time.LocalDate;
import org.schoellerfamily.gedbrowser.analytics.visitor.PersonAnalysisVisitor;
import org.schoellerfamily.gedbrowser.datamodel.Attribute;
import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.navigator.PersonNavigator;
import org.schoellerfamily.gedbrowser.datamodel.visitor.GetDateVisitor;

/**
 * Basic birth date estimator for a person. Only looks at the person's events.
 * If you want a more extensive analysis, use BirthDateEstimator.
 *
 * @see BirthDateEstimator
 * @author Dick Schoeller
 */
public class BasicBirthDateEstimator extends Estimator {
    /** Hold the person we are estimating. */
    private final Person person;
    /** Typicals. */
    private final Typicals typicals;

    /**
     * Constructor.
     *
     * @param person the person we are estimating
     */
    public BasicBirthDateEstimator(final Person person) {
        this.person = person;
        this.typicals = new StandardTypicals();
    }

    /**
     * @return the estimated birth date as a LocalDate
     */
    public final LocalDate estimate() {
        LocalDate localDate = estimateFromBirthDate();
        localDate = estimateFromOwnMarriage(localDate);
        localDate = estimateFromOtherEvents(localDate);
        return localDate;
    }

    /**
     * @return a local date from the actual birth date value
     */
    protected final LocalDate estimateFromBirthDate() {
        final GetDateVisitor visitor = new GetDateVisitor("Birth");
        person.accept(visitor);
        final String birthDateString = visitor.getDate();
        return createLocalDate(birthDateString);
    }

    /**
     * @param localDate if not null we already have a better estimate
     * @return the estimate from own marriages
     */
    protected final LocalDate estimateFromOwnMarriage(
            final LocalDate localDate) {
        if (localDate != null) {
            return localDate;
        }
        final PersonNavigator navigator = new PersonNavigator(person);
        final List<Family> families = navigator.getFamilies();
        LocalDate date = null;
        for (final Family family : families) {
            date = processMarriageDate(date, family);
        }
        if (date != null) {
            date = minusYearsAdjustToBegin(date, typicals.ageAtMarriage());
        }
        return date;
    }

    /**
     * Try other lifecycle events.
     *
     * @param localDate if not null we already have a better estimate
     * @return estimate
     */
    protected final LocalDate estimateFromOtherEvents(
            final LocalDate localDate) {
        if (localDate != null) {
            return localDate;
        }
        final PersonAnalysisVisitor visitor = new PersonAnalysisVisitor();
        person.accept(visitor);
        for (final Attribute attr : visitor.getAttributes()) {
            final String dateString = getDate(attr);
            final LocalDate date = estimateFromEvent(attr, dateString);
            if (date != null) {
                return date;
            }
        }
        return null;
    }

    /**
     * @param gob the event
     * @param dateString the date string from the event
     * @return the adjust date estimate. Can return null.
     */
    private LocalDate estimateFromEvent(final GedObject gob,
            final String dateString) {
        if (dateString == null || dateString.isEmpty()) {
            return null;
        }
        LocalDate date = createLocalDate(dateString);
        if (date == null) {
            return null;
        }
        final String eventTypeString = getString(gob);
        switch (eventTypeString) {
        case "Baptism":
        case "Christening":
        case "Naming":
            date = firstDayOfMonth(date);
            break;
        case "Bar Mitzvah":
        case "Bat Mitzvah":
            date = firstDayOfMonth(
                    minusYears(date, typicals.ageAtBarMitzvah()));
            break;
        case "Death":
        case "Burial":
            date = minusYearsAdjustToBegin(date, typicals.ageAtDeath());
            break;
        case "Changed":
            date = null;
            break;
        default:
            // Treat all adult events about the same age.
            // Changed is not an event, it's a data management attribute.
            date = minusYearsAdjustToBegin(date, typicals.ageAtMarriage());
            break;
        }
        return date;
    }
}
