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
 * Contains integration tests for the sources controller.
 *
 * @author Richard Schoeller
 */
@SpringBootTest(classes = { Application.class, TestConfiguration.class },
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"management.port=0"})
@AutoConfigureRestTestClient
class SourcesControllerIT implements MenuTestHelper {
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
    void testSourcesControllerOK() {
        final String url = "http://localhost:" + port
                + "/gedbrowser/sources?db=gl120368";
        final EntityExchangeResult<String> entity = restTestClient.get()
                .uri(URI.create(url))
                .exchange()
                .returnResult(String.class);

        assertThat(entity)
            .returns(HttpStatus.OK.value(), result -> result.getStatus().value())
            .extracting(EntityExchangeResult::getResponseBody)
                .asString().contains(
                    "<title>Sources - gl120368</title>",
                    "Sources for dataset: gl120368</h2>",
                    "href=\"source?db=gl120368&amp;id=S2050\" class=\"name\""
                        + " id=\"source-S2050\">Parish records (S2050)",
                    "href=\"source?db=gl120368&amp;id=S2124\" class=\"name\""
                        + " id=\"source-S2124\">Www.peake.net (S2124)",
                    "href=\"source?db=gl120368&amp;id=S2122\" class=\"name\""
                        + " id=\"source-S2122\">Will of R Harris (S2122)",
                    getMenu("A"));
    }

    @Test
    void testSourcesControllerBadDataSet() {
        final EntityExchangeResult<String> entity = restTestClient.get()
                .uri(URI.create("http://localhost:" + port + "/gedbrowser/sources?db=XYZZY"))
                .exchange()
                .returnResult(String.class);

        assertThat(entity)
            .returns(HttpStatus.NOT_FOUND.value(), result -> result.getStatus().value())
            .extracting(EntityExchangeResult::getResponseBody)
                .asString().contains("Data set not found");
    }
}
