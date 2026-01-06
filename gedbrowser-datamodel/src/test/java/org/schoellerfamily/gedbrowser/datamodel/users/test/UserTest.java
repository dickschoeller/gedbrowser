package org.schoellerfamily.gedbrowser.datamodel.users.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.schoellerfamily.gedbrowser.datamodel.users.UserImpl;
import org.schoellerfamily.gedbrowser.datamodel.users.UserRoleName;

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
        assertNull(user.getUsername(), "Expected null user name");
    }

    /** */
    @Test
    public void testDefaultFirstname() {
        final UserImpl user = new UserImpl();
        assertNull(user.getFirstname(), "Expected null user first name");
    }

    /** */
    @Test
    public void testDefaultLastname() {
        final UserImpl user = new UserImpl();
        assertNull(user.getLastname(), "Expected null last name");
    }

    /** */
    @Test
    public void testDefaultEmail() {
        final UserImpl user = new UserImpl();
        assertNull(user.getEmail(), "Expected null email address");
    }

    /** */
    @Test
    public void testDefaultPassword() {
        final UserImpl user = new UserImpl();
        assertNull(user.getPassword(), "Expected null password");
    }

    /** */
    @Test
    public void testDefaultRoles() {
        final UserImpl user = new UserImpl();
        assertEquals(0, user.getRoles().length, "Expected empty roles list");
    }

    /** */
    @Test
    public void testUsername() {
        final UserImpl user = new UserImpl();
        user.setUsername("test");
        assertEquals("test", user.getUsername(), "Username mismatch");
    }

    /** */
    @Test
    public void testFirstname() {
        final UserImpl user = new UserImpl();
        user.setFirstname("first");
        assertEquals("first", user.getFirstname(), "First name mismatch");
    }

    /** */
    @Test
    public void testLastname() {
        final UserImpl user = new UserImpl();
        user.setLastname("last");
        assertEquals("last", user.getLastname(), "Last name mismatch");
    }

    /** */
    @Test
    public void testEmail() {
        final UserImpl user = new UserImpl();
        user.setEmail("test@test.com");
        assertEquals("test@test.com", user.getEmail(), "Email mismatch");
    }

    /** */
    @Test
    public void testPassword() {
        final UserImpl user = new UserImpl();
        user.setPassword("password");
        assertEquals("password", user.getPassword(), "Password mismatch");
    }

    /** */
    @Test
    public void testOneRole() {
        final UserImpl user = new UserImpl();
        user.addRole("USER");
        assertEquals(1, user.getRoles().length, "Expected 1 role");
    }

    /** */
    @Test
    public void testTwoRoles() {
        final UserImpl user = new UserImpl();
        user.addRole("USER");
        user.addRole("ADMIN");
        assertEquals(2, user.getRoles().length, "Expected 2 roles");
    }

    /** */
    @Test
    public void testRoleSet() {
        final UserImpl user = new UserImpl();
        user.addRole("USER");
        user.addRole("USER");
        assertEquals(1, user.getRoles().length, "Expected 1 role");
    }

    /** */
    @Test
    public void testOneRoleContent() {
        final UserImpl user = new UserImpl();
        user.addRole("USER");
        assertEquals(UserRoleName.USER, user.getRoles()[0], "Mismatched role");
    }

    /** */
    @Test
    public void testHasRole() {
        final UserImpl user = new UserImpl();
        user.addRole("USER");
        user.addRole("ADMIN");
        user.addRole("IDIOT");
        assertTrue(user.hasRole(UserRoleName.ADMIN), "Should have admin role");
    }

    /** */
    @Test
    public void testHasRoleNegative() {
        final UserImpl user = new UserImpl();
        user.addRole("USER");
        user.addRole("IDIOT");
        assertFalse(user.hasRole(UserRoleName.ADMIN), "Should not have admin role");
    }

    /** */
    @Test
    public void testClearRoles() {
        final UserImpl user = new UserImpl();
        user.addRole("USER");
        user.addRole("IDIOT");
        user.clearRoles();
        assertEquals(0, user.getRoles().length, "Expected empty roles");
    }
}
