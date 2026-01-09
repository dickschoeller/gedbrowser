package org.schoellerfamily.gedbrowser.datamodel.users;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Dick Schoeller
 *
 * @param <T> the contained type
 */
public final class UsersImpl<T extends User> implements Users<T> {
    /** Holds the known users. */
    private final Map<String, T> users = new HashMap<>();

    @Override
    public T add(final T user) {
        return users.put(user.getUsername(), user);
    }

    @Override
    public T remove(final User user) {
        return users.remove(user.getUsername());
    }

    @Override
    public T get(final String username) {
        return users.get(username);
    }

    @Override
    public void clear() {
        users.clear();
    }

    @Override
    public Iterator<T> iterator() {
        return users.values().iterator();
    }

    @Override
    public int size() {
        return users.values().size();
    }
}
