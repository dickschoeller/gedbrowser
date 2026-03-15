package org.schoellerfamily.geoservice.client;

import java.time.Duration;

import org.springframework.web.client.ResourceAccessException;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.core.functions.CheckedSupplier;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;

/**
 * Resilience4j-backed {@link GeoServiceCallExecutor}.
 */
public final class Resilience4jGeoServiceCallExecutor implements GeoServiceCallExecutor {
    private final CircuitBreaker circuitBreaker = CircuitBreaker.ofDefaults("geoservice");

    private final Retry retry;

    /**
     * Creates an executor with the default retry policy (3 attempts, 500 ms wait).
     */
    public Resilience4jGeoServiceCallExecutor() {
        this(3, 500L);
    }

    /**
     * Creates an executor with a configurable retry policy.
     *
     * @param maxAttempts  maximum number of retry attempts (including the first call); must be &ge; 1
     * @param waitMillis   fixed wait duration in milliseconds between retries; must be &ge; 0
     */
    public Resilience4jGeoServiceCallExecutor(final int maxAttempts, final long waitMillis) {
        if (maxAttempts < 1) {
            throw new IllegalArgumentException("maxAttempts must be at least 1, got: " + maxAttempts);
        }
        if (waitMillis < 0) {
            throw new IllegalArgumentException("waitMillis must be non-negative, got: " + waitMillis);
        }
        this.retry = Retry.of("geoservice", RetryConfig.custom()
                .maxAttempts(maxAttempts)
                .waitDuration(Duration.ofMillis(waitMillis))
                .retryOnException(e -> e instanceof ResourceAccessException)
                .build());
    }

    @Override
    public <T> T execute(final ThrowingSupplier<T> supplier) throws Throwable {
        final CheckedSupplier<T> decorated = CircuitBreaker.decorateCheckedSupplier(circuitBreaker,
                Retry.decorateCheckedSupplier(retry, supplier::get));
        return decorated.get();
    }

    @Override
    public boolean isCallNotPermitted(final Throwable throwable) {
        return throwable instanceof CallNotPermittedException;
    }
}
