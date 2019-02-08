package org.schoellerfamily.gedbrowser.security.auth;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Dick Schoeller
 */
@Component
public final class LogoutSuccess implements LogoutSuccessHandler {
    /** */
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * {@inheritDoc}
     */
    @Override
    public void onLogoutSuccess(final HttpServletRequest httpServletRequest,
            final HttpServletResponse response,
            final Authentication authentication)
            throws IOException, ServletException {
        final Map<String, String> result = new HashMap<>();
        result.put("result", "success");
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(result));
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
