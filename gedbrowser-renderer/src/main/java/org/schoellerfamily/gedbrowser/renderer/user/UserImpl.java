package org.schoellerfamily.gedbrowser.renderer.user;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.schoellerfamily.gedbrowser.datamodel.users.User;
import org.schoellerfamily.gedbrowser.datamodel.users.UserRoleName;

/**
 * A basic user record.
 *
 * @author Dick Schoeller
 */
public final class UserImpl implements User {
    /** Logger. */
    private final transient Log logger = LogFactory.getLog(getClass());

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
    private final Set<UserRoleName> roles = new HashSet<>();

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
    public UserRoleName[] getRoles() {
        return roles.toArray(new UserRoleName[0]);
    }

    /**
     * @param role the role to add to the role set
     */
    public void addRole(final UserRoleName role) {
        roles.add(role);
    }

    /**
     * @param role the role to add to the role set
     */
    public void addRole(final String role) {
        try {
            roles.add(UserRoleName.valueOf(role));
        } catch (Exception e) {
            logger.warn("Tried to add unrecognized role: " + role);
        }
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
        return roles.contains(UserRoleName.valueOf(role));
    }

    public boolean hasRole(final UserRoleName role) {
        return roles.contains(role);
    }
}
