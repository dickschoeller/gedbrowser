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
     * The name of the file for persisting the users.
     */
    private final String userFileName;

    /**
     * @param userFileName the name of the file for persisting the users
     */
    public SecurityUsers(final String userFileName) {
        this.userFileName = userFileName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SecurityUser add(final SecurityUser user) {
        return users.put(user.getUsername(), user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SecurityUser remove(final SecurityUser user) {
        return users.remove(user.getUsername());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SecurityUser get(final String username) {
        return users.get(username);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clear() {
        users.clear();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterator<SecurityUser> iterator() {
        return users.values().iterator();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int size() {
        return users.values().size();
    }

    /**
     * @return the name of the file persisting the users
     */
    public String getUserFileName() {
        return userFileName;
    }
}
