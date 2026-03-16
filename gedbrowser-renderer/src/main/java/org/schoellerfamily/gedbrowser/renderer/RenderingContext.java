package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.analytics.calendar.CalendarProvider;
import org.schoellerfamily.gedbrowser.analytics.calendar.CalendarProviderFacade;
import org.schoellerfamily.gedbrowser.analytics.calendar.CalendarProviderStub;
import org.schoellerfamily.gedbrowser.datamodel.users.User;
import org.schoellerfamily.gedbrowser.datamodel.users.UserFacade;
import org.schoellerfamily.gedbrowser.datamodel.users.UserImpl;
import org.schoellerfamily.gedbrowser.datamodel.users.UserRoleName;
import org.schoellerfamily.gedbrowser.renderer.application.ApplicationInfo;
import org.schoellerfamily.gedbrowser.renderer.application.ApplicationInfoFacade;

import lombok.Getter;
import lombok.RequiredArgsConstructor;



/**
 * Represents rendering context.
 *
 * @author Richard Schoeller
 */
@RequiredArgsConstructor
@Getter
public final class RenderingContext
        implements ApplicationInfoFacade, CalendarProviderFacade, UserFacade {
    /** User detail object for use in rendering. */
    private final User user;

    /** */
    private final ApplicationInfo applicationInfo;

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
     * Checks whether user.
     *
     * @return true if the condition is met; otherwise false
     */
    public boolean isUser() {
        return user != null && user.hasRole(UserRoleName.USER);
    }

    /**
     * Checks whether admin.
     *
     * @return true if the condition is met; otherwise false
     */
    public boolean isAdmin() {
        return user != null && user.hasRole(UserRoleName.ADMIN);
    }
}
