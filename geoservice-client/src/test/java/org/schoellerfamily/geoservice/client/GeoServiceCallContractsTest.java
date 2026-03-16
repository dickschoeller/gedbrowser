package org.schoellerfamily.geoservice.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

/**
 * Contract tests for geoservice call abstractions.
 */
final class GeoServiceCallContractsTest {

    @Test
    void testGeoServiceCallExceptionPreservesCause() {
        final RuntimeException cause = new RuntimeException("boom");
        final GeoServiceCallException exception =
                new GeoServiceCallException("message", cause);

        assertEquals("message", exception.getMessage());
        assertSame(cause, exception.getCause());
    }

    @Test
    void testGeoServiceCallExecutorDefaultCallNotPermittedIsFalse() {
        final GeoServiceCallExecutor executor = new GeoServiceCallExecutor() {
            /**
             * Returns the t.
             *
             * @param supplier the supplier
             * @return the resulting t
             */
            @Override
            public <T> T execute(final ThrowingSupplier<T> supplier)
                    throws GeoServiceCallException {
                return supplier.get();
            }
        };

        assertFalse(executor.isCallNotPermitted(new RuntimeException("x")));
    }
}
