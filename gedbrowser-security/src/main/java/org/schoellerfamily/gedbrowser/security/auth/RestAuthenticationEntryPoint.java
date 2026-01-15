package org.schoellerfamily.gedbrowser.security.auth;

import java.io.IOException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 * @author Dick Schoeller
 */
@Component
public final class RestAuthenticationEntryPoint
        implements AuthenticationEntryPoint {
    /**
     * {@inheritDoc}
     */
    @Override
    public void commence(final HttpServletRequest request,
            final HttpServletResponse response,
            final AuthenticationException authException) throws IOException {
        final String message = authException.getMessage();
        final String header = request.getHeader("Authorization");
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
