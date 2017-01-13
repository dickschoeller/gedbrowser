package org.schoellerfamily.gedbrowser.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Dick Schoeller
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Person not found")
public final class PersonNotFoundException extends RuntimeException {
    /** */
    private static final long serialVersionUID = 2L;

    /** */
    private final String personId;

    /** */
    private final String datasetName;

    /**
     * @param message the message to display
     * @param personId the ID of the person not found
     * @param datasetName the name of the dataset being searched
     */
    public PersonNotFoundException(final String message, final String personId,
            final String datasetName) {
        super(message);
        this.personId = personId;
        this.datasetName = datasetName;
    }

    /**
     * Get the ID of the person that was not found.
     *
     * @return the ID
     */
    public String getPersonId() {
        return personId;
    }

    /**
     * @return get the name of the dataset being searched
     */
    public String getDatasetName() {
        return datasetName;
    }
}
