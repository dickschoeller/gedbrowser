package org.schoellerfamily.gedbrowser.security.facade;

import org.schoellerfamily.gedbrowser.security.model.UserTokenState;

/**
 * Interface makes it easy to implement a facade around UserTokenState.
 *
 * @author Dick Schoeller
 */
public interface UserTokenStateFacade extends UserTokenState {
    /**
     * Get the wrapper's UserTokenState object.
     *
     * @return the user token state
     */
    UserTokenState getUserTokenState();

    /**
     * {@inheritDoc}
     */
    @Override
    default String getAccessToken() {
        final UserTokenState userTokenState = getUserTokenState();
        if (userTokenState == null) {
            return "";
        }
        return userTokenState.getAccessToken();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default Long getExpiresIn() {
        final UserTokenState userTokenState = getUserTokenState();
        if (userTokenState == null) {
            return Long.MIN_VALUE;
        }
        return userTokenState.getExpiresIn();
    }
}
