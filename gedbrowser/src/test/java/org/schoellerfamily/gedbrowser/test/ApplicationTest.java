package org.schoellerfamily.gedbrowser.test;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.schoellerfamily.gedbrowser.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalManagementPort;
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
@SpringBootTest(classes = Application.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"management.server.port=0"})
@AutoConfigureRestTestClient
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
public class ApplicationTest {
    /**
     * Management port.
     */
    @LocalManagementPort
    private int mgt;

    /**
     * Server port.
     */
    @LocalServerPort
    private int port;

    /**
     * RestTestClient injected by Spring's test support.
     */
    @Autowired
    private RestTestClient restTestClient;

    /** */
    @Test
    public final void shouldReturn200WhenSendingRequestToInfoEndpoint() {
        final EntityExchangeResult<String> entity = restTestClient.get()
                .uri(URI.create("http://localhost:" + mgt + "/actuator/info"))
                .exchange()
                .returnResult(String.class);
        final HttpStatusCode status = entity.getStatus();
        then(status).isEqualTo(HttpStatusCode.valueOf(HttpStatus.OK.value()));
    }

    /** */
    @Test
    public final void shouldReturn200WhenSendingRequestToHealthEndpoint() {
        final EntityExchangeResult<String> entity = restTestClient.get()
                .uri(URI.create("http://localhost:" + mgt + "/actuator/health"))
                .exchange()
                .returnResult(String.class);

        final HttpStatusCode status = entity.getStatus();
        then(status).isEqualTo(HttpStatusCode.valueOf(HttpStatus.OK.value()));
    }

    /** */
    @Test
    public final void shouldReturn200WhenSendingRequestToLoadEndpoint() {
        final EntityExchangeResult<String> entity = restTestClient.get()
                .uri(URI.create("http://localhost:" + mgt + "/actuator/restore"))
                .exchange()
                .returnResult(String.class);

        final HttpStatusCode status = entity.getStatus();
        then(status).isEqualTo(HttpStatusCode.valueOf(HttpStatus.OK.value()));
        then(entity.getResponseBody()).contains("Reloaded");
    }

    /** */
    @Test
    public final void testApplicationName() {
        final Application a = new Application();
        assertEquals("gedbrowser", a.getApplicationName(), "Application name mismatch");
    }
}
