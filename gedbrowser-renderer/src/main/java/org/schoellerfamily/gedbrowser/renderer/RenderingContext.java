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

    /**
     * Special case anonymous context.
     *
     * @return the created context
     */
    public static RenderingContext anonymous() {
        final User user2 = new User();
        user2.setUsername("Anonymous");
        user2.setFirstname("Al");
        user2.setLastname("Anonymous");
        return new RenderingContext(user2, false, false);
    }

    /**
     * Special case user context.
     *
     * @return the created context
     */
    public static RenderingContext user() {
        final User user2 = new User();
        user2.setUsername("User");
        user2.setFirstname("Ursula");
        user2.setLastname("User");
        return new RenderingContext(user2, true, false);
    }

    /**
     * Constructor with authorities.
     * @param user the user detail object
     * @param isUser whether it is an identified user
     * @param isAdmin whether this user is an administrator
     */
    public RenderingContext(final User user, final boolean isUser,
            final boolean isAdmin) {
        this.isUser = isUser;
        this.isAdmin = isAdmin;
        this.user = user;
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
}
