package org.schoellerfamily.gedbrowser.security.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author Dick Schoeller
 */
@ControllerAdvice
public class ExceptionHandlingController {
    /**
     * @param ex the exception
     * @return the response entity
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
