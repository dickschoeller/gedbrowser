package org.schoellerfamily.gedbrowser;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.schoellerfamily.gedbrowser.renderer.user.User;

/**
 * @author Dick Schoeller
 */
public final class Users implements Iterable<User> {
    /** Holds the known users. */
    private final Map<String, User> users = new HashMap<>();

    /**
     * @param user the user to add
     * @return that user
     */
    public User add(final User user) {
        return users.put(user.getUsername(), user);
    }

    /**
     * @param user the user to remove
     * @return that user
     */
    public User remove(final User user) {
        return users.remove(user.getUsername());
    }

    /**
     * @param username the username of the user we are getting
     * @return that user
     */
    public User get(final String username) {
        return users.get(username);
    }

    /**
     * Resets to an empty set.
     */
    public void clear() {
        users.clear();
    }

    /**
     * @return the iterator for the value collection
     */
    public Iterator<User> iterator() {
        return users.values().iterator();
    }

    /**
     * @return the iterator for the value collection
     */
    public int size() {
        return users.values().size();
    }
}
