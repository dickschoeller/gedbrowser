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
public class SubmitterControllerTest implements MenuTestHelper {
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

        final HttpStatusCode status = entity.getStatus();
        then(status).isEqualTo(HttpStatusCode.valueOf(HttpStatus.OK.value()));
        then(entity.getResponseBody()).contains("<title>Phil Williams - U1 - gl120368</title>")
            .contains("Name:</span> Phil Williams")
            .contains(getMenu("A"));
    }

    /** */
    @Test
    void testSubmitterControllerU2() {
        final String url = "http://localhost:" + port + "/gedbrowser/submitter?db=gl120368&id=U2";
        final EntityExchangeResult<String> entity = restTestClient.get()
            .uri(URI.create(url))
            .exchange()
            .returnResult(String.class);

        final HttpStatusCode status = entity.getStatus();
        then(status).isEqualTo(HttpStatusCode.valueOf(HttpStatus.OK.value()));
        then(entity.getResponseBody()).contains("<title>Arthur PUNCHARD - U2 - gl120368</title>")
            .contains("Name:</span> Arthur PUNCHARD")
            .contains("Changed:</span> 24 MAR 2007")
            .contains(getMenu("A"));
    }

    /** */
    @Test
    void testSubmitterControllerU4() {
        final String url = "http://localhost:" + port + "/gedbrowser/submitter?db=gl120368&id=U4";
        final EntityExchangeResult<String> entity = restTestClient.get()
            .uri(URI.create(url))
            .exchange()
            .returnResult(String.class);

        final HttpStatusCode status = entity.getStatus();
        then(status).isEqualTo(HttpStatusCode.valueOf(HttpStatus.OK.value()));
        then(entity.getResponseBody())
            .contains("<title>Created by FamilySearch (TM) Internet"
                + " Genealogy Service - U4 - gl120368</title>")
            .contains("Name:</span> Created by FamilySearch")
            .contains("Address:</span> 50 East North Temple Street<br/>")
            .contains("Salt Lake City, Utah 84150")
            .contains(getMenu("A"));
    }

    /** */
    @Test
    void testSubmitterControllerBadDataSet() {
        final EntityExchangeResult<String> entity = restTestClient.get()
            .uri(URI.create("http://localhost:" + port + "/gedbrowser/submitter?db=XYZZY&id=U4"))
            .exchange()
            .returnResult(String.class);

        final HttpStatusCode status = entity.getStatus();
        then(status).isEqualTo(HttpStatusCode.valueOf(HttpStatus.NOT_FOUND.value()));
        then(entity.getResponseBody()).contains("Data set not found");
    }

    /** */
    @Test
    void testSubmitterControllerBadSubmitter() {
        final EntityExchangeResult<String> entity = restTestClient.get()
            .uri(URI
                .create("http://localhost:" + port + "/gedbrowser/submitter?db=gl120368&id=U99999"))
            .exchange()
            .returnResult(String.class);

        final HttpStatusCode status = entity.getStatus();
        then(status).isEqualTo(HttpStatusCode.valueOf(HttpStatus.NOT_FOUND.value()));
        then(entity.getResponseBody()).contains("Submitter not found").contains(getMenu("A"));
    }
}
