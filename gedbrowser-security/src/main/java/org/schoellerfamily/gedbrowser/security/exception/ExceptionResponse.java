package org.schoellerfamily.gedbrowser.security.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Dick Schoeller
 */
@RequiredArgsConstructor
@Getter
public final class ExceptionResponse {
    /**
     * The error code.
     */
    private final String errorCode;

    /**
     * A descriptive message.
     */
    private final String errorMessage;
}
