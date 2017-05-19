package org.schoellerfamily.gedbrowser.controller.exception;

import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Dick Schoeller
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Person not found")
public final class PersonNotFoundException extends ObjectNotFoundException {
    /** */
    private static final long serialVersionUID = 3L;

    /**
     * @param message the message to display
     * @param personId the ID of the person not found
     * @param datasetName the name of the dataset being searched
     * @param context the rendering context
     */
    public PersonNotFoundException(final String message, final String personId,
            final String datasetName, final RenderingContext context) {
        super(message, personId, datasetName, context);
    }

    /**
     * Get the ID of the person that was not found.
     *
     * @return the ID
     */
    public String getPersonId() {
        return super.getId();
    }
}
