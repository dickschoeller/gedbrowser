package org.schoellerfamily.gedbrowser.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Dick Schoeller
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Data set not found")
public final class DataSetNotFoundException extends RuntimeException {
    /** */
    private static final long serialVersionUID = 2L;

    /** */
    private final String datasetName;

    /**
     * @param message the message to display
     * @param datasetName the dataset that was requested
     */
    public DataSetNotFoundException(final String message,
            final String datasetName) {
        super(message);
        this.datasetName = datasetName;
    }

    /**
     * @return the name of the requested dataset
     */
    public String getDatasetName() {
        return datasetName;
    }
}
