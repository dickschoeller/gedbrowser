package org.schoellerfamily.gedbrowser.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;



/**
 * Represents an error related to data set not found.
 *
 * @author Richard Schoeller
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Data set not found")
public final class DataSetNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 2L;

    /** Dataset name. */
    private final String datasetName;

    /**
     * Creates a new DataSetNotFoundException.
     *
     * @param message the message
     * @param datasetName the dataset name to use
     */
    public DataSetNotFoundException(final String message, final String datasetName) {
        super(message);
        this.datasetName = datasetName;
    }

    /**
     * Get the name of the dataset.
     *
     * @return the name of the requested dataset
     */
    public String getDatasetName() {
        return datasetName;
    }
}
