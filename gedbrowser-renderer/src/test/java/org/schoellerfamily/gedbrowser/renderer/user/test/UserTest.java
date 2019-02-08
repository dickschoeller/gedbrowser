package org.schoellerfamily.gedbrowser.renderer.user.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.schoellerfamily.gedbrowser.datamodel.users.UserRoleName;
import org.schoellerfamily.gedbrowser.renderer.user.UserImpl;

/**
 * Test the User object. The only thing slightly complex is role management.
 *
 * @author Dick Schoeller
 */
public final class UserTest {
    /** */
    @Test
    public void testDefaultUsername() {
        final UserImpl user = new UserImpl();
        assertNull("Expected null user name", user.getUsername());
    }

    /** */
    @Test
    public void testDefaultFirstname() {
        final UserImpl user = new UserImpl();
        assertNull("Expected null user first name", user.getFirstname());
    }

    /** */
    @Test
    public void testDefaultLastname() {
        final UserImpl user = new UserImpl();
        assertNull("Expected null last name", user.getLastname());
    }

    /** */
    @Test
    public void testDefaultEmail() {
        final UserImpl user = new UserImpl();
        assertNull("Expected null email address", user.getEmail());
    }

    /** */
    @Test
    public void testDefaultPassword() {
        final UserImpl user = new UserImpl();
        assertNull("Expected null password", user.getPassword());
    }

    /** */
    @Test
    public void testDefaultRoles() {
        final UserImpl user = new UserImpl();
        assertEquals("Expected empty roles list", 0, user.getRoles().length);
    }

    /** */
    @Test
    public void testUsername() {
        final UserImpl user = new UserImpl();
        user.setUsername("test");
        assertEquals("Username mismatch", "test", user.getUsername());
    }

    /** */
    @Test
    public void testFirstname() {
        final UserImpl user = new UserImpl();
        user.setFirstname("first");
        assertEquals("First name mismatch", "first", user.getFirstname());
    }

    /** */
    @Test
    public void testLastname() {
        final UserImpl user = new UserImpl();
        user.setLastname("last");
        assertEquals("Last name mismatch", "last", user.getLastname());
    }

    /** */
    @Test
    public void testEmail() {
        final UserImpl user = new UserImpl();
        user.setEmail("test@test.com");
        assertEquals("Email mismatch", "test@test.com", user.getEmail());
    }

    /** */
    @Test
    public void testPassword() {
        final UserImpl user = new UserImpl();
        user.setPassword("password");
        assertEquals("Password mismatch", "password", user.getPassword());
    }

    /** */
    @Test
    public void testOneRole() {
        final UserImpl user = new UserImpl();
        user.addRole("USER");
        assertEquals("Expected 1 role", 1, user.getRoles().length);
    }

    /** */
    @Test
    public void testTwoRoles() {
        final UserImpl user = new UserImpl();
        user.addRole("USER");
        user.addRole("ADMIN");
        assertEquals("Expected 2 roles", 2, user.getRoles().length);
    }

    /** */
    @Test
    public void testRoleSet() {
        final UserImpl user = new UserImpl();
        user.addRole("USER");
        user.addRole("USER");
        assertEquals("Expected 1 role", 1, user.getRoles().length);
    }

    /** */
    @Test
    public void testOneRoleContent() {
        final UserImpl user = new UserImpl();
        user.addRole("USER");
        assertEquals("Mismatched role", UserRoleName.USER, user.getRoles()[0]);
    }

    /** */
    @Test
    public void testHasRole() {
        final UserImpl user = new UserImpl();
        user.addRole("USER");
        user.addRole("ADMIN");
        user.addRole("IDIOT");
        assertTrue("Should have admin role", user.hasRole("ADMIN"));
    }

    /** */
    @Test
    public void testHasRoleNegative() {
        final UserImpl user = new UserImpl();
        user.addRole("USER");
        user.addRole("IDIOT");
        assertFalse("Should not have admin role", user.hasRole("ADMIN"));
    }

    /** */
    @Test
    public void testClearRoles() {
        final UserImpl user = new UserImpl();
        user.addRole("USER");
        user.addRole("IDIOT");
        user.clearRoles();
        assertEquals("Expected empty roles", 0, user.getRoles().length);
    }
}
