package org.schoellerfamily.gedbrowser.security.exception;

/**
 * Represents an error related to resource conflict.
 *
 * @author Richard Schoeller
 */
public final class ResourceConflictException extends RuntimeException {
    /** */
    private static final long serialVersionUID = 1L;

    /** */
    private final Long resourceId;

    /**
     * Creates a new ResourceConflictException.
     *
     * @param resourceId the unique identifier for resource
     * @param message the message
     */
    public ResourceConflictException(final Long resourceId,
            final String message) {
        super(message);
        this.resourceId = resourceId;
    }

    /**
     * Gets the resource id.
     *
     * @return the resource id
     */
    public Long getResourceId() {
        return resourceId;
    }
}
