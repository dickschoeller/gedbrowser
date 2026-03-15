package org.schoellerfamily.geoservice.client;

/**
 * Supplier that can throw checked or unchecked exceptions.
 *
 * @param <T> supplied type
 */
@FunctionalInterface
public interface ThrowingSupplier<T> {
    /**
     * @return supplied value
     * @throws GeoServiceCallException when supplier execution fails
     */
    T get() throws GeoServiceCallException;
}
