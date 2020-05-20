package org.schoellerfamily.gedbrowser.datamodel.users;

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
    default void setUsername(final String username) {
        if (getUser() == null) {
            return;
        }
        getUser().setUsername(username);
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
    default void setFirstname(final String firstname) {
        if (getUser() == null) {
            return;
        }
        getUser().setFirstname(firstname);
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
    default void setLastname(final String lastname) {
        if (getUser() == null) {
            return;
        }
        getUser().setLastname(lastname);
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
    default void setEmail(final String email) {
        if (getUser() == null) {
            return;
        }
        getUser().setEmail(email);
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
    default void setPassword(final String password) {
        if (getUser() == null) {
            return;
        }
        getUser().setPassword(password);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default UserRoleName[] getRoles() {
        if (getUser() == null) {
            return new UserRoleName[0];
        }
        return getUser().getRoles();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default void addRole(final String role) {
        if (getUser() == null) {
            return;
        }
        getUser().addRole(role);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default boolean hasRole(UserRoleName role) {
        if (getUser() == null) {
            return false;
        }
        return getUser().hasRole(role);
    }
}
