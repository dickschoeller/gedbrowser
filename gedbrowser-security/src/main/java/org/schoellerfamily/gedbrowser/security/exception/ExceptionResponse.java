package org.schoellerfamily.gedbrowser.security.exception;

/**
 * @author Dick Schoeller
 */
public final class ExceptionResponse {
    /**
     * The error code.
     */
    private final String errorCode;

    /**
     * A descriptive message.
     */
    private final String errorMessage;

    /**
     * Constructor.
     *
     * @param errorCode the error code
     * @param errorMessage the error message
     */
    public ExceptionResponse(final String errorCode,
            final String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    /**
     * @return the error code
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * @return the error messaged
     */
    public String getErrorMessage() {
        return errorMessage;
    }
}
