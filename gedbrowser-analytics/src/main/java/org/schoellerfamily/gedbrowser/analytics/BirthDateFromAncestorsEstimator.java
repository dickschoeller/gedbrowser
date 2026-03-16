package org.schoellerfamily.gedbrowser.analytics;

import java.util.List;

import org.joda.time.LocalDate;
import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.navigator.FamilyNavigator;
import org.schoellerfamily.gedbrowser.datamodel.navigator.PersonNavigator;

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
     *
     * @param person the person whose dates we are estimating
     */
    public BirthDateFromAncestorsEstimator(final Person person) {
        this.person = person;
        this.typicals = new StandardTypicals();
    }

    /**
     * Estimate the birth date from all available data.
     *
     * @param localDate if not null we already have a better estimate
     * @return estimate from own ancestors
     */
    public LocalDate estimate(final LocalDate localDate) {
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
     * Estimate from events other than birth or marriage.
     *
     * @param localDate if not null we already have a better estimate
     * @return estimate based on other ancestor events
     */
    public LocalDate estimateFromOtherEvents(final LocalDate localDate) {
        if (localDate != null) {
            return localDate;
        }
        final PersonNavigator navigator = new PersonNavigator(person);
        final List<Family> families = navigator.getFamiliesC();
        LocalDate date = null;
        for (final Family family : families) {
            final Person father = getFather(family);
            final Person mother = getMother(family);
            date = estimateFromOtherEvents(date, createEstimator(father));
            date = estimateFromOtherEvents(date, createEstimator(mother));
            date = estimateFromAncestorsOtherEvents(date,
                    createEstimator(father));
            date = estimateFromAncestorsOtherEvents(date,
                    createEstimator(mother));
            if (date != null) {
                break;
            }
        }
        return ancestorAdjustment(date);
    }

    private LocalDate estimateFromAncestorsOtherEvents(final LocalDate date,
            final BirthDateEstimator bde) {
        return bde.estimateFromAncestorsOtherEvents(date);
    }

    private LocalDate estimateFromOtherEvents(final LocalDate date,
            final BirthDateEstimator bde) {
        return bde.estimateFromOtherEvents(date);
    }

    /**
     * Estimate from ancestors' birth dates.
     *
     * @param localDate if not null we already have a better estimate
     * @return estimate from some ancestor's birth date
     */
    public LocalDate estimateFromBirth(final LocalDate localDate) {
        if (localDate != null) {
            return localDate;
        }
        final PersonNavigator navigator = new PersonNavigator(person);
        final List<Family> families = navigator.getFamiliesC();
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
        return ancestorAdjustment(date);
    }

    private LocalDate estimateFromAncestorsBirth(final LocalDate date,
            final Person parent) {
        final BirthDateEstimator bde = createEstimator(parent);
        return estimateFromAncestorsBirth(date, bde);
    }

    private LocalDate estimateFromBirthDate(final LocalDate date,
            final Person parent) {
        if (date != null) {
            return date;
        }
        final String dateString = getBirthDate(parent);
        return createLocalDate(dateString);
    }

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
    public LocalDate estimateFromMarriage(final LocalDate localDate) {
        if (localDate != null) {
            return localDate;
        }
        final PersonNavigator navigator = new PersonNavigator(person);
        final List<Family> families = navigator.getFamiliesC();
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

    private LocalDate estimateFromFatherMarriage(final LocalDate localDate,
            final Family family) {
        if (localDate != null) {
            return localDate;
        }
        final FamilyNavigator navigator = new FamilyNavigator(family);
        final Person husband = navigator.getFather();
        return estimateFromParentMarriage(husband);
    }

    private LocalDate estimateFromMotherMarriage(final LocalDate localDate,
            final Family family) {
        if (localDate != null) {
            return localDate;
        }
        final FamilyNavigator navigator = new FamilyNavigator(family);
        final Person wife = navigator.getMother();
        return estimateFromParentMarriage(wife);
    }

    private LocalDate estimateFromParentMarriage(final Person parent) {
        final BirthDateEstimator bde = createEstimator(parent);
        return estimateFromParentMarriage(bde);
    }

    private LocalDate estimateFromParentMarriage(final BirthDateEstimator bde) {
        return ancestorAdjustment(bde.estimateFromAncestorsMarriage(null));
    }

    private LocalDate ancestorAdjustment(final LocalDate date) {
        if (date == null) {
            return null;
        }
        return date.plusYears(typicals.ageAtMarriage()
                + typicals.gapBetweenChildren())
                .withMonthOfYear(1).withDayOfMonth(1);
    }

    private LocalDate childAdjustment(final LocalDate date) {
        if (date == null) {
            return date;
        }
        return date.plusYears(typicals.gapBetweenChildren())
                .withMonthOfYear(1).withDayOfMonth(1);
    }
}
