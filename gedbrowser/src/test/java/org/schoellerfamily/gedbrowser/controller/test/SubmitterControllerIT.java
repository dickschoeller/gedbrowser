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
 * @author Dick Schoeller
 */
@SpringBootTest(classes = { Application.class,
    TestConfiguration.class }, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = { "management.port=0" })
@AutoConfigureRestTestClient
class SubmitterControllerIT implements MenuTestHelper {
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
    void testSubmitterControllerU1() {
        final String url = "http://localhost:" + port + "/gedbrowser/submitter?db=gl120368&id=U1";
        final EntityExchangeResult<String> entity = restTestClient.get()
            .uri(URI.create(url))
            .exchange()
            .returnResult(String.class);

        assertThat(entity)
            .returns(HttpStatus.OK.value(), result -> result.getStatus().value())
            .extracting(EntityExchangeResult::getResponseBody)
                .asString().contains(
                    "<title>Phil Williams - U1 - gl120368</title>",
                    "Name:</span> Phil Williams",
                    getMenu("A"));
    }

    /** */
    @Test
    void testSubmitterControllerU2() {
        final String url = "http://localhost:" + port + "/gedbrowser/submitter?db=gl120368&id=U2";
        final EntityExchangeResult<String> entity = restTestClient.get()
            .uri(URI.create(url))
            .exchange()
            .returnResult(String.class);

        assertThat(entity)
            .returns(HttpStatus.OK.value(), result -> result.getStatus().value())
            .extracting(EntityExchangeResult::getResponseBody)
                .asString().contains(
                    "<title>Arthur PUNCHARD - U2 - gl120368</title>",
                    "Name:</span> Arthur PUNCHARD",
                    "Changed:</span> 24 MAR 2007",
                    getMenu("A"));
    }

    /** */
    @Test
    void testSubmitterControllerU4() {
        final String url = "http://localhost:" + port + "/gedbrowser/submitter?db=gl120368&id=U4";
        final EntityExchangeResult<String> entity = restTestClient.get()
            .uri(URI.create(url))
            .exchange()
            .returnResult(String.class);

        assertThat(entity)
            .returns(HttpStatus.OK.value(), result -> result.getStatus().value())
            .extracting(EntityExchangeResult::getResponseBody)
                .asString().contains(
                    "<title>Created by FamilySearch (TM) Internet"
                        + " Genealogy Service - U4 - gl120368</title>",
                    "Name:</span> Created by FamilySearch",
                    "Address:</span> 50 East North Temple Street<br/>",
                    "Salt Lake City, Utah 84150",
                    getMenu("A"));
    }

    /** */
    @Test
    void testSubmitterControllerBadDataSet() {
        final EntityExchangeResult<String> entity = restTestClient.get()
            .uri(URI.create("http://localhost:" + port + "/gedbrowser/submitter?db=XYZZY&id=U4"))
            .exchange()
            .returnResult(String.class);

        assertThat(entity)
            .returns(HttpStatus.NOT_FOUND.value(), result -> result.getStatus().value())
            .extracting(EntityExchangeResult::getResponseBody)
                .asString().contains("Data set not found");
    }

    /** */
    @Test
    void testSubmitterControllerBadSubmitter() {
        final EntityExchangeResult<String> entity = restTestClient.get()
            .uri(URI
                .create("http://localhost:" + port + "/gedbrowser/submitter?db=gl120368&id=U99999"))
            .exchange()
            .returnResult(String.class);

        assertThat(entity)
            .returns(HttpStatus.NOT_FOUND.value(), result -> result.getStatus().value())
            .extracting(EntityExchangeResult::getResponseBody)
                .asString().contains(
                    "Submitter not found",
                    getMenu("A"));
    }
}
