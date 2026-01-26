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
class IndexControllerTest implements MenuTestHelper {
    /**
     * Template for building surname index URLs.
     */
    private static final String URL_TEMPLATE =
        "http://localhost:%d/gedbrowser/surnames?db=%s&letter=%s";

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
    void testIndexControllerC() {
        final String url = URL_TEMPLATE.formatted(port, "gl120368", "C");
        final EntityExchangeResult<String> entity = restTestClient.get()
            .uri(URI.create(url))
            .exchange()
            .returnResult(String.class);

        assertThat(entity)
            .returns(HttpStatus.OK, EntityExchangeResult::getStatus)
            .extracting(EntityExchangeResult::getResponseBody)
                .asString().contains(
                    "<title>Index - C - gl120368</title>",
                    "id=\"letter-?\" href=\"surnames?db=gl120368&amp;letter=?\"",
                    "<li id=\"I2508\"><a href=\"person?db=gl120368&amp;id=",
                    getMenu("C"));
    }

    /** */
    @Test
    void testIndexControllerB() {
        final String url = URL_TEMPLATE.formatted(port, "gl120368", "B");
        final EntityExchangeResult<String> entity = restTestClient.get()
            .uri(URI.create(url))
            .exchange()
            .returnResult(String.class);

        assertThat(entity)
            .returns(HttpStatus.OK, EntityExchangeResult::getStatus)
            .extracting(EntityExchangeResult::getResponseBody)
                .asString().contains(
                    "<title>Index - B - gl120368</title>",
                    "id=\"letter-?\" href=\"surnames?db=gl120368&amp;letter=?\"",
                    "<li id=\"I2561\"><a href=\"person?db=gl120368&amp;id=",
                    getMenu("B"));
    }

    /** */
    @Test
    void testIndexControllerBadDataSet() {
        final String url = URL_TEMPLATE.formatted(port, "XYZZY", "A");
        final EntityExchangeResult<String> entity = restTestClient.get()
            .uri(URI.create(url))
            .exchange()
            .returnResult(String.class);

        assertThat(entity)
            .returns(HttpStatus.NOT_FOUND, EntityExchangeResult::getStatus)
            .extracting(EntityExchangeResult::getResponseBody)
                .asString().contains("Data set not found");
    }

    /** */
    @Test
    void testIndexControllerLetter() {
        final String url = URL_TEMPLATE.formatted(port, "gl120368", "q");
        final EntityExchangeResult<String> entity = restTestClient.get()
            .uri(URI.create(url))
            .exchange()
            .returnResult(String.class);

        assertThat(entity)
            .returns(HttpStatus.OK, EntityExchangeResult::getStatus)
            .extracting(EntityExchangeResult::getResponseBody)
                .asString()
                    .contains(
                        "<title>Index - q - gl120368</title>",
                        "id=\"letter-?\" href=\"surnames?db=gl120368&amp;letter=?\"",
                        getMenu("q"))
                    .doesNotContain("<li><a href=\"person?db=gl120368&amp;id=");
    }
}
