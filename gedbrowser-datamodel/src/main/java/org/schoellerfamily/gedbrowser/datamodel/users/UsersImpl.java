package org.schoellerfamily.gedbrowser.datamodel.users;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Represents users impl in the domain model.
 *
 * @author Richard Schoeller
 * @param <T> the contained type
 */
public final class UsersImpl<T extends User> implements Users<T> {
    /** Holds the known users. */
    private final Map<String, T> users = new HashMap<>();

    /**
     * Returns the t.
     *
     * @param user the user
     * @return the resulting t
     */
    @Override
    public T add(final T user) {
        return users.put(user.getUsername(), user);
    }

    /**
     * Returns the t.
     *
     * @param user the user
     * @return the resulting t
     */
    @Override
    public T remove(final User user) {
        return users.remove(user.getUsername());
    }

    /**
     * Gets the value.
     *
     * @param username the username to use
     * @return the value
     */
    @Override
    public T get(final String username) {
        return users.get(username);
    }

    /**
     * Executes clear.
     */
    @Override
    public void clear() {
        users.clear();
    }

    /**
     * Returns the iterator.
     *
     * @return the resulting iterator
     */
    @Override
    public Iterator<T> iterator() {
        return users.values().iterator();
    }

    /**
     * Returns the int.
     *
     * @return the resulting int
     */
    @Override
    public int size() {
        return users.values().size();
    }
}
