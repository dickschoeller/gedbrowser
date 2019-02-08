package org.schoellerfamily.gedbrowser.security.controller.test;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

/**
 * @author Dick Schoeller
 */
public class PasswordTestHelper {
    /** */
    private final int port;
    /** */
    private final TestRestTemplate testRestTemplate;

    /**
     * @param testRestTemplate lets us make rest calls
     * @param port the port to connect to
     */
    public PasswordTestHelper(final TestRestTemplate testRestTemplate,
            final int port) {
        this.testRestTemplate = testRestTemplate;
        this.port = port;
    }

    /**
     * Login and return the entity. Other exposed methods will use this and then
     * pass back different pars of the response
     *
     * @param headers headers, including access token
     * @param oldPassword the old password
     * @param newPassword the new password
     * @return the entity
     * @throws URISyntaxException if the URL is broken
     */
    public ResponseEntity<String> changePassword(final HttpHeaders headers,
            final String oldPassword, final String newPassword)
                    throws URISyntaxException {
        final String url = "http://localhost:" + port + "/v1/changePassword";
        final PasswordChanger pc = new PasswordChanger();
        pc.setOldPassword(oldPassword);
        pc.setNewPassword(newPassword);
        final HttpEntity<PasswordChanger> loginReq =
                new HttpEntity<>(pc, headers);
        final ResponseEntity<String> responseEntity = testRestTemplate
                .postForEntity(new URI(url), loginReq, String.class);
        return responseEntity;
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
