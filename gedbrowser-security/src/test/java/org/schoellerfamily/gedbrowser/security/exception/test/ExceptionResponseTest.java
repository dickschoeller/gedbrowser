package org.schoellerfamily.gedbrowser.security.exception.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.schoellerfamily.gedbrowser.security.exception.ExceptionResponse;

/**
 * @author Dick Schoeller
 */
class ExceptionResponseTest {
    /** */
    @Test
    void testMessage() {
        final ExceptionResponse er = new ExceptionResponse("code", "message");
        assertEquals("message", er.getErrorMessage(), "Messages don't match");
    }

    /** */
    @Test
    void testId() {
        final ExceptionResponse er = new ExceptionResponse("code", "message");
        assertEquals("code", er.getErrorCode(), "Codes don't match");
    }
}
