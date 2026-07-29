package org.schoellerfamily.geoservice.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.DefaultHttpClientConfiguration;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.runtime.server.EmbeddedServer;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;

/**
 * Contains integration tests for the load endpoint.
 *
 * @author Richard Schoeller
 */
@MicronautTest(environments = "test")
@SuppressWarnings({ "PMD.JUnitTestsShouldIncludeAssert", "java:S5976", "java:S4144" })
@TestMethodOrder(MethodOrderer.MethodName.class)
class LoadEndpointIT {
    /** Embedded test server used to create a tuned client. */
    @Inject
    private EmbeddedServer server;

    /** HTTP client with a longer timeout for loadAndFind endpoint calls. */
    private HttpClient client;

    @BeforeEach
    void setUpClient() {
        final DefaultHttpClientConfiguration configuration = new DefaultHttpClientConfiguration();
        final int readTimeout = 300;
        final int requestTimeout = 310;
        configuration.setReadTimeout(Duration.ofSeconds(readTimeout));
        configuration.setRequestTimeout(Duration.ofSeconds(requestTimeout));
        client = HttpClient.create(server.getURL(), configuration);
    }

    @AfterEach
    void closeClient() {
        if (client != null) {
            client.close();
        }
    }

    private HttpResponse<?> response(final String uri) {
        try {
            return client.toBlocking().exchange(HttpRequest.GET(uri), String.class);
        } catch (HttpClientResponseException ex) {
            return ex.getResponse();
        }
    }

    @Test
    void testAReturn200WhenSendingRequestToClearEndpoint() {
        final HttpResponse<?> response = response("/actuator/clear");
        assertEquals(HttpStatus.OK, response.getStatus());
        assertThat(response.getBody(String.class).orElse(""))
                .contains("Load complete")
                .contains("0 locations in the cache");
    }

    @Test
    void testBReturn200WhenSendingRequestToLoadEndpoint() {
        final HttpResponse<?> response = response("/actuator/load");
        assertEquals(HttpStatus.OK, response.getStatus());
        assertThat(response.getBody(String.class).orElse(""))
                .contains("Load complete")
                .contains("917 locations in the cache");
    }

    @Test
    void testCReturn200WhenSendingRequestToClearEndpoint() {
        final HttpResponse<?> response = response("/actuator/clear");
        assertEquals(HttpStatus.OK, response.getStatus());
        assertThat(response.getBody(String.class).orElse(""))
                .contains("Load complete")
                .contains("0 locations in the cache");
    }

    @Test
    void testDReturn200WhenSendingRequestToLoadAndFindEndpoint() {
        final HttpResponse<?> response = response("/actuator/loadAndFind");
        assertEquals(HttpStatus.OK, response.getStatus());
        assertThat(response.getBody(String.class).orElse(""))
                .contains("Load complete")
                .contains("917 locations in the cache");
    }
}
