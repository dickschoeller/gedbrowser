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
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.client.EntityExchangeResult;
import org.springframework.test.web.servlet.client.RestTestClient;



/**
 * Contains integration tests for the submission controller.
 *
 * @author Richard Schoeller
 */
@SpringBootTest(classes = { Application.class,
    TestConfiguration.class }, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = { "management.port=0" })
@AutoConfigureRestTestClient
class SubmissionControllerIT implements MenuTestHelper {
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

    @Test
    void testSubmissionControllerS33750() {
        final String url = "http://localhost:" + port + "/gedbrowser/submission?db=gl120368&id=B1";
        final EntityExchangeResult<String> entity = restTestClient.get()
            .uri(URI.create(url))
            .exchange()
            .returnResult(String.class);

        assertThat(entity)
            .returns(HttpStatus.OK.value(), result -> result.getStatus().value())
            .extracting(EntityExchangeResult::getResponseBody)
                .asString().contains(
                    "<title>B1 - gl120368",
                    getMenu("A"));
    }

    @Test
    void testSubmissionControllerBadDataSet() {
        final EntityExchangeResult<String> entity = restTestClient.get()
            .uri(URI
                .create("http://localhost:" + port + "/gedbrowser/submission?db=XYZZY&id=S33750"))
            .exchange()
            .returnResult(String.class);

        assertThat(entity)
            .returns(HttpStatus.NOT_FOUND.value(), result -> result.getStatus().value())
            .extracting(EntityExchangeResult::getResponseBody)
                .asString().contains("Data set not found");
    }

    @Test
    void testSubmissionControllerBadSubmission() {
        final EntityExchangeResult<String> entity = restTestClient.get()
            .uri(URI
                .create("http://localhost:" + port + "/gedbrowser/submission?db=gl120368&id=XYZZY"))
            .exchange()
            .returnResult(String.class);

        assertThat(entity)
            .returns(HttpStatus.NOT_FOUND.value(), result -> result.getStatus().value())
            .extracting(EntityExchangeResult::getResponseBody)
                .asString().contains(
                    "Submission not found",
                    getMenu("A"));
    }
}
