package org.schoellerfamily.gedbrowser.renderer;

/**
 * Keep track of the user context that we are rendering under.
 *
 * @author Dick Schoeller
 */
public final class RenderingContext {
    /** Name of the current user. */
    private final String name;

    /** Whether this is an identified user. */
    private final boolean user;

    /** Whether this user is an administrator. */
    private final boolean admin;

    /**
     * Special case anonymous context.
     *
     * @return the created context
     */
    public static RenderingContext anonymous() {
        return new RenderingContext("Anonymous", false, false);
    }

    /**
     * Special case user context.
     *
     * @return the created context
     */
    public static RenderingContext user() {
        return new RenderingContext("User", true, false);
    }

    /**
     * Constructor with authorities.
     *
     * @param name the name of this user
     * @param user whether it is an identified user
     * @param admin whether this user is an administrator
     */
    public RenderingContext(final String name, final boolean user,
            final boolean admin) {
        this.name = name;
        this.user = user;
        this.admin = admin;
    }

    /**
     * @return the user's name
     */
    public String getName() {
        return name;
    }

    /**
     * @return true if it is an identified user
     */
    public boolean isUser() {
        return user;
    }

    /**
     * @return true if the user is an administrator
     */
    public boolean isAdmin() {
        return admin;
    }
}
