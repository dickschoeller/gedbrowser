package org.schoellerfamily.gedbrowser.reader.users;

import org.schoellerfamily.gedbrowser.datamodel.users.User;

/**
 * Creates user instances.
 *
 * @author Richard Schoeller
 * @param <T> the created type
 */
public interface UserFactory<T extends User> {
    /**
     * @return the built user
     */
    T createUser();
}
