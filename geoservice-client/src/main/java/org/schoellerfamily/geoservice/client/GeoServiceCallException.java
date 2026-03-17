package org.schoellerfamily.geoservice.client;

/**
 * Represents an error related to geo service call.
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
