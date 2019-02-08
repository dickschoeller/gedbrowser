package org.schoellerfamily.gedbrowser.security.controller;

import org.schoellerfamily.gedbrowser.security.model.UserTokenState;
import org.schoellerfamily.gedbrowser.security.model.UserTokenStateImpl;
import org.schoellerfamily.gedbrowser.security.service.impl.CustomUserDetailsService;
import org.schoellerfamily.gedbrowser.security.token.TokenHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Dick Schoeller
 */
@RestController
@RequestMapping(value = "/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {
    /** */
    @Autowired
    private CustomUserDetailsService userDetailsService;

    /** */
    @Autowired
    private TokenHelper tokenHelper;

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
    public ResponseEntity<?> refreshAuthenticationToken(
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
    private ResponseEntity<?> doRefresh(final HttpServletResponse response,
            final String authToken) {
        final String refreshedToken = tokenHelper.refreshToken(authToken);

        final Cookie authCookie = new Cookie(cookie, (refreshedToken));
        authCookie.setPath("/");
        authCookie.setHttpOnly(true);
        authCookie.setMaxAge(expiresIn);
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
    public ResponseEntity<?> changePassword(
            @RequestBody final PasswordChanger passwordChanger) {
        userDetailsService.changePassword(passwordChanger.getOldPassword(),
                passwordChanger.getNewPassword());
        final Map<String, String> result = new HashMap<>();
        result.put("result", "success");
        return ResponseEntity.accepted().body(result);
    }

    /**
     * @author Dick Schoeller
     */
    static final class PasswordChanger {
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
