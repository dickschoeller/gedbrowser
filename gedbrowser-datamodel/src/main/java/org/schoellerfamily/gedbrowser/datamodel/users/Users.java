package org.schoellerfamily.gedbrowser.datamodel.users;

import java.util.Iterator;

/**
 * @author Dick Schoeller
 *
 * @param <T> the contained type
 */
public interface Users<T extends User> extends Iterable<T> {
    /**
     * @param user the user to add
     * @return that user
     */
    T add(T user);

    /**
     * @param user the user to remove
     * @return that user
     */
    T remove(T user);

    /**
     * @param username the username of the user we are getting
     * @return that user
     */
    T get(String username);

    /**
     * Resets to an empty set.
     */
    void clear();

    /**
     * @return the iterator for the value collection
     */
    Iterator<T> iterator();

    /**
     * @return the iterator for the value collection
     */
    int size();
}
