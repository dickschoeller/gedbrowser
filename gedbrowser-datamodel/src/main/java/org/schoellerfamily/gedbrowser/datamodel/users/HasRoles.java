package org.schoellerfamily.gedbrowser.datamodel.users;

import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;



/**
 * Represents has roles in the domain model.
 *
 * @author Richard Schoeller
 */
@Slf4j
@NoArgsConstructor
public class HasRoles {

    /**
     * Performs method.
     */
    private final Set<UserRoleName> roles = new HashSet<>();

    /**
     * Gets the roles.
     *
     * @return the roles
     */
    public UserRoleName[] getRoles() {
        return roles.toArray(new UserRoleName[0]);
    }

    /**
     * Executes add role.
     *
     * @param role the role
     */
    public void addRole(final UserRoleName role) {
        roles.add(role);
    }

    /**
     * Add a role to the collection.
     *
     * @param role the role
     */
    public void addRole(final String role) {
        try {
            roles.add(UserRoleName.valueOf(role));
        } catch (Exception e) {
            log.warn("Tried to add unrecognized role: {}", role);
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

    /**
     * Check if the user has a particular role.
     *
     * @param role role that we are looking for
     * @return true if the user has the role
     */
    public boolean hasRole(final UserRoleName role) {
        return roles.contains(role);
    }

}
