package org.schoellerfamily.gedbrowser.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Dick Schoeller
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Source not found")
public final class SourceNotFoundException extends RuntimeException {
    /** */
    private static final long serialVersionUID = 1L;

    /**
     * @param message the message to display
     */
    public SourceNotFoundException(final String message) {
        super(message);
    }
}
