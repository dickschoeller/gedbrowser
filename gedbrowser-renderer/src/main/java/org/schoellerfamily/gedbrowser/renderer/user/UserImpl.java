package org.schoellerfamily.gedbrowser.renderer.user;

import java.util.HashSet;
import java.util.Set;

/**
 * A basic user record.
 *
 * @author Dick Schoeller
 */
public final class UserImpl implements User {
    /** */
    private String username;
    /** */
    private String firstname;
    /** */
    private String lastname;
    /** */
    private String email;
    /** */
    private String password;
    /** */
    private final Set<String> roles = new HashSet<>();

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username
     */
    public void setUsername(final String username) {
        this.username = username;
    }

    /**
     * @return the user's first name
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * @param firstname the user's first name
     */
    public void setFirstname(final String firstname) {
        this.firstname = firstname;
    }

    /**
     * @return the user's last name
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * @param lastname the user's last name
     */
    public void setLastname(final String lastname) {
        this.lastname = lastname;
    }

    /**
     * @return the user's email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the user's email address
     */
    public void setEmail(final String email) {
        this.email = email;
    }

    /**
     * @return the user's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the user's password
     */
    public void setPassword(final String password) {
        this.password = password;
    }

    /**
     * The roles supported are user and admin.
     *
     * @return the set of roles for this user
     */
    public String[] getRoles() {
        return roles.toArray(new String[roles.size()]);
    }

    /**
     * @param role the role to add to the role set
     */
    public void addRole(final String role) {
        roles.add(role);
    }

    /**
     * Clear the role set.
     */
    public void clearRoles() {
        roles.clear();
    }

    /**
     * Check if the user has a particular role.
     *
     * @param role role that we are looking for
     * @return true if the user has the role
     */
    public boolean hasRole(final String role) {
        return roles.contains(role);
    }
}
