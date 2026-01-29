package org.schoellerfamily.gedbrowser.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URI;

import org.junit.jupiter.api.Test;
import org.schoellerfamily.gedbrowser.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalManagementPort;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.client.EntityExchangeResult;
import org.springframework.test.web.servlet.client.RestTestClient;

@SpringBootTest(classes = Application.class,
webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"management.server.port=0"})
@AutoConfigureRestTestClient
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
public class ApplicationIT {
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
    void shouldReturn200WhenSendingRequestToLoadEndpoint() {
        final EntityExchangeResult<String> entity = restTestClient.get()
                .uri(URI.create("http://localhost:" + mgt + "/actuator/restore"))
                .exchange()
                .returnResult(String.class);

        final HttpStatusCode status = entity.getStatus();
        assertThat(status).isEqualTo(HttpStatusCode.valueOf(HttpStatus.OK.value()));
        assertThat(entity.getResponseBody()).contains("Reloaded");
    }

}
