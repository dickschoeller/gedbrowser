package org.schoellerfamily.gedbrowser.security.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.schoellerfamily.gedbrowser.datamodel.users.UserRoleName;
import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * A basic user record.
 *
 * @author Dick Schoeller
 */
public final class UserImpl implements SecurityUser {
    /** */
    private static final long serialVersionUID = 1L;

    /** Logger. */
    @JsonIgnore
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
    private String password = null;
    /** */
    private final Set<UserRoleName> roles = new HashSet<>();

    /**
     * {@inheritDoc}
     */
    @Override
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
     * {@inheritDoc}
     */
    @Override
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
     * {@inheritDoc}
     */
    @Override
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
     * {@inheritDoc}
     */
    @Override
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
     * {@inheritDoc}
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPassword(final String password) {
        this.password = password;
    }

    /**
     * {@inheritDoc}
     */
    @Override
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
    @Override
    public boolean hasRole(final UserRoleName role) {
        return roles.contains(role);
    }

    /**
     * {@inheritDoc}
     */
    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
      return true;
    }

    /**
     * {@inheritDoc}
     */
    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
      return true;
    }

    /**
     * {@inheritDoc}
     */
    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
      return true;
    }

    /**
     * {@inheritDoc}
     */
    @JsonIgnore
    @Override
    public boolean isEnabled() {
      return true;
    }

    /**
     * {@inheritDoc}
     */
    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        final List<GrantedAuthority> authorities = new ArrayList<>();
        for (final UserRoleName role : roles) {
            authorities.add(createAuthority(role));
        }
        return authorities;
    }

    /**
     * @param role the role to map to an authority
     * @return the authority
     */
    private Authority createAuthority(final UserRoleName role) {
        final Authority authority = new Authority();
        authority.setUserRoleName(role);
        return authority;
    }
}
