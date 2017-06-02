package org.schoellerfamily.gedbrowser.controller.exception;

import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Dick Schoeller
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Submittor not found")
public class SubmittorNotFoundException extends ObjectNotFoundException {
    /** */
    private static final long serialVersionUID = 1L;

    /**
     * @param message the message to display
     * @param submittorId the ID of the submittor not found
     * @param datasetName the name of the dataset being searched
     * @param context the rendering context
     */
    public SubmittorNotFoundException(final String message,
            final String submittorId, final String datasetName,
            final RenderingContext context) {
        super(message, submittorId, datasetName, context);
    }

    /**
     * Get the ID of the source that was not found.
     *
     * @return the ID
     */
    public String getSubmittorId() {
        return super.getId();
    }

}
