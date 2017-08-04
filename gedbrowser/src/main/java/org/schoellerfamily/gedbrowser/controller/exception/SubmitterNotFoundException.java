package org.schoellerfamily.gedbrowser.controller.exception;

import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Dick Schoeller
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Submitter not found")
public class SubmitterNotFoundException extends ObjectNotFoundException {
    /** */
    private static final long serialVersionUID = 1L;

    /**
     * @param message the message to display
     * @param submitterId the ID of the submitter not found
     * @param datasetName the name of the dataset being searched
     * @param context the rendering context
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
