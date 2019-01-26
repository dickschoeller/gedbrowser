package org.schoellerfamily.gedbrowser.security.model.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.schoellerfamily.gedbrowser.security.facade.UserFacade;
import org.schoellerfamily.gedbrowser.security.model.User;
import org.schoellerfamily.gedbrowser.security.model.UserImpl;
import org.schoellerfamily.gedbrowser.security.model.UserRoleName;

/**
 * @author Dick Schoeller
 */
public class UserFacadeTest {
    /**
     * @author Dick Schoeller
     */
    private static class UserFacadeImpl implements UserFacade {
        /** */
        private static final long serialVersionUID = 1L;

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
        userImpl.addRole("ADMIN");
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
        assertEquals("Mismatched role", UserRoleName.ROLE_USER,
                user.getRoles()[0]);
    }

    /** */
    @Test
    public void testHasRole() {
        final UserImpl userImpl = new UserImpl();
        final User user = new UserFacadeImpl(userImpl);
        userImpl.addRole("USER");
        userImpl.addRole("ADMIN");
        userImpl.addRole("IDIOT");
        assertTrue("Should have admin role",
                user.hasRole(UserRoleName.ROLE_ADMIN));
    }

    /** */
    @Test
    public void testHasRoleNegative() {
        final UserImpl userImpl = new UserImpl();
        final User user = new UserFacadeImpl(userImpl);
        userImpl.addRole("USER");
        userImpl.addRole("IDIOT");
        assertFalse("Should not have admin role",
                user.hasRole(UserRoleName.ROLE_ADMIN));
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
    public void testSimpleAccountNonExpired() {
        final UserImpl userImpl = new UserImpl();
        final User user = new UserFacadeImpl(userImpl);
        assertTrue("Expected always true", user.isAccountNonExpired());
    }

    /** */
    @Test
    public void testSimpleAccountNonLocked() {
        final UserImpl userImpl = new UserImpl();
        final User user = new UserFacadeImpl(userImpl);
        assertTrue("Expected always true", user.isAccountNonLocked());
    }

    /** */
    @Test
    public void testSimpleEnabled() {
        final UserImpl userImpl = new UserImpl();
        final User user = new UserFacadeImpl(userImpl);
        assertTrue("Expected always true", user.isEnabled());
    }

    /** */
    @Test
    public void testSimpleCredNonExpired() {
        final UserImpl userImpl = new UserImpl();
        final User user = new UserFacadeImpl(userImpl);
        assertTrue("Expected always true", user.isCredentialsNonExpired());
    }

    /** */
    @Test
    public void testSimpleSetPassword() {
        final UserImpl userImpl = new UserImpl();
        final User user = new UserFacadeImpl(userImpl);
        user.setPassword("string");
        assertEquals("Expected matching password",
                "string", user.getPassword());
    }

    /** */
    @Test
    public void testSimpleEmptyAuthorities() {
        final UserImpl userImpl = new UserImpl();
        final User user = new UserFacadeImpl(userImpl);
        assertTrue("Expected null password", user.getAuthorities().isEmpty());
    }

    /** */
    @Test
    public void testSimpleWithRoleAuthorities() {
        final UserImpl userImpl = new UserImpl();
        final User user = new UserFacadeImpl(userImpl);
        userImpl.addRole("USER");
        assertEquals("Expected null password", 1, user.getAuthorities().size());
    }

    /** */
    @Test
    public void testNullUsername() {
        final User user = new UserFacadeImpl(null);
        assertNull("Expected null user name", user.getUsername());
    }

    /** */
    @Test
    public void testNullFirstname() {
        final User user = new UserFacadeImpl(null);
        assertNull("Expected null user first name", user.getFirstname());
    }

    /** */
    @Test
    public void testNullLastname() {
        final User user = new UserFacadeImpl(null);
        assertNull("Expected null last name", user.getLastname());
    }

    /** */
    @Test
    public void testNullEmail() {
        final User user = new UserFacadeImpl(null);
        assertNull("Expected null email address", user.getEmail());
    }

    /** */
    @Test
    public void testNullPassword() {
        final User user = new UserFacadeImpl(null);
        assertNull("Expected null password", user.getPassword());
    }

    /** */
    @Test
    public void testNullRoles() {
        final User user = new UserFacadeImpl(null);
        assertEquals("Expected empty roles list", 0, user.getRoles().length);
    }

    /** */
    @Test
    public void testNullHasRole() {
        final User user = new UserFacadeImpl(null);
        assertFalse("Expected to not have this role",
                user.hasRole(UserRoleName.ROLE_USER));
    }

    /** */
    @Test
    public void testNullAccountNonExpired() {
        final User user = new UserFacadeImpl(null);
        assertTrue("Expected always true", user.isAccountNonExpired());
    }

    /** */
    @Test
    public void testNullAccountNonLocked() {
        final User user = new UserFacadeImpl(null);
        assertTrue("Expected always true", user.isAccountNonLocked());
    }

    /** */
    @Test
    public void testNullEnabled() {
        final User user = new UserFacadeImpl(null);
        assertTrue("Expected always true", user.isEnabled());
    }

    /** */
    @Test
    public void testNullCredNonExpired() {
        final User user = new UserFacadeImpl(null);
        assertTrue("Expected always true", user.isCredentialsNonExpired());
    }

    /** */
    @Test
    public void testNullSetPassword() {
        final User user = new UserFacadeImpl(null);
        user.setPassword("string");
        assertNull("Expected null password", user.getPassword());
    }

    /** */
    @Test
    public void testNullAuthorities() {
        final User user = new UserFacadeImpl(null);
        assertTrue("Expected null password", user.getAuthorities().isEmpty());
    }
}
