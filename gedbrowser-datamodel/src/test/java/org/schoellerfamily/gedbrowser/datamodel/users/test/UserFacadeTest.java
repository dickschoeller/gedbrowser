package org.schoellerfamily.gedbrowser.datamodel.users.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.schoellerfamily.gedbrowser.datamodel.users.User;
import org.schoellerfamily.gedbrowser.datamodel.users.UserFacade;
import org.schoellerfamily.gedbrowser.datamodel.users.UserImpl;
import org.schoellerfamily.gedbrowser.datamodel.users.UserRoleName;

/**
 * @author Dick Schoeller
 */
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
        assertNull("Expected null user name", user.getUsername());
    }

    /** */
    @Test
    public void testDefaultFirstname() {
        final UserImpl userImpl = new UserImpl();
        final User user = new UserFacadeImpl(userImpl);
        assertNull("Expected null user first name", user.getFirstname());
    }

    /** */
    @Test
    public void testDefaultLastname() {
        final UserImpl userImpl = new UserImpl();
        final User user = new UserFacadeImpl(userImpl);
        assertNull("Expected null last name", user.getLastname());
    }

    /** */
    @Test
    public void testDefaultEmail() {
        final UserImpl userImpl = new UserImpl();
        final User user = new UserFacadeImpl(userImpl);
        assertNull("Expected null email address", user.getEmail());
    }

    /** */
    @Test
    public void testDefaultPassword() {
        final UserImpl userImpl = new UserImpl();
        final User user = new UserFacadeImpl(userImpl);
        assertNull("Expected null password", user.getPassword());
    }

    /** */
    @Test
    public void testDefaultRoles() {
        final UserImpl userImpl = new UserImpl();
        final User user = new UserFacadeImpl(userImpl);
        assertEquals("Expected empty roles list", 0, user.getRoles().length);
    }

    /** */
    @Test
    public void testUsername() {
        final UserImpl userImpl = new UserImpl();
        final User user = new UserFacadeImpl(userImpl);
        userImpl.setUsername("test");
        assertEquals("Username mismatch", "test", user.getUsername());
    }

    /** */
    @Test
    public void testFirstname() {
        final UserImpl userImpl = new UserImpl();
        final User user = new UserFacadeImpl(userImpl);
        userImpl.setFirstname("first");
        assertEquals("First name mismatch", "first", user.getFirstname());
    }

    /** */
    @Test
    public void testLastname() {
        final UserImpl userImpl = new UserImpl();
        final User user = new UserFacadeImpl(userImpl);
        userImpl.setLastname("last");
        assertEquals("Last name mismatch", "last", user.getLastname());
    }

    /** */
    @Test
    public void testEmail() {
        final UserImpl userImpl = new UserImpl();
        final User user = new UserFacadeImpl(userImpl);
        userImpl.setEmail("test@test.com");
        assertEquals("Email mismatch", "test@test.com", user.getEmail());
    }

    /** */
    @Test
    public void testPassword() {
        final UserImpl userImpl = new UserImpl();
        final User user = new UserFacadeImpl(userImpl);
        userImpl.setPassword("password");
        assertEquals("Password mismatch", "password", user.getPassword());
    }

    /** */
    @Test
    public void testSetUsername() {
        final UserImpl userImpl = new UserImpl();
        final User user = new UserFacadeImpl(userImpl);
        user.setUsername("test");
        assertEquals("Username mismatch", "test", user.getUsername());
    }

    /** */
    @Test
    public void testSetFirstname() {
        final UserImpl userImpl = new UserImpl();
        final User user = new UserFacadeImpl(userImpl);
        user.setFirstname("first");
        assertEquals("First name mismatch", "first", user.getFirstname());
    }

    /** */
    @Test
    public void testSetLastname() {
        final UserImpl userImpl = new UserImpl();
        final User user = new UserFacadeImpl(userImpl);
        user.setLastname("last");
        assertEquals("Last name mismatch", "last", user.getLastname());
    }

    /** */
    @Test
    public void testSetEmail() {
        final UserImpl userImpl = new UserImpl();
        final User user = new UserFacadeImpl(userImpl);
        user.setEmail("test@test.com");
        assertEquals("Email mismatch", "test@test.com", user.getEmail());
    }

    /** */
    @Test
    public void testSetPassword() {
        final UserImpl userImpl = new UserImpl();
        final User user = new UserFacadeImpl(userImpl);
        user.setPassword("password");
        assertEquals("Password mismatch", "password", user.getPassword());
    }

    /** */
    @Test
    public void testNullUserUsername() {
        final UserFacadeImpl user = new UserFacadeImpl(null);
        user.setUsername("test");
        assertNull("Username should be null", user.getUsername());
    }

    /** */
    @Test
    public void testNullUserFirstname() {
        final UserFacadeImpl user = new UserFacadeImpl(null);
        user.setFirstname("first");
        assertNull("First name should be null", user.getFirstname());
    }

    /** */
    @Test
    public void testNullUserLastname() {
        final UserFacadeImpl user = new UserFacadeImpl(null);
        user.setLastname("last");
        assertNull("Last name should be null", user.getLastname());
    }

    /** */
    @Test
    public void testNullUserEmail() {
        final UserFacadeImpl user = new UserFacadeImpl(null);
        user.setEmail("test@test.com");
        assertNull("Email should be null", user.getEmail());
    }

    /** */
    @Test
    public void testNullUserPassword() {
        final UserFacadeImpl user = new UserFacadeImpl(null);
        user.setPassword("password");
        assertNull("Password should be null", user.getPassword());
    }

    /** */
    @Test
    public void testOneRole() {
        final UserImpl userImpl = new UserImpl();
        final User user = new UserFacadeImpl(userImpl);
        userImpl.addRole("USER");
        assertEquals("Expected 1 role", 1, user.getRoles().length);
    }

    /** */
    @Test
    public void testTwoRoles() {
        final UserImpl userImpl = new UserImpl();
        final User user = new UserFacadeImpl(userImpl);
        userImpl.addRole("USER");
        user.addRole("ADMIN");
        assertEquals("Expected 2 roles", 2, user.getRoles().length);
    }

    /** */
    @Test
    public void testRoleSet() {
        final UserImpl userImpl = new UserImpl();
        final User user = new UserFacadeImpl(userImpl);
        userImpl.addRole("USER");
        userImpl.addRole("USER");
        assertEquals("Expected 1 role", 1, user.getRoles().length);
    }

    /** */
    @Test
    public void testOneRoleContent() {
        final UserImpl userImpl = new UserImpl();
        final User user = new UserFacadeImpl(userImpl);
        userImpl.addRole("USER");
        assertEquals("Mismatched role", UserRoleName.USER, user.getRoles()[0]);
    }

    /** */
    @Test
    public void testHasRole() {
        final UserImpl userImpl = new UserImpl();
        final User user = new UserFacadeImpl(userImpl);
        userImpl.addRole("USER");
        userImpl.addRole("ADMIN");
        userImpl.addRole("IDIOT");
        assertTrue("Should have admin role", user.hasRole(UserRoleName.ADMIN));
    }

    /** */
    @Test
    public void testWithUserRoleNames() {
        final UserImpl userImpl = new UserImpl();
        final User user = new UserFacadeImpl(userImpl);
        userImpl.addRole(UserRoleName.ADMIN);
        userImpl.addRole(UserRoleName.USER);
        assertTrue("Should have admin role", user.hasRole(UserRoleName.ADMIN));
    }

    /** */
    @Test
    public void testHasRoleNegative() {
        final UserImpl userImpl = new UserImpl();
        final User user = new UserFacadeImpl(userImpl);
        userImpl.addRole("USER");
        userImpl.addRole("IDIOT");
        assertFalse("Should not have admin role", user.hasRole(UserRoleName.ADMIN));
    }

    /** */
    @Test
    public void testClearRoles() {
        final UserImpl userImpl = new UserImpl();
        final User user = new UserFacadeImpl(userImpl);
        userImpl.addRole("USER");
        userImpl.addRole("IDIOT");
        userImpl.clearRoles();
        assertEquals("Expected empty roles", 0, user.getRoles().length);
    }

    /** */
    @Test
    public void testNullUserOneRole() {
        final User user = new UserFacadeImpl(null);
        user.addRole("USER");
        assertEquals("Expected 0 roles", 0, user.getRoles().length);
    }

    /** */
    @Test
    public void testNullTwoRoles() {
        final User user = new UserFacadeImpl(null);
        user.addRole("USER");
        user.addRole("ADMIN");
        assertEquals("Expected 0 roles", 0, user.getRoles().length);
    }

    /** */
    @Test
    public void testNullHasRole() {
        final User user = new UserFacadeImpl(null);
        user.addRole("USER");
        user.addRole("ADMIN");
        user.addRole("IDIOT");
        assertFalse("Should not have any roles", user.hasRole(UserRoleName.ADMIN));
    }
}
