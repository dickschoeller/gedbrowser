package org.schoellerfamily.gedbrowser.security.model;

/**
 * Represents user token state impl.
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
     * Creates a new UserTokenStateImpl.
     */
    public UserTokenStateImpl() {
        this.accessToken = "";
        this.expiresIn = Long.MIN_VALUE;
    }

    /**
     * Creates a new UserTokenStateImpl.
     *
     * @param accessToken the access token
     * @param expiresIn the expires in
     */
    public UserTokenStateImpl(final String accessToken, final long expiresIn) {
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
    }

    /**
     * Returns the access token.
     *
     * @return the access token
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
     * Returns the expires in.
     *
     * @return the expires in
     */
    @Override
    public Long getExpiresIn() {
        return expiresIn;
    }

    /**
     * Set the time of token expiration in seconds.
     *
     * @param expiresIn the time until token expiration in seconds
     */
    public void setExpiresIn(final Long expiresIn) {
        this.expiresIn = expiresIn;
    }

}
