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
@SpringBootTest(classes = { Application.class, TestConfiguration.class },
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"management.port=0"})
@AutoConfigureRestTestClient
class HeadControllerIT implements MenuTestHelper {
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
    void testHeadController() {
        final String url = "http://localhost:" + port
                + "/gedbrowser/head?db=gl120368";
        final EntityExchangeResult<String> entity = restTestClient.get()
                .uri(URI.create(url))
                .exchange()
                .returnResult(String.class);

        assertThat(entity)
            .returns(HttpStatus.OK.value(), result -> result.getStatus().value())
            .extracting(EntityExchangeResult::getResponseBody)
                .asString().contains(
                    "<title>Header - gl120368</title>",
                    "File:</span> C:\\Users\\Phil\\Documents\\W0803.GED",
                    "GEDCOM:</span> 5.5, LINEAGE-LINKED",
                    "Character Set:</span> ANSI",
                    "Destination:</span> FTM",
                    "Submitter:</span> <a class=\"name\""
                    + " href=\"submitter?db=gl120368&amp;id=U1\">Phil Williams"
                    + " [U1]</a>",
                    getMenu("A"));
    }

    /** */
    @Test
    void testHeadControllerSchoeller() {
        final String url = "http://localhost:" + port
                + "/gedbrowser/head?db=mini-schoeller";
        final EntityExchangeResult<String> entity = restTestClient.get()
                .uri(URI.create(url))
                .exchange()
                .returnResult(String.class);

        assertThat(entity)
            .returns(HttpStatus.OK.value(), result -> result.getStatus().value())
            .extracting(EntityExchangeResult::getResponseBody)
                .asString().contains(
                    "<title>Header - mini-schoeller</title>",
                    "Submitter:</span> <a class=\"name\""
                    + " href=\"submitter?db=mini-schoeller&amp;"
                    + "id=SUB1\">Richard Schoeller [SUB1]</a>",
                    "GEDCOM:</span> 5.5.1, LINEAGE-LINKED",
                    "Destination:</span> GED55",
                    "Date:</span> 16 FEB 2001 22:04</li>",
                    "Character Set:</span> UTF-8",
                    getMenu("mini-schoeller", "A"));
    }

    /** */
    @Test
    void testHeadControllerBadDataSet() {
        final EntityExchangeResult<String> entity = restTestClient.get()
                .uri(URI.create("http://localhost:" + port + "/gedbrowser/head?db=XYZZY"))
                .exchange()
                .returnResult(String.class);

        assertThat(entity)
            .returns(HttpStatus.NOT_FOUND.value(), result -> result.getStatus().value())
            .extracting(EntityExchangeResult::getResponseBody)
                .asString().contains("Data set not found");
    }
}
