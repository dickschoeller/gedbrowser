package org.schoellerfamily.gedbrowser.analytics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.junit.Test;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.reader.AbstractGedLine;
import org.schoellerfamily.gedbrowser.reader.ReaderHelper;

/**
 * @author Dick Schoeller
 */
public class LivingEstimatorTest {
    /** Divide output into buckets of 10 years. */
    private static final int AGE_BUCKET_SIZE = 10;
    /** Anybody estimated at over 100 is assumed dead. */
    private static final int AGE_CUTOFF_FOR_LIVING_CHECK = 100;

    /**
     * Smoke test and dump the listing by bucket group.
     *
     * @throws IOException if there is a file IO problem
     */
    @Test
    public final void testFactoryGedFile() throws IOException {
        final AbstractGedLine top = ReaderHelper.readFileTestSource(this,
                "/var/lib/gedbrowser/schoeller.ged");
        final GedObject root = top.createGedObject((AbstractGedLine) null);
        final List<Person> living = new ArrayList<>();
        final Map<Integer, Set<Person>> buckets = new HashMap<>();
        final List<Person> dead = new ArrayList<>();
        fillBuckets(root, living, dead, buckets);
        dumpBuckets(living, dead, buckets);
    }

    /**
     * @param root data set to process
     * @param living the list of people estimated to be living
     * @param dead the list of people estimated to be dead
     * @param buckets the living people divided into buckets by decade
     */
    private void fillBuckets(final GedObject root, final List<Person> living,
            final List<Person> dead, final Map<Integer, Set<Person>> buckets) {
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
    }

    /**
     * @param buckets the collection of people into age ranges
     * @param person the person to add
     */
    private void addToBucket(final Map<Integer, Set<Person>> buckets,
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
    private static class BucketComparator implements Comparator<Person> {
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

    /**
     * @param living the list of people estimated to be living
     * @param dead the list of people estimated to be dead
     * @param buckets the living people divided into buckets by decade
     */
    private void dumpBuckets(final List<Person> living,
            final List<Person> dead, final Map<Integer, Set<Person>> buckets) {
        System.out.println("schoeller contains " + living.size()
                + " living people and " + dead.size() + " dead people");
        for (int i = 0; i < AGE_CUTOFF_FOR_LIVING_CHECK; i += AGE_BUCKET_SIZE) {
            final Set<Person> bucket = buckets.get(i);
            System.out.println("    " + bucket.size() + " people in the range "
                    + i + "-" + (i + AGE_BUCKET_SIZE - 1));
            for (final Person person : bucket) {
                System.out.println(dumpableName(person));
            }
        }
    }
}
