package org.schoellerfamily.gedbrowser.security.service;

import java.util.List;

import org.schoellerfamily.gedbrowser.security.model.SecurityUser;
import org.schoellerfamily.gedbrowser.security.model.UserRequest;

/**
 * Provides services for user.
 *
 * @author Richard Schoeller
 */
public interface UserService {
    /**
     * Reset the login credentials of all users to default.
     */
    void resetCredentials();

    /**
     * Find a user by name.
     *
     * @param username the name
     * @return the user
     */
    SecurityUser findByUsername(String username);

    /**
     * Get the list of all users.
     *
     * @return list of all users
     */
    List<SecurityUser> findAll();

    /**
     * Save a user (can create or update).
     *
     * @param user the user request
     * @return the updated user
     */
    SecurityUser save(UserRequest user);
}
