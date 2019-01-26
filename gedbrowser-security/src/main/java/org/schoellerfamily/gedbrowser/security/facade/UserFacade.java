package org.schoellerfamily.gedbrowser.security.facade;

import java.util.ArrayList;
import java.util.Collection;

import org.schoellerfamily.gedbrowser.security.model.User;
import org.schoellerfamily.gedbrowser.security.model.UserRoleName;
import org.springframework.security.core.GrantedAuthority;

/**
 * @author Dick Schoeller
 */
public interface UserFacade extends User {
    /**
     * Get the associated User object.
     *
     * @return the user
     */
    User getUser();

    /**
     * {@inheritDoc}
     */
    @Override
    default String getUsername() {
        if (getUser() == null) {
            return null;
        }
        return getUser().getUsername();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default String getFirstname() {
        if (getUser() == null) {
            return null;
        }
        return getUser().getFirstname();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default String getLastname() {
        if (getUser() == null) {
            return null;
        }
        return getUser().getLastname();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default String getEmail() {
        if (getUser() == null) {
            return null;
        }
        return getUser().getEmail();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default String getPassword() {
        if (getUser() == null) {
            return null;
        }
        return getUser().getPassword();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default void setPassword(final String password) {
        if (getUser() == null) {
            return;
        }
        getUser().setPassword(password);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default UserRoleName[] getRoles() {
        if (getUser() == null) {
            return new UserRoleName[0];
        }
        return getUser().getRoles();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default boolean hasRole(UserRoleName role) {
        if (getUser() == null) {
            return false;
        }
        return getUser().hasRole(role);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default boolean isAccountNonExpired() {
        if (getUser() == null) {
            return true;
        }
        return getUser().isAccountNonExpired();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default boolean isAccountNonLocked() {
        if (getUser() == null) {
            return true;
        }
        return getUser().isAccountNonLocked();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default boolean isCredentialsNonExpired() {
        if (getUser() == null) {
            return true;
        }
        return getUser().isCredentialsNonExpired();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default boolean isEnabled() {
        if (getUser() == null) {
            return true;
        }
        return getUser().isEnabled();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default Collection<? extends GrantedAuthority> getAuthorities() {
        if (getUser() == null) {
            return new ArrayList<>();
        }
        return getUser().getAuthorities();
    }
}
