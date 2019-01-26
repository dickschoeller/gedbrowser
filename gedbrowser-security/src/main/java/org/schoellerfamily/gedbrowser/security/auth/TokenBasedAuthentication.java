package org.schoellerfamily.gedbrowser.security.auth;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author Dick Schoeller
 */
public final class TokenBasedAuthentication
        extends AbstractAuthenticationToken {
    /** */
    private static final long serialVersionUID = 1L;
    /** */
    private String token;
    /** */
    private final UserDetails principle;

    /**
     * Constructor.
     *
     * @param principle the details about the user
     */
    public TokenBasedAuthentication(final UserDetails principle) {
        super(principle.getAuthorities());
        this.principle = principle;
    }

    /**
     * @return the token
     */
    public String getToken() {
        return token;
    }

    /**
     * @param token the new token
     */
    public void setToken(final String token) {
        this.token = token;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAuthenticated() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getCredentials() {
        return token;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserDetails getPrincipal() {
        return principle;
    }
}
