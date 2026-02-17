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

    @Override
    default String getUsername() {
        if (getUser() == null) {
            return null;
        }
        return getUser().getUsername();
    }

    @Override
    default void setUsername(final String username) {
        if (getUser() == null) {
            return;
        }
        getUser().setUsername(username);
    }

    @Override
    default String getFirstname() {
        if (getUser() == null) {
            return null;
        }
        return getUser().getFirstname();
    }

    @Override
    default void setFirstname(final String firstname) {
        if (getUser() == null) {
            return;
        }
        getUser().setFirstname(firstname);
    }

    @Override
    default String getLastname() {
        if (getUser() == null) {
            return null;
        }
        return getUser().getLastname();
    }

    @Override
    default void setLastname(final String lastname) {
        if (getUser() == null) {
            return;
        }
        getUser().setLastname(lastname);
    }

    @Override
    default String getEmail() {
        if (getUser() == null) {
            return null;
        }
        return getUser().getEmail();
    }

    @Override
    default void setEmail(final String email) {
        if (getUser() == null) {
            return;
        }
        getUser().setEmail(email);
    }

    @Override
    default String getPassword() {
        if (getUser() == null) {
            return null;
        }
        return getUser().getPassword();
    }

    @Override
    default void setPassword(final String password) {
        if (getUser() == null) {
            return;
        }
        getUser().setPassword(password);
    }

    @Override
    default UserRoleName[] getRoles() {
        if (getUser() == null) {
            return new UserRoleName[0];
        }
        return getUser().getRoles();
    }

    @Override
    default void addRole(final String role) {
        if (getUser() == null) {
            return;
        }
        getUser().addRole(role);
    }

    @Override
    default boolean hasRole(final UserRoleName role) {
        return getUser() != null && getUser().hasRole(role);
    }
}
