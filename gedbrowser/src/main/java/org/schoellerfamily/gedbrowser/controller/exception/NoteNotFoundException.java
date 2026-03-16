package org.schoellerfamily.gedbrowser.controller.exception;

import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;



/**
 * Represents an error related to note not found.
 *
 * @author Richard Schoeller
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Note not found")
public final class NoteNotFoundException extends ObjectNotFoundException {
    /** */
    private static final long serialVersionUID = 1L;

    /**
     * Creates a new NoteNotFoundException.
     *
     * @param message the message
     * @param noteId the unique identifier for note
     * @param datasetName the dataset name to use
     * @param context the context
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
