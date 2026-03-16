package org.schoellerfamily.gedbrowser.security.auth;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

/**
 * @author Dick Schoeller
 */
public final class AnonAuthentication extends AbstractAuthenticationToken {
    /** */
    private static final long serialVersionUID = 1L;

    /** */
    private static final int HASH_ROOT = 7;

    /**
     * Creates a new AnonAuthentication.
     *
     */
    public AnonAuthentication() {
        super((Collection<? extends GrantedAuthority>) null);
    }

    /**
     * Gets the credentials.
     *
     * @return the credentials
     */
    @Override
    public Object getCredentials() {
        return null;
    }

    /**
     * Gets the principal.
     *
     * @return the principal
     */
    @Override
    public Object getPrincipal() {
        return null;
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
     * Checks whether h code.
     *
     * @return true if the condition is met; otherwise false
     */
    @Override
    public int hashCode() {
        return HASH_ROOT;
    }

    /**
     * Returns the boolean.
     *
     * @param obj the obj
     * @return the resulting boolean
     */
    @Override
    public boolean equals(final Object obj) {
        return this == obj || (obj != null && getClass() == obj.getClass());
    }
}
