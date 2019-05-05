package org.schoellerfamily.gedbrowser.security.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.schoellerfamily.gedbrowser.datamodel.users.UserRoleName;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Dick Schoeller
 */
public class HasRoles implements Serializable {
    /** */
    private static final long serialVersionUID = 1L;

    /** Logger. */
    @JsonIgnore
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
    public void addRole(final String role) {
        try {
            final UserRoleName valueOf = UserRoleName.valueOf(role);
            roles.add(valueOf);
        } catch (IllegalArgumentException e) {
            logger.warn("Tried to add an unknown role type: " + role);
        }
    }

    /**
     * Clear the role set.
     */
    public void clearRoles() {
        roles.clear();
    }

    /**
     * {@inheritDoc}
     */
    public boolean hasRole(final UserRoleName role) {
        return roles.contains(role);
    }

}
