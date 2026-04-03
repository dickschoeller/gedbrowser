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
 * Provides support for testing user test helper behavior.
 *
 * @author Richard Schoeller
 */
public class UserTestHelper {
    /** */
    private final int port;
    /** */
    private final RestTestClient client;

    /**
     * Creates a new UserTestHelper.
     *
     * @param client the client
     * @param port the port
     */
    public UserTestHelper(final RestTestClient client, final int port) {
        this.client = client;
        this.port = port;
    }

    /**
     * Executes whoami.
     *
     * @param headers the headers
     * @return the resulting security user
     */
    public SecurityUser whoami(final HttpHeaders headers) throws URISyntaxException {
        final EntityExchangeResult<UserImpl> res = whoamiResponse(headers);
        return res.getResponseBody();
    }

    /**
     * Executes whoami response.
     *
     * @param headers the headers
     * @return the resulting entity exchange result
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
     * Returns the users string.
     *
     * @param headers the headers
     * @return the users string
     */
    public String getUsersString(final HttpHeaders headers) throws URISyntaxException {
        final String url = baseUrl() + "users";
        return getString(url, headers);
    }

    /**
     * Returns the user.
     *
     * @param headers the headers
     * @param requestName the request name to use
     * @return the user
     */
    public SecurityUser getUser(final HttpHeaders headers, final String requestName)
        throws URISyntaxException {
        final EntityExchangeResult<UserImpl> res = getUserResponse(headers, requestName);
        return res.getResponseBody();
    }

    /**
     * Returns the user response.
     *
     * @param headers the headers
     * @param requestName the request name to use
     * @return the user response
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
     * Returns the users response.
     *
     * @param headers the headers
     * @return the users response
     */
    public EntityExchangeResult<List<UserImpl>> getUsersResponse(final HttpHeaders headers)
        throws URISyntaxException {
        final String url = baseUrl() + "users";
        return client.get()
            .uri(new URI(url))
            .headers(h -> h.addAll(headers))
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .returnResult(new ParameterizedTypeReference<List<UserImpl>>() {
            });
    }

    /**
     * Returns the users.
     *
     * @param headers the headers
     * @return the users
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

    private String baseUrl() {
        return "http://localhost:" + port + "/v1/";
    }

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
