package org.schoellerfamily.gedbrowser.api.test;

import java.net.URI;
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
     * @param password the password     * @return the entity
     * @return the entity
     */
    public EntityExchangeResult<LoginResponse> login(final String username, final String password) {
        final String url = "http://localhost:" + port + "/gedbrowserng/v1/login";
        final String loginString = "username=" + username + "&password=" + password;
        return client.post()
            .uri(URI.create(url))
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
         * Default constructor.
         */
        public LoginResponse() {
            this.accessToken = null;
            this.expiresIn = 1L;
        }

        /**
         * @return the access token
         */
        public String getAccessToken() {
            return accessToken;
        }

        /**
         * @return the expiration in seconds
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
    public HttpHeaders buildHeaders(final EntityExchangeResult<LoginResponse> loginResponse) {
        final LoginResponse lr = loginResponse.getResponseBody();
        final String accessToken = Optional.ofNullable(lr)
            .map(LoginResponse::getAccessToken)
            .orElse(null);

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
     * @return the response entity
     */
    public EntityExchangeResult<String> logout(final HttpHeaders headers) {
        final String url = "http://localhost:" + port + "/gedbrowserng/v1/logout";
        return client.post()
            .uri(URI.create(url))
            .headers(h -> h.addAll(headers))
            .exchange()
            .returnResult(String.class);
    }

    /**
     * Login and return a request entity.
     *
     * @param <T> the entity type
     * @return the entity
     */
    public <T> org.springframework.http.HttpEntity<T> adminEntity() {
        return new org.springframework.http.HttpEntity<T>(adminLogin());
    }

    /**
     * Login and return a built request header.
     *
     * @return the header
     */
    public org.springframework.http.HttpHeaders adminLogin() {
        final org.springframework.http.HttpHeaders headers = buildHeaders(
            login("schoeller@comcast.net", "HAHANOWAY"));
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    /**
     * Login and return a request entity.
     *
     * @param <T> the entity type
     * @return the entity
     */
    public <T> org.springframework.http.HttpEntity<T> userEntity() {
        return new org.springframework.http.HttpEntity<T>(userLogin());
    }

    /**
     * Login and return a built request header.
     *
     * @return the header
     */
    public org.springframework.http.HttpHeaders userLogin() {
        final org.springframework.http.HttpHeaders headers = buildHeaders(login("guest", "guest"));
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
}
