package org.schoellerfamily.gedbrowser.security.model.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.schoellerfamily.gedbrowser.security.model.UserRequest;

/**
 * @author Dick Schoeller
 */
public class UserRequestTest {
    /** */
    @Test
    public void testDefaultUsername() {
        final UserRequest request = new UserRequest();
        assertNull("should default to null", request.getUsername());
    }

    /** */
    @Test
    public void testDefaultFirstname() {
        final UserRequest request = new UserRequest();
        assertNull("should default to null", request.getFirstname());
    }

    /** */
    @Test
    public void testDefaultLastname() {
        final UserRequest request = new UserRequest();
        assertNull("should default to null", request.getLastname());
    }

    /** */
    @Test
    public void testDefaultPassword() {
        final UserRequest request = new UserRequest();
        assertNull("should default to null", request.getPassword());
    }

    /** */
    @Test
    public void testDefaultEmail() {
        final UserRequest request = new UserRequest();
        assertNull("should default to null", request.getEmail());
    }

    /** */
    @Test
    public void testDefaultId() {
        final UserRequest request = new UserRequest();
        assertNull("should default to null", request.getId());
    }

    /** */
    @Test
    public void testSetUsername() {
        final UserRequest request = new UserRequest();
        request.setUsername("user");
        assertEquals("should match set value", "user", request.getUsername());
    }

    /** */
    @Test
    public void testSetFirstname() {
        final UserRequest request = new UserRequest();
        request.setFirstname("first");
        assertEquals("should match set value", "first", request.getFirstname());
    }

    /** */
    @Test
    public void testSetLastname() {
        final UserRequest request = new UserRequest();
        request.setLastname("last");
        assertEquals("should match set value", "last", request.getLastname());
    }

    /** */
    @Test
    public void testSetPassword() {
        final UserRequest request = new UserRequest();
        request.setPassword("pass");
        assertEquals("should match set value", "pass", request.getPassword());
    }

    /** */
    @Test
    public void testSetEmail() {
        final UserRequest request = new UserRequest();
        request.setEmail("email@email.com");
        assertEquals("should match set value",
                "email@email.com", request.getEmail());
    }

    /** */
    @Test
    public void testSetId() {
        final UserRequest request = new UserRequest();
        request.setId(Long.MAX_VALUE);
        assertEquals("should match set value", Long.valueOf(Long.MAX_VALUE),
                request.getId());
    }
}
