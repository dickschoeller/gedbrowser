package org.schoellerfamily.gedbrowser.security.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.schoellerfamily.gedbrowser.datamodel.users.Users;

/**
 * @author Dick Schoeller
 */
public final class SecurityUsers implements Users<SecurityUser> {
    /** Holds the known users. */
    private final Map<String, SecurityUser> users = new HashMap<>();

    /**
     * @param user the user to add
     * @return that user
     */
    public SecurityUser add(final SecurityUser user) {
        return users.put(user.getUsername(), user);
    }

    /**
     * @param user the user to remove
     * @return that user
     */
    public SecurityUser remove(final SecurityUser user) {
        return users.remove(user.getUsername());
    }

    /**
     * @param username the username of the user we are getting
     * @return that user
     */
    public SecurityUser get(final String username) {
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
    public Iterator<SecurityUser> iterator() {
        return users.values().iterator();
    }

    /**
     * @return the iterator for the value collection
     */
    public int size() {
        return users.values().size();
    }
}
