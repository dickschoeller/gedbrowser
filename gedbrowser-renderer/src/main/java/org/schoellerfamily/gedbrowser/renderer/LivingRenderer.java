package org.schoellerfamily.gedbrowser.renderer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Dick Schoeller
 */
@Slf4j
public final class LivingRenderer extends GedRenderer<Root>
        implements HeaderHrefRenderer<Root>, IndexHrefRenderer<Root>,
        PlacesHrefRenderer<Root>, SaveHrefRenderer<Root>,
        SubmittersHrefRenderer<Root>, SourcesHrefRenderer<Root> {

    /** The size of a bucket. */
    private static final int AGE_BUCKET_SIZE = 10;
    /** If a person is older than this, guess that they are dead. */
    private static final int ANCIENT = 100;

    /**
     * Creates a new LivingRenderer.
     *
     * @param root the root
     * @param renderingContext the rendering context
     */
    public LivingRenderer(final Root root,
            final RenderingContext renderingContext) {
        super(root, new GedRendererFactory(), renderingContext);
    }

    /**
     * Gets the buckets.
     *
     * @return the buckets
     */
    public List<Bucket> getBuckets() {
        log.info("Starting LivingRenderer.getBuckets()");
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
        log.info("Exiting LivingRenderer.getBuckets()");
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
        final List<PersonRenderer> persons = createPersonRenderers(buckets, lower);
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
    @Getter
    public static final class Bucket {
        /** Lower bound for this bucket. */
        private final Integer lower;
        /** Upper bound for this bucket. */
        private final Integer upper;
        /** List of persons in this bucket. */
        private final List<PersonRenderer> persons;

        /**
         * Executes bucket.
         *
         * @param lower the lower
         * @param upper the upper
         * @param persons the persons
         */
        public Bucket(final Integer lower, final Integer upper,
                final Collection<PersonRenderer> persons) {
            this.lower = lower;
            this.upper = upper;
            this.persons = persons.stream().toList();
        }
    }
}
