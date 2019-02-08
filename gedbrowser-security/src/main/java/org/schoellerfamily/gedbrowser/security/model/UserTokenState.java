package org.schoellerfamily.gedbrowser.security.model;

/**
 * @author Dick Schoeller
 */
public interface UserTokenState {

    /**
     * Get the access token string.
     *
     * @return the token string
     */
    String getAccessToken();

    /**
     * Get the time until token expiration in ?????
     *
     * @return the expiration time
     */
    Long getExpiresIn();
}
