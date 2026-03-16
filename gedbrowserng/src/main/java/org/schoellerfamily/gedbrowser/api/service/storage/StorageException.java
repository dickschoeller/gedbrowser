package org.schoellerfamily.gedbrowser.api.service.storage;

/**
 * Represents an error related to storage.
 *
 * @author Richard Schoeller
 */
public class StorageException extends RuntimeException {
    /** */
    private static final long serialVersionUID = 1L;

    /**
     * Creates a new StorageException.
     *
     * @param message the message
     */
    public StorageException(final String message) {
        super(message);
    }

    /**
     * Creates a new StorageException.
     *
     * @param message the message
     * @param cause the cause
     */
    public StorageException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
