package org.schoellerfamily.gedbrowser.security.controller.test;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.client.EntityExchangeResult;
import org.springframework.test.web.servlet.client.RestTestClient;

/**
 * Provides support for testing password test helper behavior.
 *
 * @author Richard Schoeller
 */
public class PasswordTestHelper {
    /** */
    private final int port;
    /** */
    private final RestTestClient client;

    /**
     * Creates a new PasswordTestHelper.
     *
     * @param client the client
     * @param port the port
     */
    public PasswordTestHelper(final RestTestClient client,
            final int port) {
        this.client = client;
        this.port = port;
    }

    /**
     * Change password using JSON body and provided headers (with auth token).
     *
     * @param headers headers, including access token
     * @param oldPassword the old password
     * @param newPassword the new password
     * @return the entity exchange result containing the response body
     * @throws URISyntaxException if the URL is broken
     */
    public EntityExchangeResult<String> changePassword(final HttpHeaders headers,
            final String oldPassword, final String newPassword)
                    throws URISyntaxException {
        final String url = "http://localhost:" + port + "/v1/changePassword";
        final PasswordChanger pc = new PasswordChanger();
        pc.setOldPassword(oldPassword);
        pc.setNewPassword(newPassword);
        return client.post()
                .uri(new URI(url))
                .headers(h -> h.addAll(headers))
                .body(pc)
                .exchange()
                .returnResult(String.class);
    }

    /**
     * @author Richard Schoeller
     */
    static final class PasswordChanger {
        /** */
        private String oldPassword;
        /** */
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
