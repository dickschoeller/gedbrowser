package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.schoellerfamily.gedbrowser.analytics.calendar.CalendarProvider;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.users.UserImpl;
import org.schoellerfamily.gedbrowser.reader.testreader.TestDataReader;
import org.schoellerfamily.gedbrowser.renderer.LivingRenderer;
import org.schoellerfamily.gedbrowser.renderer.LivingRenderer.Bucket;
import org.schoellerfamily.gedbrowser.renderer.PersonRenderer;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.schoellerfamily.gedbrowser.renderer.application.ApplicationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author Dick Schoeller
 */
@ExtendWith(SpringExtension.class)
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
    @BeforeEach
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
        assertEquals("surnames?db=null&letter=A", renderer.getIndexHref(),
            "The index link should refer to the letter A");
    }

    /** */
    @Test
    public void testRenderAdminIndexHref() {
        final LivingRenderer renderer = new LivingRenderer(root, adminContext);
        assertEquals("surnames?db=null&letter=A", renderer.getIndexHref(),
            "The index link should refer to the letter A");
    }

    /**
     *
     */
    @Test
    public void testRenderUserBuckets() {
        final LivingRenderer renderer = new LivingRenderer(root, userContext);
        assertTrue(renderer.getBuckets().isEmpty(), "In user context, this is always empty");
    }

    /**
     *
     */
    @Test
    public void testRenderAdminBuckets() {
        final LivingRenderer renderer = new LivingRenderer(root, adminContext);
        assertFalse(renderer.getBuckets().isEmpty(),
            "In admin context, there should be some buckets");
    }

    /** */
    @Test
    public void testRenderAdminBucket() {
        final LivingRenderer renderer = new LivingRenderer(root, adminContext);
        final int twentyToTwentyNine = 2;
        final List<PersonRenderer> persons = renderer.getBuckets()
            .get(twentyToTwentyNine)
            .getPersons();
        assertFalse(persons.isEmpty(), "In admin context, there should be someone in the bucket");
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
            assertTrue(checkBucketRanges(limit, expLower, expUpper, bucket),
                "Bucket " + i++ + " is screwed up");
            expLower += increment;
            expUpper += increment;
        }
    }

    /**
     * @param limit         the upper limit of "living"
     * @param expectedLower expected bucket lower bound
     * @param expectedUpper expected bucket upper bound
     * @param bucket        the bucket being checked
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
    public void testHeadMenuItem() throws IOException {
        final Root root1 = reader.readFileTestSource();
        final LivingRenderer renderer = new LivingRenderer(root1, userContext);
        assertEquals("head?db=gl120368", renderer.getHeaderHref(), "head href mismatch");
    }

    /**
     * Test whether the menu items are as expected.
     *
     * @throws IOException if can't read data file
     */
    @Test
    public void testSaveMenuItem() throws IOException {
        final Root root1 = reader.readFileTestSource();
        final LivingRenderer renderer = new LivingRenderer(root1, userContext);
        assertEquals("save?db=gl120368", renderer.getSaveHref(), "save href mismatch");
    }

    /**
     * Test whether the menu items are as expected.
     *
     * @throws IOException if can't read data file
     */
    @Test
    public void testSaveFilename() throws IOException {
        final Root root1 = reader.readFileTestSource();
        final LivingRenderer renderer = new LivingRenderer(root1, userContext);
        assertEquals("gl120368.ged", renderer.getSaveFilename(), "save href mismatch");
    }

    /**
     * Test whether the menu items are as expected.
     *
     * @throws IOException if can't read data file
     */
    @Test
    public void testIndexMenuItem() throws IOException {
        final Root root1 = reader.readFileTestSource();
        final LivingRenderer renderer = new LivingRenderer(root1, userContext);
        assertEquals("surnames?db=gl120368&letter=A", renderer.getIndexHref(),
            "index href mismatch");
    }

    /**
     * Test whether the menu items are as expected.
     *
     * @throws IOException if can't read data file
     */
    @Test
    public void testLivingMenuItem() throws IOException {
        final Root root1 = reader.readFileTestSource();
        final LivingRenderer renderer = new LivingRenderer(root1, userContext);
        assertEquals("living?db=gl120368", renderer.getLivingHref(), "living href mismatch");
    }

    /**
     * Test whether the menu items are as expected.
     *
     * @throws IOException if can't read data file
     */
    @Test
    public void testSourcesMenuItem() throws IOException {
        final Root root1 = reader.readFileTestSource();
        final LivingRenderer renderer = new LivingRenderer(root1, userContext);
        assertEquals("sources?db=gl120368", renderer.getSourcesHref(), "submitters href mismatch");
    }

    /**
     * Test whether the menu items are as expected.
     *
     * @throws IOException if can't read data file
     */
    @Test
    public void testSubmittersMenuItem() throws IOException {
        final Root root1 = reader.readFileTestSource();
        final LivingRenderer renderer = new LivingRenderer(root1, userContext);
        assertEquals("submitters?db=gl120368", renderer.getSubmittersHref(),
            "sources href mismatch");
    }

    /**
     * Test whether the menu items are as expected.
     *
     * @throws IOException if can't read data file
     */
    @Test
    public void testPlacesMenuItem() throws IOException {
        final Root root1 = reader.readFileTestSource();
        final LivingRenderer renderer = new LivingRenderer(root1, userContext);
        assertEquals("places?db=gl120368", renderer.getPlacesHref(), "places href mismatch");
    }
}
