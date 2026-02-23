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
    private final String token;
    /** */
    private final UserDetails principle;

    /**
     * Constructor.
     *
     * @param principle the details about the user
     * @param token the new token
     */
    public TokenBasedAuthentication(final UserDetails principle, final String token) {
        super(principle.getAuthorities());
        this.principle = principle;
        this.token = token;
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public UserDetails getPrincipal() {
        return principle;
    }
}
