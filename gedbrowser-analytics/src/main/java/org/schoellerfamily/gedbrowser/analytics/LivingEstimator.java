package org.schoellerfamily.gedbrowser.analytics;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Logger;

import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.visitor.PersonVisitor;

/**
 * @author Dick Schoeller
 */
public final class LivingEstimator {
    /** Logger. */
    private static final Logger LOGGER = Logger.getLogger(LivingEstimator.class
            .getName());

    /** Divide output into buckets of 10 years. */
    private static final int AGE_BUCKET_SIZE = 10;
    /** Limit of age we will guess is living. */
    private static final int VERY_OLD_AGE = 100;

    /** Hold the person we are estimating. */
    private final Person person;
    /** Provides the "today" for use in comparisons. */
    private final CalendarProvider provider;

    /**
     * Constructor.
     *
     * @param person the person we are estimating
     * @param provider the provider of "today" for comparisons
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
        final PersonVisitor visitor = new PersonVisitor();
        person.accept(visitor);
        if (visitor.hasDeathAttribute()) {
            // Death attribute found, we're out of here.
            return false;
        } else {
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
    }

    /**
     * @param root data set to process
     * @param living the list of people estimated to be living
     * @param dead the list of people estimated to be dead
     * @param buckets the living people divided into buckets by decade
     * @param provider the provider of "today" for comparisons
     */
    public static void fillBuckets(final GedObject root,
            final List<Person> living, final List<Person> dead,
            final Map<Integer, Set<Person>> buckets,
            final CalendarProvider provider) {
        LOGGER.entering("LivingEstimator", "fillBuckets");
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
        LOGGER.exiting("LivingEstimator", "fillBuckets");
    }

    /**
     * Get an estimate from another estimator.
     *
     * @param le an estimator to get the estimate from
     * @return the estimate
     */
    private static boolean estimate(final LivingEstimator le) {
        return le.estimate();
    }

    /**
     * Create a new estimator.
     *
     * @param person the person we're estimating
     * @param provider the calendar provider we are using to determine now
     * @return the estimator
     */
    private static LivingEstimator createLivingEstimator(final Person person,
            final CalendarProvider provider) {
        return new LivingEstimator(person, provider);
    }

    /**
     * @param buckets the collection of people into age ranges
     * @param person the person to add
     * @param provider the calendar provider we are using to determine now
     */
    private static void addToBucket(final Map<Integer, Set<Person>> buckets,
            final Person person,
            final CalendarProvider provider) {
        final BirthDateEstimator bde = new BirthDateEstimator(person);
        final LocalDate date = bde.estimateBirthDate();
        final LocalDate today = provider.nowDate();
        final Period p = new Period(date, today);
        final int age = p.getYears();
        final int bucket = (age / AGE_BUCKET_SIZE) * AGE_BUCKET_SIZE;
        Set<Person> persons = buckets.get(bucket);
        if (persons == null) {
            persons = new TreeSet<Person>(new BucketComparator());
            buckets.put(bucket, persons);
        }
        persons.add(person);
    }

    /**
     * @author Dick Schoeller
     */
    private static class BucketComparator implements Comparator<Person>,
            Serializable {
        /** */
        private static final long serialVersionUID = 1L;

        /**
         * <p>
         * Implements comparison by sorting by index name (and ID if names are
         * the same).
         * </p>
         * {@inheritDoc}
         */
        @Override
        public int compare(final Person arg0, final Person arg1) {
            final String dumpableName0 = dumpableName(arg0);
            final String dumpableName1 = dumpableName(arg1);
            return compare(dumpableName0, dumpableName1);
        }

        /**
         * Compare 2 strings. Only added this method to avoid Demeter.
         *
         * @param name0 the first name
         * @param name1 the second name
         * @return a negative integer, zero, or a positive integer as the first
         *         argument is less than, equal to, or greater than the second.
         */
        private int compare(final String name0, final String name1) {
            return name0.compareTo(name1);
        }
    }

    /**
     * Emit a string that is good for sorting and for dumping.
     *
     * @param person the person who name to dump
     * @return the string to dump
     */
    private static String dumpableName(final Person person) {
        return person.getIndexName() + " [" + person.toString() + "]";
    }
}
