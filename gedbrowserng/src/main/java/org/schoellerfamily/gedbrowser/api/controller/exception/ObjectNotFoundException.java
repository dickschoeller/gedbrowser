package org.schoellerfamily.gedbrowser.api.controller.exception;

/**
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
     * @param message the message to display
     * @param objectType the type of object queried
     * @param id the ID of the object not found
     * @param datasetName the name of the dataset being searched
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
     * @return get the name of the dataset being searched
     */
    public String getDatasetName() {
        return datasetName;
    }
}
