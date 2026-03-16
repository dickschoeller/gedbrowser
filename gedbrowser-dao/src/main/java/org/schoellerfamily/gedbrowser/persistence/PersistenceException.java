package org.schoellerfamily.gedbrowser.persistence;

/**
 * Represents an error related to persistence.
 *
 * @author Richard Schoeller
 */
public class PersistenceException extends RuntimeException {
    /** */
    private static final long serialVersionUID = -2001575196443146690L;

    /**
     * Creates a new PersistenceException.
     */
    public PersistenceException() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * Creates a new PersistenceException.
     *
     * @param message the message
     * @param cause the cause
     * @param enableSuppression the enable suppression
     * @param writableStackTrace the writable stack trace
     */
    public PersistenceException(final String message,
            final Throwable cause, final boolean enableSuppression,
            final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    /**
     * Creates a new PersistenceException.
     *
     * @param message the message
     * @param cause the cause
     */
    public PersistenceException(final String message,
            final Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates a new PersistenceException.
     *
     * @param message the message
     */
    public PersistenceException(final String message) {
        super(message);
    }

    /**
     * Creates a new PersistenceException.
     *
     * @param cause the cause
     */
    public PersistenceException(final Throwable cause) {
        super(cause);
    }

}
