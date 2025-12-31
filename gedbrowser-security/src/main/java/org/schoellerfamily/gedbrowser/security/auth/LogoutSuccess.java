package org.schoellerfamily.gedbrowser.security.auth;

import java.io.IOException;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import tools.jackson.databind.ObjectMapper;

/**
 * @author Dick Schoeller
 */
@Component
@RequiredArgsConstructor
public final class LogoutSuccess implements LogoutSuccessHandler {
    /** */
    private final ObjectMapper objectMapper;

    /**
     * {@inheritDoc}
     */
    @Override
    public void onLogoutSuccess(final HttpServletRequest httpServletRequest,
            final HttpServletResponse response,
            final Authentication authentication)
            throws IOException, ServletException {
        final Map<String, String> result = Map.of("result", "success");
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(result));
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
