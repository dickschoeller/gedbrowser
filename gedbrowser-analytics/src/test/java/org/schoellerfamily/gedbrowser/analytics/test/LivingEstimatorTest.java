package org.schoellerfamily.gedbrowser.analytics.test;

import static org.junit.Assert.assertNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.schoellerfamily.gedbrowser.analytics.LivingEstimator;
import org.schoellerfamily.gedbrowser.analytics.calendar.CalendarProvider;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.reader.AbstractGedLine;
import org.schoellerfamily.gedbrowser.reader.GedObjectCreator;
import org.schoellerfamily.gedbrowser.reader.testreader.TestResourceReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Dick Schoeller
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestConfiguration.class })
public final class LivingEstimatorTest {
    /** Divide output into buckets of 10 years. */
    private static final int AGE_BUCKET_SIZE = 10;
    /** Anybody estimated at over 100 is assumed dead. */
    private static final int AGE_CUTOFF_FOR_LIVING_CHECK = 100;

    /** */
    @Autowired
    private transient CalendarProvider provider;
    /** */
    @Autowired
    private transient GedObjectCreator g2g;

    /**
     * Smoke test and dump the listing by bucket group.
     *
     * @throws IOException if there is a file IO problem
     */
    @Test
    public void testFactoryGedFile() throws IOException {
        final AbstractGedLine top =
                TestResourceReader.readFileTestSource(this, "gl120368.ged");
        final Root root = g2g.create(top);
        final List<Person> living = new ArrayList<>();
        final Map<Integer, Set<Person>> buckets = new HashMap<>();
        final List<Person> dead = new ArrayList<>();
        LivingEstimator.fillBuckets(root, living, dead, buckets, provider);
        dumpBuckets(living, dead, buckets);
        assertNull("Should always pass", null);
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
