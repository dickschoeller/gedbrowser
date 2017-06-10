package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.analytics.calendar.CalendarProvider;
import org.schoellerfamily.gedbrowser.analytics.calendar.CalendarProviderFacade;
import org.schoellerfamily.gedbrowser.analytics.calendar.CalendarProviderStub;
import org.schoellerfamily.gedbrowser.renderer.application.ApplicationInfo;
import org.schoellerfamily.gedbrowser.renderer.application.ApplicationInfoFacade;
import org.schoellerfamily.gedbrowser.renderer.user.User;
import org.schoellerfamily.gedbrowser.renderer.user.UserFacade;
import org.schoellerfamily.gedbrowser.renderer.user.UserImpl;

/**
 * Keep track of the user context that we are rendering under.
 *
 * @author Dick Schoeller
 */
public final class RenderingContext
        implements ApplicationInfoFacade, CalendarProviderFacade, UserFacade {
    /** User detail object for use in rendering. */
    private final User user;

    /** */
    private final ApplicationInfo appInfo;

    /** */
    private final CalendarProvider calendarProvider;

    /**
     * Special case anonymous context.
     *
     * @param appInfo the application info to use in the context
     * @return the created context
     */
    public static RenderingContext anonymous(final ApplicationInfo appInfo) {
        return anonymous(appInfo, new CalendarProviderStub());
    }

    /**
     * Special case anonymous context.
     *
     * @param appInfo the application info to use in the context
     * @param provider the calendar provider to use in this context
     * @return the created context
     */
    public static RenderingContext anonymous(final ApplicationInfo appInfo,
            final CalendarProvider provider) {
        final UserImpl user2 = new UserImpl();
        user2.setUsername("Anonymous");
        user2.setFirstname("Al");
        user2.setLastname("Anonymous");
        user2.clearRoles();
        user2.setEmail("anon@gmail.com");
        return new RenderingContext(user2, appInfo, provider);
    }

    /**
     * Special case user context.
     * @param appInfo the application info to use in the context
     *
     * @return the created context
     */
    public static RenderingContext user(final ApplicationInfo appInfo) {
        final UserImpl user2 = new UserImpl();
        user2.setUsername("User");
        user2.setFirstname("Ursula");
        user2.setLastname("User");
        user2.clearRoles();
        user2.addRole("USER");
        return new RenderingContext(user2, appInfo, new CalendarProviderStub());
    }

    /**
     * Constructor with authorities.
     * @param user the user detail object
     * @param appInfo provides common strings about the application
     * @param calendarProvider the calendar provider to use
     */
    public RenderingContext(final User user, final ApplicationInfo appInfo,
            final CalendarProvider calendarProvider) {
        this.user = user;
        this.appInfo = appInfo;
        this.calendarProvider = calendarProvider;
    }

    /**
     * @return true if it is an identified user
     */
    public boolean isUser() {
        if (user == null) {
            return false;
        }
        return user.hasRole("USER");
    }

    /**
     * @return true if the user is an administrator
     */
    public boolean isAdmin() {
        if (user == null) {
            return false;
        }
        return user.hasRole("ADMIN");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User getUser() {
        return user;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CalendarProvider getCalendarProvider() {
        return calendarProvider;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ApplicationInfo getApplicationInfo() {
        return appInfo;
    }
}
