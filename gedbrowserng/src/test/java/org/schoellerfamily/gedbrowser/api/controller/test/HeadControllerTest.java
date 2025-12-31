package org.schoellerfamily.gedbrowser.api.controller.test;

import static org.assertj.core.api.BDDAssertions.then;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.schoellerfamily.gedbrowser.api.Application;
import org.schoellerfamily.gedbrowser.api.test.TestConfiguration;
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
@SpringBootTest(classes = { Application.class, TestConfiguration.class },
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"management.port=0"})
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
@AutoConfigureRestTestClient
public class HeadControllerTest {
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
    public final void testGetHeadGl120368() {
        final String url = "http://localhost:" + port
                + "/gedbrowserng/v1/dbs/gl120368";
        final EntityExchangeResult<String> entity = restTestClient.get()
                .uri(url)
                .exchange()
                .returnResult(String.class);

        final HttpStatusCode status = entity.getStatus();
        then(status).isEqualTo(HttpStatusCode.valueOf(HttpStatus.OK.value()));
        then(entity.getResponseBody())
            .contains(
                "\"type\" : \"head\"",
                "\"string\" : \"Header\"",
                "3C8079D5-1C5A-4473-8939-6631E48D01BB");
    }

    /** */
    @Test
    public final void testGetHeadMiniSchoeller() {
        final String url = "http://localhost:" + port
                + "/gedbrowserng/v1/dbs/mini-schoeller";
        final EntityExchangeResult<String> entity = restTestClient.get()
                .uri(url)
                .exchange()
                .returnResult(String.class);

        then(entity.getStatus()).isEqualTo(HttpStatusCode.valueOf(HttpStatus.OK.value()));
        then(entity.getResponseBody())
            .contains(
                "\"type\" : \"head\"",
                "\"string\" : \"Header\"",
                "\"tail\" : \"TMG\"");
    }

    /** */
    @Test
    public final void testGetHeadBadDataSet() {
        final EntityExchangeResult<String> entity = restTestClient.get()
                .uri("http://localhost:" + port + "/gedbrowserng/v1/dbs/XYZZY")
                .exchange()
                .returnResult(String.class);

        then(entity.getStatus()).isEqualTo(HttpStatusCode.valueOf(HttpStatus.NOT_FOUND.value()));
        then(entity.getResponseBody()).contains(
                "  \"cause\" : null",
                "  \"stackTrace\" : [ ]",
                "  \"datasetName\" : \"XYZZY\"",
                "  \"message\" : \"Data set XYZZY not found\"",
                "  \"suppressed\" : [ ]",
                "  \"localizedMessage\" : \"Data set XYZZY not found\"");
    }
}