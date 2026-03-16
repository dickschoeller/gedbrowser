package org.schoellerfamily.gedbrowser.controller.exception;

import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;



/**
 * Represents an error related to submission not found.
 *
 * @author Richard Schoeller
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Submission not found")
public final class SubmissionNotFoundException extends ObjectNotFoundException {
    /** */
    private static final long serialVersionUID = 1L;

    /**
     * Creates a new SubmissionNotFoundException.
     *
     * @param message the message
     * @param sourceId the unique identifier for source
     * @param datasetName the dataset name to use
     * @param context the context
     */
    public SubmissionNotFoundException(final String message,
            final String sourceId, final String datasetName,
            final RenderingContext context) {
        super(message, sourceId, datasetName, context);
    }

    /**
     * Get the ID of the source that was not found.
     *
     * @return the ID
     */
    public String getSubmissionId() {
        return super.getId();
    }
}
