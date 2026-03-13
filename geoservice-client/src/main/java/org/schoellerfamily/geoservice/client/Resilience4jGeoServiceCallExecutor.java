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

    private final Retry retry = Retry.of("geoservice", RetryConfig.custom()
            .maxAttempts(3)
            .waitDuration(Duration.ofMillis(500))
            .retryOnException(e -> e instanceof ResourceAccessException)
            .build());

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
