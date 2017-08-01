package org.schoellerfamily.gedbrowser.controller.exception;

import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Dick Schoeller
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Note not found")
public final class NoteNotFoundException extends ObjectNotFoundException {
    /** */
    private static final long serialVersionUID = 1L;

    /**
     * @param message the message to display
     * @param noteId the ID of the source not found
     * @param datasetName the name of the dataset being searched
     * @param context the rendering context
     */
    public NoteNotFoundException(final String message, final String noteId,
            final String datasetName, final RenderingContext context) {
        super(message, noteId, datasetName, context);
    }

    /**
     * Get the ID of the source that was not found.
     *
     * @return the ID
     */
    public String getNoteId() {
        return super.getId();
    }
}
