package org.schoellerfamily.gedbrowser.analytics;

import java.util.List;

import org.joda.time.LocalDate;
import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.navigator.PersonNavigator;

/**
 * Represents birth date from spouses estimator.
 *
 * @author Richard Schoeller
 */
public final class BirthDateFromSpousesEstimator extends Estimator {
    /** Hold the person we are estimating. */
    private final Person person;
    /** */
    private final Typicals typicals;

    /**
     * Creates a new BirthDateFromSpousesEstimator.
     *
     * @param person the person
     */
    public BirthDateFromSpousesEstimator(final Person person) {
        this.person = person;
        this.typicals = new StandardTypicals();
    }

    /**
     * Executes estimate.
     *
     * @param localDate the local date
     * @param shortEstimate the short estimate
     * @return the resulting local date
     */
    @SuppressWarnings("java:S3776")
    public LocalDate estimate(final LocalDate localDate,
            final boolean shortEstimate) {
        if (localDate != null) {
            return localDate;
        }
        LocalDate date = null;
        int delta = 0;
        final PersonNavigator navigator = new PersonNavigator(person);
        final List<Family> families = navigator.getFamilies();
        for (final Family family : families) {
            final Person mother = getMother(family);
            final Person father = getFather(family);
            final Person spouse;
            if (person.equals(mother)) {
                if (father == null) {
                    continue;
                }
                spouse = father;
                delta = typicals.gapBetweenSpouses();
            } else {
                if (mother == null) {
                    continue;
                }
                spouse = mother;
                delta = -typicals.gapBetweenSpouses();
            }
            final BirthDateEstimator bde = createEstimator(spouse);
            // TODO can we do a template method or similar pattern here?
            if (shortEstimate) {
                date = shortEstimate(bde);
            } else {
                date = estimateFromSpouse(bde, null, true);
            }
            if (date != null) {
                break;
            }
        }
        if (date != null) {
            date = firstDayOfMonth(plusYears(date, delta));
        }
        return date;
    }

    /**
     * Executes estimate from ancestors.
     *
     * @param localDate the local date
     * @return the resulting local date
     */
    @SuppressWarnings("java:S3776")
    public LocalDate estimateFromAncestors(final LocalDate localDate) {
        if (localDate != null) {
            return localDate;
        }
        LocalDate date = null;
        final PersonNavigator navigator = new PersonNavigator(person);
        final List<Family> families = navigator.getFamilies();
        for (final Family family : families) {
            final Person mother = getMother(family);
            final Person father = getFather(family);
            final Person spouse;
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
            date = ancestorsEstimate(bde);
            if (date != null) {
                break;
            }
        }
        return date;
    }

    private LocalDate shortEstimate(final BirthDateEstimator bde) {
        return bde.shortEstimate();
    }

    private LocalDate estimateFromSpouse(final BirthDateEstimator bde,
            final LocalDate localDate,
            final boolean shortEstimate) {
        return bde.estimateFromSpouses(localDate, shortEstimate);
    }

    private LocalDate ancestorsEstimate(final BirthDateEstimator bde) {
        return bde.ancestorsEstimate();
    }
}
