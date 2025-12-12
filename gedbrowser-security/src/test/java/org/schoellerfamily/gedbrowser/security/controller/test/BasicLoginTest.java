package org.schoellerfamily.gedbrowser.security.controller.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URISyntaxException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.schoellerfamily.gedbrowser.security.controller.test.LoginTestHelper.LoginResponse;
import org.schoellerfamily.gedbrowser.security.test.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestClientException;

/**
 * @author Dick Schoeller
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"management.port=0"})
public class BasicLoginTest {
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

    /**
     * Creates a login session for the test.
     */
    private LoginTestHelper helper;

    /**
     * Initialize the login helper.
     */
    @BeforeEach
    public void before() {
        helper = new LoginTestHelper(testRestTemplate, port);
    }

    /**
     * @throws RestClientException the there is a restful exception
     * @throws URISyntaxException if the URL is bad
     */
    @Test
    public final void testLoginController()
            throws RestClientException, URISyntaxException {
        final ResponseEntity<LoginResponse> response =
                helper.login("guest", "guest");
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Mismatched login status");
        helper.logout(helper.buildHeaders(response));
    }

    /**
     * @throws RestClientException the there is a restful exception
     * @throws URISyntaxException if the URL is bad
     */
    @Test
    public final void testLoginControllerBadUser()
            throws RestClientException, URISyntaxException {
        assertEquals(HttpStatus.UNAUTHORIZED,
                helper.login("XYZZY", "guest").getStatusCode(), "Mismatched login status");
    }

    /**
     * @throws RestClientException the there is a restful exception
     * @throws URISyntaxException if the URL is bad
     */
    @Test
    public final void testLoginControllerBadPassword()
            throws RestClientException, URISyntaxException {
        assertEquals(HttpStatus.UNAUTHORIZED,
                helper.login("guest", "XYZZY").getStatusCode(), "Mismatched login status");
    }
}