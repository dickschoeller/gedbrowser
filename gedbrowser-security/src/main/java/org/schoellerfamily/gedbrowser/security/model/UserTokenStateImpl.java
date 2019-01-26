package org.schoellerfamily.gedbrowser.security.model;

/**
 * Carries the state of a user's access token.
 */
public class UserTokenStateImpl implements UserTokenState {
    /**
     * Holds the access token.
     */
    private String accessToken;

    /**
     * Holds the expiration time.
     */
    private Long expiresIn;

    /**
     * Constructor.
     */
    public UserTokenStateImpl() {
        this.accessToken = "";
        this.expiresIn = Long.MIN_VALUE;
    }

    /**
     * Constructor.
     *
     * @param accessToken the token string
     * @param expiresIn the time until token expiration in ?????
     */
    public UserTokenStateImpl(final String accessToken, final long expiresIn) {
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getAccessToken() {
        return accessToken;
    }

    /**
     * Set the access token string.
     *
     * @param accessToken the token string
     */
    public void setAccessToken(final String accessToken) {
        this.accessToken = accessToken;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long getExpiresIn() {
        return expiresIn;
    }

    /**
     * Set the time of token expiration in ???
     *
     * @param expiresIn the time until token expiration in ???
     */
    public void setExpiresIn(final Long expiresIn) {
        this.expiresIn = expiresIn;
    }

}
