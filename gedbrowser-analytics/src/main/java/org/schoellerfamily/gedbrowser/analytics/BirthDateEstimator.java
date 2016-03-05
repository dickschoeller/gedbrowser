package org.schoellerfamily.gedbrowser.analytics;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;
import org.schoellerfamily.gedbrowser.datamodel.Attribute;
import org.schoellerfamily.gedbrowser.datamodel.Date;
import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Person;

/**
 * @author Dick Schoeller
 */
public class BirthDateEstimator {
    /** Typical age at death. */
    private static final int TYPICAL_AGE_AT_DEATH = 75;
    /** Typical age at bar/bat mitzvah. */
    private static final int AGE_AT_BAR_MITZVAH = 13;
    /** Typical age at marriage. */
    private static final int TYPICAL_AGE_AT_MARRIAGE = 25;
    /** Typical gap between children. */
    private static final int TYPICAL_GAP_BETWEEN_CHILDREN = 2;
    /** Typical gap between husband and wife. */
    private static final int TYPICAL_GAP_BETWEEN_SPOUSES = 5;

    /** Hold the person we are estimating. */
    private final Person person;

    /**
     * Constructor.
     *
     * @param person the person we are estimating
     */
    public BirthDateEstimator(final Person person) {
        this.person = person;
    }

    /**
     * Avoid warnings about new in loops.
     *
     * @param p the person to estimate
     * @return the estimator
     */
    private BirthDateEstimator createEstimator(final Person p) {
        return new BirthDateEstimator(p);
    }

    /**
     * @return the estimated birth date as a LocalDate
     */
    public final LocalDate estimateBirthDate() {
        final String birthDateString = person.getBirthDate();
        LocalDate localDate = null;
        if (!birthDateString.isEmpty()) {
            final Date birthDate = new Date(null, birthDateString);
            localDate = new LocalDate(birthDate.getEstimateCalendar());
        }
        if (localDate != null) {
            return localDate;
        }
        localDate = estimateFromOwnMarriage();
        if (localDate != null) {
            return localDate;
        }
        localDate = estimateFromSiblings();
        if (localDate != null) {
            return localDate;
        }
        localDate = estimateFromParentsMarriage();
        if (localDate != null) {
            return localDate;
        }
        localDate = estimateFromParentsBirth();
        if (localDate != null) {
            return localDate;
        }
        localDate = estimateFromChildren();
        if (localDate != null) {
            return localDate;
        }
        localDate = estimateFromSpouse(true);
        if (localDate != null) {
            return localDate;
        }
        localDate = estimateFromOtherEvents();
        if (localDate != null) {
            return localDate;
        }
        localDate = estimateFromAncestors();
        if (localDate != null) {
            return localDate;
        }
        localDate = estimateFromSpousesAncestors();
        if (localDate != null) {
            return localDate;
        }
        localDate = estimateFromChildrensSpousesAncestors();
        if (localDate != null) {
            return localDate;
        }
        localDate = estimateFromSiblingsSpouses();
        if (localDate != null) {
            return localDate;
        }
        localDate = estimateFromSpouse(false);
        if (localDate != null) {
            return localDate;
        }

        return localDate;
    }

    /**
     * @return an estimate from spouses of siblings
     */
    private LocalDate estimateFromSiblingsSpouses() {
        LocalDate date = null;
        final List<Family> families = person
                .getFamiliesC(new ArrayList<Family>());
        for (final Family family : families) {
            for (final Person sibling : family.getChildren()) {
                if (person.equals(sibling)) {
                    continue;
                }
                final BirthDateEstimator bde = createEstimator(sibling);
                date = bde.estimateFromSpouse(true);
                if (date != null) {
                    break;
                }
                date = bde.estimateFromSpousesAncestors();
                if (date != null) {
                    break;
                }
            }
        }
        return date;
    }

    /**
     * @return estimate from ancestors of spouses of children
     */
    private LocalDate estimateFromChildrensSpousesAncestors() {
        LocalDate date = null;
        final List<Family> families = person
                .getFamilies(new ArrayList<Family>());
        for (final Family family : families) {
            for (final Person child : family.getChildren()) {
                final BirthDateEstimator bde = createEstimator(child);
                date = bde.estimateFromSpousesAncestors();
                if (date != null) {
                    break;
                }
            }
        }
        if (date != null) {
            date = date.minusYears(TYPICAL_AGE_AT_MARRIAGE
                    + TYPICAL_GAP_BETWEEN_CHILDREN)
                    .withMonthOfYear(1).withDayOfMonth(1);
        }
        return date;
    }

    /**
     * @return estimate from spouses' ancestors
     */
    private LocalDate estimateFromSpousesAncestors() {
        LocalDate date = null;
        final List<Family> families = person
                .getFamilies(new ArrayList<Family>());
        for (final Family family : families) {
            final Person mother = family.getMother();
            final Person father = family.getFather();
            Person spouse;
            if (person.equals(mother)) {
                if (father == null) {
                    continue;
                }
                spouse = father;
            } else {
                if (mother == null) {
                    continue;
                }
                spouse = mother;
            }
            final BirthDateEstimator bde = createEstimator(spouse);
            date = bde.estimateFromAncestors();
            if (date != null) {
                break;
            }
        }
        return date;
    }

    /**
     * @return estimate from own ancestors
     */
    private LocalDate estimateFromAncestors() {
        LocalDate localDate = null;
        localDate = estimateFromAncestorsMarriage();
        if (localDate == null) {
            localDate = estimateFromAncestorsBirth();
            if (localDate == null) {
                localDate = estimateAncestorsOtherEvents();
            }
        }
        return localDate;
    }

    /**
     * @return estimate based on other ancestor events
     */
    private LocalDate estimateAncestorsOtherEvents() {
        final List<Family> families = person
                .getFamiliesC(new ArrayList<Family>());
        LocalDate date = null;
        for (final Family family : families) {
            final Person father = family.getFather();
            if (father != null) {
                final BirthDateEstimator bde = createEstimator(father);
                date = bde.estimateFromOtherEvents();
                if (date != null) {
                    break;
                }
            }
            final Person mother = family.getMother();
            if (mother != null) {
                final BirthDateEstimator bde = createEstimator(mother);
                date = bde.estimateFromOtherEvents();
                if (date != null) {
                    break;
                }
            }
            if (father != null) {
                final BirthDateEstimator bde = createEstimator(father);
                date = bde.estimateAncestorsOtherEvents();
                if (date != null) {
                    break;
                }
            }
            if (mother != null) {
                final BirthDateEstimator bde = createEstimator(mother);
                date = bde.estimateAncestorsOtherEvents();
                if (date != null) {
                    break;
                }
            }
        }
        if (date != null) {
            date = date.plusYears(
                    TYPICAL_AGE_AT_MARRIAGE + TYPICAL_GAP_BETWEEN_CHILDREN)
                    .withMonthOfYear(1).withDayOfMonth(1);
        }
        return date;
    }

    /**
     * @return estimate from some ancestor's birth date
     */
    private LocalDate estimateFromAncestorsBirth() {
        final List<Family> families = person
                .getFamiliesC(new ArrayList<Family>());
        LocalDate date = null;
        for (final Family family : families) {
            final Person father = family.getFather();
            if (father != null) {
                final String dateString = father.getBirthDate();
                if (dateString != null && !dateString.isEmpty()) {
                    final Date birthDate = new Date(null, dateString);
                    date = new LocalDate(birthDate.getEstimateCalendar());
                    break;
                }
            }
            final Person mother = family.getMother();
            if (mother != null) {
                final String dateString = mother.getBirthDate();
                if (dateString != null && !dateString.isEmpty()) {
                    final Date birthDate = new Date(null, dateString);
                    date = new LocalDate(birthDate.getEstimateCalendar());
                    break;
                }
            }
            if (father != null) {
                final BirthDateEstimator bde = createEstimator(father);
                date = bde.estimateFromAncestorsBirth();
                if (date != null) {
                    break;
                }
            }
            if (mother != null) {
                final BirthDateEstimator bde = createEstimator(mother);
                date = bde.estimateFromAncestorsBirth();
                if (date != null) {
                    break;
                }
            }
        }
        if (date != null) {
            date = date.plusYears(
                    TYPICAL_AGE_AT_MARRIAGE + TYPICAL_GAP_BETWEEN_CHILDREN)
                    .withMonthOfYear(1).withDayOfMonth(1);
        }
        return date;
    }

    /**
     * @return estimate from parents birth date
     */
    private LocalDate estimateFromParentsBirth() {
        final List<Family> families = person
                .getFamiliesC(new ArrayList<Family>());
        LocalDate date = null;
        for (final Family family : families) {
            final Person father = family.getFather();
            if (father != null) {
                final String dateString = father.getBirthDate();
                if (dateString != null && !dateString.isEmpty()) {
                    final Date birthDate = new Date(null, dateString);
                    date = new LocalDate(birthDate.getEstimateCalendar());
                    date = date.plusYears(
                            TYPICAL_AGE_AT_MARRIAGE
                                    + TYPICAL_GAP_BETWEEN_CHILDREN)
                            .withMonthOfYear(1).withDayOfMonth(1);
                    break;
                }
            }
            final Person mother = family.getMother();
            if (mother != null) {
                final String dateString = mother.getBirthDate();
                if (dateString != null && !dateString.isEmpty()) {
                    final Date birthDate = new Date(null, dateString);
                    date = new LocalDate(birthDate.getEstimateCalendar());
                    date = date.plusYears(
                            TYPICAL_AGE_AT_MARRIAGE
                                    + TYPICAL_GAP_BETWEEN_CHILDREN)
                            .withMonthOfYear(1).withDayOfMonth(1);
                    break;
                }
            }
        }
        return date;
    }

    /**
     * @return the estimated birth date as a LocalDate
     */
    private LocalDate shortEstimate() {
        final String birthDateString = person.getBirthDate();
        LocalDate localDate = null;
        if (!birthDateString.isEmpty()) {
            final Date birthDate = new Date(null, birthDateString);
            localDate = new LocalDate(birthDate.getEstimateCalendar());
        }
        if (localDate != null) {
            return localDate;
        }
        localDate = estimateFromSiblings();
        if (localDate != null) {
            return localDate;
        }
        localDate = estimateFromParentsMarriage();
        if (localDate != null) {
            return localDate;
        }
        localDate = estimateFromChildren();
        if (localDate != null) {
            return localDate;
        }
        return null;
    }

    /**
     * Try estimating from sibling dates. Null if no siblings or siblings
     * also don't have dates.
     *
     * @return the estimate from siblings
     */
    private LocalDate estimateFromSiblings() {
        boolean beforePerson = true;
        LocalDate date = null;
        int increment = TYPICAL_GAP_BETWEEN_CHILDREN;
        final List<Family> families = person
                .getFamiliesC(new ArrayList<Family>());
        for (final Family family : families) {
            for (final Person sibling : family.getChildren()) {
                if (person.equals(sibling)) {
                    beforePerson = false;
                    if (date != null) {
                        break;
                    }
                    increment = 0;
                    continue;
                }
                final String siblingString = sibling.getBirthDate();
                if (siblingString.isEmpty()) {
                    if (beforePerson) {
                        increment += TYPICAL_GAP_BETWEEN_CHILDREN;
                    } else {
                        increment -= TYPICAL_GAP_BETWEEN_CHILDREN;
                    }
                    continue;
                }
                final Date siblingDate = new Date(null, siblingString);
                date = new LocalDate(siblingDate.getEstimateCalendar());
                if (beforePerson) {
                    increment = TYPICAL_GAP_BETWEEN_CHILDREN;
                } else {
                    increment -= TYPICAL_GAP_BETWEEN_CHILDREN;
                    break;
                }
            }
        }

        if (date != null) {
            date = date.plusYears(increment).withDayOfMonth(1);
        }
        return date;
    }

    /**
     * @return the estimate from own marriages
     */
    private LocalDate estimateFromOwnMarriage() {
        final List<Family> families = person
                .getFamilies(new ArrayList<Family>());
        LocalDate date = null;
        for (final Family family : families) {
            date = processMarriageDate(date, family);
        }
        if (date != null) {
            date = date.minusYears(TYPICAL_AGE_AT_MARRIAGE).withDayOfMonth(1);
        }
        return date;
    }

    /**
     * @param date the current best date
     * @param family the family being processed
     * @return the date we got out of this family
     */
    private LocalDate processMarriageDate(final LocalDate date,
            final Family family) {
        LocalDate returnDate = date;
        for (final GedObject gob : family.getAttributes()) {
            if (!(gob instanceof Attribute)) {
                continue;
            }
            if (!"Marriage".equals(gob.getString())) {
                continue;
            }
            final Attribute attr = (Attribute) gob;
            final String datestring = attr.getDate();
            if (datestring.isEmpty()) {
                continue;
            }
            final Date marriageDate = new Date(null, datestring);
            final LocalDate marriageLocalDate = new LocalDate(
                    marriageDate.getEstimateCalendar());
            if (returnDate == null) {
                returnDate = marriageLocalDate;
            } else {
                if (marriageLocalDate.isBefore(returnDate)) {
                    returnDate = marriageLocalDate;
                }
            }
        }
        return returnDate;
    }

    /**
     * @return estimate from the date of parents marriage.
     */
    private LocalDate estimateFromParentsMarriage() {
        int offsetCount = 0;
        final List<Family> families = person
                .getFamiliesC(new ArrayList<Family>());
        LocalDate date = null;
        for (final Family family : families) {
            offsetCount = 1;
            date = processMarriageDate(date, family);
            for (final Person sibling : family.getChildren()) {
                if (person.equals(sibling)) {
                    break;
                }
                offsetCount++;
            }
        }
        if (date != null) {
            date = date.plusYears(TYPICAL_GAP_BETWEEN_CHILDREN * offsetCount)
                    .withDayOfMonth(1);
        }
        return date;
    }

    /**
     * Try recursing through the ancestors to find a marriage date.
     *
     * @return an estimate based on some ancestor's marriage date
     */
    private LocalDate estimateFromAncestorsMarriage() {
        final List<Family> families = person
                .getFamiliesC(new ArrayList<Family>());
        LocalDate date = null;
        for (final Family family : families) {
            date = processMarriageDate(date, family);
            if (date != null) {
                date = date.plusYears(TYPICAL_GAP_BETWEEN_CHILDREN)
                        .withMonthOfYear(1).withDayOfMonth(1);
                break;
            }
            final Person husband = family.getFather();
            if (husband != null) {
                final BirthDateEstimator bde = createEstimator(husband);
                date = bde.estimateFromOwnMarriage();
                if (date != null) {
                    date = date.plusYears(TYPICAL_AGE_AT_MARRIAGE
                            + TYPICAL_GAP_BETWEEN_CHILDREN)
                            .withMonthOfYear(1).withDayOfMonth(1);
                    break;
                }
                date = bde.estimateFromAncestorsMarriage();
                if (date != null) {
                    date = date.plusYears(TYPICAL_AGE_AT_MARRIAGE
                            + TYPICAL_GAP_BETWEEN_CHILDREN)
                            .withMonthOfYear(1).withDayOfMonth(1);
                    break;
                }
            }
            final Person wife = family.getMother();
            if (wife != null) {
                final BirthDateEstimator bde = createEstimator(wife);
                date = bde.estimateFromOwnMarriage();
                if (date != null) {
                    date = date.plusYears(TYPICAL_AGE_AT_MARRIAGE
                            + TYPICAL_GAP_BETWEEN_CHILDREN)
                            .withMonthOfYear(1).withDayOfMonth(1);
                    break;
                }
                date = bde.estimateFromAncestorsMarriage();
                if (date != null) {
                    date = date.plusYears(TYPICAL_AGE_AT_MARRIAGE
                            + TYPICAL_GAP_BETWEEN_CHILDREN)
                            .withMonthOfYear(1).withDayOfMonth(1);
                    break;
                }
            }
        }
        return date;
    }

    /**
     * @return estimated date from children's births
     */
    private LocalDate estimateFromChildren() {
        int count = 1;
        LocalDate date = null;
        final List<Family> families = person
                .getFamilies(new ArrayList<Family>());
        for (final Family family : families) {
            for (final Person child : family.getChildren()) {
                final String birthDateString = child.getBirthDate();
                if (birthDateString.isEmpty()) {
                    final BirthDateEstimator bde =
                            createEstimator(child);
                    date = bde.estimateFromChildren();
                    if (date != null) {
                        break;
                    }
                    date = bde.estimateFromOwnMarriage();
                    if (date != null) {
                        break;
                    }
                    date = bde.estimateFromSpouse(true);
                    if (date != null) {
                        break;
                    }
                    count++;
                    continue;
                }
                final Date birthDate = new Date(null, birthDateString);
                date = new LocalDate(birthDate.getEstimateCalendar());
                if (date != null) {
                    break;
                }
            }
            if (date != null) {
                break;
            }
        }
        if (date != null) {
            final int increment = TYPICAL_AGE_AT_MARRIAGE
                    + (TYPICAL_GAP_BETWEEN_CHILDREN * count);
            date = date.minusYears(increment).withDayOfMonth(1);
        }
        return date;
    }

    /**
     * @param shortEstimate whether to do a short estimate or a deep estimate
     * @return the estimate from own marriages
     */
    private LocalDate estimateFromSpouse(final boolean shortEstimate) {
        LocalDate date = null;
        int delta = 0;
        final List<Family> families = person
                .getFamilies(new ArrayList<Family>());
        for (final Family family : families) {
            final Person mother = family.getMother();
            final Person father = family.getFather();
            Person spouse;
            if (person.equals(mother)) {
                if (father == null) {
                    continue;
                }
                spouse = father;
                delta = TYPICAL_GAP_BETWEEN_SPOUSES;
            } else {
                if (mother == null) {
                    continue;
                }
                spouse = mother;
                delta = -TYPICAL_GAP_BETWEEN_SPOUSES;
            }
            final BirthDateEstimator bde = createEstimator(spouse);
            // TODO can we do a template method or similar pattern here?
            if (shortEstimate) {
                date = bde.shortEstimate();
            } else {
                date = bde.estimateFromSpouse(true);
            }
            if (date != null) {
                break;
            }
        }
        if (date != null) {
            date = date.plusYears(delta).withDayOfMonth(1);
        }
        return date;
    }

    /**
     * Try other lifecycle events.
     *
     * @return estimate
     */
    private LocalDate estimateFromOtherEvents() {
        for (final GedObject gob : person.getAttributes()) {
            if (!(gob instanceof Attribute)) {
                continue;
            }
            final Attribute attr = (Attribute) gob;
            final String dateString = attr.getDate();
            if (dateString == null || dateString.isEmpty()) {
                continue;
            }
            final Date date = new Date(null, dateString);
            LocalDate localDate = new LocalDate(date.getEstimateCalendar());
            final String eventTypeString = gob.getString();
            if ("Baptism".equals(eventTypeString)
                    || "Christening".equals(eventTypeString)) {
                localDate = localDate.withDayOfMonth(1);
                return localDate;
            }
            if ("Bar Mitzvah".equals(eventTypeString)
                    || "Bat Mitzvah".equals(eventTypeString)) {
                localDate = localDate.minusYears(AGE_AT_BAR_MITZVAH)
                        .withDayOfMonth(1);
                return localDate;
            } else if ("Death".equals(eventTypeString)
                    || "Burial".equals(eventTypeString)) {
                localDate = localDate.minusYears(TYPICAL_AGE_AT_DEATH)
                        .withMonthOfYear(1)
                        .withDayOfMonth(1);
                return localDate;
            } else if (!"Changed".equals(eventTypeString)) {
                // Treat all adult events about the same age.
                // Changed is not an event, it's a data management attribute.
                localDate = localDate.minusYears(TYPICAL_AGE_AT_MARRIAGE)
                        .withMonthOfYear(1)
                        .withDayOfMonth(1);
                return localDate;
            }
        }
        return null;
    }
}
