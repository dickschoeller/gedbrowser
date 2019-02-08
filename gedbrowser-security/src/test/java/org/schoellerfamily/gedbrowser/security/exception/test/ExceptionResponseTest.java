package org.schoellerfamily.gedbrowser.security.exception.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.schoellerfamily.gedbrowser.security.exception.ExceptionResponse;

/**
 * @author Dick Schoeller
 */
public class ExceptionResponseTest {
    /** */
    @Test
    public void testMessage() {
        final ExceptionResponse er = new ExceptionResponse("code", "message");
        assertEquals("Messages don't match", "message", er.getErrorMessage());
    }

    /** */
    @Test
    public void testId() {
        final ExceptionResponse er = new ExceptionResponse("code", "message");
        assertEquals("Codes don't match", "code", er.getErrorCode());
    }

}
