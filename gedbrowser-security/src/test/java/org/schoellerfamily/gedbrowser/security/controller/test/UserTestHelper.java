package org.schoellerfamily.gedbrowser.security.controller.test;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import org.schoellerfamily.gedbrowser.security.model.SecurityUser;
import org.schoellerfamily.gedbrowser.security.model.UserImpl;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.client.EntityExchangeResult;
import org.springframework.test.web.servlet.client.RestTestClient;

/**
 * @author Dick Schoeller
 */
public class UserTestHelper {
    /** */
    private final int port;
    /** */
    private final RestTestClient client;

    /**
     * @param client lets us make rest calls
     * @param port   the port to connect to
     */
    public UserTestHelper(final RestTestClient client, final int port) {
        this.client = client;
        this.port = port;
    }

    /**
     * @param headers the headers
     * @return the user
     * @throws URISyntaxException if there is a URL problem
     */
    public SecurityUser whoami(final HttpHeaders headers) throws URISyntaxException {
        final EntityExchangeResult<UserImpl> res = whoamiResponse(headers);
        return res.getResponseBody();
    }

    /**
     * @param headers the headers
     * @return the response
     * @throws URISyntaxException if there is a URL problem
     */
    public EntityExchangeResult<UserImpl> whoamiResponse(final HttpHeaders headers)
        throws URISyntaxException {
        final String url = baseUrl() + "whoami";
        return client.get()
            .uri(new URI(url))
            .headers(h -> h.addAll(headers))
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .returnResult(UserImpl.class);
    }

    /**
     * @param headers the headers
     * @return the JSON string of users
     * @throws URISyntaxException if there is a problem with the URL
     */
    public String getUsersString(final HttpHeaders headers) throws URISyntaxException {
        final String url = baseUrl() + "users";
        return getString(url, headers);
    }

    /**
     * @param headers     the necessary headers
     * @param requestName the name of the user to get
     * @return the User
     * @throws URISyntaxException if the URI is bogus
     */
    public SecurityUser getUser(final HttpHeaders headers, final String requestName)
        throws URISyntaxException {
        final EntityExchangeResult<UserImpl> res = getUserResponse(headers, requestName);
        return res.getResponseBody();
    }

    /**
     * @param headers     the necessary headers
     * @param requestName the name of the user to get
     * @return the response
     * @throws URISyntaxException if the URI is bogus
     */
    public EntityExchangeResult<UserImpl> getUserResponse(final HttpHeaders headers,
        final String requestName) throws URISyntaxException {
        final String url = baseUrl() + "users/" + requestName;
        return client.get()
            .uri(new URI(url))
            .headers(h -> h.addAll(headers))
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .returnResult(UserImpl.class);
    }

    /**
     * @param headers the necessary headers
     * @return the User list
     * @throws URISyntaxException if the URI is bogus
     */
    public List<UserImpl> getUsers(final HttpHeaders headers) throws URISyntaxException {
        final String url = baseUrl() + "users";
        final EntityExchangeResult<List<UserImpl>> res = client.get()
            .uri(new URI(url))
            .headers(h -> h.addAll(headers))
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .returnResult(new ParameterizedTypeReference<List<UserImpl>>() {
            });
        return res.getResponseBody();
    }

    /**
     * @return the base URL of the API
     */
    private String baseUrl() {
        return "http://localhost:" + port + "/v1/";
    }

    /**
     * @param url     the usl to access
     * @param headers the necessary headers
     * @return the string format of the response body
     * @throws URISyntaxException if the URI is bogus
     */
    private String getString(final String url, final HttpHeaders headers)
        throws URISyntaxException {
        final EntityExchangeResult<String> res = client.get()
            .uri(new URI(url))
            .headers(h -> h.addAll(headers))
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .returnResult(String.class);
        return Optional.ofNullable(res.getResponseBody()).orElse("");
    }
}
