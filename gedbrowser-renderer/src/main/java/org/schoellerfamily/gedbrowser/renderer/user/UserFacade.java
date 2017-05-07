package org.schoellerfamily.gedbrowser.renderer.user;

/**
 * @author Dick Schoeller
 */
public interface UserFacade extends User {
    /**
     * Get the associated User object.
     *
     * @return the user
     */
    User getUser();

    /**
     * {@inheritDoc}
     */
    @Override
    default String getUsername() {
        if (getUser() == null) {
            return null;
        }
        return getUser().getUsername();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default String getFirstname() {
        if (getUser() == null) {
            return null;
        }
        return getUser().getFirstname();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default String getLastname() {
        if (getUser() == null) {
            return null;
        }
        return getUser().getLastname();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default String getEmail() {
        if (getUser() == null) {
            return null;
        }
        return getUser().getEmail();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default String getPassword() {
        if (getUser() == null) {
            return null;
        }
        return getUser().getPassword();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default String[] getRoles() {
        if (getUser() == null) {
            return new String[0];
        }
        return getUser().getRoles();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default boolean hasRole(String role) {
        if (getUser() == null) {
            return false;
        }
        return getUser().hasRole(role);
    }
}
