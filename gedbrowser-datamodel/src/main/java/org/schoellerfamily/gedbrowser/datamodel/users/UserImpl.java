package org.schoellerfamily.gedbrowser.datamodel.users;

/**
 * A basic user record.
 *
 * @author Dick Schoeller
 */
public final class UserImpl extends HasRoles implements User {
    /** */
    private String username;

    /** */
    private String firstname;

    /** */
    private String lastname;

    /** */
    private String email;

    /** */
    private String password;

    /**
     * {@inheritDoc}
     */
    @Override
    public String getUsername() {
        return username;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setUsername(final String username) {
        this.username = username;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getFirstname() {
        return firstname;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setFirstname(final String firstname) {
        this.firstname = firstname;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getLastname() {
        return lastname;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setLastname(final String lastname) {
        this.lastname = lastname;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getEmail() {
        return email;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setEmail(final String email) {
        this.email = email;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPassword(final String password) {
        this.password = password;
    }
}
