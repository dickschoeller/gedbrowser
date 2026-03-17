package org.schoellerfamily.geoservice.client;



/**
 * Defines the contract for throwing supplier.
 *
 * @param <T> the type of results supplied by this supplier
 */
@FunctionalInterface
public interface ThrowingSupplier<T> {
    /**
     * @return supplied value
     * @throws GeoServiceCallException when supplier execution fails
     */
    T get() throws GeoServiceCallException;
}
