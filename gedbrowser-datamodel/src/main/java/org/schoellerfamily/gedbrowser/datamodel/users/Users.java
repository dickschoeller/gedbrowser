package org.schoellerfamily.gedbrowser.datamodel.users;

import java.util.Iterator;

/**
 * Defines the contract for users.
 *
 * @author Richard Schoeller
 * @param <T> the contained type
 */
public interface Users<T extends User> extends Iterable<T> {
    /**
     * Performs add.
     *
     * @param user the user to add
     * @return that user
     */
    T add(T user);

    /**
     * Performs remove.
     *
     * @param user the user to remove
     * @return that user
     */
    T remove(T user);

    /**
     * Performs get.
     *
     * @param username the username of the user we are getting
     * @return that user
     */
    T get(String username);

    /**
     * Resets to an empty set.
     */
    void clear();

    /**
     * Performs iterator.
     *
     * @return the iterator for the value collection
     */
    Iterator<T> iterator();

    /**
     * Performs size.
     *
     * @return the iterator for the value collection
     */
    int size();
}
