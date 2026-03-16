package org.schoellerfamily.gedbrowser.security.auth;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;



/**
 * Represents authentication failure handler.
 *
 * @author Richard Schoeller
 */
@Component
public class AuthenticationFailureHandler
        extends SimpleUrlAuthenticationFailureHandler {
    /**
     * Executes on authentication failure.
     *
     * @param request the request
     */
    @Override
    public void onAuthenticationFailure(final HttpServletRequest request,
            final HttpServletResponse response,
            final AuthenticationException exception)
            throws IOException, ServletException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, exception.getMessage());
    }
}
