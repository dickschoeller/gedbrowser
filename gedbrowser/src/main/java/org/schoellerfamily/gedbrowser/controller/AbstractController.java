package org.schoellerfamily.gedbrowser.controller;

import org.schoellerfamily.gedbrowser.Users;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.schoellerfamily.gedbrowser.renderer.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author Dick Schoeller
 */
public class AbstractController {
    /**
     * @param users the known users
     * @return the rendering context
     */
    protected final RenderingContext createRenderingContext(final Users users) {
        final Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        final User user = users.get(authentication.getName());
        final RenderingContext renderingContext =
                new RenderingContextBuilder(authentication, user).build();
        return renderingContext;
    }
}
