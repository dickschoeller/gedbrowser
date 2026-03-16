package org.schoellerfamily.gedbrowser.analytics;

import org.joda.time.LocalDate;
import org.schoellerfamily.gedbrowser.datamodel.Person;

/**
 * Represents birth date estimator.
 *
 * @author Richard Schoeller
 */
public final class BirthDateEstimator extends BasicBirthDateEstimator {
    /**
     * Associated utility for estimating from ancestor dates.
     */
    private final BirthDateFromAncestorsEstimator ancestorsEstimator;

    /**
     * Associated utility for estimating from parent dates.
     */
    private final BirthDateFromParentsEstimator parentsEstimator;

    /**
     * Associated utility for estimating from siblings dates.
     */
    private final BirthDateFromSiblingsEstimator siblingsEstimator;

    /**
     * Associated utility for estimating from siblings dates.
     */
    private final BirthDateFromSpousesEstimator spousesEstimator;

    /**
     * Associated utility for estimating from siblings dates.
     */
    private final BirthDateFromChildrenEstimator childrenEstimator;

    /**
     * Creates a new BirthDateEstimator.
     *
     * @param person the person
     */
    public BirthDateEstimator(final Person person) {
        super(person);
        this.ancestorsEstimator =
                new BirthDateFromAncestorsEstimator(person);
        this.parentsEstimator = new BirthDateFromParentsEstimator(person);
        this.siblingsEstimator =
                new BirthDateFromSiblingsEstimator(person);
        this.spousesEstimator =
                new BirthDateFromSpousesEstimator(person);
        this.childrenEstimator =
                new BirthDateFromChildrenEstimator(person);
    }

    /**
     * Estimate the birth date.
     *
     * @return the estimated birth date as a LocalDate
     */
    public LocalDate estimateBirthDate() {
        LocalDate localDate = estimateFromBirthDate();
        localDate = estimateFromOwnMarriage(localDate);
        localDate = siblingsEstimator.estimateFromSiblings(localDate);
        localDate = parentsEstimator.estimateFromMarriage(localDate);
        localDate = parentsEstimator.estimateFromBirth(localDate);
        localDate = childrenEstimator.estimate(localDate);
        localDate = spousesEstimator.estimate(localDate, true);
        localDate = estimateFromOtherEvents(localDate);
        localDate = ancestorsEstimator.estimate(localDate);
        localDate = spousesEstimator.estimateFromAncestors(localDate);
        localDate = childrenEstimator.estimateFromSpousesAncestors(localDate);
        localDate = siblingsEstimator.estimateFromSiblingsSpouses(localDate);
        localDate = spousesEstimator.estimate(localDate, false);
        return localDate;
    }

    /**
     * Estimate from spouses' ancestors.
     *
     * @param localDate the input date estimate
     * @return the new estimate
     */
    public LocalDate estimateFromSpousesAncestors(final LocalDate localDate) {
        return spousesEstimator.estimateFromAncestors(localDate);
    }

    /**
     * Try recursing through the ancestors to find any date.
     *
     * @return the estimate based on the ancestors' dates.
     */
    public LocalDate ancestorsEstimate() {
        return ancestorsEstimator.estimate(null);
    }

    /**
     * Estimate the birth date using just close relatives.
     *
     * @return the estimated birth date as a LocalDate
     */
    public LocalDate shortEstimate() {
        LocalDate localDate = estimateFromBirthDate();
        localDate = siblingsEstimator.estimateFromSiblings(localDate);
        localDate = parentsEstimator.estimateFromMarriage(localDate);
        localDate = childrenEstimator.estimate(localDate);
        return localDate;
    }

    /**
     * Estimate from ancestors' other events.
     *
     * @param localDate if not null we already have a better estimate
     * @return estimate from own ancestors' other events
     */
    public LocalDate estimateFromAncestorsOtherEvents(
            final LocalDate localDate) {
        return ancestorsEstimator.estimateFromOtherEvents(localDate);
    }

    /**
     * Try recursing through the ancestors to find a marriage date.
     *
     * @param localDate if not null we already have a better estimate
     * @return an estimate based on some ancestor's marriage date
     */
    public LocalDate estimateFromAncestorsMarriage(final LocalDate localDate) {
        return ancestorsEstimator.estimateFromMarriage(localDate);
    }

    /**
     * Try recursing through the ancestors to find a birth date.
     *
     * @param localDate if not null we already have a better estimate
     * @return estimate from some ancestor's birth date
     */
    public LocalDate estimateFromAncestorsBirth(final LocalDate localDate) {
        return ancestorsEstimator.estimateFromBirth(localDate);
    }

    /**
     * Estimate from own marriages.
     *
     * @param localDate if not null we already have a better estimate
     * @param shortEstimate whether to do a short estimate or a deep estimate
     * @return the estimate from own marriages
     */
    public LocalDate estimateFromSpouses(final LocalDate localDate,
            final boolean shortEstimate) {
        return spousesEstimator.estimate(localDate, shortEstimate);
    }

    /**
     * Estimate from some children's dates.
     *
     * @param localDate if not null we already have a better estimate
     * @return estimate from some children
     */
    public LocalDate estimateFromChildren(final LocalDate localDate) {
        return childrenEstimator.estimate(localDate);
    }
}
