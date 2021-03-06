package org.schoellerfamily.gedbrowser.renderer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.schoellerfamily.gedbrowser.analytics.LivingEstimator;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.users.UserRoleName;
import org.schoellerfamily.gedbrowser.renderer.href.HeaderHrefRenderer;
import org.schoellerfamily.gedbrowser.renderer.href.IndexHrefRenderer;
import org.schoellerfamily.gedbrowser.renderer.href.PlacesHrefRenderer;
import org.schoellerfamily.gedbrowser.renderer.href.SaveHrefRenderer;
import org.schoellerfamily.gedbrowser.renderer.href.SourcesHrefRenderer;
import org.schoellerfamily.gedbrowser.renderer.href.SubmittersHrefRenderer;

/**
 * @author Dick Schoeller
 */
public final class LivingRenderer extends GedRenderer<Root>
        implements HeaderHrefRenderer<Root>, IndexHrefRenderer<Root>,
        PlacesHrefRenderer<Root>, SaveHrefRenderer<Root>,
        SubmittersHrefRenderer<Root>, SourcesHrefRenderer<Root> {
    /** Logger. */
    private final Log logger = LogFactory.getLog(getClass());

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
        logger.info("Starting LivingRenderer.getBuckets()");
        final List<Bucket> bucketList = new ArrayList<>();
        if (getRenderingContext().hasRole(UserRoleName.ADMIN)) {
            final Root root = getGedObject();
            final List<Person> living = new ArrayList<>();
            final Map<Integer, Set<Person>> buckets = new HashMap<>();
            final List<Person> dead = new ArrayList<>();
            LivingEstimator.fillBuckets(root, living, dead, buckets,
                    getRenderingContext());
            // FIXME this involves too much knowledge of bucket structure
            for (int lower = 0; lower < ANCIENT; lower += AGE_BUCKET_SIZE) {
                final Bucket bucket = createBucket(buckets, lower);
                bucketList.add(bucket);
            }
        }
        logger.info("Exiting LivingRenderer.getBuckets()");
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
}
