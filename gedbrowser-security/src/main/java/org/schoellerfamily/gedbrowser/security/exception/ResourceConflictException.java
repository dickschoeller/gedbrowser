package org.schoellerfamily.gedbrowser.security.exception;

/**
 * @author Dick Schoeller
 */
public final class ResourceConflictException extends RuntimeException {
    /** */
    private static final long serialVersionUID = 1L;

    /** */
    private final Long resourceId;

    /**
     * Constructor.
     *
     * @param resourceId the resource ID
     * @param message an error message string
     */
    public ResourceConflictException(final Long resourceId,
            final String message) {
        super(message);
        this.resourceId = resourceId;
    }

    /**
     * @return the resource ID
     */
    public Long getResourceId() {
        return resourceId;
    }
}
