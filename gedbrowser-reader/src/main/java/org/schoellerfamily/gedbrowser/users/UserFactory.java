package org.schoellerfamily.gedbrowser.users;

import org.schoellerfamily.gedbrowser.datamodel.users.User;

/**
 * Factory interface for creating User objects. Implementations will generally
 * be injected to situations that need to create them as a lambda.
 *
 * @author Dick Schoeller
 *
 * @param <T> the created type
 */
public interface UserFactory<T extends User> {
    /**
     * @return the built user
     */
    T createUser();
}
