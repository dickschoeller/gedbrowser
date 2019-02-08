package org.schoellerfamily.gedbrowser.security.controller.test;

import static org.junit.Assert.assertEquals;

import java.net.URISyntaxException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.schoellerfamily.gedbrowser.security.controller.test.LoginTestHelper.LoginResponse;
import org.schoellerfamily.gedbrowser.security.test.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
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
    @Before
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
        assertEquals("Mismatched login status", HttpStatus.OK,
                response.getStatusCode());
        helper.logout(helper.buildHeaders(response));
    }

    /**
     * @throws RestClientException the there is a restful exception
     * @throws URISyntaxException if the URL is bad
     */
    @Test
    public final void testLoginControllerBadUser()
            throws RestClientException, URISyntaxException {
        assertEquals("Mismatched login status", HttpStatus.UNAUTHORIZED,
                helper.login("XYZZY", "guest").getStatusCode());
    }

    /**
     * @throws RestClientException the there is a restful exception
     * @throws URISyntaxException if the URL is bad
     */
    @Test
    public final void testLoginControllerBadPassword()
            throws RestClientException, URISyntaxException {
        assertEquals("Mismatched login status", HttpStatus.UNAUTHORIZED,
                helper.login("guest", "XYZZY").getStatusCode());
    }
}
