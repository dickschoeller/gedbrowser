package org.schoellerfamily.gedbrowser.security.model.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.schoellerfamily.gedbrowser.security.facade.UserTokenStateFacade;
import org.schoellerfamily.gedbrowser.security.model.UserTokenState;
import org.schoellerfamily.gedbrowser.security.model.UserTokenStateImpl;

/**
 * @author Dick Schoeller
 */
public class UserTokenStateFacadeTest {
    /** */
    private static class UserTokenStateFacadeImpl
            implements UserTokenStateFacade {
        /**
         * Private class for testing the facade interface.
         */
        private final UserTokenState userTokenState;

        /**
         * @param userTokenState the wrapped state
         */
        UserTokenStateFacadeImpl(final UserTokenState userTokenState) {
            this.userTokenState = userTokenState;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public UserTokenState getUserTokenState() {
            return userTokenState;
        }
    }

    /** */
    @Test
    public void testDefaultExpiration() {
        final UserTokenState userTokenState = new UserTokenStateImpl();
        final UserTokenState facade = new UserTokenStateFacadeImpl(
                userTokenState);
        assertEquals("Default should be min Long", Long.valueOf(Long.MIN_VALUE),
                facade.getExpiresIn());
    }

    /** */
    @Test
    public void testDefaultToken() {
        final UserTokenState userTokenState = new UserTokenStateImpl();
        final UserTokenState facade = new UserTokenStateFacadeImpl(
                userTokenState);
        assertEquals("Default should be empty string", "",
                facade.getAccessToken());
    }

    /** */
    @Test
    public void testInitializedExpiration() {
        final UserTokenState userTokenState = new UserTokenStateImpl("token",
                1L);
        final UserTokenState facade = new UserTokenStateFacadeImpl(
                userTokenState);
        assertEquals("Value doesn't match what was passed to constructor",
                Long.valueOf(1L), facade.getExpiresIn());
    }

    /** */
    @Test
    public void testInitializedToken() {
        final UserTokenState userTokenState = new UserTokenStateImpl("token",
                1L);
        final UserTokenState facade = new UserTokenStateFacadeImpl(
                userTokenState);
        assertEquals("Value doesn't match what was passed to constructor",
                "token", facade.getAccessToken());
    }

    /** */
    @Test
    public void testSetExpiration() {
        final UserTokenStateImpl userTokenState = new UserTokenStateImpl();
        userTokenState.setExpiresIn(1L);
        final UserTokenState facade = new UserTokenStateFacadeImpl(
                userTokenState);
        assertEquals("Value doesn't match what was set", Long.valueOf(1L),
                facade.getExpiresIn());
    }

    /** */
    @Test
    public void testSetToken() {
        final UserTokenStateImpl userTokenState = new UserTokenStateImpl();
        userTokenState.setAccessToken("token");
        final UserTokenState facade = new UserTokenStateFacadeImpl(
                userTokenState);
        assertEquals("Value doesn't match what was set", "token",
                facade.getAccessToken());
    }

    /** */
    @Test
    public void testNullExpiration() {
        final UserTokenState facade = new UserTokenStateFacadeImpl(null);
        assertEquals("Default should be min Long", Long.valueOf(Long.MIN_VALUE),
                facade.getExpiresIn());
    }

    /** */
    @Test
    public void testNullToken() {
        final UserTokenState facade = new UserTokenStateFacadeImpl(null);
        assertEquals("Default should be empty string", "",
                facade.getAccessToken());
    }
}
