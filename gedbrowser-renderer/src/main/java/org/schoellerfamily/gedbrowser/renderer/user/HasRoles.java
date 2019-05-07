package org.schoellerfamily.gedbrowser.renderer.user;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.schoellerfamily.gedbrowser.datamodel.users.UserRoleName;

/**
 * @author Dick Schoeller
 */
public class HasRoles {
    /** Logger. */
    private final transient Log logger = LogFactory.getLog(getClass());

    /** */
    private final Set<UserRoleName> roles = new HashSet<>();

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings({ "PMD.OptimizableToArrayCall" })
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
     * {@inheritDoc}
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

    /**
     * {@inheritDoc}
     */
    public boolean hasRole(final UserRoleName role) {
        return roles.contains(role);
    }

}
