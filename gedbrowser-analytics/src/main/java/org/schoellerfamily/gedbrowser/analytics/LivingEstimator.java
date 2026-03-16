package org.schoellerfamily.gedbrowser.analytics;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.schoellerfamily.gedbrowser.analytics.calendar.CalendarProvider;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.visitor.PersonVisitor;

import lombok.extern.slf4j.Slf4j;

/**
 * Class carries out estimation of whether a person is living or dead.
 *
 * @author Dick Schoeller
 */
@Slf4j
public final class LivingEstimator {
    /** Divide output into buckets of 10 years. */
    private static final int AGE_BUCKET_SIZE = 10;
    /** Limit of age we will guess is living. */
    private static final int VERY_OLD_AGE = 100;

    /** Hold the person we are estimating. */
    private final Person person;
    /** Provides the "today" for use in comparisons. */
    private final CalendarProvider provider;

    /**
     * Creates a new LivingEstimator.
     *
     * @param person the person
     * @param provider the provider
     */
    public LivingEstimator(final Person person,
            final CalendarProvider provider) {
        this.person = person;
        this.provider = provider;
    }

    /**
     * Tries to guess whether someone is living. If there is a death
     * record, then no. If age (exact or estimated) is over 100, then
     * no. Otherwise, yes.
     *
     * @return estimated living or dead
     */
    public boolean estimate() {
        if (hasDeathAttribute()) {
            // Death attribute found, we're out of here.
            return false;
        }
        final BirthDateEstimator bde = new BirthDateEstimator(person);
        final LocalDate date = bde.estimateBirthDate();
        if (date == null) {
            // No death date and no birth date estimate, assume living.
            return true;
        }
        final LocalDate today = provider.nowDate();
        final Period p = new Period(date, today);
        return p.getYears() < VERY_OLD_AGE;
    }

    /**
     * Just does the hasDeathAttribute check. This will allow us to compare dead
     * with estimated dead.
     *
     * @return true if the person has the death attribute
     */
    public boolean hasDeathAttribute() {
        final PersonVisitor visitor = new PersonVisitor();
        person.accept(visitor);
        return visitor.hasDeathAttribute();
    }

    /**
     * Executes fill buckets.
     *
     * @param root the root
     * @param living the living
     * @param dead the dead
     * @param buckets the buckets
     * @param provider the provider
     */
    public static void fillBuckets(final GedObject root,
            final List<Person> living, final List<Person> dead,
            final Map<Integer, Set<Person>> buckets,
            final CalendarProvider provider) {
        log.info("Entering LivingEstimator.fillBuckets");
        for (final String letter : root.findSurnameInitialLetters()) {
            for (final String surname : root.findBySurnamesBeginWith(letter)) {
                for (final Person person : root.findBySurname(surname)) {
                    final LivingEstimator le =
                            createLivingEstimator(person, provider);
                    if (estimate(le)) {
                        living.add(person);
                        addToBucket(buckets, person, provider);
                    } else {
                        dead.add(person);
                    }
                }
            }
        }
        log.info("Exiting LivingEstimator.fillBuckets");
    }

    private static boolean estimate(final LivingEstimator le) {
        return le.estimate();
    }

    private static LivingEstimator createLivingEstimator(final Person person,
            final CalendarProvider provider) {
        return new LivingEstimator(person, provider);
    }

    private static void addToBucket(final Map<Integer, Set<Person>> buckets,
            final Person person,
            final CalendarProvider provider) {
        final BirthDateEstimator bde = new BirthDateEstimator(person);
        final LocalDate date = bde.estimateBirthDate();
        final LocalDate today = provider.nowDate();
        final Period p = new Period(date, today);
        final int age = p.getYears();
        final int bucket = (age / AGE_BUCKET_SIZE) * AGE_BUCKET_SIZE;
        final Set<Person> persons =
            buckets.computeIfAbsent(bucket, k -> new TreeSet<Person>(new BucketComparator()));
        persons.add(person);
    }

    /**
     * @author Dick Schoeller
     */
    private static final class BucketComparator implements Comparator<Person>,
            Serializable {
        /** */
        private static final long serialVersionUID = 1L;

        /**
         * Executes compare.
         *
         * @param arg0 the arg0
         * @param arg1 the arg1
         * @return the resulting int
         */
        @Override
        public int compare(final Person arg0, final Person arg1) {
            final String dumpableName0 = dumpableName(arg0);
            final String dumpableName1 = dumpableName(arg1);
            return compare(dumpableName0, dumpableName1);
        }

        private int compare(final String name0, final String name1) {
            return name0.compareTo(name1);
        }
    }

    /* default */ static String dumpableName(final Person person) {
        return person.getIndexName() + " [" + person.toString() + "]";
    }
}
