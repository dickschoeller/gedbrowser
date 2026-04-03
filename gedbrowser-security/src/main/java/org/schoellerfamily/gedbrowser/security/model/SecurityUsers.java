package org.schoellerfamily.gedbrowser.security.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.schoellerfamily.gedbrowser.datamodel.users.Users;

/**
 * Represents security users.
 *
 * @author Richard Schoeller
 */
public final class SecurityUsers implements Users<SecurityUser> {
    /**
     * The string value.
     */
    private final Map<String, SecurityUser> users = new HashMap<>();

    /**
     * The name of the file for persisting the users.
     */
    private final String userFileName;

    /**
     * Creates a new SecurityUsers.
     *
     * @param userFileName the user file name to use
     */
    public SecurityUsers(final String userFileName) {
        this.userFileName = userFileName;
    }

    /**
     * Returns the security user.
     *
     * @param user the user
     * @return the resulting security user
     */
    @Override
    public SecurityUser add(final SecurityUser user) {
        return users.put(user.getUsername(), user);
    }

    /**
     * Returns the security user.
     *
     * @param user the user
     * @return the resulting security user
     */
    @Override
    public SecurityUser remove(final SecurityUser user) {
        return users.remove(user.getUsername());
    }

    /**
     * Gets the value.
     *
     * @param username the username to use
     * @return the value
     */
    @Override
    public SecurityUser get(final String username) {
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
    public Iterator<SecurityUser> iterator() {
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

    /**
     * Gets the user file name.
     *
     * @return the user file name
     */
    public String getUserFileName() {
        return userFileName;
    }
}
