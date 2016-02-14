package org.schoellerfamily.gedbrowser.persistence;

/**
 * @author Dick Schoeller
 */
public class PersistenceException extends RuntimeException {
    /** */
    private static final long serialVersionUID = -2001575196443146690L;

    /**
     * Constructor.
     */
    public PersistenceException() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructor.
     *
     * @param message the message string
     * @param cause the original throwable
     * @param enableSuppression whether to enable suppression
     * @param writableStackTrace whether you can write the trace
     */
    public PersistenceException(final String message,
            final Throwable cause, final boolean enableSuppression,
            final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    /**
     * Constructor.
     *
     * @param message the message string
     * @param cause the original throwable
     */
    public PersistenceException(final String message,
            final Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor.
     *
     * @param message the message string
     */
    public PersistenceException(final String message) {
        super(message);
    }

    /**
     * Constructor.
     *
     * @param cause the original throwable
     */
    public PersistenceException(final Throwable cause) {
        super(cause);
    }

}
