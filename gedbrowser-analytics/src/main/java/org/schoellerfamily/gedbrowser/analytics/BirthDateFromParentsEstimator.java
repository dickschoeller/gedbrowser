package org.schoellerfamily.gedbrowser.analytics;

import java.util.List;

import org.joda.time.LocalDate;
import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.navigator.PersonNavigator;

/**
 * Encapsulates the estimation methods associated with look at a person's
 * parents.
 *
 * @author Dick Schoeller
 */
public final class BirthDateFromParentsEstimator extends Estimator {
    /** Hold the person we are estimating. */
    private final Person person;
    /** */
    private final Typicals typicals;

    /**
     * Constructor.
     * @param person the person whose dates we are estimating
     */
    public BirthDateFromParentsEstimator(final Person person) {
        this.person = person;
        this.typicals = new StandardTypicals();
    }

    /**
     * @param localDate if not null we already have a better estimate
     * @return estimate from parents birth date
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
            if (father != null) {
                final String dateString = getBirthDate(father);
                if (validDateString(dateString)) {
                    date = createLocalDate(dateString);
                    date = parentDateIncrement(date);
                    break;
                }
            }
            final Person mother = getMother(family);
            if (mother != null) {
                final String dateString = getBirthDate(mother);
                if (validDateString(dateString)) {
                    date = createLocalDate(dateString);
                    date = parentDateIncrement(date);
                    break;
                }
            }
        }
        return date;
    }

    /**
     * Adjust the given date by age of parent at first child.
     *
     * @param date the input date
     * @return the adjusted date
     */
    private LocalDate parentDateIncrement(final LocalDate date) {
        final int years = typicals.ageAtMarriage()
                + typicals.gapBetweenChildren();
        return plusYearsAdjustToBegin(date, years);
    }

    /**
     * @param localDate if not null we already have a better estimate
     * @return estimate from the date of parents marriage.
     */
    public LocalDate estimateFromMarriage(final LocalDate localDate) {
        if (localDate != null) {
            return localDate;
        }
        int offsetCount = 0;
        final PersonNavigator navigator = new PersonNavigator(person);
        final List<Family> families = navigator.getFamiliesC();
        LocalDate date = null;
        for (final Family family : families) {
            offsetCount = 1;
            date = processMarriageDate(date, family);
            for (final Person sibling : getChildren(family)) {
                if (person.equals(sibling)) {
                    break;
                }
                offsetCount++;
            }
        }
        if (date != null) {
            date = firstDayOfMonth(plusYears(date,
                    typicals.gapBetweenChildren() * offsetCount));
        }
        return date;
    }
}
