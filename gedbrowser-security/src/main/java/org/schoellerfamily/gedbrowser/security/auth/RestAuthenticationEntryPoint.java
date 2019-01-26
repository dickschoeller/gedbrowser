package org.schoellerfamily.gedbrowser.security.auth;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
        // This is invoked when user tries to access a secured REST resource
        // without supplying any credentials. We should just send a 401
        // Unauthorized response because there is no 'login page' to redirect
        // to.
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                authException.getMessage());
    }
}
