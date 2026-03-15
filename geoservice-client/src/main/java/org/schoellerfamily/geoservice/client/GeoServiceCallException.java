package org.schoellerfamily.geoservice.client;

/**
 * Exception raised when a geoservice call cannot be completed by the executor.
 */
public class GeoServiceCallException extends Exception {
    /**
     * Creates an exception with message and cause.
     *
     * @param message failure description
     * @param cause root cause
     */
    public GeoServiceCallException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
