package org.schoellerfamily.gedbrowser.reader.users;

import org.schoellerfamily.gedbrowser.datamodel.users.Users;

/**
 * Creates users instances.
 *
 * @author Richard Schoeller
 * @param <T> the created type
 */
public interface UsersFactory<T extends Users<?>> {
    /**
     * @return the object that manages a collection of users
     */
    T createUsers();
}
