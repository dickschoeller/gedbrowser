package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.schoellerfamily.gedbrowser.renderer.User;

/**
 * Test the User object. The only thing slightly complex is role management.
 *
 * @author Dick Schoeller
 */
public final class UserTest {
    /** */
    @Test
    public void testDefaultUsername() {
        final User user = new User();
        assertNull("Expected null user name", user.getUsername());
    }

    /** */
    @Test
    public void testDefaultFirstname() {
        final User user = new User();
        assertNull("Expected null user first name", user.getFirstname());
    }

    /** */
    @Test
    public void testDefaultLastname() {
        final User user = new User();
        assertNull("Expected null last name", user.getLastname());
    }

    /** */
    @Test
    public void testDefaultEmail() {
        final User user = new User();
        assertNull("Expected null email address", user.getEmail());
    }

    /** */
    @Test
    public void testDefaultPassword() {
        final User user = new User();
        assertNull("Expected null password", user.getPassword());
    }

    /** */
    @Test
    public void testDefaultRoles() {
        final User user = new User();
        assertEquals("Expected empty roles list", 0, user.getRoles().length);
    }

    /** */
    @Test
    public void testUsername() {
        final User user = new User();
        user.setUsername("test");
        assertEquals("Username mismatch", "test", user.getUsername());
    }

    /** */
    @Test
    public void testFirstname() {
        final User user = new User();
        user.setFirstname("first");
        assertEquals("First name mismatch", "first", user.getFirstname());
    }

    /** */
    @Test
    public void testLastname() {
        final User user = new User();
        user.setLastname("last");
        assertEquals("Last name mismatch", "last", user.getLastname());
    }

    /** */
    @Test
    public void testEmail() {
        final User user = new User();
        user.setEmail("test@test.com");
        assertEquals("Email mismatch", "test@test.com", user.getEmail());
    }

    /** */
    @Test
    public void testPassword() {
        final User user = new User();
        user.setPassword("password");
        assertEquals("Password mismatch", "password", user.getPassword());
    }

    /** */
    @Test
    public void testOneRole() {
        final User user = new User();
        user.addRole("USER");
        assertEquals("Expected 1 role", 1, user.getRoles().length);
    }

    /** */
    @Test
    public void testTwoRoles() {
        final User user = new User();
        user.addRole("USER");
        user.addRole("ADMIN");
        assertEquals("Expected 2 roles", 2, user.getRoles().length);
    }

    /** */
    @Test
    public void testRoleSet() {
        final User user = new User();
        user.addRole("USER");
        user.addRole("USER");
        assertEquals("Expected 1 role", 1, user.getRoles().length);
    }

    /** */
    @Test
    public void testOneRoleContent() {
        final User user = new User();
        user.addRole("USER");
        assertEquals("Mismatched role", "USER", user.getRoles()[0]);
    }

    /** */
    @Test
    public void testHasRole() {
        final User user = new User();
        user.addRole("USER");
        user.addRole("ADMIN");
        user.addRole("IDIOT");
        assertTrue("Should have admin role", user.hasRole("ADMIN"));
    }

    /** */
    @Test
    public void testHasRoleNegative() {
        final User user = new User();
        user.addRole("USER");
        user.addRole("IDIOT");
        assertFalse("Should not have admin role", user.hasRole("ADMIN"));
    }

    /** */
    @Test
    public void testClearRoles() {
        final User user = new User();
        user.addRole("USER");
        user.addRole("IDIOT");
        user.clearRoles();
        assertEquals("Expected empty roles", 0, user.getRoles().length);
    }
}
