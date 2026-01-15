package org.schoellerfamily.gedbrowser.security.controller.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.schoellerfamily.gedbrowser.security.model.SecurityUser;
import org.schoellerfamily.gedbrowser.security.model.UserImpl;
import org.schoellerfamily.gedbrowser.security.model.UserRequest;
import org.schoellerfamily.gedbrowser.security.test.Application;
import org.schoellerfamily.gedbrowser.security.test.SecurityTestHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.client.EntityExchangeResult;
import org.springframework.test.web.servlet.client.RestTestClient;
import org.springframework.web.client.RestClientException;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Dick Schoeller
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(
    classes = Application.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = { "management.port=0" })
@Slf4j
@AutoConfigureRestTestClient
public final class UserControllerTest {
    /** */
    private static final int MILLIS_PER_SECOND = 1000;

    /**
     * RestTestClient injected by Spring's test support.
     */
    @Autowired
    private RestTestClient restTestClient;

    /**
     * Server port.
     */
    @LocalServerPort
    private int port;

    /** */
    @Value("${gedbrowser.home:#{ systemProperties['user.dir'] }/src/test/resources}")
    private String gedbrowserHome;

    /** */
    @Value("${jwt.expires_in:600}")
    private int expiresIn;

    /**
     * Handles login/logout for the tests.
     */
    private LoginTestHelper loginHelper;

    /**
     * Handles password changes for the test.
     */
    private PasswordTestHelper passwordHelper;

    /** */
    private HttpHeaders headers;
    /** */
    private HttpHeaders headers2;
    /** */
    private HttpHeaders headers3;

    /**
     * Handles user related requests.
     */
    private UserTestHelper userHelper;

    @BeforeEach
    void setUp() {
        loginHelper = new LoginTestHelper(restTestClient, port);
        passwordHelper = new PasswordTestHelper(restTestClient, port);
        userHelper = new UserTestHelper(restTestClient, port);
        SecurityTestHelper.resetUserFile(gedbrowserHome + "/testUserFile.csv");
        headers = new HttpHeaders();
        headers2 = new HttpHeaders();
        headers3 = new HttpHeaders();
    }

    @AfterEach
    void tearDown() throws URISyntaxException, RestClientException {
        loginHelper.logout(headers);
        loginHelper.logout(headers2);
        loginHelper.logout(headers3);
        SecurityTestHelper.resetUserFile(gedbrowserHome + "/testUserFile.csv");
    }

    @Test
    void testWhoami() throws RestClientException, URISyntaxException {
        log.info("Test whomai");
        headers = loginHelper.buildHeaders(loginHelper.login("guest", "guest"));
        final SecurityUser user = userHelper.whoami(headers);
        final String username = user.getUsername();
        log.info("I am {}", username);
        assertEquals("guest", username, "Mismatched user");
    }

    @Test
    void testWhoami2() throws RestClientException, URISyntaxException {
        log.info("Test whomai 2");
        headers = loginHelper
            .buildHeaders(loginHelper.login("schoeller@comcast.net", "HAHANOWAY"));
        final SecurityUser user = userHelper.whoami(headers);
        final String username = user.getUsername();
        log.info("I am {}", username);
        assertEquals("schoeller@comcast.net", username, "Mismatched user");
    }

    @Test
    void testWhoamiNotLoggedIn() throws RestClientException, URISyntaxException {
        log.info("Test whomai not logged in");
        headers = new HttpHeaders();
        final EntityExchangeResult<UserImpl> response = userHelper
            .whoamiResponse(new HttpHeaders());
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatus(), "Should have failed");
    }

    @Test
    void testGetUserGuest() throws RestClientException, URISyntaxException {
        log.info("Test get user guest");
        headers = loginHelper
            .buildHeaders(loginHelper.login("schoeller@comcast.net", "HAHANOWAY"));
        final SecurityUser user = userHelper.getUser(headers, "guest");
        final String username = user.getUsername();
        log.info("I got {}", username);
        assertEquals("guest", username, "Mismatched user");
    }

    @Test
    void testGetUserGuestNotLoggedIn() throws RestClientException, URISyntaxException {
        log.info("Test get user guest, not logged in");
        headers = new HttpHeaders();
        final EntityExchangeResult<UserImpl> response = userHelper.getUserResponse(headers,
            "guest");
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatus(), "");
    }

    /**
     * @throws RestClientException the there is a restful exception
     * @throws URISyntaxException  if the URL is bad
     */
    @Test
    void testRefresh() throws RestClientException, URISyntaxException {
        log.info("Test refresh");
        headers = loginHelper.buildHeaders(loginHelper.login("guest", "guest"));
        final EntityExchangeResult<String> response = refresh(headers);
        assertEquals(HttpStatus.OK, response.getStatus(), "should be OK");
    }

    @Test
    void testExpiredRefresh() throws RestClientException, URISyntaxException, InterruptedException {
        log.info("Test expired refresh");
        headers = loginHelper.buildHeaders(loginHelper.login("guest", "guest"));
        Thread.sleep((expiresIn + 1) * MILLIS_PER_SECOND);
        final EntityExchangeResult<String> response = refresh(headers);
        assertEquals(HttpStatus.ACCEPTED, response.getStatus(), "should be ACCEPTED");
    }

    @Test
    void testGetUserSchoeller()
        throws RestClientException, URISyntaxException, UnsupportedEncodingException {
        log.info("Test get user schoeller");
        headers = loginHelper
            .buildHeaders(loginHelper.login("schoeller@comcast.net", "HAHANOWAY"));
        String requestName = URLEncoder.encode("schoeller@comcast.net", "UTF-8");
        final SecurityUser user = userHelper.getUser(headers, requestName);
        final String username = user.getUsername();
        log.info("I got {}", username);
        assertEquals("schoeller@comcast.net", username, "Mismatched user");
    }

    @Test
    void testGetUsers()
        throws RestClientException, URISyntaxException, UnsupportedEncodingException {
        log.info("Test get users");
        headers = loginHelper
            .buildHeaders(loginHelper.login("schoeller@comcast.net", "HAHANOWAY"));
        final List<UserImpl> users = userHelper.getUsers(headers);
        log.info("List contains:");
        for (SecurityUser user : users) {
            log.info("   {}", user.getUsername());
        }
        assertEquals(2, users.size(), "Wrong count");
    }

    @Test
    void testGetUsersNotAdmin()
        throws RestClientException, URISyntaxException, UnsupportedEncodingException {
        log.info("Test get users not admin");
        headers = loginHelper.buildHeaders(loginHelper.login("guest", "guest"));
        final EntityExchangeResult<List<UserImpl>> response = userHelper.getUsersResponse(headers);
        assertEquals(HttpStatus.FORBIDDEN, response.getStatus(),
            "Expected unauthorized response");
    }

    @Test
    void testSignup() throws RestClientException, URISyntaxException {
        log.info("Test signup");
        final String url = baseUrl() + "signup";
        headers = buildHeaders();
        final UserRequest userRequest = new UserRequest();
        userRequest.setUsername("newuser");
        userRequest.setPassword("newuser");
        userRequest.setEmail("newuser@nomail.net");
        userRequest.setFirstname("New");
        userRequest.setLastname("User");
        final SecurityUser user = post(url, headers, userRequest);
        assertEquals("newuser", user.getUsername(), "Wrong user found");
    }

    @Test
    void testSignupExistingUser() throws RestClientException, URISyntaxException {
        log.info("Test signup existing user");
        final String url = baseUrl() + "signup";
        headers = buildHeaders();
        final UserRequest userRequest = new UserRequest();
        userRequest.setUsername("neweruser");
        userRequest.setPassword("neweruser");
        userRequest.setEmail("neweruser@nomail.net");
        userRequest.setFirstname("Newer");
        userRequest.setLastname("User");
        post(url, headers, userRequest);
        final String userString = postString(url, headers, userRequest);
        assertTrue(userString.contains("Username already exists"), "Expected error string");
    }

    @Test
    void testResetCredentials() throws RestClientException, URISyntaxException {
        log.info("Test reset-credentials");
        headers = loginHelper.buildHeaders(loginHelper.login("guest", "guest"));
        assertEquals(HttpStatus.ACCEPTED, resetCredentials(headers).getStatus(),
            "Should have accepted the reset");
        assertEquals(HttpStatus.OK, loginHelper.logout(headers).getStatus(),
            "Should have OKed the logout");

        headers2 = loginHelper
            .buildHeaders(loginHelper.login("schoeller@comcast.net", "123"));
        assertEquals(HttpStatus.ACCEPTED,
            passwordHelper.changePassword(headers2, "123", "HAHANOWAY").getStatus(),
            "Should have accepted the password change");
        assertEquals(HttpStatus.OK, loginHelper.logout(headers2).getStatus(),
            "Should have OKed the logout");

        headers3 = loginHelper.buildHeaders(loginHelper.login("guest", "123"));
        assertEquals(HttpStatus.ACCEPTED,
            passwordHelper.changePassword(headers3, "123", "guest").getStatus(), "");
        assertEquals(HttpStatus.OK, loginHelper.logout(headers3).getStatus(),
            "Should have OKed the logout");
    }

    @Test
    void testChangePasswordAndBack() throws RestClientException, URISyntaxException {
        log.info("Test reset-credentials");
        headers = loginHelper.buildHeaders(loginHelper.login("guest", "guest"));
        final EntityExchangeResult<String> changeResponse = passwordHelper.changePassword(headers,
            "guest", "newpassword");
        assertEquals(HttpStatus.ACCEPTED, changeResponse.getStatus(),
            "Unexpected response from changing password");
        final EntityExchangeResult<String> changeBackResponse = passwordHelper
            .changePassword(headers, "newpassword", "guest");
        assertEquals(HttpStatus.ACCEPTED, changeBackResponse.getStatus(),
            "Unexpected response from changing password back");
    }

    private String baseUrl() {
        return "http://localhost:" + port + "/v1/";
    }

    private HttpHeaders buildHeaders() {
        final HttpHeaders h = new HttpHeaders();
        h.setContentType(MediaType.APPLICATION_JSON);
        h.setAccept(List.of(MediaType.APPLICATION_JSON));
        return h;
    }

    private SecurityUser post(final String url, final HttpHeaders heads,
        final UserRequest userRequest) throws RestClientException, URISyntaxException {
        final EntityExchangeResult<UserImpl> res = restTestClient.post()
            .uri(new URI(url))
            .headers(h -> h.addAll(h))
            .body(userRequest)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .returnResult(UserImpl.class);
        return res.getResponseBody();
    }

    private String postString(final String url, final HttpHeaders heads,
        final UserRequest userRequest) throws RestClientException, URISyntaxException {
        final EntityExchangeResult<String> res = restTestClient.post()
            .uri(new URI(url))
            .headers(h -> h.addAll(heads))
            .body(userRequest)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .returnResult(String.class);
        final String body = res.getResponseBody();
        return (body == null) ? "" : body;
    }

    @Test
    void testChangePasswordAndBack1() throws RestClientException, URISyntaxException {
        log.info("Test reset-credentials");
        headers = loginHelper.buildHeaders(loginHelper.login("guest", "guest"));
        final EntityExchangeResult<String> changeResponse = passwordHelper.changePassword(headers,
            "guest", "newpassword");
        assertEquals(HttpStatus.ACCEPTED, changeResponse.getStatus(),
            "Unexpected response from changing password");
        final EntityExchangeResult<String> changeBackResponse = passwordHelper
            .changePassword(headers, "newpassword", "guest");
        assertEquals(HttpStatus.ACCEPTED, changeBackResponse.getStatus(),
            "Unexpected response from changing password back");
    }

    private EntityExchangeResult<String> resetCredentials(final HttpHeaders heads)
        throws URISyntaxException {
        final String url = "http://localhost:" + port + "/v1/" + "reset-credentials";
        return restTestClient.get()
            .uri(new URI(url))
            .headers(h -> h.addAll(heads))
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .returnResult(String.class);
    }

    private EntityExchangeResult<String> refresh(final HttpHeaders heads)
        throws URISyntaxException {
        final String url = "http://localhost:" + port + "/v1/" + "refresh";
        return restTestClient.get()
            .uri(new URI(url))
            .headers(h -> h.addAll(heads))
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .returnResult(String.class);
    }
}
