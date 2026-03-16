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
class UserFacadeTest {
    /**
     * @author Dick Schoeller
     */
    private static class UserFacadeImpl implements UserFacade {
        /** */
        private final User user;

        UserFacadeImpl(final User user) {
            this.user = user;
        }

        @Override
        public User getUser() {
            return user;
        }
    }

    @Test
    void testDefaultUsername() {
        final UserImpl userImpl = new UserImpl();
        final User user = new UserFacadeImpl(userImpl);
        assertNull(user.getUsername(), "Expected null user name");
    }

    @Test
    void testDefaultFirstname() {
        final UserImpl userImpl = new UserImpl();
        final User user = new UserFacadeImpl(userImpl);
        assertNull(user.getFirstname(), "Expected null user first name");
    }

    @Test
    void testDefaultLastname() {
        final UserImpl userImpl = new UserImpl();
        final User user = new UserFacadeImpl(userImpl);
        assertNull(user.getLastname(), "Expected null last name");
    }

    @Test
    void testDefaultEmail() {
        final UserImpl userImpl = new UserImpl();
        final User user = new UserFacadeImpl(userImpl);
        assertNull(user.getEmail(), "Expected null email address");
    }

    @Test
    void testDefaultPassword() {
        final UserImpl userImpl = new UserImpl();
        final User user = new UserFacadeImpl(userImpl);
        assertNull(user.getPassword(), "Expected null password");
    }

    @Test
    void testDefaultRoles() {
        final UserImpl userImpl = new UserImpl();
        final User user = new UserFacadeImpl(userImpl);
        assertEquals(0, user.getRoles().length, "Expected empty roles list");
    }

    @Test
    void testUsername() {
        final UserImpl userImpl = new UserImpl();
        final User user = new UserFacadeImpl(userImpl);
        userImpl.setUsername("test");
        assertEquals("test", user.getUsername(), "Username mismatch");
    }

    @Test
    void testFirstname() {
        final UserImpl userImpl = new UserImpl();
        final User user = new UserFacadeImpl(userImpl);
        userImpl.setFirstname("first");
        assertEquals("first", user.getFirstname(), "First name mismatch");
    }

    @Test
    void testLastname() {
        final UserImpl userImpl = new UserImpl();
        final User user = new UserFacadeImpl(userImpl);
        userImpl.setLastname("last");
        assertEquals("last", user.getLastname(), "Last name mismatch");
    }

    @Test
    void testEmail() {
        final UserImpl userImpl = new UserImpl();
        final User user = new UserFacadeImpl(userImpl);
        userImpl.setEmail("test@test.com");
        assertEquals("test@test.com", user.getEmail(), "Email mismatch");
    }

    @Test
    void testPassword() {
        final UserImpl userImpl = new UserImpl();
        final User user = new UserFacadeImpl(userImpl);
        userImpl.setPassword("password");
        assertEquals("password", user.getPassword(), "Password mismatch");
    }

    @Test
    void testSetUsername() {
        final UserImpl userImpl = new UserImpl();
        final User user = new UserFacadeImpl(userImpl);
        user.setUsername("test");
        assertEquals("test", user.getUsername(), "Username mismatch");
    }

    @Test
    void testSetFirstname() {
        final UserImpl userImpl = new UserImpl();
        final User user = new UserFacadeImpl(userImpl);
        user.setFirstname("first");
        assertEquals("first", user.getFirstname(), "First name mismatch");
    }

    @Test
    void testSetLastname() {
        final UserImpl userImpl = new UserImpl();
        final User user = new UserFacadeImpl(userImpl);
        user.setLastname("last");
        assertEquals("last", user.getLastname(), "Last name mismatch");
    }

    @Test
    void testSetEmail() {
        final UserImpl userImpl = new UserImpl();
        final User user = new UserFacadeImpl(userImpl);
        user.setEmail("test@test.com");
        assertEquals("test@test.com", user.getEmail(), "Email mismatch");
    }

    @Test
    void testSetPassword() {
        final UserImpl userImpl = new UserImpl();
        final User user = new UserFacadeImpl(userImpl);
        user.setPassword("password");
        assertEquals("password", user.getPassword(), "Password mismatch");
    }

    @Test
    void testNullUserUsername() {
        final UserFacadeImpl user = new UserFacadeImpl(null);
        user.setUsername("test");
        assertNull(user.getUsername(), "Username should be null");
    }

    @Test
    void testNullUserFirstname() {
        final UserFacadeImpl user = new UserFacadeImpl(null);
        user.setFirstname("first");
        assertNull(user.getFirstname(), "First name should be null");
    }

    @Test
    void testNullUserLastname() {
        final UserFacadeImpl user = new UserFacadeImpl(null);
        user.setLastname("last");
        assertNull(user.getLastname(), "Last name should be null");
    }

    @Test
    void testNullUserEmail() {
        final UserFacadeImpl user = new UserFacadeImpl(null);
        user.setEmail("test@test.com");
        assertNull(user.getEmail(), "Email should be null");
    }

    @Test
    void testNullUserPassword() {
        final UserFacadeImpl user = new UserFacadeImpl(null);
        user.setPassword("password");
        assertNull(user.getPassword(), "Password should be null");
    }

    @Test
    void testOneRole() {
        final UserImpl userImpl = new UserImpl();
        final User user = new UserFacadeImpl(userImpl);
        userImpl.addRole("USER");
        assertEquals(1, user.getRoles().length, "Expected 1 role");
    }

    @Test
    void testTwoRoles() {
        final UserImpl userImpl = new UserImpl();
        final User user = new UserFacadeImpl(userImpl);
        userImpl.addRole("USER");
        user.addRole("ADMIN");
        assertEquals(2, user.getRoles().length, "Expected 2 roles");
    }

    @Test
    void testRoleSet() {
        final UserImpl userImpl = new UserImpl();
        final User user = new UserFacadeImpl(userImpl);
        userImpl.addRole("USER");
        userImpl.addRole("USER");
        assertEquals(1, user.getRoles().length, "Expected 1 role");
    }

    @Test
    void testOneRoleContent() {
        final UserImpl userImpl = new UserImpl();
        final User user = new UserFacadeImpl(userImpl);
        userImpl.addRole("USER");
        assertEquals(UserRoleName.USER, user.getRoles()[0], "Mismatched role");
    }

    @Test
    void testHasRole() {
        final UserImpl userImpl = new UserImpl();
        final User user = new UserFacadeImpl(userImpl);
        userImpl.addRole("USER");
        userImpl.addRole("ADMIN");
        userImpl.addRole("IDIOT");
        assertTrue(user.hasRole(UserRoleName.ADMIN), "Should have admin role");
    }

    @Test
    void testWithUserRoleNames() {
        final UserImpl userImpl = new UserImpl();
        final User user = new UserFacadeImpl(userImpl);
        userImpl.addRole(UserRoleName.ADMIN);
        userImpl.addRole(UserRoleName.USER);
        assertTrue(user.hasRole(UserRoleName.ADMIN), "Should have admin role");
    }

    @Test
    void testHasRoleNegative() {
        final UserImpl userImpl = new UserImpl();
        final User user = new UserFacadeImpl(userImpl);
        userImpl.addRole("USER");
        userImpl.addRole("IDIOT");
        assertFalse(user.hasRole(UserRoleName.ADMIN), "Should not have admin role");
    }

    @Test
    void testClearRoles() {
        final UserImpl userImpl = new UserImpl();
        final User user = new UserFacadeImpl(userImpl);
        userImpl.addRole("USER");
        userImpl.addRole("IDIOT");
        userImpl.clearRoles();
        assertEquals(0, user.getRoles().length, "Expected empty roles");
    }

    @Test
    void testNullUserOneRole() {
        final User user = new UserFacadeImpl(null);
        user.addRole("USER");
        assertEquals(0, user.getRoles().length, "Expected 0 roles");
    }

    @Test
    void testNullTwoRoles() {
        final User user = new UserFacadeImpl(null);
        user.addRole("USER");
        user.addRole("ADMIN");
        assertEquals(0, user.getRoles().length, "Expected 0 roles");
    }

    @Test
    void testNullHasRole() {
        final User user = new UserFacadeImpl(null);
        user.addRole("USER");
        user.addRole("ADMIN");
        user.addRole("IDIOT");
        assertFalse(user.hasRole(UserRoleName.ADMIN), "Should not have any roles");
    }
}
