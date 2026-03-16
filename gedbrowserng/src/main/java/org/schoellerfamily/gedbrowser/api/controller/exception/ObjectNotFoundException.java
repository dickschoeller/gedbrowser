package org.schoellerfamily.gedbrowser.api.controller.exception;

/**
 * Exception thrown when an object is not found.
 *
 * @author Dick Schoeller
 */
public class ObjectNotFoundException extends RuntimeException {
    /** */
    private static final long serialVersionUID = 1L;

    /** */
    private final String objectType;

    /** */
    private final String id;

    /** */
    private final String datasetName;

    /**
     * Creates a new ObjectNotFoundException.
     *
     * @param message the message
     * @param objectType the object type to use
     * @param id the unique identifier for the target
     * @param datasetName the dataset name to use
     */
    public ObjectNotFoundException(final String message,
            final String objectType, final String id,
            final String datasetName) {
        super(message);
        this.objectType = objectType;
        this.id = id;
        this.datasetName = datasetName;
    }

    /**
     * Get the name of the type of object being queried.
     *
     * @return the type string of the object sought
     */
    public String getObjectType() {
        return objectType;
    }

    /**
     * Get the ID of the person that was not found.
     *
     * @return the ID
     */
    public String getId() {
        return id;
    }

    /**
     * Gets the dataset name.
     *
     * @return the dataset name
     */
    public String getDatasetName() {
        return datasetName;
    }
}
