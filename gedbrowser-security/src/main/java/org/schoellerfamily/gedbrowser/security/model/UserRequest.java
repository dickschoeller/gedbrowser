package org.schoellerfamily.gedbrowser.security.model;

/**
 * Represents a request for user.
 *
 * @author Richard Schoeller
 */
public class UserRequest {
    /**
     * The request ID.
     */
    private Long id;

    /**
     * The requested username.
     */
    private String username;

    /**
     * The requested password.
     */
    private String password;

    /**
     * The requested first name.
     */
    private String firstname;

    /**
     * The requested last name.
     */
    private String lastname;

    /**
     * The requested email.
     */
    private String email;

    /**
     * Gets the username.
     *
     * @return the username
     */
    public String getUsername() {
      return username;
    }

    /**
     * Sets the username.
     *
     * @param username the username to use
     */
    public void setUsername(final String username) {
      this.username = username;
    }

    /**
     * Gets the password.
     *
     * @return the password
     */
    public String getPassword() {
      return password;
    }

    /**
     * Sets the password.
     *
     * @param password the password
     */
    public void setPassword(final String password) {
      this.password = password;
    }

    /**
     * Gets the firstname.
     *
     * @return the firstname
     */
    public String getFirstname() {
      return firstname;
    }

    /**
     * Sets the firstname.
     *
     * @param firstname the firstname to use
     */
    public void setFirstname(final String firstname) {
      this.firstname = firstname;
    }

    /**
     * Gets the lastname.
     *
     * @return the lastname
     */
    public String getLastname() {
      return lastname;
    }

    /**
     * Sets the lastname.
     *
     * @param lastname the lastname to use
     */
    public void setLastname(final String lastname) {
      this.lastname = lastname;
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    public Long getId() {
      return id;
    }

    /**
     * Sets the id.
     *
     * @param id the unique identifier for the target
     */
    public void setId(final Long id) {
      this.id = id;
    }

    /**
     * Sets the email.
     *
     * @param email the email
     */
    public void setEmail(final String email) {
        this.email = email;
    }

    /**
     * Gets the email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }
}
