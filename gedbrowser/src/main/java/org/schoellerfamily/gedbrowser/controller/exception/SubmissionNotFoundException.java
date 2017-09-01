package org.schoellerfamily.gedbrowser.controller.exception;

import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Dick Schoeller
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Submission not found")
public final class SubmissionNotFoundException extends ObjectNotFoundException {
    /** */
    private static final long serialVersionUID = 1L;

    /**
     * @param message the message to display
     * @param sourceId the ID of the source not found
     * @param datasetName the name of the dataset being searched
     * @param context the rendering context
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
