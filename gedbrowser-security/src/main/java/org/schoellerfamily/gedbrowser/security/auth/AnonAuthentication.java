package org.schoellerfamily.gedbrowser.security.auth;

import org.springframework.security.authentication.AbstractAuthenticationToken;

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
        super(null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getCredentials() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getPrincipal() {
        return null;
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
    public int hashCode() {
        return HASH_ROOT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        return (getClass() == obj.getClass());
    }
}
