package org.schoellerfamily.gedbrowser.security.model.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.schoellerfamily.gedbrowser.security.model.UserTokenState;
import org.schoellerfamily.gedbrowser.security.model.UserTokenStateImpl;

/**
 * @author Dick Schoeller
 */
public final class UserTokenStateTest {
    /** */
    @Test
    void testDefaultExpiration() {
        final UserTokenState userTokenState = new UserTokenStateImpl();
        assertEquals(Long.valueOf(Long.MIN_VALUE), userTokenState.getExpiresIn(),
            "Default should be min Long");
    }

    /** */
    @Test
    void testDefaultToken() {
        final UserTokenState userTokenState = new UserTokenStateImpl();
        assertEquals("", userTokenState.getAccessToken(), "Default should be empty string");
    }

    /** */
    @Test
    void testInitializedExpiration() {
        final UserTokenState userTokenState = new UserTokenStateImpl("token", 1L);
        assertEquals(Long.valueOf(1L), userTokenState.getExpiresIn(),
            "Value doesn't match what was passed to constructor");
    }

    /** */
    @Test
    void testInitializedToken() {
        final UserTokenState userTokenState = new UserTokenStateImpl("token", 1L);
        assertEquals("token", userTokenState.getAccessToken(),
            "Value doesn't match what was passed to constructor");
    }

    /** */
    @Test
    void testSetExpiration() {
        final UserTokenStateImpl userTokenState = new UserTokenStateImpl();
        userTokenState.setExpiresIn(1L);
        assertEquals(Long.valueOf(1L), userTokenState.getExpiresIn(),
            "Value doesn't match what was set");
    }

    /** */
    @Test
    void testSetToken() {
        final UserTokenStateImpl userTokenState = new UserTokenStateImpl();
        userTokenState.setAccessToken("token");
        assertEquals("token", userTokenState.getAccessToken(), "Value doesn't match what was set");
    }
}
