package org.schoellerfamily.gedbrowser.security.model;

/**
 * @author Dick Schoeller
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
     * @return the username
     */
    public String getUsername() {
      return username;
    }

    /**
     * @param username the username
     */
    public void setUsername(final String username) {
      this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
      return password;
    }

    /**
     * @param password the password
     */
    public void setPassword(final String password) {
      this.password = password;
    }

    /**
     * @return the first name
     */
    public String getFirstname() {
      return firstname;
    }

    /**
     * @param firstname the first name
     */
    public void setFirstname(final String firstname) {
      this.firstname = firstname;
    }

    /**
     * @return the last name
     */
    public String getLastname() {
      return lastname;
    }

    /**
     * @param lastname the last name
     */
    public void setLastname(final String lastname) {
      this.lastname = lastname;
    }

    /**
     * @return the ID
     */
    public Long getId() {
      return id;
    }

    /**
     * @param id the ID
     */
    public void setId(final Long id) {
      this.id = id;
    }

    /**
     * @param email the email address
     */
    public void setEmail(final String email) {
        this.email = email;
    }

    /**
     * @return the email address
     */
    public String getEmail() {
        return email;
    }
}
