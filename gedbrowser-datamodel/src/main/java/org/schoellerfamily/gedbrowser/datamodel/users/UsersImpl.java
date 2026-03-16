package org.schoellerfamily.gedbrowser.datamodel.users;

import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Represents users impl in the domain model.
 *
 * @author Richard Schoeller
 * @param <T> the contained type
 */
@NoArgsConstructor
public final class UsersImpl<T extends User> implements Users<T> {

    /**
     * Performs method.
     */
    private final Map<String, T> users = new HashMap<>();

    /**
     * Adds the specified user to this collection, keyed by its username.
     *
     * @param user the user to add
     * @return the previous user associated with the username, or {@code null}
     */
    @Override
    public T add(final T user) {
        return users.put(user.getUsername(), user);
    }

    /**
     * Removes the specified user (by username) from this collection.
     *
     * @param user the user whose username is to be removed
     * @return the previous user associated with the username, or {@code null}
     */
    @Override
    public T remove(final User user) {
        return users.remove(user.getUsername());
    }

    /**
     * Gets the user associated with the given username.
     *
     * @param username the username to use
     * @return the user associated with the username, or {@code null} if none
     */
    @Override
    public T get(final String username) {
        return users.get(username);
    }

    /**
     * Removes all users from this collection.
     */
    @Override
    public void clear() {
        users.clear();
    }

    /**
     * Returns an iterator over the users in this collection.
     *
     * @return an iterator over the contained users
     */
    @Override
    public Iterator<T> iterator() {
        return users.values().iterator();
    }

    /**
     * Returns the number of users in this collection.
     *
     * @return the number of users
     */
    @Override
    public int size() {
        return users.values().size();
    }
}
