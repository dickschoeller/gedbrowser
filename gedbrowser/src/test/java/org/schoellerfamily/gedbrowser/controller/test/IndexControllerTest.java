package org.schoellerfamily.gedbrowser.controller.test;

import static org.assertj.core.api.BDDAssertions.then;

import java.net.URI;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.schoellerfamily.gedbrowser.Application;
import org.schoellerfamily.gedbrowser.test.TestConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.client.EntityExchangeResult;
import org.springframework.test.web.servlet.client.RestTestClient;

/**
 * @author Dick Schoeller
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = { Application.class,
    TestConfiguration.class }, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = { "management.port=0" })
@AutoConfigureRestTestClient
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
public class IndexControllerTest implements MenuTestHelper {
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

        final HttpStatusCode status = entity.getStatus();
        then(status).isEqualTo(HttpStatusCode.valueOf(HttpStatus.OK.value()));
        then(entity.getResponseBody()).contains("<title>Index - C - gl120368</title>")
            .contains("<span><a id=\"letter-?\"" + " href=\"surnames?db=gl120368&amp;letter=?\""
                + " class=\"name\">[?]</a>   </span>")
            .contains("<li id=\"I2508\"><a href=\"person?db=gl120368&amp;id=")
            .contains(getMenu("C"));
    }

    /** */
    @Test
    void testIndexControllerB() {
        final String url = URL_TEMPLATE.formatted(port, "gl120368", "B");
        final EntityExchangeResult<String> entity = restTestClient.get()
            .uri(URI.create(url))
            .exchange()
            .returnResult(String.class);

        final HttpStatusCode status = entity.getStatus();
        then(status).isEqualTo(HttpStatusCode.valueOf(HttpStatus.OK.value()));
        then(entity.getResponseBody()).contains("<title>Index - B - gl120368</title>")
            .contains("<span><a id=\"letter-?\"" + " href=\"surnames?db=gl120368&amp;letter=?\""
                + " class=\"name\">[?]</a>   </span>")
            .contains("<li id=\"I2561\"><a href=\"person?db=gl120368&amp;id=")
            .contains(getMenu("B"));
    }

    /** */
    @Test
    void testIndexControllerBadDataSet() {
        String url = URL_TEMPLATE.formatted(port, "XYZZY", "A");
        final EntityExchangeResult<String> entity = restTestClient.get()
            .uri(URI.create(url))
            .exchange()
            .returnResult(String.class);

        final HttpStatusCode status = entity.getStatus();
        then(status).isEqualTo(HttpStatusCode.valueOf(HttpStatus.NOT_FOUND.value()));
        then(entity.getResponseBody()).contains("Data set not found");
    }

    /** */
    @Test
    void testIndexControllerLetter() {
        String url = URL_TEMPLATE.formatted(port, "gl120368", "q");
        final EntityExchangeResult<String> entity = restTestClient.get()
            .uri(URI.create(url))
            .exchange()
            .returnResult(String.class);

        final HttpStatusCode status = entity.getStatus();
        then(status).isEqualTo(HttpStatusCode.valueOf(HttpStatus.OK.value()));
        then(entity.getResponseBody()).contains("<title>Index - q - gl120368</title>")
            .contains("<span><a id=\"letter-?\"" + " href=\"surnames?db=gl120368&amp;letter=?\""
                + " class=\"name\">[?]</a>   </span>")
            .doesNotContain("<li><a href=\"person?db=gl120368&amp;id=")
            .contains(getMenu("q"));
    }
}
