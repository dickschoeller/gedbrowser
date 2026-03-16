package org.schoellerfamily.gedbrowser.analytics;

import java.util.List;

import org.joda.time.LocalDate;
import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.navigator.PersonNavigator;

/**
 * Represents birth date from siblings estimator.
 *
 * @author Richard Schoeller
 */
public final class BirthDateFromSiblingsEstimator extends Estimator {
    /** Hold the person we are estimating. */
    private final Person person;
    /** */
    private final Typicals typicals;

    /**
     * Creates a new BirthDateFromSiblingsEstimator.
     *
     * @param person the person
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
    @SuppressWarnings("java:S3776")
    public LocalDate estimateFromSiblings(final LocalDate localDate) {
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

    private int incrementWhenDate(final boolean beforePerson,
            final int increment) {
        if (beforePerson) {
            return typicals.gapBetweenChildren();
        }
        return increment - typicals.gapBetweenChildren();
    }

    private int incrementWhenEmptyDate(final boolean beforePerson,
            final int increment) {
        if (beforePerson) {
            return increment + typicals.gapBetweenChildren();
        }
        return increment - typicals.gapBetweenChildren();
    }

    /**
     * Executes estimate from siblings spouses.
     *
     * @param localDate the local date
     * @return the resulting local date
     */
    @SuppressWarnings("java:S3776")
    public LocalDate estimateFromSiblingsSpouses(final LocalDate localDate) {
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

    private LocalDate estimateFromSpousesAncestors(final BirthDateEstimator bde,
            final LocalDate date) {
        return bde.estimateFromSpousesAncestors(date);
    }

    private LocalDate estimateFromSpouse(final BirthDateEstimator bde,
            final LocalDate date) {
        return bde.estimateFromSpouses(date, true);
    }
}
