package org.schoellerfamily.gedbrowser.api.controller;

import org.schoellerfamily.gedbrowser.api.controller.exception.ObjectNotFoundException;
import org.schoellerfamily.gedbrowser.api.controller.exception.DataSetNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
            final ObjectNotFoundException ex, final WebRequest request) {
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
            final DataSetNotFoundException ex, final WebRequest request) {
        final DataSetNotFoundException newex = new DataSetNotFoundException(
                ex.getMessage(), ex.getDatasetName());
        newex.setStackTrace(new StackTraceElement[0]);
        return handleExceptionInternal(ex, newex, new HttpHeaders(),
                HttpStatus.NOT_FOUND, request);
    }
}
