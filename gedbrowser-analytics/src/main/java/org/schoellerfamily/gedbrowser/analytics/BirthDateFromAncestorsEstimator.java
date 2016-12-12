package org.schoellerfamily.gedbrowser.analytics;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;
import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.Person;

/**
 * Encapsulates the estimation methods associated with look at a person's
 * ancestors.
 *
 * @author Dick Schoeller
 */
@SuppressWarnings("PMD.TooManyMethods")
public final class BirthDateFromAncestorsEstimator extends Estimator {
    /** Hold the person we are estimating. */
    private final Person person;
    /** */
    private final Typicals typicals;

    /**
     * Constructor.
     * @param person the person whose dates we are estimating
     */
    public BirthDateFromAncestorsEstimator(final Person person) {
        this.person = person;
        this.typicals = new StandardTypicals();
    }

    /**
     * @param localDate if not null we already have a better estimate
     * @return estimate from own ancestors
     */
    LocalDate estimate(final LocalDate localDate) {
        if (localDate != null) {
            return localDate;
        }
        LocalDate date = null;
        date = estimateFromMarriage(date);
        date = estimateFromBirth(date);
        date = estimateFromOtherEvents(date);
        return date;
    }

    /**
     * @param localDate if not null we already have a better estimate
     * @return estimate based on other ancestor events
     */
    LocalDate estimateFromOtherEvents(final LocalDate localDate) {
        if (localDate != null) {
            return localDate;
        }
        final List<Family> families = person
                .getFamiliesC(new ArrayList<Family>());
        LocalDate date = null;
        for (final Family family : families) {
            final Person father = getFather(family);
            final Person mother = getMother(family);
            if (father != null) {
                final BirthDateEstimator bde = createEstimator(father);
                date = estimateFromOtherEvents(date, bde);
            }
            if (mother != null) {
                final BirthDateEstimator bde = createEstimator(mother);
                date = estimateFromOtherEvents(date, bde);
            }
            if (father != null) {
                final BirthDateEstimator bde = createEstimator(father);
                date = estimateFromAncestorsOtherEvents(date, bde);
            }
            if (mother != null) {
                final BirthDateEstimator bde = createEstimator(mother);
                date = estimateFromAncestorsOtherEvents(date, bde);
            }
            if (date != null) {
                break;
            }
        }
        if (date != null) {
            date = ancestorAdjustment(date);
        }
        return date;
    }

    /**
     * @param date estimated birth date before calling this method
     * @param bde the birth date estimate to invoke
     * @return a new estimated birth date
     */
    private LocalDate estimateFromAncestorsOtherEvents(final LocalDate date,
            final BirthDateEstimator bde) {
        return bde.estimateFromAncestorsOtherEvents(date);
    }

    /**
     * @param date estimated birth date before calling this method
     * @param bde the birth date estimate to invoke
     * @return a new estimated birth date
     */
    private LocalDate estimateFromOtherEvents(final LocalDate date,
            final BirthDateEstimator bde) {
        return bde.estimateFromOtherEvents(date);
    }

    /**
     * @param localDate if not null we already have a better estimate
     * @return estimate from some ancestor's birth date
     */
    LocalDate estimateFromBirth(final LocalDate localDate) {
        if (localDate != null) {
            return localDate;
        }
        final List<Family> families = person
                .getFamiliesC(new ArrayList<Family>());
        LocalDate date = null;
        for (final Family family : families) {
            final Person father = getFather(family);
            final Person mother = getMother(family);
            date = estimateFromBirthDate(date, father);
            date = estimateFromBirthDate(date, mother);
            date = estimateFromAncestorsBirth(date, father);
            date = estimateFromAncestorsBirth(date, mother);
            if (date != null) {
                break;
            }
        }
        if (date != null) {
            date = ancestorAdjustment(date);
        }
        return date;
    }

    /**
     * @param date the date before trying this estimate
     * @param parent the person whose birth date we're going to try to apply
     * @return the new estimate
     */
    private LocalDate estimateFromAncestorsBirth(final LocalDate date,
            final Person parent) {
        if (parent == null || date != null) {
            return date;
        }
        final BirthDateEstimator bde = createEstimator(parent);
        return estimateFromAncestorsBirth(date, bde);
    }

    /**
     * @param date the date before trying this estimate
     * @param parent the person whose birth date we're going to try to apply
     * @return the new estimate
     */
    private LocalDate estimateFromBirthDate(final LocalDate date,
            final Person parent) {
        if (date != null) {
            return date;
        }
        if (parent != null) {
            final String dateString = getBirthDate(parent);
            return createLocalDate(dateString);
        }
        return null;
    }

    /**
     * @param localDate if not null we already have a better estimate
     * @param bde birth date estimator for some related person
     * @return the date estimate
     */
    private LocalDate estimateFromAncestorsBirth(final LocalDate localDate,
            final BirthDateEstimator bde) {
        return bde.estimateFromAncestorsBirth(localDate);
    }

    /**
     * Try recursing through the ancestors to find a marriage date.
     *
     * @param localDate if not null we already have a better estimate
     * @return an estimate based on some ancestor's marriage date
     */
    LocalDate estimateFromMarriage(final LocalDate localDate) {
        if (localDate != null) {
            return localDate;
        }
        final List<Family> families = person
                .getFamiliesC(new ArrayList<Family>());
        LocalDate date = null;
        for (final Family family : families) {
            date = processMarriageDate(date, family);
            date = childAdjustment(date);
            date = estimateFromFatherMarriage(date, family);
            date = estimateFromMotherMarriage(date, family);
            if (date != null) {
                break;
            }
        }
        return date;
    }

    /**
     * @param localDate if not null we already have a better estimate
     * @param family the family from which father will be checked
     * @return the estimated birth date
     */
    private LocalDate estimateFromFatherMarriage(final LocalDate localDate,
            final Family family) {
        if (localDate != null) {
            return localDate;
        }
        final Person husband = family.getFather();
        if (husband != null) {
            return estimateFromParentMarriage(husband);
        }
        return null;
    }

    /**
     * @param localDate if not null we already have a better estimate
     * @param family the family from which mother will be checked
     * @return the estimated birth date
     */
    private LocalDate estimateFromMotherMarriage(final LocalDate localDate,
            final Family family) {
        if (localDate != null) {
            return localDate;
        }
        final Person wife = family.getMother();
        if (wife != null) {
            return estimateFromParentMarriage(wife);
        }
        return null;
    }

    /**
     * Estimate birth date from parent's marriage.
     *
     * @param parent the parent
     * @return the date estimate
     */
    private LocalDate estimateFromParentMarriage(final Person parent) {
        final BirthDateEstimator bde = createEstimator(parent);
        return estimateFromParentMarriage(bde);
    }

    /**
     * Estimate birth date from parent's marriage.
     *
     * @param bde an estimator for the parent
     * @return the date estimate
     */
    private LocalDate estimateFromParentMarriage(final BirthDateEstimator bde) {
        LocalDate date;
        date = bde.estimateFromOwnMarriage(null);
        if (date == null) {
            date = bde.estimateFromAncestorsMarriage(null);
            if (date != null) {
                date = ancestorAdjustment(date);
            }
        } else {
            date = ancestorAdjustment(date);
        }
        return date;
    }

    /**
     * Apply a standard adjustment from an ancestor's marriage date to a
     * person's birth date.
     *
     * @param date the ancestor's marriage date
     * @return the adjusted date
     */
    @SuppressWarnings("PMD.LawOfDemeter")
    private LocalDate ancestorAdjustment(final LocalDate date) {
        return date.plusYears(typicals.ageAtMarriage()
                + typicals.gapBetweenChildren())
                .withMonthOfYear(1).withDayOfMonth(1);
    }

    /**
     * Adjust by the gap between children and to beginning of month.
     *
     * @param date the input date
     * @return the adjusted date
     */
    @SuppressWarnings("PMD.LawOfDemeter")
    private LocalDate childAdjustment(final LocalDate date) {
        if (date == null) {
            return date;
        }
        return date.plusYears(typicals.gapBetweenChildren())
                .withMonthOfYear(1).withDayOfMonth(1);
    }
}
