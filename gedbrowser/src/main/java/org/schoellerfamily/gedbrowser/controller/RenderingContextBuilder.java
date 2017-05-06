package org.schoellerfamily.gedbrowser.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.schoellerfamily.gedbrowser.analytics.calendar.CalendarProvider;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.schoellerfamily.gedbrowser.renderer.application.ApplicationInfo;
import org.schoellerfamily.gedbrowser.renderer.user.User;
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

    /** */
    private final ApplicationInfo appInfo;

    /** */
    private final CalendarProvider calendarProvider;

    /**
     * Constructor.
     *
     * @param authentication the authentication object
     * @param user the associated user detail object
     * @param appInfo supports rendering information about the application
     * @param calendarProvider the calendar provider to use
     */
    public RenderingContextBuilder(final Authentication authentication,
            final User user, final ApplicationInfo appInfo,
            final CalendarProvider calendarProvider) {
        this.authentication = authentication;
        this.user = user;
        this.appInfo = appInfo;
        this.calendarProvider = calendarProvider;
    }

    /**
     * Build the rendering context.
     *
     * @return the rendering context
     */
    public RenderingContext build() {
        logger.debug("Entering build");
        if (authentication == null) {
            return new RenderingContext(null, false, false, appInfo,
                    calendarProvider);
        }
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
        logger.debug("Exiting build");
        return new RenderingContext(user, isUser, isAdmin, appInfo,
                calendarProvider);
    }
}
