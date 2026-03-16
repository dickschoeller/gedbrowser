package org.schoellerfamily.gedbrowser.security.controller.test;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.client.EntityExchangeResult;
import org.springframework.test.web.servlet.client.RestTestClient;

import lombok.RequiredArgsConstructor;

/**
 * @author Dick Schoeller
 */
@RequiredArgsConstructor
public final class LoginTestHelper {
    /** */
    private final RestTestClient client;

    /** */
    private final int port;

    /**
     * Login and return the entity. Other exposed methods will use this and then
     * pass back different pars of the response
     *
     * @param username the username
     * @param password the password
     * @return the entity
     * @throws URISyntaxException if the URL is broken
     */
    public EntityExchangeResult<LoginResponse> login(final String username,
            final String password) throws URISyntaxException {
        final String url = "http://localhost:" + port + "/v1/login";
        final String loginString = "username=" + username + "&password="
                + password;
        return client.post()
            .uri(new URI(url))
            .body(loginString)
            .accept(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
            .exchange()
            .returnResult(LoginResponse.class);
    }

    /**
     * @author Dick Schoeller
     */
    public static final class LoginResponse {
        /**
         * The access token.
         */
        private final String accessToken;
        /**
         * When it expires in seconds.
         */
        private final Long expiresIn;

        /**
         * Executes login response.
         *
         */
        public LoginResponse() {
            this.accessToken = null;
            this.expiresIn = 1L;
        }

        /**
         * Gets the access token.
         *
         * @return the access token
         */
        public String getAccessToken() {
            return accessToken;
        }

        /**
         * Gets the expires in.
         *
         * @return the expires in
         */
        public Long getExpiresIn() {
            return expiresIn;
        }
    }

    /**
     * Build headers from login response.
     *
     * @param loginResponse the login response
     * @return the headers
     */
    public HttpHeaders buildHeaders(
            final EntityExchangeResult<LoginResponse> loginResponse) {
        String accessToken = Optional.ofNullable(loginResponse.getResponseBody())
            .map(LoginResponse::getAccessToken).orElse(null);

        // If accessToken is not present in the parsed body, try extracting it
        // from the Set-Cookie header (AUTH-TOKEN cookie).
        if (accessToken == null) {
            final String setCookie = loginResponse.getResponseHeaders()
                    .getFirst(HttpHeaders.SET_COOKIE);
            if (setCookie != null) {
                final String cookieName = "AUTH-TOKEN";
                final int idx = setCookie.indexOf(cookieName + "=");
                if (idx >= 0) {
                    final int start = idx + (cookieName + "=").length();
                    int end = setCookie.indexOf(';', start);
                    if (end < 0) {
                        end = setCookie.length();
                    }
                    accessToken = setCookie.substring(start, end);
                }
            }
        }

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        if (accessToken != null) {
            headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
        }
        final List<MediaType> accepts = List.of(MediaType.APPLICATION_JSON);
        headers.setAccept(accepts);
        return headers;
    }

    /**
     * Executes logout request.
     *
     * @param headers the headers
     * @return the resposne entity
     * @throws URISyntaxException the URL is messed up
     */
    public EntityExchangeResult<String> logout(final HttpHeaders headers)
            throws URISyntaxException {
        final String url = "http://localhost:" + port + "/v1/logout";
        return client.post()
            .uri(new URI(url))
            .headers(h -> h.addAll(headers))
            .exchange()
            .returnResult(String.class);
    }
}
