package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.schoellerfamily.gedbrowser.analytics.calendar.CalendarProvider;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.reader.testreader.TestDataReader;
import org.schoellerfamily.gedbrowser.renderer.LivingRenderer;
import org.schoellerfamily.gedbrowser.renderer.LivingRenderer.Bucket;
import org.schoellerfamily.gedbrowser.renderer.PersonRenderer;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.schoellerfamily.gedbrowser.renderer.application.ApplicationInfo;
import org.schoellerfamily.gedbrowser.renderer.user.UserImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Dick Schoeller
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestConfiguration.class })
public final class LivingRendererTest {
    /** */
    @Autowired
    private transient TestDataReader reader;
    /** */
    @Autowired
    private transient CalendarProvider provider;
    /** */
    @Autowired
    private transient ApplicationInfo appInfo;

    /** */
    private transient RenderingContext adminContext;

    /** */
    private transient RenderingContext userContext;

    /** */
    private transient Root root;

    /**
     * @throws IOException if there is a problem reading the test data
     */
    @Before
    public void init() throws IOException {
        root = reader.readBigTestSource();
        final UserImpl admin = new UserImpl();
        admin.setUsername("admin");
        admin.addRole("USER");
        admin.addRole("ADMIN");
        adminContext = new RenderingContext(admin, appInfo, provider);
        final UserImpl user = new UserImpl();
        user.setUsername("user");
        user.addRole("USER");
        userContext = new RenderingContext(user, appInfo, provider);
    }

    /**
     *
     */
    @Test
    public void testRenderUserIndexHref() {
        final LivingRenderer renderer = new LivingRenderer(root, userContext);
        assertEquals("The index link should refer to the letter A",
                "surnames?db=null&letter=A", renderer.getIndexHref());
    }

    /** */
    @Test
    public void testRenderAdminIndexHref() {
        final LivingRenderer renderer = new LivingRenderer(root, adminContext);
        assertEquals("The index link should refer to the letter A",
                "surnames?db=null&letter=A", renderer.getIndexHref());
    }

    /**
     *
     */
    @Test
    public void testRenderUserBuckets() {
        final LivingRenderer renderer = new LivingRenderer(root, userContext);
        assertTrue("In user context, this is always empty",
                renderer.getBuckets().isEmpty());
    }

    /**
     *
     */
    @Test
    public void testRenderAdminBuckets() {
        final LivingRenderer renderer = new LivingRenderer(root, adminContext);
        assertFalse("In admin context, there should be some buckets",
                renderer.getBuckets().isEmpty());
    }

    /** */
    @Test
    public void testRenderAdminBucket() {
        final LivingRenderer renderer = new LivingRenderer(root, adminContext);
        final int twentyToTwentyNine = 2;
        final List<PersonRenderer> persons = renderer.getBuckets()
                .get(twentyToTwentyNine).getPersons();
        assertFalse("In admin context, there should be someone in the bucket",
                persons.isEmpty());
    }

    /**
     *
     */
    @Test
    public void testRenderBucketSizes() {
        final LivingRenderer renderer = new LivingRenderer(root, adminContext);
        final List<Bucket> buckets = renderer.getBuckets();
        final int increment = 10;
        final int limit = 100;
        int expLower = 0;
        int expUpper = increment - 1;
        int i = 0;
        for (final Bucket bucket : buckets) {
            assertTrue("Bucket " + i++ + " is screwed up",
                    checkBucketRanges(limit, expLower, expUpper, bucket));
            expLower += increment;
            expUpper += increment;
        }
    }

    /**
     * @param limit the upper limit of "living"
     * @param expectedLower expected bucket lower bound
     * @param expectedUpper expected bucket upper bound
     * @param bucket the bucket being checked
     * @return true if the bucket ranges are OK
     */
    private boolean checkBucketRanges(final int limit, final int expectedLower,
            final int expectedUpper, final Bucket bucket) {
        if (expectedLower != bucket.getLower().intValue()) {
            return false;
        }
        if (expectedUpper != bucket.getUpper().intValue()) {
            return false;
        }
        return (expectedLower < limit);
    }


    /**
     * Test whether the menu items are as expected.
     *
     * @throws IOException if can't read data file
     */
    @Test
    public void testHeaderMenuItem() throws IOException {
        final Root root1 = reader.readFileTestSource();
        final LivingRenderer renderer = new LivingRenderer(root1,
                userContext);
        assertEquals("head href mismatch",
                "head?db=gl120368", renderer.getHeaderHref());
    }

    /**
     * Test whether the menu items are as expected.
     *
     * @throws IOException if can't read data file
     */
    @Test
    public void testIndexMenuItem() throws IOException {
        final Root root1 = reader.readFileTestSource();
        final LivingRenderer renderer = new LivingRenderer(root1,
                userContext);
        assertEquals("index href mismatch",
                "surnames?db=gl120368&letter=A", renderer.getIndexHref());
    }

    /**
     * Test whether the menu items are as expected.
     *
     * @throws IOException if can't read data file
     */
    @Test
    public void testLivingMenuItem() throws IOException {
        final Root root1 = reader.readFileTestSource();
        final LivingRenderer renderer = new LivingRenderer(root1,
                userContext);
        assertEquals("living href mismatch",
                "living?db=gl120368", renderer.getLivingHref());
    }

    /**
     * Test whether the menu items are as expected.
     *
     * @throws IOException if can't read data file
     */
    @Test
    public void testSourcesMenuItem() throws IOException {
        final Root root1 = reader.readFileTestSource();
        final LivingRenderer renderer = new LivingRenderer(root1,
                userContext);
        assertEquals("submittors href mismatch",
                "sources?db=gl120368", renderer.getSourcesHref());
    }

    /**
     * Test whether the menu items are as expected.
     *
     * @throws IOException if can't read data file
     */
    @Test
    public void testSubmittorsMenuItem() throws IOException {
        final Root root1 = reader.readFileTestSource();
        final LivingRenderer renderer = new LivingRenderer(root1,
                userContext);
        assertEquals("sources href mismatch",
                "submittors?db=gl120368", renderer.getSubmittorsHref());
    }
}
