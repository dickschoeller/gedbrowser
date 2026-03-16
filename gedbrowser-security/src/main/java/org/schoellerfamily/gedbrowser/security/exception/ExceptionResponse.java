package org.schoellerfamily.gedbrowser.security.exception;

import lombok.Getter;



/**
 * Represents a response for exception.
 *
 * @author Richard Schoeller
 */
@Getter
public final class ExceptionResponse {
    /**
     * Creates a new ExceptionResponse.
     *
     * @param errorCode the error code
     * @param errorMessage the descriptive error message
     */
    public ExceptionResponse(final String errorCode,
            final String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    /**
     * The error code.
     */
    private final String errorCode;

    /**
     * A descriptive message.
     */
    private final String errorMessage;
}
