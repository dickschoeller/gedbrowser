package org.schoellerfamily.gedbrowser.security.exception.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.schoellerfamily.gedbrowser.security.exception.ResourceConflictException;

/**
 * @author Dick Schoeller
 */
public class ResourceConfliectExceptionTest {
    /** */
    @Test
    public void testMessage() {
        final Exception e = new ResourceConflictException(1L, "message");
        assertEquals("Messages don't match", "message", e.getMessage());
    }

    /** */
    @Test
    public void testId() {
        final ResourceConflictException e =
                new ResourceConflictException(1L, "message");
        assertEquals("Ids don't match", Long.valueOf(1L), e.getResourceId());
    }
}
