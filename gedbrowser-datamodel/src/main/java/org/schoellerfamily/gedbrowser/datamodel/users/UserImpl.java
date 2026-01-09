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

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void setUsername(final String username) {
        this.username = username;
    }

    @Override
    public String getFirstname() {
        return firstname;
    }

    @Override
    public void setFirstname(final String firstname) {
        this.firstname = firstname;
    }

    @Override
    public String getLastname() {
        return lastname;
    }

    @Override
    public void setLastname(final String lastname) {
        this.lastname = lastname;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(final String email) {
        this.email = email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(final String password) {
        this.password = password;
    }
}
