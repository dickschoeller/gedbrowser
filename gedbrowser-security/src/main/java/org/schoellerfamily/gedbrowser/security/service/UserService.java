package org.schoellerfamily.gedbrowser.security.service;

import java.util.List;

import org.schoellerfamily.gedbrowser.security.model.User;
import org.schoellerfamily.gedbrowser.security.model.UserRequest;

/**
 * @author Dick Schoeller
 */
public interface UserService {
    /**
     * Reset the login credentials of all users to default.
     */
    void resetCredentials();

//    /**
//     * Find a user by ID.
//     *
//     * @param id the user ID
//     * @return the user
//     */
//    User findById(Long id);

    /**
     * Find a user by name.
     *
     * @param username the name
     * @return the user
     */
    User findByUsername(String username);

    /**
     * Get the list of all users.
     *
     * @return list of all users
     */
    List<User> findAll();

    /**
     * Save a user (can create or update).
     *
     * @param user the user request
     * @return the updated user
     */
    User save(UserRequest user);
}
