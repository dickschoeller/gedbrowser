package org.schoellerfamily.gedbrowser.security.model;

import org.schoellerfamily.gedbrowser.datamodel.users.UserRoleName;
import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Builder;
import lombok.Getter;

/**
 * @author Dick Schoeller
 */
@Builder
public final class Authority implements GrantedAuthority {
    /** */
    private static final long serialVersionUID = 1L;

    /**
     * The role represented by this authority.
     */
    @Getter
    @JsonIgnore
    private final UserRoleName userRoleName;

    /**
     * Returns the authority.
     *
     * @return the authority
     */
    @Override
    public String getAuthority() {
        if (userRoleName == null) {
            return null;
        }
        return "ROLE_" + userRoleName.name();
    }
}
