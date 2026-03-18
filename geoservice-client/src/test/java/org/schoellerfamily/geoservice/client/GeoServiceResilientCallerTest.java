package org.schoellerfamily.geoservice.client;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
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
import org.springframework.retry.ExhaustedRetryException;
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
     * Verifies that a {@link ResourceAccessException} is tracked across attempts until
     * the configured {@code max-attempts} count is reached. With stateful retry,
     * each external call to {@code fetchPrimary} constitutes one attempt. The first
     * call makes 1 backend request and throws {@link ResourceAccessException}; the
     * second call makes 1 more backend request and throws {@link ExhaustedRetryException}
     * once all attempts are used up.
        * Because {@code @CircuitBreaker} uses {@code BinaryExceptionClassifier(false)} as
        * its rollback classifier, {@code shouldRethrow} is always false, causing the
        * GLOBAL_STATE break after each attempt and {@code ExhaustedRetryException} to be
        * thrown on every failed call (including the first one). After {@code maxAttempts=2}
        * failed calls the circuit opens and subsequent calls fast-fail.
        */
    @Test
    @DirtiesContext
    void testRetryOnConnectivityErrorMakesMaxAttempts() {
        // max-attempts=2: two separate calls, each making one backend request
        server.expect(requestTo(PLACE_URL))
            .andRespond(MockRestResponseCreators.withException(
                new IOException("connection refused")));
        server.expect(requestTo(PLACE_URL))
            .andRespond(MockRestResponseCreators.withException(
                new IOException("connection refused")));

        // Attempt 1: backend request made, ResourceAccessException stored and re-thrown.
        assertThrows(ExhaustedRetryException.class,
            () -> resilientCaller.fetchPrimary(PLACE_URL));

          // Attempt 2: another backend request made, circuit now exhausted.
        final ExhaustedRetryException retryEx = assertThrows(ExhaustedRetryException.class,
            () -> resilientCaller.fetchPrimary(PLACE_URL));
        assertInstanceOf(ResourceAccessException.class, retryEx.getCause());

        // Confirms that both attempts reached the mock server.
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
        // Phase 1: Make max-attempts=2 calls to exhaust retries and open the circuit.
        server.expect(requestTo(PLACE_URL))
            .andRespond(MockRestResponseCreators.withException(
                new IOException("connection refused")));
        server.expect(requestTo(PLACE_URL))
            .andRespond(MockRestResponseCreators.withException(
                new IOException("connection refused")));

        // Attempt 1: ResourceAccessException is stored and re-thrown.
        assertThrows(ExhaustedRetryException.class,
            () -> resilientCaller.fetchPrimary(PLACE_URL));

        // Attempt 2: retries exhausted, ExhaustedRetryException thrown, circuit opens.
        final ExhaustedRetryException ex1 = assertThrows(ExhaustedRetryException.class,
            () -> resilientCaller.fetchPrimary(PLACE_URL));
        assertInstanceOf(ResourceAccessException.class, ex1.getCause());
        server.verify();

        // Phase 2: Circuit is open – no backend call should be made.
        // Clearing expectations: any backend call now would raise AssertionError.
        server.reset();

        // When the circuit is open, Spring Retry throws ExhaustedRetryException
        // immediately without calling the backend.
        final ExhaustedRetryException ex2 = assertThrows(ExhaustedRetryException.class,
            () -> resilientCaller.fetchPrimary(PLACE_URL));
        assertInstanceOf(ResourceAccessException.class, ex2.getCause());
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
