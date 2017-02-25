package org.schoellerfamily.gedbrowser.analytics;

import java.util.List;

import org.joda.time.LocalDate;
import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.navigator.PersonNavigator;

/**
 * Encapsulates the estimation methods associated with look at a person's
 * ancestors.
 *
 * @author Dick Schoeller
 */
public final class BirthDateFromSiblingsEstimator extends Estimator {
    /** Hold the person we are estimating. */
    private final Person person;
    /** */
    private final Typicals typicals;

    /**
     * Constructor.
     * @param person the person whose dates we are estimating
     */
    public BirthDateFromSiblingsEstimator(final Person person) {
        this.person = person;
        this.typicals = new StandardTypicals();
    }

    /**
     * Try estimating from sibling dates. Null if no siblings or siblings
     * also don't have dates.
     *
     * @param localDate if not null we already have a better estimate
     * @return the estimate from siblings
     */
    LocalDate estimateFromSiblings(final LocalDate localDate) {
        if (localDate != null) {
            return localDate;
        }
        boolean beforePerson = true;
        LocalDate date = null;
        int increment = typicals.gapBetweenChildren();
        final PersonNavigator navigator = new PersonNavigator(person);
        final List<Family> families = navigator.getFamiliesC();
        for (final Family family : families) {
            for (final Person sibling : getChildren(family)) {
                if (person.equals(sibling)) {
                    beforePerson = false;
                    if (date != null) {
                        break;
                    }
                    increment = 0;
                    continue;
                }
                final String siblingString = getBirthDate(sibling);
                if (!validDateString(siblingString)) {
                    increment = incrementWhenEmptyDate(beforePerson, increment);
                    continue;
                }
                date = createLocalDate(siblingString);
                increment = incrementWhenDate(beforePerson, increment);
                if (!beforePerson) {
                    break;
                }
            }
        }

        if (date != null) {
            date = firstDayOfMonth(plusYears(date, increment));
        }
        return date;
    }

    /**
     * @param beforePerson whether we are before the person being estimated
     * @param increment current increment
     * @return new increment
     */
    private int incrementWhenDate(final boolean beforePerson,
            final int increment) {
        if (beforePerson) {
            return typicals.gapBetweenChildren();
        } else {
            return increment - typicals.gapBetweenChildren();
        }
    }

    /**
     * @param beforePerson whether we are before the person being estimated
     * @param increment current increment
     * @return new increment
     */
    private int incrementWhenEmptyDate(final boolean beforePerson,
            final int increment) {
        if (beforePerson) {
            return increment + typicals.gapBetweenChildren();
        } else {
            return increment - typicals.gapBetweenChildren();
        }
    }

    /**
     * @param localDate if not null we already have a better estimate
     * @return an estimate from spouses of siblings
     */
    LocalDate estimateFromSiblingsSpouses(final LocalDate localDate) {
        if (localDate != null) {
            return localDate;
        }
        LocalDate date = null;
        final PersonNavigator navigator = new PersonNavigator(person);
        final List<Family> families = navigator.getFamiliesC();
        for (final Family family : families) {
            for (final Person sibling : getChildren(family)) {
                if (person.equals(sibling)) {
                    continue;
                }
                final BirthDateEstimator bde = createEstimator(sibling);
                date = estimateFromSpouse(bde, date);
                date = estimateFromSpousesAncestors(bde, date);
                if (date != null) {
                    break;
                }
            }
        }
        return date;
    }

    /**
     * @param bde birth date estimator to use
     * @param date the input date
     * @return the adjusted date
     */
    private LocalDate estimateFromSpousesAncestors(final BirthDateEstimator bde,
            final LocalDate date) {
        return bde.estimateFromSpousesAncestors(date);
    }

    /**
     * @param bde birth date estimator to use
     * @param date the input date
     * @return the adjusted date
     */
    private LocalDate estimateFromSpouse(final BirthDateEstimator bde,
            final LocalDate date) {
        return bde.estimateFromSpouses(date, true);
    }
}
