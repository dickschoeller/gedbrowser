package org.schoellerfamily.gedbrowser.security.model;

import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Dick Schoeller
 */
public final class Authority implements GrantedAuthority {
    /** */
    private static final long serialVersionUID = 1L;

    /**
     * The role represented by this authority.
     */
    private UserRoleName userRoleName;

    /**
     * Set the user role.
     *
     * @param name the role
     */
    public void setUserRoleName(final UserRoleName name) {
        this.userRoleName = name;
    }

    /**
     * @return the role
     */
    @JsonIgnore
    public UserRoleName getUserRoleName() {
        return userRoleName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getAuthority() {
        if (userRoleName == null) {
            return null;
        }
        return userRoleName.name();
    }
}
