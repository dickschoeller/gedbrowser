package org.schoellerfamily.gedbrowser.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.schoellerfamily.gedbrowser.Users;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.schoellerfamily.gedbrowser.renderer.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author Dick Schoeller
 */
public class AbstractController {
    /** Logger. */
    private final transient Log logger = LogFactory.getLog(getClass());

    /**
     * @param users the known users
     * @return the rendering context
     */
    protected final RenderingContext createRenderingContext(final Users users) {
        logger.debug("Entering createRenderingContext");
        final Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        final User user = users.get(authentication.getName());
        final RenderingContext renderingContext =
                new RenderingContextBuilder(authentication, user).build();
        logger.debug("Exiting createRenderingContext");
        return renderingContext;
    }
}
