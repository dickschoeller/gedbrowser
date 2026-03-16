package org.schoellerfamily.gedbrowser.api.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when a requested data set cannot be found.
 *
 * @author Dick Schoeller
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Data set not found")
public final class DataSetNotFoundException extends RuntimeException {
    /** */
    private static final long serialVersionUID = 2L;

    /** */
    private final String datasetName;

    /**
     * Creates a new DataSetNotFoundException.
     *
     * @param message the message
     * @param datasetName the dataset name to use
     */
    public DataSetNotFoundException(final String message,
            final String datasetName) {
        super(message);
        this.datasetName = datasetName;
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
