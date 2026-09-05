package org.schoellerfamily.gedbrowser.security.auth;

import java.io.IOException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;



/**
 * Represents rest authentication entry point.
 *
 * @author Richard Schoeller
 */
@Component
@Slf4j
public final class RestAuthenticationEntryPoint
        implements AuthenticationEntryPoint {
    /**
     * Executes commence.
     *
     * @param request the request
     */
    @Override
    public void commence(final HttpServletRequest request,
            final HttpServletResponse response,
            final AuthenticationException authException) throws IOException {
        final String message = authException.getMessage();
        final String header = request.getHeader("Authorization");
        final Object errorUri = request.getAttribute("jakarta.servlet.error.request_uri");
        log.warn("Authentication failed: method={} uri={} dispatcherType={} originalErrorUri={}"
                + " hasAuthorizationHeader={} message={}",
                request.getMethod(),
                request.getRequestURI(),
                request.getDispatcherType(),
                errorUri,
                header != null,
                message);
        if (header != null) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, message);
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, message);
        }
        // This is invoked when user tries to access a secured REST resource
        // without supplying any credentials. We should just send a 401
        // Unauthorized response because there is no 'login page' to redirect
        // to.
    }
}
