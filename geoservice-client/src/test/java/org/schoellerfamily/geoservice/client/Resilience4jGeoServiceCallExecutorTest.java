package org.schoellerfamily.geoservice.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;
import org.springframework.web.client.ResourceAccessException;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;

/**
 * Tests for {@link Resilience4jGeoServiceCallExecutor}.
 */
final class Resilience4jGeoServiceCallExecutorTest {

    @Test
    void testExecuteReturnsSupplierValue() throws Exception {
        final Resilience4jGeoServiceCallExecutor executor =
                new Resilience4jGeoServiceCallExecutor();

        final String result = executor.execute(() -> "ok");

        assertEquals("ok", result);
    }

    @Test
    void testExecuteRetriesResourceAccessException() throws Exception {
        final Resilience4jGeoServiceCallExecutor executor =
                new Resilience4jGeoServiceCallExecutor(3, 0L);
        final AtomicInteger attempts = new AtomicInteger(0);

        final String result = executor.execute(() -> {
            if (attempts.getAndIncrement() < 2) {
                throw new ResourceAccessException("temporary");
            }
            return "recovered";
        });

        assertEquals("recovered", result);
        assertEquals(3, attempts.get());
    }

    @Test
    void testExecuteDoesNotRetryUnexpectedExceptionType() {
        final Resilience4jGeoServiceCallExecutor executor =
                new Resilience4jGeoServiceCallExecutor(3, 0L);
        final AtomicInteger attempts = new AtomicInteger(0);

        final GeoServiceCallException exception = assertThrows(
                GeoServiceCallException.class,
                () -> executor.execute(() -> {
                    attempts.incrementAndGet();
                    throw new IllegalStateException("unexpected");
                }));
        assertInstanceOf(IllegalStateException.class, exception.getCause());
        assertEquals(1, attempts.get());
    }

    @Test
    void testConstructorRejectsInvalidAttemptCount() {
        assertThrows(IllegalArgumentException.class,
                () -> new Resilience4jGeoServiceCallExecutor(0, 0L));
    }

    @Test
    void testConstructorRejectsNegativeWaitMillis() {
        assertThrows(IllegalArgumentException.class,
                () -> new Resilience4jGeoServiceCallExecutor(1, -1L));
    }

    @Test
    void testIsCallNotPermittedRecognizesResilienceException()
            throws ReflectiveOperationException {
        final Resilience4jGeoServiceCallExecutor executor =
                new Resilience4jGeoServiceCallExecutor();
        final Field field = Resilience4jGeoServiceCallExecutor.class
                .getDeclaredField("circuitBreaker");
        field.setAccessible(true);
        final CircuitBreaker circuitBreaker = (CircuitBreaker) field.get(executor);
        final CallNotPermittedException exception =
                CallNotPermittedException.createCallNotPermittedException(circuitBreaker);

        assertTrue(executor.isCallNotPermitted(exception));
        assertTrue(executor.isCallNotPermitted(
                new GeoServiceCallException("wrapped", exception)));
        assertFalse(executor.isCallNotPermitted(new RuntimeException("other")));
    }

    @Test
    void testExecuteWrapsCheckedFailures() {
        final Resilience4jGeoServiceCallExecutor executor =
                new Resilience4jGeoServiceCallExecutor(1, 0L);

        final GeoServiceCallException exception = assertThrows(
                GeoServiceCallException.class,
                () -> executor.execute(() -> {
                    throw new GeoServiceCallException("inner", null);
                }));
        assertInstanceOf(GeoServiceCallException.class, exception.getCause());
    }

        @Test
        void testExecuteRethrowsErrors() {
                final Resilience4jGeoServiceCallExecutor executor =
                                new Resilience4jGeoServiceCallExecutor(1, 0L);

                assertThrows(AssertionError.class, () -> executor.execute(() -> {
                        throw new AssertionError("fatal");
                }));
        }
}
