package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.schoellerfamily.gedbrowser.renderer.User;

/**
 * @author Dick Schoeller
 */
public final class UserTest {
    /** */
    @Test
    public void testDefaultUsername() {
        final User user = new User();
        assertNull(user.getUsername());
    }

    /** */
    @Test
    public void testDefaultFirstname() {
        final User user = new User();
        assertNull(user.getFirstname());
    }

    /** */
    @Test
    public void testDefaultLastname() {
        final User user = new User();
        assertNull(user.getLastname());
    }

    /** */
    @Test
    public void testDefaultEmail() {
        final User user = new User();
        assertNull(user.getEmail());
    }

    /** */
    @Test
    public void testDefaultPassword() {
        final User user = new User();
        assertNull(user.getPassword());
    }

    /** */
    @Test
    public void testDefaultRoles() {
        final User user = new User();
        assertEquals(0, user.getRoles().length);
    }

    /** */
    @Test
    public void testUsername() {
        final User user = new User();
        user.setUsername("test");
        assertEquals("test", user.getUsername());
    }

    /** */
    @Test
    public void testFirstname() {
        final User user = new User();
        user.setFirstname("first");
        assertEquals("first", user.getFirstname());
    }

    /** */
    @Test
    public void testLastname() {
        final User user = new User();
        user.setLastname("last");
        assertEquals("last", user.getLastname());
    }

    /** */
    @Test
    public void testEmail() {
        final User user = new User();
        user.setEmail("test@test.com");
        assertEquals("test@test.com", user.getEmail());
    }

    /** */
    @Test
    public void testPassword() {
        final User user = new User();
        user.setPassword("password");
        assertEquals("password", user.getPassword());
    }

    /** */
    @Test
    public void testOneRole() {
        final User user = new User();
        user.addRole("USER");
        assertEquals(1, user.getRoles().length);
    }

    /** */
    @Test
    public void testTwoRoles() {
        final User user = new User();
        user.addRole("USER");
        user.addRole("ADMIN");
        assertEquals(2, user.getRoles().length);
    }

    /** */
    @Test
    public void testRoleSet() {
        final User user = new User();
        user.addRole("USER");
        user.addRole("USER");
        assertEquals(1, user.getRoles().length);
    }

    /** */
    @Test
    public void testOneRoleContent() {
        final User user = new User();
        user.addRole("USER");
        assertEquals("USER", user.getRoles()[0]);
    }

    /** */
    @Test
    public void testHasRole() {
        final User user = new User();
        user.addRole("USER");
        user.addRole("ADMIN");
        user.addRole("IDIOT");
        assertTrue(user.hasRole("ADMIN"));
    }

    /** */
    @Test
    public void testHasRoleNegative() {
        final User user = new User();
        user.addRole("USER");
        user.addRole("IDIOT");
        assertFalse(user.hasRole("ADMIN"));
    }

    /** */
    @Test
    public void testClearRoles() {
        final User user = new User();
        user.addRole("USER");
        user.addRole("IDIOT");
        user.clearRoles();
        assertEquals(0, user.getRoles().length);
    }
}
