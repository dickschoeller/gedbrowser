package org.schoellerfamily.gedbrowser.controller.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URI;

import org.junit.jupiter.api.Test;
import org.schoellerfamily.gedbrowser.Application;
import org.schoellerfamily.gedbrowser.test.TestConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.client.EntityExchangeResult;
import org.springframework.test.web.servlet.client.RestTestClient;

/**
 * @author Dick Schoeller
 */
@SpringBootTest(classes = { Application.class, TestConfiguration.class },
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"management.port=0"})
@AutoConfigureRestTestClient
public class PlaceIndexControllerTest implements MenuTestHelper {
    /**
     * Not sure what this is good for.
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
    void testControllerGL120368() {
        final String url = "http://localhost:" + port
                + "/gedbrowser/places?db=gl120368";
        final EntityExchangeResult<String> entity = restTestClient.get()
                .uri(URI.create(url))
                .exchange()
                .returnResult(String.class);

        assertThat(entity)
            .returns(HttpStatus.OK, EntityExchangeResult::getStatus)
            .extracting(EntityExchangeResult::getResponseBody)
                .asString().contains(
                    "<title>Places - gl120368</title>",
                    getMenu("A"));
    }

    /** */
    @Test
    void testControllerBadDataSet() {
        final EntityExchangeResult<String> entity = restTestClient.get()
                .uri(URI.create("http://localhost:" + port + "/gedbrowser/places?db=XYZZY"))
                .exchange()
                .returnResult(String.class);

        assertThat(entity)
            .returns(HttpStatus.NOT_FOUND, EntityExchangeResult::getStatus)
            .extracting(EntityExchangeResult::getResponseBody)
                .asString().contains("Data set not found");
    }
}
