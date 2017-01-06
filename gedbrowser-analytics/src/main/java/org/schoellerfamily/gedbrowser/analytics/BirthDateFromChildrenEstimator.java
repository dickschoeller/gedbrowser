package org.schoellerfamily.gedbrowser.analytics;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;
import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.Person;

/**
 * Encapsulates the estimation methods associated with look at a person's
 * children.
 *
 * @author Dick Schoeller
 */
public final class BirthDateFromChildrenEstimator extends Estimator {
    /** Hold the person we are estimating. */
    private final Person person;
    /** */
    private final Typicals typicals;

    /**
     * Constructor.
     * @param person the person whose dates we are estimating
     */
    public BirthDateFromChildrenEstimator(final Person person) {
        this.person = person;
        this.typicals = new StandardTypicals();
    }

    /**
     * @param localDate if not null we already have a better estimate
     * @return estimated date from children's births
     */
    public LocalDate estimate(final LocalDate localDate) {
        if (localDate != null) {
            return localDate;
        }
        int count = 1;
        LocalDate date = null;
        final List<Family> families = person
                .getFamilies(new ArrayList<Family>());
        for (final Family family : families) {
            for (final Person child : getChildren(family)) {
                final String birthDateString = getBirthDate(child);
                if (validDateString(birthDateString)) {
                    date = createLocalDate(birthDateString);
                    break;
                }
                final BirthDateEstimator bde = createEstimator(child);
                date = estimateFromChild(bde);
                if (date != null) {
                    break;
                }
                count++;
            }
            if (date != null) {
                break;
            }
        }
        if (date != null) {
            final int increment = typicals.ageAtMarriage()
                    + (typicals.gapBetweenChildren() * count);
            date = firstDayOfMonth(minusYears(date, increment));
        }
        return date;
    }

    /**
     * @param localDate if not null we already have a better estimate
     * @return estimate from ancestors of spouses of children
     */
    public LocalDate estimateFromSpousesAncestors(
            final LocalDate localDate) {
        if (localDate != null) {
            return localDate;
        }
        LocalDate date = null;
        final List<Family> families = person
                .getFamilies(new ArrayList<Family>());
        for (final Family family : families) {
            for (final Person child : getChildren(family)) {
                final BirthDateEstimator bde = createEstimator(child);
                date = estimateFromSpousesAncestors(bde, date);
                if (date != null) {
                    break;
                }
            }
        }
        if (date != null) {
            date = minusYearsAdjustToBegin(date, typicals.ageAtMarriage()
                    + typicals.gapBetweenChildren());
        }
        return date;
    }

    /**
     * @param bde the estimator for this person
     * @param localDate the input date estimate
     * @return the new estimate
     */
    private LocalDate estimateFromSpousesAncestors(final BirthDateEstimator bde,
            final LocalDate localDate) {
        return bde.estimateFromSpousesAncestors(localDate);
    }

    /**
     * @param bde the estimator for this child
     * @return the new estimate
     */
    private LocalDate estimateFromChild(final BirthDateEstimator bde) {
        LocalDate date = null;
        date = bde.estimateFromChildren(date);
        date = bde.estimateFromOwnMarriage(date);
        date = bde.estimateFromSpouses(date, true);
        date = bde.estimateFromOtherEvents(date);
        return date;
    }
}
