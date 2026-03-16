package org.schoellerfamily.gedbrowser.security.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.schoellerfamily.gedbrowser.datamodel.users.UserRoleName;

import lombok.extern.slf4j.Slf4j;



/**
 * Represents has roles.
 *
 * @author Richard Schoeller
 */
@Slf4j
public class HasRoles implements Serializable {
    /** */
    private static final long serialVersionUID = 1L;

    /** */
    private final Set<UserRoleName> roles = new HashSet<>();

    /**
     * Returns the roles.
     *
     * @return the roles
     */
    @SuppressWarnings({ "PMD.OptimizableToArrayCall" })
    public UserRoleName[] getRoles() {
        return roles.toArray(new UserRoleName[0]);
    }

    /**
     * Executes add role.
     *
     * @param role the role
     */
    public void addRole(final String role) {
        try {
            final UserRoleName valueOf = UserRoleName.valueOf(role);
            roles.add(valueOf);
        } catch (IllegalArgumentException e) {
            log.warn("Tried to add an unknown role type: {}", role);
        }
    }

    /**
     * Clear the role set.
     */
    public void clearRoles() {
        roles.clear();
    }

    /**
     * Indicates whether role is present.
     *
     * @param role the role
     * @return true if the condition is met; otherwise false
     */
    public boolean hasRole(final UserRoleName role) {
        return roles.contains(role);
    }

}
