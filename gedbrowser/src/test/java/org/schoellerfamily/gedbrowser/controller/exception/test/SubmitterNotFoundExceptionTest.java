package org.schoellerfamily.gedbrowser.controller.exception.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.schoellerfamily.gedbrowser.controller.ApplicationInfoImpl;
import org.schoellerfamily.gedbrowser.controller.exception.SubmitterNotFoundException;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.schoellerfamily.gedbrowser.renderer.application.ApplicationInfo;

/**
 * @author Dick Schoeller
 */
public class SubmitterNotFoundExceptionTest {
    /** */
    private SubmitterNotFoundException exception;

    /** */
    @Before
    public void setUp() {
        final ApplicationInfo appInfo = new ApplicationInfoImpl();
        exception = new SubmitterNotFoundException("Submitter not found", "ID1",
                "xyzzy", RenderingContext.user(appInfo));
    }

    /** */
    @Test
    public void testMessage() {
        assertEquals("Message doesn't match", "Submitter not found",
                exception.getMessage());
    }

    /** */
    @Test
    public void testDatasetName() {
        assertEquals("Dataset name doesn't match", "xyzzy",
                exception.getDatasetName());
    }

    /** */
    @Test
    public void testId() {
        assertEquals("Object ID doesn't match", "ID1", exception.getId());
    }

    /** */
    @Test
    public void testSubmitterId() {
        assertEquals("Submitter ID doesn't match", "ID1",
                exception.getSubmitterId());
    }

    /** */
    @Test
    public void testIndexHref() {
        assertEquals("Index href doesn't match", "surnames?db=xyzzy&letter=A",
                exception.getIndexHref());
    }

    /** */
    @Test
    public void testHeaderHref() {
        assertEquals("Header href doesn't match", "head?db=xyzzy",
                exception.getHeaderHref());
    }

    /** */
    @Test
    public void testSourcesHref() {
        assertEquals("Sources href doesn't match", "sources?db=xyzzy",
                exception.getSourcesHref());
    }

    /** */
    @Test
    public void testSubmittersHref() {
        assertEquals("Submitters href doesn't match", "submitters?db=xyzzy",
                exception.getSubmittersHref());
    }

    /** */
    @Test
    public void testIsUser() {
        assertTrue("Should be user", exception.hasRole("USER"));
    }

    /** */
    @Test
    public void testIsNotAdmin() {
        assertFalse("Should not be admin", exception.hasRole("ADMIN"));
    }

    /** */
    @Test
    public void testLivingHref() {
        assertEquals("Living href doesn't match", "living?db=xyzzy",
                exception.getLivingHref());
    }

    /** */
    @Test
    public void testPlacesHref() {
        assertEquals("Places href doesn't match", "places?db=xyzzy",
                exception.getPlacesHref());
    }
}
