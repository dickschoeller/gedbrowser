package org.schoellerfamily.geoservice.client;

/**
 * A supplier of results that may throw a {@link GeoServiceCallException}.
 * <p>
 * Implementations may also throw unchecked exceptions (for example,
 * {@link RuntimeException}) as usual, but arbitrary checked exceptions
 * are not part of this interface's contract.
 *
 * @param <T> the type of results supplied by this supplier
 */
@FunctionalInterface
public interface ThrowingSupplier<T> {

    /**
     * Gets a result.
     *
     * @return the supplied result
     * @throws GeoServiceCallException if obtaining the result fails
     */
    T get() throws GeoServiceCallException;
}
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
