package org.schoellerfamily.gedbrowser.controller.exception;

import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when a submitter is not found.
 *
 * @author Dick Schoeller
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Submitter not found")
public class SubmitterNotFoundException extends ObjectNotFoundException {
    /** */
    private static final long serialVersionUID = 1L;

    /**
     * Creates a new SubmitterNotFoundException.
     *
     * @param message the message
     * @param submitterId the unique identifier for submitter
     * @param datasetName the dataset name to use
     * @param context the context
     */
    public SubmitterNotFoundException(final String message,
            final String submitterId, final String datasetName,
            final RenderingContext context) {
        super(message, submitterId, datasetName, context);
    }

    /**
     * Get the ID of the source that was not found.
     *
     * @return the ID
     */
    public String getSubmitterId() {
        return super.getId();
    }

}
