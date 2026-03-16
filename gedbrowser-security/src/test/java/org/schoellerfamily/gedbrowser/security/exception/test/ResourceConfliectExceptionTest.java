package org.schoellerfamily.gedbrowser.security.exception.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.schoellerfamily.gedbrowser.security.exception.ResourceConflictException;

/**
 * @author Dick Schoeller
 */
class ResourceConfliectExceptionTest {
    @Test
    void testMessage() {
        final Exception e = new ResourceConflictException(1L, "message");
        assertEquals("message", e.getMessage(), "Messages don't match");
    }

    @Test
    void testId() {
        final ResourceConflictException e =
                new ResourceConflictException(1L, "message");
        assertEquals(Long.valueOf(1L), e.getResourceId(), "Ids don't match");
    }
}
