package org.schoellerfamily.gedbrowser.datamodel.users;

/**
 * @author Dick Schoeller
 */
public interface User {
    /**
     * @return the user name
     */
    String getUsername();

    /**
     * @param username the user name
     */
    void setUsername(String username);

    /**
     * @return the user's first name
     */
    String getFirstname();

    /**
     * @param firstname the first name
     */
    void setFirstname(String firstname);

    /**
     * @return the user's last name
     */
    String getLastname();

    /**
     * @param lastname the last name
     */
    void setLastname(String lastname);

    /**
     * @return the user's email address
     */
    String getEmail();

    /**
     * @param email the email address
     */
    void setEmail(String email);

    /**
     * @return the user's password
     */
    String getPassword();

    /**
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
