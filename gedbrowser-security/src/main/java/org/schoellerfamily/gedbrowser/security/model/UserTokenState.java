package org.schoellerfamily.gedbrowser.security.model;

/**
 * Defines the contract for user token state.
 *
 * @author Richard Schoeller
 */
public interface UserTokenState {

    /**
     * Get the access token string.
     *
     * @return the token string
     */
    String getAccessToken();

    /**
     * Get the time until token expiration in seconds.
     *
     * @return the expiration time
     */
    Long getExpiresIn();
}
