package org.schoellerfamily.gedbrowser.renderer.user;

/**
 * @author Dick Schoeller
 */
public interface User {
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
     * The roles supported are user and admin.
     *
     * @return the set of roles for this user
     */
    String[] getRoles();

    /**
     * Check if the user has a particular role.
     *
     * @param role role that we are looking for
     * @return true if the user has the role
     */
    boolean hasRole(String role);
}
