package org.schoellerfamily.gedbrowser.security.model;

import java.io.Serializable;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author Dick Schoeller
 */
public interface User extends UserDetails, Serializable {
    /**
     * @return the username
     */
    String getUsername();

    /**
     * @return the user's first name
     */
    String getFirstname();

    /**
     * @return the user's last name
     */
    String getLastname();

    /**
     * @return the user's email address
     */
    String getEmail();

    /**
     * @return the user's password
     */
    String getPassword();

    /**
     * @param password the user's new password
     */
    void setPassword(String password);

    /**
     * @return the actual roles
     */
    UserRoleName[] getRoles();

    /**
     * Check if the user has a particular role.
     *
     * @param role role that we are looking for
     * @return true if the user has the role
     */
    boolean hasRole(UserRoleName role);
}
