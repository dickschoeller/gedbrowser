package org.schoellerfamily.gedbrowser.security.controller.test;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.schoellerfamily.gedbrowser.security.model.SecurityUser;
import org.schoellerfamily.gedbrowser.security.model.UserImpl;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

/**
 * @author Dick Schoeller
 */
public class UserTestHelper {
    /** */
    private final int port;
    /** */
    private final TestRestTemplate testRestTemplate;

    /**
     * @param testRestTemplate lets us make rest calls
     * @param port the port to connect to
     */
    public UserTestHelper(final TestRestTemplate testRestTemplate,
            final int port) {
        this.testRestTemplate = testRestTemplate;
        this.port = port;
    }

    /**
     * @param headers the headers
     * @return the user
     * @throws URISyntaxException if there is a URL problem
     */
    public SecurityUser whoami(final HttpHeaders headers)
            throws URISyntaxException {
        return whoamiResponse(headers).getBody();
    }

    /**
     * @param headers the headers
     * @return the response
     * @throws URISyntaxException if there is a URL problem
     */
    public ResponseEntity<? extends SecurityUser> whoamiResponse(
            final HttpHeaders headers) throws URISyntaxException {
        final String url = baseUrl() + "whoami";
        final HttpEntity<UserImpl> requestEntity =
                new HttpEntity<UserImpl>(headers);
        return testRestTemplate.exchange(new URI(url), HttpMethod.GET,
                requestEntity, UserImpl.class);
    }

    /**
     * @param headers the headers
     * @return the JSON string of users
     * @throws URISyntaxException if there is a problem with the URL
     */
    public String getUsersString(final HttpHeaders headers)
            throws URISyntaxException {
        final String url = baseUrl() + "users";
        final String usersString = getString(url, headers);
        return usersString;
    }

    /**
     * @param headers the necessary headers
     * @param requestName the name of the user to get
     * @return the User
     * @throws URISyntaxException if the URI is bogus
     */
    public SecurityUser getUser(final HttpHeaders headers,
            final String requestName) throws URISyntaxException {
        return getUserResponse(headers, requestName).getBody();
    }

    /**
     * @param headers the necessary headers
     * @param requestName the name of the user to get
     * @return the response
     * @throws URISyntaxException if the URI is bogus
     */
    public ResponseEntity<? extends SecurityUser> getUserResponse(
            final HttpHeaders headers, final String requestName)
            throws URISyntaxException {
        final String url = baseUrl() + "users/" + requestName;
        final HttpEntity<UserImpl> requestEntity =
                new HttpEntity<UserImpl>(headers);
        final ResponseEntity<UserImpl> responseEntity = testRestTemplate
                .exchange(new URI(url), HttpMethod.GET, requestEntity,
                        UserImpl.class);
        return responseEntity;
    }

    /**
     * @param headers the necessary headers
     * @return the User
     * @throws URISyntaxException if the URI is bogus
     */
    public List<UserImpl> getUsers(final HttpHeaders headers)
            throws URISyntaxException {
        final String url = baseUrl() + "users";
        final HttpEntity<ArrayList<UserImpl>> requestEntity =
                new HttpEntity<ArrayList<UserImpl>>(headers);
        final ParameterizedTypeReference<ArrayList<UserImpl>> ref =
                new ParameterizedTypeReference<ArrayList<UserImpl>>() { };
        final ResponseEntity<ArrayList<UserImpl>> responseEntity =
                testRestTemplate
                    .exchange(new URI(url), HttpMethod.GET, requestEntity, ref);
        final List<UserImpl> users = responseEntity.getBody();
        return users;
    }

    /**
     * @return the base URL of the API
     */
    private String baseUrl() {
        return "http://localhost:" + port + "/v1/";
    }

    /**
     * @param url the usl to access
     * @param headers the necessary headers
     * @return the string format of the response body
     * @throws URISyntaxException if the URI is bogus
     */
    private String getString(final String url, final HttpHeaders headers)
            throws URISyntaxException {
        final HttpEntity<String> requestEntity = new HttpEntity<String>(
                headers);
        final ParameterizedTypeReference<String> ref =
                new ParameterizedTypeReference<String>() { };
        final ResponseEntity<String> responseEntity = testRestTemplate
                .exchange(new URI(url), HttpMethod.GET, requestEntity, ref);
        return responseEntity.getBody();
    }
}
