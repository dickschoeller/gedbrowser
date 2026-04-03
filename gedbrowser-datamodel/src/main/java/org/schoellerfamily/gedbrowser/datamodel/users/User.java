package org.schoellerfamily.gedbrowser.datamodel.users;

/**
 * Defines the contract for user.
 *
 * @author Richard Schoeller
 */
public interface User {
    /**
     * Gets the username.
     *
     * @return the user name
     */
    String getUsername();

    /**
     * Sets the username.
     *
     * @param username the user name
     */
    void setUsername(String username);

    /**
     * Gets the firstname.
     *
     * @return the user's first name
     */
    String getFirstname();

    /**
     * Sets the firstname.
     *
     * @param firstname the first name
     */
    void setFirstname(String firstname);

    /**
     * Gets the lastname.
     *
     * @return the user's last name
     */
    String getLastname();

    /**
     * Sets the lastname.
     *
     * @param lastname the last name
     */
    void setLastname(String lastname);

    /**
     * Gets the email.
     *
     * @return the user's email address
     */
    String getEmail();

    /**
     * Sets the email.
     *
     * @param email the email address
     */
    void setEmail(String email);

    /**
     * Gets the password.
     *
     * @return the user's password
     */
    String getPassword();

    /**
     * Sets the password.
     *
     * @param password the password
     */
    void setPassword(String password);

    /**
     * The roles supported are user and admin.
     *
     * @return the set of roles for this user
     */
    UserRoleName[] getRoles();

    /**
     * Adds the role.
     *
     * @param role the role to add to the role set
     */
    void addRole(String role);

    /**
     * Check if the user has a particular role.
     *
     * @param role role that we are looking for
     * @return true if the user has the role
     */
    boolean hasRole(UserRoleName role);
}
