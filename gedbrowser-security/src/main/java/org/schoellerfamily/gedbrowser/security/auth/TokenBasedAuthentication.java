package org.schoellerfamily.gedbrowser.security.auth;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Represents token based authentication.
 *
 * @author Richard Schoeller
 */
public final class TokenBasedAuthentication
        extends AbstractAuthenticationToken {
    /** */
    private static final long serialVersionUID = 1L;
    /** */
    private final String token;
    /** */
    private final UserDetails principle;

    /**
     * Creates a new TokenBasedAuthentication.
     *
     * @param principle the principle
     * @param token the token
     */
    public TokenBasedAuthentication(final UserDetails principle, final String token) {
        super(principle.getAuthorities());
        this.principle = principle;
        this.token = token;
    }

    /**
     * Checks whether authenticated.
     *
     * @return true if the condition is met; otherwise false
     */
    @Override
    public boolean isAuthenticated() {
        return true;
    }

    /**
     * Gets the credentials.
     *
     * @return the credentials
     */
    @Override
    public Object getCredentials() {
        return token;
    }

    /**
     * Gets the principal.
     *
     * @return the principal
     */
    @Override
    public UserDetails getPrincipal() {
        return principle;
    }
}
