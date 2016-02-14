package org.schoellerfamily.gedbrowser.controller;

import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

/**
 * @author Dick Schoeller
 */
public final class RenderingContextBuilder {
    /** The authentication object that allows us to build the context. */
    private final Authentication authentication;

    /**
     * Constructor.
     *
     * @param authentication the authentication object
     */
    public RenderingContextBuilder(final Authentication authentication) {
        this.authentication = authentication;
    }

    /**
     * Build the rendering context.
     *
     * @return the rendering context
     */
    public RenderingContext build() {
        final String name = authentication.getName();
        boolean user = false;
        boolean admin = false;
        for (final GrantedAuthority author : authentication.getAuthorities()) {
            switch (author.getAuthority()) {
            case "ROLE_ADMIN":
                admin = true;
                break;
            case "ROLE_USER":
                if (!"guest".equals(name)) {
                    user = true;
                }
                break;
            default:
                break;
            }
        }
        return new RenderingContext(name, user, admin);
    }
}
