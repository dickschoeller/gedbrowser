package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.users.UserRoleName;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.schoellerfamily.gedbrowser.renderer.application.ApplicationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;



/**
 * Contains tests for rendering context.
 *
 * @author Richard Schoeller
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { TestConfiguration.class })
class RenderingContextTest {
    /** */
    @Autowired
    private transient ApplicationInfo appInfo;

    /** */
    private RenderingContext anonymousContext;

    @BeforeEach
    void setUp() {
        anonymousContext = RenderingContext.anonymous(appInfo);
    }

    @Test
    void testGetApplicationName() {
        assertEquals("gedbrowser", anonymousContext.getApplicationName(),
            "Application name mistmatch");
    }

    @Test
    void testGetApplicationURL() {
        assertEquals("https://github.com/dickschoeller/gedbrowser",
            anonymousContext.getApplicationURL(), "Application URL mismatch");
    }

    @Test
    void testGetEmail() {
        assertEquals("anon@gmail.com", anonymousContext.getEmail(), "Email mismatch");
    }

    @Test
    void testGetFirstname() {
        assertEquals("Al", anonymousContext.getFirstname(), "First name mismatch");
    }

    @Test
    void testGetHomeURL() {
        assertEquals("http://www.schoellerfamily.org/", anonymousContext.getHomeURL(),
            "Home URL misamtch");
    }

    @Test
    void testGetLastname() {
        assertEquals("Anonymous", anonymousContext.getLastname(), "Last name mismatch");
    }

    @Test
    void testGetMaintainerEmail() {
        assertEquals("schoeller@comcast.net", anonymousContext.getMaintainerEmail(),
            "Maintainer email mismatch");
    }

    @Test
    void testGetMaintainerName() {
        assertEquals("Richard Schoeller", anonymousContext.getMaintainerName(),
            "Maintainer name mismatch");
    }

    @Test
    void testGetPassword() {
        assertNull(anonymousContext.getPassword(), "Expected null password");
    }

    @Test
    void testGetUsername() {
        assertEquals("Anonymous", anonymousContext.getUsername(), "Username mismatch");
    }

    @Test
    void testGetVersion() {
        assertEquals(GedObject.VERSION, anonymousContext.getVersion(), "Version mismatch");
    }

    @Test
    void testGetRoles() {
        assertEquals(0, anonymousContext.getRoles().length, "Expected empty roles");
    }

    @Test
    void testHasRole() {
        assertTrue(RenderingContext.user(appInfo).hasRole(UserRoleName.USER), "Expected user role");
    }

    @Test
    void testNullUserGetEmail() {
        final RenderingContext rc = new RenderingContext(null, null, null);
        assertNull(rc.getEmail(), "Expected null email");
    }

    @Test
    void testNullUserGetFirstname() {
        final RenderingContext rc = new RenderingContext(null, null, null);
        assertNull(rc.getFirstname(), "Expected null first name");
    }

    @Test
    void testNullUserGetLastname() {
        final RenderingContext rc = new RenderingContext(null, null, null);
        assertNull(rc.getLastname(), "Expected null last name");
    }

    @Test
    void testNullUserGetPassword() {
        final RenderingContext rc = new RenderingContext(null, null, null);
        assertNull(rc.getPassword(), "Expected null password");
    }

    @Test
    void testNullUserGetUsername() {
        final RenderingContext rc = new RenderingContext(null, null, null);
        assertNull(rc.getUsername(), "Expected null username");
    }

    @Test
    void testNullUserGetRoles() {
        final RenderingContext rc = new RenderingContext(null, null, null);
        assertEquals(0, rc.getRoles().length, "Expected empty roles");
    }

    @Test
    void testNullUserHasRole() {
        final RenderingContext rc = new RenderingContext(null, null, null);
        assertFalse(rc.hasRole(UserRoleName.USER), "Expected hasn't role user");
    }
}
