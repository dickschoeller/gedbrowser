package org.schoellerfamily.gedbrowser.renderer.test;

import java.io.IOException;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.renderer.LivingRenderer;
import org.schoellerfamily.gedbrowser.renderer.LivingRenderer.Bucket;
import org.schoellerfamily.gedbrowser.renderer.PersonRenderer;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.schoellerfamily.gedbrowser.renderer.User;

/**
 * @author Dick Schoeller
 */
public final class LivingRendererTest {
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
        final User user = new User();
        user.setUsername("user");
        user.addRole("USER");
        userContext = new RenderingContext(user, true, false);
        final User admin = new User();
        admin.setUsername("admin");
        admin.addRole("USER");
        admin.addRole("ADMIN");
        adminContext = new RenderingContext(admin, true, true);
        root = (Root) TestDataReader.getInstance().readBigTestSource();
    }

    /**
     *
     */
    @Test
    public void testRenderUserIndexHref() {
        final LivingRenderer renderer = new LivingRenderer(root,
                userContext);
        Assert.assertEquals("The index link should refer to the letter A",
                "surnames?db=null&letter=A", renderer.getIndexHref());
    }

    /** */
    @Test
    public void testRenderAdminIndexHref() {
        final LivingRenderer renderer = new LivingRenderer(root,
                adminContext);
        Assert.assertEquals("The index link should refer to the letter A",
                "surnames?db=null&letter=A", renderer.getIndexHref());
    }

    /**
     *
     */
    @Test
    public void testRenderUserBuckets() {
        final LivingRenderer renderer = new LivingRenderer(root,
                userContext);
        Assert.assertTrue("In user context, this is always empty",
                renderer.getBuckets().isEmpty());
    }

    /**
     *
     */
    @Test
    public void testRenderAdminBuckets() {
        final LivingRenderer renderer = new LivingRenderer(root,
                adminContext);
        Assert.assertFalse("In admin context, there should be some buckets",
                renderer.getBuckets().isEmpty());
    }

    /** */
    @Test
    public void testRenderAdminBucket() {
        final LivingRenderer renderer = new LivingRenderer(root,
                adminContext);
        // FIXME this is too dependent on the test data's relationship to today'
        final int thirtyToThirtyNine = 3;
        final List<PersonRenderer> persons =
                renderer.getBuckets().get(thirtyToThirtyNine).getPersons();
        Assert.assertFalse(
                "In admin context, there should be someone in the bucket",
                persons.isEmpty());
    }

    /**
     *
     */
    @Test
    public void testRenderBucketSizes() {
        final LivingRenderer renderer = new LivingRenderer(root,
                adminContext);
        final List<Bucket> buckets = renderer.getBuckets();
        final int increment = 10;
        final int limit = 100;
        int expLower = 0;
        int expUpper = increment - 1;
        int i = 0;
        for (final Bucket bucket : buckets) {
            Assert.assertTrue("Bucket " + i++ + " is screwed up",
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
}
