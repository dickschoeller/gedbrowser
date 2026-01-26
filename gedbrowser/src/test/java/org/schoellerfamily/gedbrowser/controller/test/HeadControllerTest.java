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
class HeadControllerTest implements MenuTestHelper {
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

        final HttpStatusCode status = entity.getStatus();
        assertThat(status).isEqualTo(HttpStatusCode.valueOf(HttpStatus.OK.value()));
        assertThat(entity.getResponseBody())
            .contains("<title>Header - gl120368</title>")
            .contains("File:</span> C:\\Users\\Phil\\Documents\\W0803.GED")
            .contains("GEDCOM:</span> 5.5, LINEAGE-LINKED")
            .contains("Character Set:</span> ANSI")
            .contains("Destination:</span> FTM")
            .contains("Submitter:</span> <a class=\"name\""
                    + " href=\"submitter?db=gl120368&amp;id=U1\">Phil Williams"
                    + " [U1]</a>")
            .contains(getMenu("A"));
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

        final HttpStatusCode status = entity.getStatus();
        assertThat(status).isEqualTo(HttpStatusCode.valueOf(HttpStatus.OK.value()));
        assertThat(entity.getResponseBody())
            .contains("<title>Header - mini-schoeller</title>")
            .contains("Submitter:</span> <a class=\"name\""
                    + " href=\"submitter?db=mini-schoeller&amp;"
                    + "id=SUB1\">Richard Schoeller [SUB1]</a>")
            .contains("GEDCOM:</span> 5.5.1, LINEAGE-LINKED")
            .contains("Destination:</span> GED55")
            .contains("Date:</span> 16 FEB 2001 22:04</li>")
            .contains("Character Set:</span> UTF-8")
            .contains(getMenu("mini-schoeller", "A"));
    }

    /** */
    @Test
    void testHeadControllerBadDataSet() {
        final EntityExchangeResult<String> entity = restTestClient.get()
                .uri(URI.create("http://localhost:" + port + "/gedbrowser/head?db=XYZZY"))
                .exchange()
                .returnResult(String.class);

        final HttpStatusCode status = entity.getStatus();
        assertThat(status).isEqualTo(HttpStatusCode.valueOf(HttpStatus.NOT_FOUND.value()));
        assertThat(entity.getResponseBody()).contains("Data set not found");
    }
}
