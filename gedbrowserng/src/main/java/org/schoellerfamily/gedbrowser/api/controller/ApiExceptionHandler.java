package org.schoellerfamily.gedbrowser.api.controller;

import org.schoellerfamily.gedbrowser.api.controller.exception.DataSetNotFoundException;
import org.schoellerfamily.gedbrowser.api.controller.exception.ObjectNotFoundException;
import org.schoellerfamily.gedbrowser.api.service.storage.StorageException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * @author Dick Schoeller
 */
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
    /**
     * @param ex the exception being handled
     * @param request the request that it is associated with
     * @return the response
     */
    @ExceptionHandler(value = { ObjectNotFoundException.class })
    protected ResponseEntity<Object> handleObjectNotFound(
    		@NonNull
            final ObjectNotFoundException ex,
            @NonNull
            final WebRequest request) {
        final ObjectNotFoundException newex = new ObjectNotFoundException(
                ex.getMessage(), ex.getObjectType(), ex.getId(),
                ex.getDatasetName());
        newex.setStackTrace(new StackTraceElement[0]);
        return handleExceptionInternal(ex, newex, new HttpHeaders(),
                HttpStatus.NOT_FOUND, request);
    }

    /**
     * @param ex the exception being handled
     * @param request the request that it is associated with
     * @return the response
     */
    @ExceptionHandler(value = { DataSetNotFoundException.class })
    protected ResponseEntity<Object> handleDataSetNotFound(
    		@NonNull
            final DataSetNotFoundException ex,
            @NonNull
            final WebRequest request) {
        final DataSetNotFoundException newex = new DataSetNotFoundException(
                ex.getMessage(), ex.getDatasetName());
        newex.setStackTrace(new StackTraceElement[0]);
        return handleExceptionInternal(ex, newex, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    /**
     * @param ex the exception being handled
     * @param request the request that it is associated with
     * @return the response
     */
    @ExceptionHandler(value = { StorageException.class })
    protected ResponseEntity<Object> handleStorageException(
    		@NonNull
            final StorageException ex,
            @NonNull
            final WebRequest request) {
        final StorageException newex = new StorageException(
                ex.getMessage());
        newex.setStackTrace(new StackTraceElement[0]);
        return handleExceptionInternal(ex, newex, new HttpHeaders(),
                HttpStatus.BAD_REQUEST, request);
    }
}
