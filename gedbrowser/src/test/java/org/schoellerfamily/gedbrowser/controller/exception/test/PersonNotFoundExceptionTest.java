package org.schoellerfamily.gedbrowser.controller.exception.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.schoellerfamily.gedbrowser.controller.ApplicationInfoImpl;
import org.schoellerfamily.gedbrowser.controller.exception.PersonNotFoundException;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.schoellerfamily.gedbrowser.renderer.application.ApplicationInfo;

/**
 * @author Dick Schoeller
 */
public class PersonNotFoundExceptionTest {
    /** */
    private PersonNotFoundException exception;

    /** */
    @BeforeEach
    public void setUp() {
        final ApplicationInfo appInfo = new ApplicationInfoImpl(null, null, null, null);
        exception = new PersonNotFoundException("Person not found", "ID1",
                "xyzzy", RenderingContext.user(appInfo));
    }

    /** */
    @Test
    public void testMessage() {
        assertEquals("Person not found", exception.getMessage(), "Message doesn't match");
    }

    /** */
    @Test
    public void testDatasetName() {
        assertEquals("xyzzy", exception.getDatasetName(), "Dataset name doesn't match");
    }

    /** */
    @Test
    public void testId() {
        assertEquals("ID1", exception.getId(), "Object ID doesn't match");
    }

    /** */
    @Test
    public void testPersonId() {
        assertEquals("ID1", exception.getPersonId(), "Person ID doesn't match");
    }

    /** */
    @Test
    public void testIndexHref() {
        assertEquals("surnames?db=xyzzy&letter=A", exception.getIndexHref(), "Index href doesn't match");
    }

    /** */
    @Test
    public void testHeaderHref() {
        assertEquals("head?db=xyzzy", exception.getHeaderHref(), "Header href doesn't match");
    }

    /** */
    @Test
    public void testSourcesHref() {
        assertEquals("sources?db=xyzzy", exception.getSourcesHref(), "Sources href doesn't match");
    }

    /** */
    @Test
    public void testSubmittersHref() {
        assertEquals("submitters?db=xyzzy", exception.getSubmittersHref(), "Submitters href doesn't match");
    }

    /** */
    @Test
    public void testIsUser() {
        assertTrue(exception.hasRole("USER"), "Should be user");
    }

    /** */
    @Test
    public void testIsNotAdmin() {
        assertFalse(exception.hasRole("ADMIN"), "Should not be admin");
    }

    /** */
    @Test
    public void testLivingHref() {
        assertEquals("living?db=xyzzy", exception.getLivingHref(), "Living href doesn't match");
    }

    /** */
    @Test
    public void testPlacesHref() {
        assertEquals("places?db=xyzzy", exception.getPlacesHref(), "Places href doesn't match");
    }
}