package org.schoellerfamily.gedbrowser.security.auth;

import java.io.IOException;

import org.schoellerfamily.gedbrowser.security.model.SecurityUser;
import org.schoellerfamily.gedbrowser.security.model.UserTokenState;
import org.schoellerfamily.gedbrowser.security.model.UserTokenStateImpl;
import org.schoellerfamily.gedbrowser.security.token.TokenHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import tools.jackson.databind.ObjectMapper;



/**
 * Represents authentication success handler.
 *
 * @author Richard Schoeller
 */
@Component
@RequiredArgsConstructor
public class AuthenticationSuccessHandler
        extends SimpleUrlAuthenticationSuccessHandler {
    /**
     * The expires in value.
     */
    @Value("${jwt.expires_in:600}")
    private final int expiresIn;

    /**
     * The cookie value.
     */
    @Value("${jwt.cookie:AUTH-TOKEN}")
    private final String cookie;

    /**
     * The token helper value.
     */
    private final TokenHelper tokenHelper;

    /**
     * The object mapper value.
     */
    private final ObjectMapper objectMapper;

    /**
     * Executes on authentication success.
     *
     * @param request the request
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
        authCookie.setSecure(true);

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
