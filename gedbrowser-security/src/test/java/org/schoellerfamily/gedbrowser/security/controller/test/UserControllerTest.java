package org.schoellerfamily.gedbrowser.security.controller.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.schoellerfamily.gedbrowser.security.model.SecurityUser;
import org.schoellerfamily.gedbrowser.security.model.UserImpl;
import org.schoellerfamily.gedbrowser.security.model.UserRequest;
import org.schoellerfamily.gedbrowser.security.test.Application;
import org.schoellerfamily.gedbrowser.security.test.SecurityTestHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestClientException;

/**
 * @author Dick Schoeller
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"management.port=0"})
public class UserControllerTest {
    /** Logger. */
    private final transient Log logger = LogFactory.getLog(getClass());

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
    @Value("${gedbrowser.home:/var/lib/gedbrowser}")
    private String gedbrowserHome;

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
    @Before
    public void before() {
        loginHelper = new LoginTestHelper(testRestTemplate, port);
        passwordHelper = new PasswordTestHelper(testRestTemplate, port);
        userHelper = new UserTestHelper(testRestTemplate, port);
        SecurityTestHelper.resetUserFile(gedbrowserHome + "/testUserFile.csv");
    }

    /**
     * Reset user file after.
     */
    @After
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
        logger.info("Test whomai");
        final HttpHeaders headers =
                loginHelper.buildHeaders(loginHelper.login("guest", "guest"));
        final SecurityUser user = userHelper.whoami(headers);
        final String username = user.getUsername();
        logger.info("I am " + username);
        assertEquals("Mismatched user", "guest", username);
    }

    /**
     * @throws RestClientException the there is a restful exception
     * @throws URISyntaxException if the URL is bad
     */
    @Test
    public final void testWhoami2()
            throws RestClientException, URISyntaxException {
        logger.info("Test whomai 2");
        final HttpHeaders headers = loginHelper.buildHeaders(
                loginHelper.login("schoeller@comcast.net", "HAHANOWAY"));
        final SecurityUser user = userHelper.whoami(headers);
        final String username = user.getUsername();
        logger.info("I am " + username);
        assertEquals("Mismatched user", "schoeller@comcast.net", username);
    }

    /**
     * @throws RestClientException the there is a restful exception
     * @throws URISyntaxException if the URL is bad
     */
    @Test
    public final void testWhoamiNotLoggedIn()
            throws RestClientException, URISyntaxException {
        logger.info("Test whomai not logged in");
        final ResponseEntity<? extends SecurityUser> response = userHelper
                .whoamiResponse(new HttpHeaders());
        assertEquals("Should have failed", HttpStatus.FORBIDDEN,
                response.getStatusCode());
    }

    /**
     * @throws RestClientException the there is a restful exception
     * @throws URISyntaxException if the URL is bad
     */
    @Test
    public final void testGetUserGuest()
            throws RestClientException, URISyntaxException {
        logger.info("Test get user guest");
        final HttpHeaders headers =
                loginHelper.buildHeaders(loginHelper.login("guest", "guest"));
        final SecurityUser user = userHelper.getUser(headers, "guest");
        final String username = user.getUsername();
        logger.info("I got " + username);
        assertEquals("Mismatched user", "guest", username);
    }

    /**
     * @throws RestClientException the there is a restful exception
     * @throws URISyntaxException if the URL is bad
     */
    @Test
    public final void testGetUserGuestNotLoggedIn()
            throws RestClientException, URISyntaxException {
        logger.info("Test get user guest, not logged in");
        final HttpHeaders headers = new HttpHeaders();
        final ResponseEntity<? extends SecurityUser> response =
                userHelper.getUserResponse(headers, "guest");
        assertEquals("", HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    /**
     * @throws RestClientException the there is a restful exception
     * @throws URISyntaxException if the URL is bad
     */
    @Test
    public final void testRefresh()
            throws RestClientException, URISyntaxException {
        logger.info("Test refresh");
        final HttpHeaders headers =
                loginHelper.buildHeaders(loginHelper.login("guest", "guest"));
        ResponseEntity<String> response = refresh(headers);
        assertEquals("should be OK", HttpStatus.OK, response.getStatusCode());
    }

    /**
     * @throws RestClientException the there is a restful exception
     * @throws URISyntaxException if the URL is bad
     * @throws InterruptedException if the sleep is interrupted
     */
    @Test
    public final void testExpiredRefresh() throws RestClientException,
            URISyntaxException, InterruptedException {
        logger.info("Test expired refresh");
        final HttpHeaders headers =
                loginHelper.buildHeaders(loginHelper.login("guest", "guest"));
        final int threeSeconds = 10000;
        Thread.sleep(threeSeconds);
        ResponseEntity<String> response = refresh(headers);
        assertEquals("should be ACCEPTED", HttpStatus.ACCEPTED,
                response.getStatusCode());
    }

    /**
     * @throws RestClientException the there is a restful exception
     * @throws URISyntaxException if the URL is bad
     * @throws UnsupportedEncodingException if it doesn't know the charset
     */
    @Test
    public final void testGetUserSchoeller() throws RestClientException,
            URISyntaxException, UnsupportedEncodingException {
        logger.info("Test get user schoeller");
        final HttpHeaders headers =
                loginHelper.buildHeaders(loginHelper.login("guest", "guest"));
        String requestName =
                URLEncoder.encode("schoeller@comcast.net", "UTF-8");
        final SecurityUser user = userHelper.getUser(headers, requestName);
        final String username = user.getUsername();
        logger.info("I got " + username);
        assertEquals("Mismatched user", "schoeller@comcast.net", username);
    }

    /**
     * @throws RestClientException the there is a restful exception
     * @throws URISyntaxException if the URL is bad
     * @throws UnsupportedEncodingException if it doesn't know the charset
     */
    @Test
    public final void testGetUsers() throws RestClientException,
            URISyntaxException, UnsupportedEncodingException {
        logger.info("Test get users");
        final HttpHeaders headers = loginHelper.buildHeaders(
                loginHelper.login("schoeller@comcast.net", "HAHANOWAY"));
        final List<UserImpl> users = userHelper.getUsers(headers);
        logger.info("List contains:");
        for (SecurityUser user: users) {
            logger.info("   " + user.getUsername());
        }
        assertEquals("Wrong count", 2, users.size());
    }

    /**
     * @throws RestClientException the there is a restful exception
     * @throws URISyntaxException if the URL is bad
     * @throws UnsupportedEncodingException if it doesn't know the charset
     */
    @Test
    public final void testGetUsersNotAdmin() throws RestClientException,
            URISyntaxException, UnsupportedEncodingException {
        logger.info("Test get users not admin");
        final HttpHeaders headers =
                loginHelper.buildHeaders(loginHelper.login("guest", "guest"));
        final String usersString = userHelper.getUsersString(headers);
        assertTrue("Expected unauthorized response",
                usersString.contains(
                        "\"status\":403,\"error\":\"Forbidden\""));
    }

    /**
     * @throws URISyntaxException if the URL is bad
     * @throws RestClientException if there is some REST problem
     */
    @Test
    public void testSignup() throws RestClientException, URISyntaxException {
        logger.info("Test signup");
        final String url = baseUrl() + "signup";
        final HttpHeaders headers = buildHeaders();
        final UserRequest userRequest = new UserRequest();
        userRequest.setUsername("newuser");
        userRequest.setPassword("newuser");
        userRequest.setEmail("newuser@nomail.net");
        userRequest.setFirstname("New");
        userRequest.setLastname("User");
        final SecurityUser user = post(url, headers, userRequest);
        assertEquals("Wrong user found", "newuser", user.getUsername());
    }

    /**
     * @throws URISyntaxException if the URL is bad
     * @throws RestClientException if there is some REST problem
     */
    @Test
    public void testSignupExistingUser()
            throws RestClientException, URISyntaxException {
        logger.info("Test signup existing user");
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
        assertTrue("Expected error string",
                userString.contains("Username already exists"));
    }

    /**
     * @throws RestClientException the there is a restful exception
     * @throws URISyntaxException if the URL is bad
     */
    @Test
    public final void testResetCredentials()
            throws RestClientException, URISyntaxException {
        logger.info("Test reset-credentials");
        final HttpHeaders headers1 = loginHelper.buildHeaders(
                loginHelper.login("guest", "guest"));
        assertEquals("Should have accepted the reset", HttpStatus.ACCEPTED,
                resetCredentials(headers1).getStatusCode());
        assertEquals("Should have OKed the logout",
                HttpStatus.OK, loginHelper.logout(headers1).getStatusCode());

        final HttpHeaders headers2 = loginHelper.buildHeaders(loginHelper
                .login("schoeller@comcast.net", "123"));
        assertEquals("Should have accepted the password change",
                HttpStatus.ACCEPTED,
                passwordHelper.changePassword(headers2, "123", "HAHANOWAY")
                        .getStatusCode());
        assertEquals("Should have OKed the logout", HttpStatus.OK,
                loginHelper.logout(headers2).getStatusCode());

        final HttpHeaders headers3 = loginHelper.buildHeaders(
                loginHelper.login("guest", "123"));
        assertEquals("", HttpStatus.ACCEPTED, passwordHelper.changePassword(
                headers3, "123", "guest").getStatusCode());
        assertEquals("Should have OKed the logout",
                HttpStatus.OK, loginHelper.logout(headers3).getStatusCode());
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
        final List<MediaType> accepts = new ArrayList<>();
        accepts.add(MediaType.APPLICATION_JSON);
        headers.setAccept(accepts);
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
        logger.info("Test reset-credentials");
        final HttpHeaders headers =
                loginHelper.buildHeaders(loginHelper.login("guest", "guest"));
        final ResponseEntity<String> changeResponse =
                passwordHelper.changePassword(headers, "guest", "newpassword");
        assertEquals("Unexpected response from changing password",
                HttpStatus.ACCEPTED,
                changeResponse.getStatusCode());
        final ResponseEntity<String> changeBackResponse = passwordHelper
                .changePassword(headers, "newpassword", "guest");
        assertEquals("Unexpected response from changing password back",
                HttpStatus.ACCEPTED, changeBackResponse.getStatusCode());
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
