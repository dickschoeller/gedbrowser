package org.schoellerfamily.gedbrowser.renderer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import org.schoellerfamily.gedbrowser.analytics.LivingEstimator;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Root;

/**
 * @author Dick Schoeller
 */
public final class LivingRenderer extends GedRenderer<Root> {
    /** Logger. */
    private static final Logger LOGGER = Logger.getLogger(LivingRenderer.class
            .getName());

    /** The size of a bucket. */
    private static final int AGE_BUCKET_SIZE = 10;
    /** If a person is older than this, guess that they are dead. */
    private static final int ANCIENT = 100;

    /**
     * Constructor.
     *
     * @param root root of data set
     * @param renderingContext the context that we are rendering in
     */
    public LivingRenderer(final Root root,
            final RenderingContext renderingContext) {
        super(root, new GedRendererFactory(), renderingContext);
    }

    /**
     * @return the buckets by 10 year bands
     */
    public List<Bucket> getBuckets() {
        LOGGER.entering("LivingRenderer", "getBuckets");
        final List<Bucket> bucketList = new ArrayList<>();
        if (getRenderingContext().hasRole("ADMIN")) {
            final Root root = getGedObject();
            final List<Person> living = new ArrayList<>();
            final Map<Integer, Set<Person>> buckets = new HashMap<>();
            final List<Person> dead = new ArrayList<>();
            LivingEstimator.fillBuckets(root, living, dead, buckets);
            // FIXME this involves too much knowledge of bucket structure
            for (int lower = 0; lower < ANCIENT; lower += AGE_BUCKET_SIZE) {
                final Bucket bucket = createBucket(buckets, lower);
                bucketList.add(bucket);
            }
        }
        LOGGER.exiting("LivingRenderer", "getBuckets");
        return bucketList;
    }

    /**
     * @param buckets the input bucket set
     * @param lower the range we want
     * @return the bucket for this range
     */
    private Bucket createBucket(final Map<Integer, Set<Person>> buckets,
            final int lower) {
        final int upper = lower + AGE_BUCKET_SIZE - 1;
        final List<PersonRenderer> persons = createPersonRenderers(buckets,
                lower);
        return new Bucket(lower, upper, persons);
    }

    /**
     * @param buckets the input bucket set
     * @param lower particular range we want
     * @return the list of renderers
     */
    private List<PersonRenderer> createPersonRenderers(
            final Map<Integer, Set<Person>> buckets, final int lower) {
        final List<PersonRenderer> persons = new ArrayList<>();
        final Set<Person> bucket = buckets.get(lower);
        if (bucket != null) {
            for (final Person person : bucket) {
                persons.add((PersonRenderer) createGedRenderer(person));
            }
        }
        return persons;
    }

    /**
     * @author Dick Schoeller
     */
    public static final class Bucket {
        /** Lower bound for this bucket. */
        private final Integer lower;
        /** Upper bound for this bucket. */
        private final Integer upper;
        /** List of persons in this bucket. */
        private final List<PersonRenderer> persons = new ArrayList<>();

        /**
         * Constructor.
         *
         * @param lower lower bound for this bucket
         * @param upper upper bound for this bucket
         * @param persons collection of persons in this bucket
         */
        public Bucket(final Integer lower, final Integer upper,
                final Collection<PersonRenderer> persons) {
            this.lower = lower;
            this.upper = upper;
            this.persons.addAll(persons);
        }

        /**
         * @return the lower bound for this bucket
         */
        public Integer getLower() {
            return lower;
        }

        /**
         * @return the upper bound for this bucket
         */
        public Integer getUpper() {
            return upper;
        }

        /**
         * @return the list of persons in this bucket
         */
        public List<PersonRenderer> getPersons() {
            return persons;
        }
    }

    /**
     * @return the href string to the index page containing this person.
     */
    public String getIndexHref() {
        return "surnames?db=" + getGedObject().getDbName() + "&letter=" + "A";
    }
}
