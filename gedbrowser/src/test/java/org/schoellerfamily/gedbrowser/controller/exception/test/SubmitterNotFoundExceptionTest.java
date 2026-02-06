package org.schoellerfamily.gedbrowser.controller.exception.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.schoellerfamily.gedbrowser.controller.ApplicationInfoImpl;
import org.schoellerfamily.gedbrowser.controller.exception.SubmitterNotFoundException;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.schoellerfamily.gedbrowser.renderer.application.ApplicationInfo;

/**
 * @author Dick Schoeller
 */
class SubmitterNotFoundExceptionTest {
    /** */
    private SubmitterNotFoundException exception;

    /** */
    @BeforeEach
    void setUp() {
        final ApplicationInfo appInfo = new ApplicationInfoImpl(null, null, null, null);
        exception = new SubmitterNotFoundException("Submitter not found", "ID1", "xyzzy",
            RenderingContext.user(appInfo));
    }

    /** */
    @Test
    void testMessage() {
        assertEquals("Submitter not found", exception.getMessage(), "Message doesn't match");
    }

    /** */
    @Test
    void testDatasetName() {
        assertEquals("xyzzy", exception.getDatasetName(), "Dataset name doesn't match");
    }

    /** */
    @Test
    void testId() {
        assertEquals("ID1", exception.getId(), "Object ID doesn't match");
    }

    /** */
    @Test
    void testSubmitterId() {
        assertEquals("ID1", exception.getSubmitterId(), "Submitter ID doesn't match");
    }

    /** */
    @Test
    void testIndexHref() {
        assertEquals("surnames?db=xyzzy&letter=A", exception.getIndexHref(),
            "Index href doesn't match");
    }

    /** */
    @Test
    void testHeaderHref() {
        assertEquals("head?db=xyzzy", exception.getHeaderHref(), "Header href doesn't match");
    }

    /** */
    @Test
    void testSourcesHref() {
        assertEquals("sources?db=xyzzy", exception.getSourcesHref(), "Sources href doesn't match");
    }

    /** */
    @Test
    void testSubmittersHref() {
        assertEquals("submitters?db=xyzzy", exception.getSubmittersHref(),
            "Submitters href doesn't match");
    }

    /** */
    @Test
    void testIsUser() {
        assertTrue(exception.hasRole("USER"), "Should be user");
    }

    /** */
    @Test
    void testIsNotAdmin() {
        assertFalse(exception.hasRole("ADMIN"), "Should not be admin");
    }

    /** */
    @Test
    void testLivingHref() {
        assertEquals("living?db=xyzzy", exception.getLivingHref(), "Living href doesn't match");
    }

    /** */
    @Test
    void testPlacesHref() {
        assertEquals("places?db=xyzzy", exception.getPlacesHref(), "Places href doesn't match");
    }
}
