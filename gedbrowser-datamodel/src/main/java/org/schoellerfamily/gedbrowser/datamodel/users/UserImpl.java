package org.schoellerfamily.gedbrowser.datamodel.users;

import lombok.NoArgsConstructor;

/**
 * Represents user impl in the domain model.
 *
 * @author Richard Schoeller
 */
@NoArgsConstructor
public final class UserImpl extends HasRoles implements User {

    /**
     * The username value.
     */
    private String username;

    /**
     * The firstname value.
     */
    private String firstname;

    /**
     * The lastname value.
     */
    private String lastname;

    /**
     * The email value.
     */
    private String email;

    /**
     * The password value.
     */
    private String password;

    /**
     * Gets the username.
     *
     * @return the username
     */
    @Override
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username.
     *
     * @param username the username to use
     */
    @Override
    public void setUsername(final String username) {
        this.username = username;
    }

    /**
     * Gets the firstname.
     *
     * @return the firstname
     */
    @Override
    public String getFirstname() {
        return firstname;
    }

    /**
     * Sets the firstname.
     *
     * @param firstname the firstname to use
     */
    @Override
    public void setFirstname(final String firstname) {
        this.firstname = firstname;
    }

    /**
     * Gets the lastname.
     *
     * @return the lastname
     */
    @Override
    public String getLastname() {
        return lastname;
    }

    /**
     * Sets the lastname.
     *
     * @param lastname the lastname to use
     */
    @Override
    public void setLastname(final String lastname) {
        this.lastname = lastname;
    }

    /**
     * Gets the email.
     *
     * @return the email
     */
    @Override
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email.
     *
     * @param email the email
     */
    @Override
    public void setEmail(final String email) {
        this.email = email;
    }

    /**
     * Gets the password.
     *
     * @return the password
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password.
     *
     * @param password the password
     */
    @Override
    public void setPassword(final String password) {
        this.password = password;
    }
}
