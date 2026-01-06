package org.schoellerfamily.gedbrowser.security.controller.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.schoellerfamily.gedbrowser.security.test.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.client.EntityExchangeResult;
import org.springframework.test.web.servlet.client.RestTestClient;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Dick Schoeller
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"management.port=0"})
@Slf4j
@AutoConfigureRestTestClient
public class PublicControllerTest {

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
    @Test
    void testFooController() throws URISyntaxException {
        final String url = "http://localhost:" + port + "/v1/foo";
        final EntityExchangeResult<String> result = restTestClient.get()
                .uri(new URI(url))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .returnResult(String.class);
        final String actual = result.getResponseBody();
        log.debug("string: {}", actual);
        assertEquals("{" + "\"foo\":\"bar\"" + "}", actual, "unexpected controller response");
    }
}
