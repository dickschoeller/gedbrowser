package org.schoellerfamily.gedbrowser.security.model.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.schoellerfamily.gedbrowser.security.model.UserRequest;

/**
 * @author Dick Schoeller
 */
class UserRequestTest {
    /** */
    @Test
    void testDefaultUsername() {
        final UserRequest request = new UserRequest();
        assertNull(request.getUsername(), "should default to null");
    }

    /** */
    @Test
    void testDefaultFirstname() {
        final UserRequest request = new UserRequest();
        assertNull(request.getFirstname(), "should default to null");
    }

    /** */
    @Test
    void testDefaultLastname() {
        final UserRequest request = new UserRequest();
        assertNull(request.getLastname(), "should default to null");
    }

    /** */
    @Test
    void testDefaultPassword() {
        final UserRequest request = new UserRequest();
        assertNull(request.getPassword(), "should default to null");
    }

    /** */
    @Test
    void testDefaultEmail() {
        final UserRequest request = new UserRequest();
        assertNull(request.getEmail(), "should default to null");
    }

    /** */
    @Test
    void testDefaultId() {
        final UserRequest request = new UserRequest();
        assertNull(request.getId(), "should default to null");
    }

    /** */
    @Test
    void testSetUsername() {
        final UserRequest request = new UserRequest();
        request.setUsername("user");
        assertEquals("user", request.getUsername(), "should match set value");
    }

    /** */
    @Test
    void testSetFirstname() {
        final UserRequest request = new UserRequest();
        request.setFirstname("first");
        assertEquals("first", request.getFirstname(), "should match set value");
    }

    /** */
    @Test
    void testSetLastname() {
        final UserRequest request = new UserRequest();
        request.setLastname("last");
        assertEquals("last", request.getLastname(), "should match set value");
    }

    /** */
    @Test
    void testSetPassword() {
        final UserRequest request = new UserRequest();
        request.setPassword("pass");
        assertEquals("pass", request.getPassword(), "should match set value");
    }

    /** */
    @Test
    void testSetEmail() {
        final UserRequest request = new UserRequest();
        request.setEmail("email@email.com");
        assertEquals("email@email.com", request.getEmail(), "should match set value");
    }

    /** */
    @Test
    void testSetId() {
        final UserRequest request = new UserRequest();
        request.setId(Long.MAX_VALUE);
        assertEquals(Long.valueOf(Long.MAX_VALUE), request.getId(), "should match set value");
    }
}
