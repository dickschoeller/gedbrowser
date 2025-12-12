package org.schoellerfamily.gedbrowser.api.test;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

/**
 * @author Dick Schoeller
 */
public final class LoginTestHelper {
    /** */
    private final TestRestTemplate template;

    /** */
    private final int port;

    /**
     * Constructor.
     *
     * @param template the REST template
     * @param port the port
     */
    public LoginTestHelper(final TestRestTemplate template, final int port) {
        this.template = template;
        this.port = port;
    }

    /**
     * Login and return the entity. Other exposed methods will use this and then
     * pass back different pars of the response
     *
     * @param username the username
     * @param password the password
     * @return the entity
     * @throws URISyntaxException if the URL is broken
     */
    public ResponseEntity<LoginResponse> login(final String username,
            final String password) throws URISyntaxException {
        final String url = "http://localhost:" + port + "/gedbrowserng/v1/login";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        final String loginString = "username=" + username + "&password="
                + password;
        final HttpEntity<String> loginReq =
                new HttpEntity<>(loginString, headers);
        final ResponseEntity<LoginResponse> responseEntity = template.postForEntity(new URI(url), loginReq, LoginResponse.class);
		return responseEntity;
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
    public HttpHeaders buildHeaders(
            final ResponseEntity<LoginResponse> loginResponse) {
        final String accessToken = Optional.ofNullable(loginResponse.getBody())
            .map(LoginResponse::getAccessToken).orElse(null);

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
     * @throws URISyntaxException the URL is messed up
     */
    public ResponseEntity<String> logout(final HttpHeaders headers)
            throws URISyntaxException {
        final String url = "http://localhost:" + port + "/gedbrowserng/v1/logout";
        final HttpEntity<String> logoutRequest = new HttpEntity<>(headers);
        return template.postForEntity(new URI(url), logoutRequest, String.class);
    }

    /**
     * Login and return a request entity.
     *
     * @return the entity
     * @throws URISyntaxException if there is a problem with the URL
     */
    public <T> HttpEntity<T> adminEntity() throws URISyntaxException {
        return new HttpEntity<T>(adminLogin());
    }

    /**
     * Login and return a built request header.
     *
     * @return the header
     * @throws URISyntaxException if there is a problem with the URL
     */
    public HttpHeaders adminLogin() throws URISyntaxException {
        final HttpHeaders headers =
                buildHeaders(login("schoeller@comcast.net", "HAHANOWAY"));
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    /**
     * Login and return a request entity.
     *
     * @return the entity
     * @throws URISyntaxException if there is a problem with the URL
     */
    public <T> HttpEntity<T> userEntity() throws URISyntaxException {
        return new HttpEntity<T>(userLogin());
    }

    /**
     * Login and return a built request header.
     *
     * @return the header
     * @throws URISyntaxException if there is a problem with the URL
     */
    public HttpHeaders userLogin() throws URISyntaxException {
        final HttpHeaders headers =
                buildHeaders(login("guest", "guest"));
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
}
