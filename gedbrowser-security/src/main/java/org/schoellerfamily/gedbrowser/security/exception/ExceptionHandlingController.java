package org.schoellerfamily.gedbrowser.security.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;



/**
 * Handles requests for exception handling.
 *
 * @author Richard Schoeller
 */
@ControllerAdvice
public class ExceptionHandlingController {
    /**
     * Creates a new ExceptionHandlingController.
     */
    public ExceptionHandlingController() {
    }

    /**
     * Executes resource conflict.
     *
     * @param ex the ex
     * @return the resulting response entity
     */
    @ExceptionHandler(ResourceConflictException.class)
    public final ResponseEntity<ExceptionResponse> resourceConflict(
            final ResourceConflictException ex) {
        final ExceptionResponse response =
                new ExceptionResponse("Conflict", ex.getMessage());
        return new ResponseEntity<ExceptionResponse>(response,
                HttpStatus.CONFLICT);
    }
}
