package org.schoellerfamily.gedbrowser.api.service.storage;

/**
 * @author Dick Schoeller
 */
public class StorageException extends RuntimeException {
    /** */
    private static final long serialVersionUID = 1L;

    /**
     * Constructor.
     *
     * @param message the message string associated with the exception
     */
    public StorageException(final String message) {
        super(message);
    }

    /**
     * Constructor.
     *
     * @param message the message string associated with the exception
     * @param cause the originating exception
     */
    public StorageException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
