package org.schoellerfamily.gedbrowser.api.controller.test;

import java.net.URI;
import java.util.List;

import org.schoellerfamily.gedbrowser.api.datamodel.ApiAttribute;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiPerson;
import org.schoellerfamily.gedbrowser.security.model.UserTokenState;
import org.schoellerfamily.gedbrowser.security.model.UserTokenStateImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.client.EntityExchangeResult;
import org.springframework.test.web.servlet.client.RestTestClient;

/**
 * @author Dick Schoeller
 */
public final class ControllerTestHelper {
    /** */
    private final RestTestClient restTestClient;
    /** */
    private final String baseUrl;
    /** */
    private final String personsUrl;
    /** */
    private final String familiesUrl;
    /** */
    private final HttpHeaders headers;
    /** */
    private final ApiPerson.ApiPersonBuilder<?, ?> builder;

    /**
     * Creates a new ControllerTestHelper.
     *
     * @param port the port
     * @param restTestClient the rest test client
     */
    public ControllerTestHelper(final int port, final RestTestClient restTestClient) {
        this.restTestClient = restTestClient;
        baseUrl = "http://localhost:" + port + "/gedbrowserng/v1/dbs/gl120368/";
        personsUrl = baseUrl + "persons";
        familiesUrl = baseUrl + "families";
        headers = adminLogin(port, restTestClient);
        headers.setContentType(MediaType.APPLICATION_JSON);
        final ApiAttribute attribute = ApiAttribute.builder()
            .type("attribute")
            .string("Death")
            .build();
        builder = ApiPerson.builder().attribute(attribute);
    }

    private HttpHeaders adminLogin(final int port, final RestTestClient client) {
        final String url = "http://localhost:" + port + "/gedbrowserng/v1/login";
        final String loginString = "username=schoeller@comcast.net&password=HAHANOWAY";
        final EntityExchangeResult<UserTokenStateImpl> response = client.post()
            .uri(URI.create(url))
            .body(loginString)
            .accept(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
            .exchange()
            .returnResult(UserTokenStateImpl.class);
        final UserTokenState body = response.getResponseBody();
        final HttpHeaders customHeaders = new HttpHeaders();
        customHeaders.setContentType(MediaType.APPLICATION_JSON);
        if (body != null && body.getAccessToken() != null) {
            customHeaders.set(HttpHeaders.AUTHORIZATION, "Bearer " + body.getAccessToken());
        }
        customHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));
        return customHeaders;
    }

    /**
     * Login the default user.
     *
     * @param port the port
     * @param client the rest test client
     * @return the http headers with the authorization token
     */
    public HttpHeaders userLogin(final int port, final RestTestClient client) {
        final String url = "http://localhost:" + port + "/gedbrowserng/v1/login";
        final String loginString = "username=guest&password=guest";
        final EntityExchangeResult<UserTokenStateImpl> response = client.post()
            .uri(URI.create(url))
            .body(loginString)
            .accept(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
            .exchange()
            .returnResult(UserTokenStateImpl.class);
        final UserTokenState body = response.getResponseBody();
        final HttpHeaders customHeaders = new HttpHeaders();
        customHeaders.setContentType(MediaType.APPLICATION_JSON);
        if (body != null && body.getAccessToken() != null) {
            customHeaders.set(HttpHeaders.AUTHORIZATION, "Bearer " + body.getAccessToken());
        }
        customHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));
        return customHeaders;
    }

    /**
     * Gets the persons url.
     *
     * @return the persons url
     */
    public String getPersonsUrl() {
        return personsUrl;
    }

    /**
     * Gets the families url.
     *
     * @return the families url
     */
    public String getFamiliesUrl() {
        return familiesUrl;
    }

    /**
     * Gets the headers.
     *
     * @return the headers
     */
    public HttpHeaders getHeaders() {
        return headers;
    }

    /**
     * Gets the person builder.
     *
     * @return the person builder
     */
    public ApiPerson.ApiPersonBuilder<?, ?> getPersonBuilder() {
        return builder;
    }

    /**
     * Creates the person.
     *
     * @return the resulting api person
     */
    public ApiPerson createPerson() {
        final ApiPerson person = buildPerson();
        final EntityExchangeResult<ApiPerson> personEntity = restTestClient.post()
            .uri(URI.create(personsUrl))
            .headers(h -> h.addAll(headers))
            .body(person)
            .exchange()
            .returnResult(ApiPerson.class);
        return personEntity.getResponseBody();
    }

    /**
     * Builds the person.
     *
     * @return the resulting api person
     */
    public ApiPerson buildPerson() {
        return builder.build();
    }

    /**
     * Returns the person.
     *
     * @param person the person
     * @return the person
     */
    public ApiPerson getPerson(final ApiPerson person) {
        final EntityExchangeResult<ApiPerson> entity = restTestClient.get()
            .uri(URI.create(personsUrl + "/" + person.getString()))
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .returnResult(ApiPerson.class);
        return entity.getResponseBody();
    }

    /**
     * Check that the input string contains all of the expected strings.
     *
     * @param input the input string
     * @param expected the expected substrings
     * @return {@code true} if all non-null expected substrings are found in the input
     *         string; {@code false} if {@code input} is {@code null}, {@code expected}
     *         is {@code null}, or any non-null expected substring is not found. Null
     *         elements in {@code expected} are silently ignored.
     */
    public static boolean containsAll(final String input, final String... expected) {
        if (input == null) {
            return false;
        }
        if (expected == null) {
            return false;
        }
        for (final String s : expected) {
            if (s == null) {
                continue;
            }
            if (!input.contains(s)) {
                return false;
            }
        }
        return true;
    }

}
