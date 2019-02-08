package org.schoellerfamily.gedbrowser.security.auth;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.schoellerfamily.gedbrowser.security.model.SecurityUser;
import org.schoellerfamily.gedbrowser.security.model.UserTokenState;
import org.schoellerfamily.gedbrowser.security.model.UserTokenStateImpl;
import org.schoellerfamily.gedbrowser.security.token.TokenHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

/**
 * @author Dick Schoeller
 */
@Component
public class AuthenticationSuccessHandler
        extends SimpleUrlAuthenticationSuccessHandler {
    /** */
    @Value("${jwt.expires_in:600}")
    private int expiresIn;

    /** */
    @Value("${jwt.cookie:AUTH-TOKEN}")
    private String cookie;

    /** */
    @Autowired
    private TokenHelper tokenHelper;

    /** */
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * {@inheritDoc}
     */
    @Override
    public void onAuthenticationSuccess(final HttpServletRequest request,
            final HttpServletResponse response,
            final Authentication authentication)
            throws IOException, ServletException {
        clearAuthenticationAttributes(request);
        final SecurityUser user = (SecurityUser) authentication.getPrincipal();

        final String jws = tokenHelper.generateToken(user.getUsername());

        // Create token auth Cookie
        final Cookie authCookie = new Cookie(cookie, (jws));
        authCookie.setHttpOnly(true);
        authCookie.setMaxAge(expiresIn);
        authCookie.setPath("/");

        // Add cookie to response
        response.addCookie(authCookie);

        final UserTokenState userTokenState =
                new UserTokenStateImpl(jws, expiresIn);
        final String jwtResponse =
                objectMapper.writeValueAsString(userTokenState);
        response.setContentType("application/json");
        response.getWriter().write(jwtResponse);
    }
}
