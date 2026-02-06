package org.schoellerfamily.gedbrowser.api.controller.test;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.schoellerfamily.gedbrowser.api.Application;
import org.schoellerfamily.gedbrowser.api.test.TestConfiguration;
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
@SpringBootTest(classes = { Application.class,
    TestConfiguration.class }, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = { "management.port=0" })
@AutoConfigureRestTestClient
class SaveControllerIT {
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
    void testSaveControllerOK() {
        final String url = "http://localhost:" + port + "/gedbrowserng/v1/dbs/gl120368/save";
        final EntityExchangeResult<String> entity = restTestClient.get()
            .uri(url)
            .exchange()
            .returnResult(String.class);

        final HttpStatusCode status = entity.getStatus();
        assertThat(status).isEqualTo(HttpStatusCode.valueOf(HttpStatus.OK.value()));
        assertThat(entity.getResponseBody()).contains("0 HEAD")
            .contains("1 SOUR FAMILY_HISTORIAN")
            .contains("2 VERS 3.1")
            .contains("2 NAME Family Historian")
            .contains("2 CORP Calico Pie Limited")
            .contains("1 FILE C:\\Users\\Phil\\Documents\\W0803.GED")
            .contains("1 GEDC")
            .contains("2 VERS 5.5")
            .contains("2 FORM LINEAGE-LINKED")
            .contains("1 CHAR ANSI")
            .contains("1 DEST FTM");
    }

    /** */
    @Test
    void testSaveControllerDatasetNotFound() {
        final String url = "http://localhost:" + port + "/gedbrowserng/v1/dbs/xyzzy/save";
        final EntityExchangeResult<String> entity = restTestClient.get()
            .uri(url)
            .exchange()
            .returnResult(String.class);

        final HttpStatusCode status = entity.getStatus();
        assertThat(status).isEqualTo(HttpStatusCode.valueOf(HttpStatus.NOT_FOUND.value()));
        assertThat(entity.getResponseBody()).contains("Data set xyzzy not found");
    }
}
