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
     * @throws Throwable when supplier execution fails
     */
    T get() throws Throwable;
}
