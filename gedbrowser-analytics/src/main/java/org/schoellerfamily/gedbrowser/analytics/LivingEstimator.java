package org.schoellerfamily.gedbrowser.analytics;

import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.schoellerfamily.gedbrowser.datamodel.Person;

/**
 * @author Dick Schoeller
 */
public final class LivingEstimator {
    /** Limit of age we will guess is living. */
    private static final int VERY_OLD_AGE = 100;

    /** Hold the person we are estimating. */
    private final Person person;

    /**
     * Constructor.
     *
     * @param person the person we are estimating
     */
    public LivingEstimator(final Person person) {
        this.person = person;
    }

    /**
     * Tries to guess whether someone is living. If there is a death
     * record, then no. If age (exact or estimated) is over 100, then
     * no. Otherwise, yes.
     *
     * @return estimated living or dead
     */
    public boolean estimate() {
        if (person.hasDeathAttribute()) {
            // Death attribute found, we're out of here.
            return false;
        } else {
            final BirthDateEstimator bde = new BirthDateEstimator(person);
            final LocalDate date = bde.estimateBirthDate();
            if (date == null) {
                // No death date and no birth date estimate, assume living.
                return true;
            }
            final LocalDate today = new LocalDate();
            final Period p = new Period(date, today);
            return p.getYears() < VERY_OLD_AGE;
        }
    }
}
