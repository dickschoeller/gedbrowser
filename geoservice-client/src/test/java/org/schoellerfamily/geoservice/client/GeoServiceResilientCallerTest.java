package org.schoellerfamily.geoservice.client;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.schoellerfamily.geoservice.model.GeoServiceItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import org.springframework.web.client.ResourceAccessException;

/**
 * Verifies the retry and circuit-breaker behavior of
 * {@link GeoServiceResilientCaller} through the Spring Retry AOP proxy.
 */
@org.springframework.boot.test.context.SpringBootTest(
    classes = {
        GeoServiceClientTestApplication.class,
        GeoServiceClientTestConfig.class
    },
    webEnvironment = org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE)
@TestPropertySource(properties = {
    "geoservice.retry.max-attempts=2",
    "geoservice.retry.wait-millis=0"
})
class GeoServiceResilientCallerTest {

    /** Shared URL used across tests. */
    private static final String PLACE_URL =
        "http://localhost:8080/geocode?name="
            + URLEncoder.encode("Retry Place", StandardCharsets.UTF_8);

    /** The Spring-managed AOP-proxied caller under test. */
    @Autowired
    private GeoServiceResilientCaller resilientCaller;

    /** Mock server wired to the same {@link org.springframework.web.client.RestClient}. */
    @Autowired
    private MockRestServiceServer server;

    @BeforeEach
    void resetServer() {
        server.reset();
    }

    /**
     * Verifies that a {@link ResourceAccessException} is retried up to the
     * configured {@code max-attempts} count. Each retry becomes a separate
     * backend request, so the mock server expects exactly {@code max-attempts}
     * calls before the exception propagates to the caller.
     */
    @Test
    void testRetryOnConnectivityErrorMakesMaxAttempts() {
        // max-attempts=2: two IO-error responses expected
        server.expect(requestTo(PLACE_URL))
            .andRespond(MockRestResponseCreators.withException(
                new IOException("connection refused")));
        server.expect(requestTo(PLACE_URL))
            .andRespond(MockRestResponseCreators.withException(
                new IOException("connection refused")));

        assertThrows(ResourceAccessException.class,
            () -> resilientCaller.fetchPrimary(PLACE_URL));

        // Confirms that both retry attempts reached the mock server.
        server.verify();
    }

    /**
     * Verifies that after retries are exhausted the circuit opens and the
     * next call fails fast without reaching the backend. Any unexpected
     * backend call would cause {@link MockRestServiceServer} to throw an
     * {@link AssertionError}, which would fail this test.
     */
    @Test
    @DirtiesContext
    void testCircuitBreakerBlocksCallsAfterExhaustedRetries() {
        // Phase 1: Exhaust retries to trigger circuit-open.
        server.expect(requestTo(PLACE_URL))
            .andRespond(MockRestResponseCreators.withException(
                new IOException("connection refused")));
        server.expect(requestTo(PLACE_URL))
            .andRespond(MockRestResponseCreators.withException(
                new IOException("connection refused")));

        assertThrows(ResourceAccessException.class,
            () -> resilientCaller.fetchPrimary(PLACE_URL));
        server.verify();

        // Phase 2: Circuit is open – no backend call should be made.
        // Clearing expectations: any backend call now would raise AssertionError.
        server.reset();

        // When the circuit is open, Spring Retry rethrows the last exception
        // (ResourceAccessException) immediately without calling the backend.
        assertThrows(ResourceAccessException.class,
            () -> resilientCaller.fetchPrimary(PLACE_URL));
        // If we reach this point without AssertionError, the circuit was open.
    }

    /**
     * Verifies that a successful response is returned when the backend is
     * available and no retries are needed.
     */
    @Test
    void testFetchPrimaryReturnsItemOnSuccess() {
        server.expect(requestTo(PLACE_URL))
            .andRespond(withSuccess(
                """
                {
                  "placeName": "Retry Place",
                  "modernPlaceName": "Retry Modern Place",
                  "result": null
                }
                """,
                MediaType.APPLICATION_JSON));

        final GeoServiceItem item = resilientCaller.fetchPrimary(PLACE_URL);

        assertNotNull(item);
        server.verify();
    }
}
