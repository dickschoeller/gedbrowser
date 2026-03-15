package org.schoellerfamily.geoservice.client;

/**
 * Executes geoservice calls with optional resilience policies.
 */
public interface GeoServiceCallExecutor {
    /**
     * Execute a call and return its value.
     *
     * @param supplier call to execute
     * @param <T>      return type
     * @return call result
     * @throws GeoServiceCallException when execution fails
     */
    <T> T execute(ThrowingSupplier<T> supplier) throws GeoServiceCallException;

    /**
     * @param throwable failure thrown by execute
     * @return true when the failure indicates circuit breaker rejection
     */
    default boolean isCallNotPermitted(final Throwable throwable) {
        return false;
    }
}
