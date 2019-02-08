package org.schoellerfamily.gedbrowser.users;

import org.schoellerfamily.gedbrowser.datamodel.users.Users;

/**
 * Factory interface for creating Users objects. Implementations will
 * generally be injected to situations that need to create them as a lambda.
 *
 * @author Dick Schoeller
 *
 * @param <T> the created type
 */
public interface UsersFactory<T extends Users<?>> {
    /**
     * @return the object that manages a collection of users
     */
    T createUsers();
}
