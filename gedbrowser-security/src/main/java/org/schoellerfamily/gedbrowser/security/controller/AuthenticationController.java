package org.schoellerfamily.gedbrowser.security.controller;

import java.util.Map;

import org.schoellerfamily.gedbrowser.security.model.UserTokenState;
import org.schoellerfamily.gedbrowser.security.model.UserTokenStateImpl;
import org.schoellerfamily.gedbrowser.security.service.impl.CustomUserDetailsService;
import org.schoellerfamily.gedbrowser.security.token.TokenHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;



/**
 * Handles requests for authentication.
 *
 * @author Richard Schoeller
 */
@RestController
@RequestMapping(value = "/v1", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class AuthenticationController {
    /**
     * The user details service value.
     */
    private final CustomUserDetailsService userDetailsService;

    /**
     * The authentication manager value.
     */
    private final AuthenticationManager authenticationManager;

    /**
     * The token helper value.
     */
    private final TokenHelper tokenHelper;

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
     * Executes refresh authentication token.
     *
     * @param request the request
     * @param response the response
     * @return the resulting response entity
     */
    @GetMapping(value = "/refresh")
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
     * Executes change password.
     *
     * @param passwordChanger the password changer
     * @return the resulting string
     */
    @PostMapping(value = "/changePassword")
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
     * @author Richard Schoeller
     */
    /* default */ static final class PasswordChanger {
        /**
         * The old password value.
         */
        private String oldPassword;
        /**
         * The new password value.
         */
        private String newPassword;

        /**
         * Gets the old password.
         *
         * @return the old password
         */
        public String getOldPassword() {
            return oldPassword;
        }

        /**
         * Sets the old password.
         *
         * @param oldPassword the old password
         */
        public void setOldPassword(final String oldPassword) {
            this.oldPassword = oldPassword;
        }

        /**
         * Gets the new password.
         *
         * @return the new password
         */
        public String getNewPassword() {
            return newPassword;
        }

        /**
         * Sets the new password.
         *
         * @param newPassword the new password
         */
        public void setNewPassword(final String newPassword) {
            this.newPassword = newPassword;
        }
    }
}
