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
     * Constructor.
     */
    public AnonAuthentication() {
        super((Collection<? extends GrantedAuthority>) null);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public int hashCode() {
        return HASH_ROOT;
    }

    @Override
    public boolean equals(final Object obj) {
        return this == obj || (obj != null && getClass() == obj.getClass());
    }
}
