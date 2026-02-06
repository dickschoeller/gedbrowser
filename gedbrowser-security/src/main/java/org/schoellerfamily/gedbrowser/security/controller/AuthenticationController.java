package org.schoellerfamily.gedbrowser.security.controller;

import java.util.Map;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.schoellerfamily.gedbrowser.security.model.UserTokenState;
import org.schoellerfamily.gedbrowser.security.model.UserTokenStateImpl;
import org.schoellerfamily.gedbrowser.security.service.impl.CustomUserDetailsService;
import org.schoellerfamily.gedbrowser.security.token.TokenHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

/**
 * @author Dick Schoeller
 */
@RestController
@RequestMapping(value = "/v1", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class AuthenticationController {
    /** */
    private final CustomUserDetailsService userDetailsService;

    /** */
    private final AuthenticationManager authenticationManager;

    /** */
    private final TokenHelper tokenHelper;

    /** */
    @Value("${jwt.expires_in:600}")
    private int expiresIn;

    /** */
    @Value("${jwt.cookie:AUTH-TOKEN}")
    private String cookie;

    /**
     * @param request the request
     * @param response the response
     * @return the response entity
     */
    @RequestMapping(value = "/refresh", method = RequestMethod.GET)
    public ResponseEntity<UserTokenState> refreshAuthenticationToken(
            final HttpServletRequest request,
            final HttpServletResponse response) {

        final String authToken = tokenHelper.getToken(request);
        final boolean canTokenBeRefreshed =
                tokenHelper.canTokenBeRefreshed(authToken);
        if (authToken != null && canTokenBeRefreshed) {
            return doRefresh(response, authToken);
        } else {
            final UserTokenState userTokenState = new UserTokenStateImpl();
            return ResponseEntity.accepted().body(userTokenState);
        }
    }

    /**
     * @param response the http response
     * @param authToken the authentication token
     * @return the response
     */
    private ResponseEntity<UserTokenState> doRefresh(final HttpServletResponse response,
            final String authToken) {
        final String refreshedToken = tokenHelper.refreshToken(authToken);

        final Cookie authCookie = new Cookie(cookie, (refreshedToken));
        authCookie.setPath("/");
        authCookie.setHttpOnly(true);
        authCookie.setMaxAge(expiresIn);
        authCookie.setSecure(true);
        // Add cookie to response
        response.addCookie(authCookie);

        final UserTokenState userTokenState = new UserTokenStateImpl(
                refreshedToken, expiresIn);
        return ResponseEntity.ok(userTokenState);
    }

    /**
     * @param passwordChanger the data carrier for password change
     * @return the response entity
     */
    @RequestMapping(value = "/changePassword", method = RequestMethod.POST)
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Map<String, String>> changePassword(
            @RequestBody final PasswordChanger passwordChanger) {
        userDetailsService.changePassword(passwordChanger.getOldPassword(),
                passwordChanger.getNewPassword(), authenticationManager);
        return ResponseEntity.accepted().body(Map.of("result", "success"));
    }

    /**
     * This will probably eventually be private or public, not default.
     *
     * @author Dick Schoeller
     */
    /* default */ static final class PasswordChanger {
        /** */
        private String oldPassword;
        /** */
        private String newPassword;

        /**
         * @return the old password
         */
        public String getOldPassword() {
            return oldPassword;
        }

        /**
         * @param oldPassword the old password
         */
        public void setOldPassword(final String oldPassword) {
            this.oldPassword = oldPassword;
        }

        /**
         * @return the new password
         */
        public String getNewPassword() {
            return newPassword;
        }

        /**
         * @param newPassword the new password
         */
        public void setNewPassword(final String newPassword) {
            this.newPassword = newPassword;
        }
    }
}
