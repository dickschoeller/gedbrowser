package org.schoellerfamily.gedbrowser.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.schoellerfamily.gedbrowser.renderer.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

/**
 * @author Dick Schoeller
 */
public final class RenderingContextBuilder {
    /** Logger. */
    private final transient Log logger = LogFactory.getLog(getClass());

    /** The authentication object that allows us to build the context. */
    private final Authentication authentication;

    /** The associated user object. */
    private final User user;

    /**
     * Constructor.
     *
     * @param authentication the authentication object
     * @param user the associated user detail object
     */
    public RenderingContextBuilder(final Authentication authentication,
            final User user) {
        this.authentication = authentication;
        this.user = user;
    }

    /**
     * Build the rendering context.
     *
     * @return the rendering context
     */
    public RenderingContext build() {
        logger.debug("Entering build");
        final String name = authentication.getName();
        boolean isUser = false;
        boolean isAdmin = false;
        for (final GrantedAuthority author : authentication.getAuthorities()) {
            switch (author.getAuthority()) {
            case "ROLE_ADMIN":
                isAdmin = true;
                break;
            case "ROLE_USER":
                if (!"guest".equals(name)) {
                    isUser = true;
                }
                break;
            default:
                break;
            }
        }
        final RenderingContext context =
                new RenderingContext(user, isUser, isAdmin);
        logger.debug("Exiting build");
        return context;
    }
}
