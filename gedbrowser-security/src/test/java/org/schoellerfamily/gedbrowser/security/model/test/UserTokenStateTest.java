package org.schoellerfamily.gedbrowser.security.model.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.schoellerfamily.gedbrowser.security.model.UserTokenState;
import org.schoellerfamily.gedbrowser.security.model.UserTokenStateImpl;

/**
 * @author Dick Schoeller
 */
public final class UserTokenStateTest {
    /** */
    @Test
    public void testDefaultExpiration() {
        final UserTokenState userTokenState = new UserTokenStateImpl();
        assertEquals("Default should be min Long", Long.valueOf(Long.MIN_VALUE),
                userTokenState.getExpiresIn());
    }

    /** */
    @Test
    public void testDefaultToken() {
        final UserTokenState userTokenState = new UserTokenStateImpl();
        assertEquals("Default should be empty string", "",
                userTokenState.getAccessToken());
    }

    /** */
    @Test
    public void testInitializedExpiration() {
        final UserTokenState userTokenState =
                new UserTokenStateImpl("token", 1L);
        assertEquals("Value doesn't match what was passed to constructor",
                Long.valueOf(1L), userTokenState.getExpiresIn());
    }

    /** */
    @Test
    public void testInitializedToken() {
        final UserTokenState userTokenState =
                new UserTokenStateImpl("token", 1L);
        assertEquals("Value doesn't match what was passed to constructor",
                "token", userTokenState.getAccessToken());
    }

    /** */
    @Test
    public void testSetExpiration() {
        final UserTokenStateImpl userTokenState = new UserTokenStateImpl();
        userTokenState.setExpiresIn(1L);
        assertEquals("Value doesn't match what was set", Long.valueOf(1L),
                userTokenState.getExpiresIn());
    }

    /** */
    @Test
    public void testSetToken() {
        final UserTokenStateImpl userTokenState = new UserTokenStateImpl();
        userTokenState.setAccessToken("token");
        assertEquals("Value doesn't match what was set", "token",
                userTokenState.getAccessToken());
    }
}
