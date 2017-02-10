package org.schoellerfamily.gedbrowser.renderer;

/**
 * Keep track of the user context that we are rendering under.
 *
 * @author Dick Schoeller
 */
public final class RenderingContext {
    /** Whether this is an identified user. */
    private final boolean isUser;

    /** Whether this user is an administrator. */
    private final boolean isAdmin;

    /** User detail object for use in rendering. */
    private final User user;

    /** */
    private final ApplicationInfo appInfo;

    /**
     * Special case anonymous context.
     * @param appInfo the application info to use in the context
     *
     * @return the created context
     */
    public static RenderingContext anonymous(final ApplicationInfo appInfo) {
        final User user2 = new User();
        user2.setUsername("Anonymous");
        user2.setFirstname("Al");
        user2.setLastname("Anonymous");
        return new RenderingContext(user2, false, false, appInfo);
    }

    /**
     * Special case user context.
     * @param appInfo the application info to use in the context
     *
     * @return the created context
     */
    public static RenderingContext user(final ApplicationInfo appInfo) {
        final User user2 = new User();
        user2.setUsername("User");
        user2.setFirstname("Ursula");
        user2.setLastname("User");
        return new RenderingContext(user2, true, false, appInfo);
    }

    /**
     * Constructor with authorities.
     * @param user the user detail object
     * @param isUser whether it is an identified user
     * @param isAdmin whether this user is an administrator
     * @param appInfo provides common strings about the application
     */
    public RenderingContext(final User user, final boolean isUser,
            final boolean isAdmin, final ApplicationInfo appInfo) {
        this.isUser = isUser;
        this.isAdmin = isAdmin;
        this.user = user;
        this.appInfo = appInfo;
    }

    /**
     * @return the user's name
     */
    public String getName() {
        return user.getUsername();
    }

    /**
     * @return true if it is an identified user
     */
    public boolean isUser() {
        return isUser;
    }

    /**
     * @return true if the user is an administrator
     */
    public boolean isAdmin() {
        return isAdmin;
    }

    /**
     * @return user's first name
     */
    public String getFirstname() {
        return user.getFirstname();
    }

    /**
     * Check if the user has a particular role.
     *
     * @param role role that we are looking for
     * @return true if the user has the role
     */
    public boolean hasRole(final String role) {
        if (user == null) {
            return false;
        }
        return user.hasRole(role);
    }

    /**
     * @return the URL for the home link on the menu bar
     */
    public String getHomeURL() {
        return appInfo.getHomeURL();
    }

    /**
     * @return the maintainer's name
     */
    public String getMaintainerName() {
        return appInfo.getMaintainerName();
    }

    /**
     * @return the email for the product owner/maintainer
     */
    public String getMaintainerEmail() {
        return appInfo.getMaintainerEmail();
    }

    /**
     * @return the version string
     */
    public String getVersion() {
        return appInfo.getVersion();
    }
}
