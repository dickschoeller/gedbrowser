package org.schoellerfamily.geoservice.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;

/**
 * Contains integration tests for the backup restore endpoint.
 *
 * @author Richard Schoeller
 */
@MicronautTest(environments = "test")
@SuppressWarnings({ "PMD.JUnitTestsShouldIncludeAssert" })
@TestMethodOrder(MethodOrderer.MethodName.class)
class BackupRestoreEndpointIT {
    /** HTTP client bound to embedded test server. */
    @Inject
    @Client("/")
    private HttpClient client;

    private HttpResponse<?> response(final String uri) {
        try {
            return client.toBlocking().exchange(HttpRequest.GET(uri), String.class);
        } catch (HttpClientResponseException ex) {
            return ex.getResponse();
        }
    }

    @Test
    void shouldReturn200WhenSendingRequestToBackupEndpoint() {
        final HttpResponse<?> response = response("/actuator/backup");
        assertEquals(HttpStatus.OK, response.getStatus());
        assertThat(response.getBody(String.class).orElse(""))
            .contains("backup succeeded to/from")
            .contains("locations in the cache");
    }

    @Test
    void shouldReturn200WhenSendingRequestToRestoreEndpoint() {
        final HttpResponse<?> response = response("/actuator/restore");
        assertEquals(HttpStatus.OK, response.getStatus());
        assertThat(response.getBody(String.class).orElse(""))
            .contains("restore succeeded to/from")
            .contains("locations in the cache");
    }
}
