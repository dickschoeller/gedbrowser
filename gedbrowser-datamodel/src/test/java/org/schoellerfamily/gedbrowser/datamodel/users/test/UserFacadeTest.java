package org.schoellerfamily.gedbrowser.datamodel.users.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.schoellerfamily.gedbrowser.datamodel.users.User;
import org.schoellerfamily.gedbrowser.datamodel.users.UserFacade;
import org.schoellerfamily.gedbrowser.datamodel.users.UserImpl;
import org.schoellerfamily.gedbrowser.datamodel.users.UserRoleName;

/**
 * @author Dick Schoeller
 */
@SuppressWarnings("null")
public class UserFacadeTest {
    /**
     * @author Dick Schoeller
     */
    private static class UserFacadeImpl implements UserFacade {
        /** */
        private final User user;

        /**
         * @param user the user object that we are wrapping
         */
        UserFacadeImpl(final User user) {
            this.user = user;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public User getUser() {
            return user;
        }
    }

    /** */
    @Test
    public void testDefaultUsername() {
        final UserImpl userImpl = new UserImpl();
        final User user = new UserFacadeImpl(userImpl);
        assertNull(user.getUsername(), "Expected null user name");
    }

    /** */
    @Test
    public void testDefaultFirstname() {
        final UserImpl userImpl = new UserImpl();
        final User user = new UserFacadeImpl(userImpl);
        assertNull(user.getFirstname(), "Expected null user first name");
    }

    /** */
    @Test
    public void testDefaultLastname() {
        final UserImpl userImpl = new UserImpl();
        final User user = new UserFacadeImpl(userImpl);
        assertNull(user.getLastname(), "Expected null last name");
    }

    /** */
    @Test
    public void testDefaultEmail() {
        final UserImpl userImpl = new UserImpl();
        final User user = new UserFacadeImpl(userImpl);
        assertNull(user.getEmail(), "Expected null email address");
    }

    /** */
    @Test
    public void testDefaultPassword() {
        final UserImpl userImpl = new UserImpl();
        final User user = new UserFacadeImpl(userImpl);
        assertNull(user.getPassword(), "Expected null password");
    }

    /** */
    @Test
    public void testDefaultRoles() {
        final UserImpl userImpl = new UserImpl();
        final User user = new UserFacadeImpl(userImpl);
        assertEquals(0, user.getRoles().length, "Expected empty roles list");
    }

    /** */
    @Test
    public void testUsername() {
        final UserImpl userImpl = new UserImpl();
        final User user = new UserFacadeImpl(userImpl);
        userImpl.setUsername("test");
        assertEquals("test", user.getUsername(), "Username mismatch");
    }

    /** */
    @Test
    public void testFirstname() {
        final UserImpl userImpl = new UserImpl();
        final User user = new UserFacadeImpl(userImpl);
        userImpl.setFirstname("first");
        assertEquals("first", user.getFirstname(), "First name mismatch");
    }

    /** */
    @Test
    public void testLastname() {
        final UserImpl userImpl = new UserImpl();
        final User user = new UserFacadeImpl(userImpl);
        userImpl.setLastname("last");
        assertEquals("last", user.getLastname(), "Last name mismatch");
    }

    /** */
    @Test
    public void testEmail() {
        final UserImpl userImpl = new UserImpl();
        final User user = new UserFacadeImpl(userImpl);
        userImpl.setEmail("test@test.com");
        assertEquals("test@test.com", user.getEmail(), "Email mismatch");
    }

    /** */
    @Test
    public void testPassword() {
        final UserImpl userImpl = new UserImpl();
        final User user = new UserFacadeImpl(userImpl);
        userImpl.setPassword("password");
        assertEquals("password", user.getPassword(), "Password mismatch");
    }

    /** */
    @Test
    public void testSetUsername() {
        final UserImpl userImpl = new UserImpl();
        final User user = new UserFacadeImpl(userImpl);
        user.setUsername("test");
        assertEquals("test", user.getUsername(), "Username mismatch");
    }

    /** */
    @Test
    public void testSetFirstname() {
        final UserImpl userImpl = new UserImpl();
        final User user = new UserFacadeImpl(userImpl);
        user.setFirstname("first");
        assertEquals("first", user.getFirstname(), "First name mismatch");
    }

    /** */
    @Test
    public void testSetLastname() {
        final UserImpl userImpl = new UserImpl();
        final User user = new UserFacadeImpl(userImpl);
        user.setLastname("last");
        assertEquals("last", user.getLastname(), "Last name mismatch");
    }

    /** */
    @Test
    public void testSetEmail() {
        final UserImpl userImpl = new UserImpl();
        final User user = new UserFacadeImpl(userImpl);
        user.setEmail("test@test.com");
        assertEquals("test@test.com", user.getEmail(), "Email mismatch");
    }

    /** */
    @Test
    public void testSetPassword() {
        final UserImpl userImpl = new UserImpl();
        final User user = new UserFacadeImpl(userImpl);
        user.setPassword("password");
        assertEquals("password", user.getPassword(), "Password mismatch");
    }

    /** */
    @Test
    public void testNullUserUsername() {
        final UserFacadeImpl user = new UserFacadeImpl(null);
        user.setUsername("test");
        assertNull(user.getUsername(), "Username should be null");
    }

    /** */
    @Test
    public void testNullUserFirstname() {
        final UserFacadeImpl user = new UserFacadeImpl(null);
        user.setFirstname("first");
        assertNull(user.getFirstname(), "First name should be null");
    }

    /** */
    @Test
    public void testNullUserLastname() {
        final UserFacadeImpl user = new UserFacadeImpl(null);
        user.setLastname("last");
        assertNull(user.getLastname(), "Last name should be null");
    }

    /** */
    @Test
    public void testNullUserEmail() {
        final UserFacadeImpl user = new UserFacadeImpl(null);
        user.setEmail("test@test.com");
        assertNull(user.getEmail(), "Email should be null");
    }

    /** */
    @Test
    public void testNullUserPassword() {
        final UserFacadeImpl user = new UserFacadeImpl(null);
        user.setPassword("password");
        assertNull(user.getPassword(), "Password should be null");
    }

    /** */
    @Test
    public void testOneRole() {
        final UserImpl userImpl = new UserImpl();
        final User user = new UserFacadeImpl(userImpl);
        userImpl.addRole("USER");
        assertEquals(1, user.getRoles().length, "Expected 1 role");
    }

    /** */
    @Test
    public void testTwoRoles() {
        final UserImpl userImpl = new UserImpl();
        final User user = new UserFacadeImpl(userImpl);
        userImpl.addRole("USER");
        user.addRole("ADMIN");
        assertEquals(2, user.getRoles().length, "Expected 2 roles");
    }

    /** */
    @Test
    public void testRoleSet() {
        final UserImpl userImpl = new UserImpl();
        final User user = new UserFacadeImpl(userImpl);
        userImpl.addRole("USER");
        userImpl.addRole("USER");
        assertEquals(1, user.getRoles().length, "Expected 1 role");
    }

    /** */
    @Test
    public void testOneRoleContent() {
        final UserImpl userImpl = new UserImpl();
        final User user = new UserFacadeImpl(userImpl);
        userImpl.addRole("USER");
        assertEquals(UserRoleName.USER, user.getRoles()[0], "Mismatched role");
    }

    /** */
    @Test
    public void testHasRole() {
        final UserImpl userImpl = new UserImpl();
        final User user = new UserFacadeImpl(userImpl);
        userImpl.addRole("USER");
        userImpl.addRole("ADMIN");
        userImpl.addRole("IDIOT");
        assertTrue(user.hasRole(UserRoleName.ADMIN), "Should have admin role");
    }

    /** */
    @Test
    public void testWithUserRoleNames() {
        final UserImpl userImpl = new UserImpl();
        final User user = new UserFacadeImpl(userImpl);
        userImpl.addRole(UserRoleName.ADMIN);
        userImpl.addRole(UserRoleName.USER);
        assertTrue(user.hasRole(UserRoleName.ADMIN), "Should have admin role");
    }

    /** */
    @Test
    public void testHasRoleNegative() {
        final UserImpl userImpl = new UserImpl();
        final User user = new UserFacadeImpl(userImpl);
        userImpl.addRole("USER");
        userImpl.addRole("IDIOT");
        assertFalse(user.hasRole(UserRoleName.ADMIN), "Should not have admin role");
    }

    /** */
    @Test
    public void testClearRoles() {
        final UserImpl userImpl = new UserImpl();
        final User user = new UserFacadeImpl(userImpl);
        userImpl.addRole("USER");
        userImpl.addRole("IDIOT");
        userImpl.clearRoles();
        assertEquals(0, user.getRoles().length, "Expected empty roles");
    }

    /** */
    @Test
    public void testNullUserOneRole() {
        final User user = new UserFacadeImpl(null);
        user.addRole("USER");
        assertEquals(0, user.getRoles().length, "Expected 0 roles");
    }

    /** */
    @Test
    public void testNullTwoRoles() {
        final User user = new UserFacadeImpl(null);
        user.addRole("USER");
        user.addRole("ADMIN");
        assertEquals(0, user.getRoles().length, "Expected 0 roles");
    }

    /** */
    @Test
    public void testNullHasRole() {
        final User user = new UserFacadeImpl(null);
        user.addRole("USER");
        user.addRole("ADMIN");
        user.addRole("IDIOT");
        assertFalse(user.hasRole(UserRoleName.ADMIN), "Should not have any roles");
    }
}
