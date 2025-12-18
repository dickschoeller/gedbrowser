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
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.schoellerfamily.gedbrowser.security.model.SecurityUser;
import org.schoellerfamily.gedbrowser.security.model.UserImpl;
import org.schoellerfamily.gedbrowser.security.model.UserRequest;
import org.schoellerfamily.gedbrowser.security.test.Application;
import org.schoellerfamily.gedbrowser.security.test.SecurityTestHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestClientException;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Dick Schoeller
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"management.port=0"})
@Slf4j
@SuppressWarnings("null")
public class UserControllerTest {

    /**
     * Not sure what this is good for.
     */
    @Autowired
    private TestRestTemplate testRestTemplate;

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

    /**
     * Handles user related requests.
     */
    private UserTestHelper userHelper;

    /**
     * Initialize the helpers. Reset user file before.
     */
    @BeforeEach
    public void before() {
        loginHelper = new LoginTestHelper(testRestTemplate, port);
        passwordHelper = new PasswordTestHelper(testRestTemplate, port);
        userHelper = new UserTestHelper(testRestTemplate, port);
        SecurityTestHelper.resetUserFile(gedbrowserHome + "/testUserFile.csv");
    }

    /**
     * Reset user file after.
     */
    @AfterEach
    public void after() {
        SecurityTestHelper.resetUserFile(gedbrowserHome + "/testUserFile.csv");
    }

    /**
     * @throws RestClientException the there is a restful exception
     * @throws URISyntaxException if the URL is bad
     */
    @Test
    public final void testWhoami()
            throws RestClientException, URISyntaxException {
        log.info("Test whomai");
        final HttpHeaders headers =
                loginHelper.buildHeaders(loginHelper.login("guest", "guest"));
        final SecurityUser user = userHelper.whoami(headers);
        final String username = user.getUsername();
        log.info("I am {}", username);
        assertEquals("guest", username, "Mismatched user");
    }

    /**
     * @throws RestClientException the there is a restful exception
     * @throws URISyntaxException if the URL is bad
     */
    @Test
    public final void testWhoami2()
            throws RestClientException, URISyntaxException {
        log.info("Test whomai 2");
        final HttpHeaders headers = loginHelper.buildHeaders(
                loginHelper.login("schoeller@comcast.net", "HAHANOWAY"));
        final SecurityUser user = userHelper.whoami(headers);
        final String username = user.getUsername();
        log.info("I am {}", username);
        assertEquals("schoeller@comcast.net", username, "Mismatched user");
    }

    /**
     * @throws RestClientException the there is a restful exception
     * @throws URISyntaxException if the URL is bad
     */
    @Test
    public final void testWhoamiNotLoggedIn()
            throws RestClientException, URISyntaxException {
        log.info("Test whomai not logged in");
        final ResponseEntity<? extends SecurityUser> response = userHelper
                .whoamiResponse(new HttpHeaders());
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode(), "Should have failed");
    }

    /**
     * @throws RestClientException the there is a restful exception
     * @throws URISyntaxException if the URL is bad
     */
    @Test
    @Disabled("Authentication problem right now")
    public final void testGetUserGuest()
            throws RestClientException, URISyntaxException {
        log.info("Test get user guest");
        final HttpHeaders headers =
                loginHelper.buildHeaders(loginHelper.login("guest", "guest"));
        final SecurityUser user = userHelper.getUser(headers, "guest");
        final String username = user.getUsername();
        log.info("I got {}", username);
        assertEquals("guest", username, "Mismatched user");
    }

    /**
     * @throws RestClientException the there is a restful exception
     * @throws URISyntaxException if the URL is bad
     */
    @Test
    public final void testGetUserGuestNotLoggedIn()
            throws RestClientException, URISyntaxException {
        log.info("Test get user guest, not logged in");
        final HttpHeaders headers = new HttpHeaders();
        final ResponseEntity<? extends SecurityUser> response =
                userHelper.getUserResponse(headers, "guest");
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode(), "");
    }

    /**
     * @throws RestClientException the there is a restful exception
     * @throws URISyntaxException if the URL is bad
     */
    @Test
    public final void testRefresh()
            throws RestClientException, URISyntaxException {
        log.info("Test refresh");
        final HttpHeaders headers =
                loginHelper.buildHeaders(loginHelper.login("guest", "guest"));
        ResponseEntity<String> response = refresh(headers);
        assertEquals(HttpStatus.OK, response.getStatusCode(), "should be OK");
    }

    /**
     * @throws RestClientException the there is a restful exception
     * @throws URISyntaxException if the URL is bad
     * @throws InterruptedException if the sleep is interrupted
     */
    @Test
    public final void testExpiredRefresh() throws RestClientException,
            URISyntaxException, InterruptedException {
        log.info("Test expired refresh");
        final HttpHeaders headers =
                loginHelper.buildHeaders(loginHelper.login("guest", "guest"));
        Thread.sleep(expiresIn * 1000 + 1000);
        ResponseEntity<String> response = refresh(headers);
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode(), "should be ACCEPTED");
    }

    /**
     * @throws RestClientException the there is a restful exception
     * @throws URISyntaxException if the URL is bad
     * @throws UnsupportedEncodingException if it doesn't know the charset
     */
    @Test
    @Disabled("Authentication problem right now")
    public final void testGetUserSchoeller() throws RestClientException,
            URISyntaxException, UnsupportedEncodingException {
        log.info("Test get user schoeller");
        final HttpHeaders headers = loginHelper.buildHeaders(
                loginHelper.login("schoeller@comcast.net", "HAHANOWAY"));
        String requestName =
                URLEncoder.encode("schoeller@comcast.net", "UTF-8");
        final SecurityUser user = userHelper.getUser(headers, requestName);
        final String username = user.getUsername();
        log.info("I got {}", username);
        assertEquals("schoeller@comcast.net", username, "Mismatched user");
    }

    /**
     * @throws RestClientException the there is a restful exception
     * @throws URISyntaxException if the URL is bad
     * @throws UnsupportedEncodingException if it doesn't know the charset
     */
    @Test
    public final void testGetUsers() throws RestClientException,
            URISyntaxException, UnsupportedEncodingException {
        log.info("Test get users");
        final HttpHeaders headers = loginHelper.buildHeaders(
                loginHelper.login("schoeller@comcast.net", "HAHANOWAY"));
        final List<UserImpl> users = userHelper.getUsers(headers);
        log.info("List contains:");
        for (SecurityUser user: users) {
            log.info("   {}", user.getUsername());
        }
        assertEquals(2, users.size(), "Wrong count");
    }

    /**
     * @throws RestClientException the there is a restful exception
     * @throws URISyntaxException if the URL is bad
     * @throws UnsupportedEncodingException if it doesn't know the charset
     */
    @Test
    @Disabled("Figure out later why this test fails")
    public final void testGetUsersNotAdmin() throws RestClientException,
            URISyntaxException, UnsupportedEncodingException {
        log.info("Test get users not admin");
        final HttpHeaders headers =
                loginHelper.buildHeaders(loginHelper.login("guest", "guest"));
        final String usersString = userHelper.getUsersString(headers);
        assertTrue(usersString.contains(
                "\"status\":403,\"error\":\"Forbidden\""), "Expected unauthorized response");
    }

    /**
     * @throws URISyntaxException if the URL is bad
     * @throws RestClientException if there is some REST problem
     */
    @Test
    public void testSignup() throws RestClientException, URISyntaxException {
        log.info("Test signup");
        final String url = baseUrl() + "signup";
        final HttpHeaders headers = buildHeaders();
        final UserRequest userRequest = new UserRequest();
        userRequest.setUsername("newuser");
        userRequest.setPassword("newuser");
        userRequest.setEmail("newuser@nomail.net");
        userRequest.setFirstname("New");
        userRequest.setLastname("User");
        final SecurityUser user = post(url, headers, userRequest);
        assertEquals("newuser", user.getUsername(), "Wrong user found");
    }

    /**
     * @throws URISyntaxException if the URL is bad
     * @throws RestClientException if there is some REST problem
     */
    @Test
    public void testSignupExistingUser()
            throws RestClientException, URISyntaxException {
        log.info("Test signup existing user");
        final String url = baseUrl() + "signup";
        final HttpHeaders headers = buildHeaders();
        final UserRequest userRequest = new UserRequest();
        userRequest.setUsername("neweruser");
        userRequest.setPassword("neweruser");
        userRequest.setEmail("neweruser@nomail.net");
        userRequest.setFirstname("Newer");
        userRequest.setLastname("User");
        post(url, headers, userRequest);
        final String userString =
                postString(url, headers, userRequest);
        assertTrue(userString.contains("Username already exists"), "Expected error string");
    }

    /**
     * @throws RestClientException the there is a restful exception
     * @throws URISyntaxException if the URL is bad
     */
    @Test
    public final void testResetCredentials()
            throws RestClientException, URISyntaxException {
        log.info("Test reset-credentials");
        final HttpHeaders headers1 = loginHelper.buildHeaders(
                loginHelper.login("guest", "guest"));
        assertEquals(HttpStatus.ACCEPTED, resetCredentials(headers1).getStatusCode(), "Should have accepted the reset");
        assertEquals(HttpStatus.OK, loginHelper.logout(headers1).getStatusCode(), "Should have OKed the logout");

        final HttpHeaders headers2 = loginHelper.buildHeaders(loginHelper
                .login("schoeller@comcast.net", "123"));
        assertEquals(HttpStatus.ACCEPTED,
                passwordHelper.changePassword(headers2, "123", "HAHANOWAY")
                        .getStatusCode(), "Should have accepted the password change");
        assertEquals(HttpStatus.OK, loginHelper.logout(headers2).getStatusCode(), "Should have OKed the logout");

        final HttpHeaders headers3 = loginHelper.buildHeaders(
                loginHelper.login("guest", "123"));
        assertEquals(HttpStatus.ACCEPTED, passwordHelper.changePassword(
                headers3, "123", "guest").getStatusCode(), "");
        assertEquals(HttpStatus.OK, loginHelper.logout(headers3).getStatusCode(), "Should have OKed the logout");
    }

    /**
     * @return the base URL of the API
     */
    private String baseUrl() {
        return "http://localhost:" + port + "/v1/";
    }

    /**
     * @return the header block
     */
    private HttpHeaders buildHeaders() {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        return headers;
    }

    /**
     * @param url the target URL
     * @param headers prepared headers
     * @param userRequest the user request object
     * @return the JSON string of the user (or error)
     * @throws RestClientException if there is a problem with the REST call
     * @throws URISyntaxException there is a problem with the URL
     */
    private SecurityUser post(final String url, final HttpHeaders headers,
            final UserRequest userRequest)
                    throws RestClientException, URISyntaxException {
        final HttpEntity<UserRequest> requestEntity =
                new HttpEntity<UserRequest>(userRequest, headers);
        final ParameterizedTypeReference<UserImpl> ref =
                new ParameterizedTypeReference<UserImpl>() { };
        final ResponseEntity<UserImpl> responseEntity = testRestTemplate
                .exchange(new URI(url), HttpMethod.POST, requestEntity, ref);
        return responseEntity.getBody();
    }

    /**
     * @param url the target URL
     * @param headers prepared headers
     * @param userRequest the user request object
     * @return the JSON string of the user (or error)
     * @throws RestClientException if there is a problem with the REST call
     * @throws URISyntaxException there is a problem with the URL
     */
    private String postString(final String url,
            final HttpHeaders headers, final UserRequest userRequest)
                    throws RestClientException, URISyntaxException {
        final HttpEntity<UserRequest> requestEntity =
                new HttpEntity<UserRequest>(userRequest, headers);
        final ParameterizedTypeReference<String> ref =
                new ParameterizedTypeReference<String>() { };
        final ResponseEntity<String> responseEntity = testRestTemplate
                .exchange(new URI(url), HttpMethod.POST, requestEntity, ref);
        return responseEntity.getBody();
    }


    /**
     * @throws RestClientException the there is a restful exception
     * @throws URISyntaxException if the URL is bad
     */
    @Test
    public final void testChangePasswordAndBack()
            throws RestClientException, URISyntaxException {
        log.info("Test reset-credentials");
        final HttpHeaders headers =
                loginHelper.buildHeaders(loginHelper.login("guest", "guest"));
        final ResponseEntity<String> changeResponse =
                passwordHelper.changePassword(headers, "guest", "newpassword");
        assertEquals(HttpStatus.ACCEPTED,
                changeResponse.getStatusCode(), "Unexpected response from changing password");
        final ResponseEntity<String> changeBackResponse = passwordHelper
                .changePassword(headers, "newpassword", "guest");
        assertEquals(HttpStatus.ACCEPTED, changeBackResponse.getStatusCode(), "Unexpected response from changing password back");
    }

    /**
     * @param headers the necessary headers
     * @return the string format of the response body
     * @throws URISyntaxException if the URI is bogus
     */
    private ResponseEntity<String> resetCredentials(final HttpHeaders headers)
            throws URISyntaxException {
        final String url =
                "http://localhost:" + port + "/v1/" + "reset-credentials";
        final HttpEntity<String> requestEntity = new HttpEntity<String>(
                headers);
        final ParameterizedTypeReference<String> ref =
                new ParameterizedTypeReference<String>() { };
        return testRestTemplate
                .exchange(new URI(url), HttpMethod.GET, requestEntity, ref);
    }

    /**
     * @param headers the necessary headers
     * @return the string format of the response body
     * @throws URISyntaxException if the URI is bogus
     */
    private ResponseEntity<String> refresh(final HttpHeaders headers)
            throws URISyntaxException {
        final String url =
                "http://localhost:" + port + "/v1/" + "refresh";
        final HttpEntity<String> requestEntity = new HttpEntity<String>(
                headers);
        final ParameterizedTypeReference<String> ref =
                new ParameterizedTypeReference<String>() { };
        return testRestTemplate
                .exchange(new URI(url), HttpMethod.GET, requestEntity, ref);
    }
}
