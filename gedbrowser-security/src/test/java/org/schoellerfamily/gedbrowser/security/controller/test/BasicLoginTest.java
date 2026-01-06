package org.schoellerfamily.gedbrowser.security.controller.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URISyntaxException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.schoellerfamily.gedbrowser.security.controller.test.LoginTestHelper.LoginResponse;
import org.schoellerfamily.gedbrowser.security.test.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.client.EntityExchangeResult;
import org.springframework.test.web.servlet.client.RestTestClient;
import org.springframework.web.client.RestClientException;

/**
 * @author Dick Schoeller
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(
    classes = Application.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = { "management.port=0" })
@AutoConfigureRestTestClient
public final class BasicLoginTest {
    /** */
    @Autowired
    private RestTestClient restTestClient;

    /**
     * Server port.
     */
    @LocalServerPort
    private int port;

    /**
     * Creates a login session for the test.
     */
    private LoginTestHelper helper;

    @BeforeEach
    void setUp() {
        helper = new LoginTestHelper(restTestClient, port);
    }

    @Test
    void testLoginController() throws RestClientException, URISyntaxException {
        final EntityExchangeResult<LoginResponse> response = helper.login("guest", "guest");
        assertEquals(HttpStatus.OK, response.getStatus(), "Mismatched login status");
        helper.logout(helper.buildHeaders(response));
    }

    @Test
    void testLoginControllerBadUser() throws RestClientException, URISyntaxException {
        assertEquals(HttpStatus.UNAUTHORIZED, helper.login("XYZZY", "guest").getStatus(),
            "Mismatched login status");
    }

    @Test
    void testLoginControllerBadPassword() throws RestClientException, URISyntaxException {
        assertEquals(HttpStatus.UNAUTHORIZED, helper.login("guest", "XYZZY").getStatus(),
            "Mismatched login status");
    }
}
