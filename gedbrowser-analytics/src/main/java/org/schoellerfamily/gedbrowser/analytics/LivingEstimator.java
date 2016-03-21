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

    /**
     * @param root data set to process
     * @param living the list of people estimated to be living
     * @param dead the list of people estimated to be dead
     * @param buckets the living people divided into buckets by decade
     */
    public static void fillBuckets(final GedObject root,
            final List<Person> living, final List<Person> dead,
            final Map<Integer, Set<Person>> buckets) {
        LOGGER.entering("LivingEstimator", "fillBuckets");
        for (final String letter : root.findSurnameInitialLetters()) {
            for (final String surname : root.findBySurnamesBeginWith(letter)) {
                for (final Person person : root.findBySurname(surname)) {
                    final LivingEstimator le = new LivingEstimator(person);
                    if (le.estimate()) {
                        living.add(person);
                        addToBucket(buckets, person);
                    } else {
                        dead.add(person);
                    }
                }
            }
        }
        LOGGER.exiting("LivingEstimator", "fillBuckets");
    }

    /**
     * @param buckets the collection of people into age ranges
     * @param person the person to add
     */
    private static void addToBucket(final Map<Integer, Set<Person>> buckets,
            final Person person) {
        final BirthDateEstimator bde = new BirthDateEstimator(person);
        final LocalDate date = bde.estimateBirthDate();
        final LocalDate today = new LocalDate();
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
         * Implements comparison by sorting by index name (and ID if names are
         * the same.
         *
         * @param arg0 the first person
         * @param arg1 the second person
         */
        @Override
        public int compare(final Person arg0, final Person arg1) {
            return dumpableName(arg0).compareTo(dumpableName(arg1));
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
