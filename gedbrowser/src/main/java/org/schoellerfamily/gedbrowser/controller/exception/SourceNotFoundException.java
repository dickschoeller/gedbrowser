package org.schoellerfamily.gedbrowser.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Dick Schoeller
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Source not found")
public final class SourceNotFoundException extends RuntimeException {
    /** */
    private static final long serialVersionUID = 1L;

    /** */
    private final String sourceId;

    /** */
    private final String datasetName;

    /**
     * @param message the message to display
     * @param sourceId the ID of the source not found
     * @param datasetName the name of the dataset being searched
     */
    public SourceNotFoundException(final String message, final String sourceId,
            final String datasetName) {
        super(message);
        this.sourceId = sourceId;
        this.datasetName = datasetName;
    }

    /**
     * Get the ID of the source that was not found.
     *
     * @return the ID
     */
    public String getSourceId() {
        return sourceId;
    }

    /**
     * @return get the name of the dataset being searched
     */
    public String getDatasetName() {
        return datasetName;
    }
}
