package org.schoellerfamily.gedbrowser.security.model;

import java.util.Arrays;
import java.util.Collection;

import org.schoellerfamily.gedbrowser.datamodel.users.UserRoleName;
import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * A basic user record.
 *
 * @author Dick Schoeller
 */
public final class UserImpl extends HasRoles implements SecurityUser {
    /** */
    private static final long serialVersionUID = 1L;

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

    /**
     * Returns the username.
     *
     * @return the username
     */
    @Override
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username.
     *
     * @param username the username to use
     */
    public void setUsername(final String username) {
        this.username = username;
    }

    /**
     * Returns the firstname.
     *
     * @return the firstname
     */
    @Override
    public String getFirstname() {
        return firstname;
    }

    /**
     * Sets the firstname.
     *
     * @param firstname the firstname to use
     */
    public void setFirstname(final String firstname) {
        this.firstname = firstname;
    }

    /**
     * Returns the lastname.
     *
     * @return the lastname
     */
    @Override
    public String getLastname() {
        return lastname;
    }

    /**
     * Sets the lastname.
     *
     * @param lastname the lastname to use
     */
    public void setLastname(final String lastname) {
        this.lastname = lastname;
    }

    /**
     * Returns the email.
     *
     * @return the email
     */
    @Override
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email.
     *
     * @param email the email
     */
    public void setEmail(final String email) {
        this.email = email;
    }

    /**
     * Returns the password.
     *
     * @return the password
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password.
     *
     * @param password the password
     */
    @Override
    public void setPassword(final String password) {
        this.password = password;
    }

    /**
     * Indicates whether account non expired.
     *
     * @return true if the condition is met; otherwise false
     */
    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
      return true;
    }

    /**
     * Indicates whether account non locked.
     *
     * @return true if the condition is met; otherwise false
     */
    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
      return true;
    }

    /**
     * Indicates whether credentials non expired.
     *
     * @return true if the condition is met; otherwise false
     */
    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
      return true;
    }

    /**
     * Indicates whether enabled.
     *
     * @return true if the condition is met; otherwise false
     */
    @JsonIgnore
    @Override
    public boolean isEnabled() {
      return true;
    }

    /**
     * Returns the authorities.
     *
     * @return the authorities
     */
    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.stream(getRoles())
            .map(this::createAuthority)
            .toList();
    }

    private Authority createAuthority(final UserRoleName role) {
        return Authority.builder()
            .userRoleName(role)
            .build();
    }
}
