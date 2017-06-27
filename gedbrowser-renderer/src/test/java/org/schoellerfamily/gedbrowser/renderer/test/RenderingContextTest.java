package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.schoellerfamily.gedbrowser.renderer.application.ApplicationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Dick Schoeller
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestConfiguration.class })
public class RenderingContextTest {
    /** */
    @Autowired
    private transient ApplicationInfo appInfo;

    /** */
    private RenderingContext anonymousContext;

    /** */
    @Before
    public void init() {
        anonymousContext = RenderingContext.anonymous(appInfo);
    }

    /** */
    @Test
    public final void testGetApplicationName() {
        assertEquals("Application name mistmatch", "gedbrowser",
                anonymousContext.getApplicationName());
    }

    /** */
    @Test
    public final void testGetApplicationURL() {
        assertEquals("Application URL mismatch",
                "https://github.com/dickschoeller/gedbrowser",
                anonymousContext.getApplicationURL());
    }

    /** */
    @Test
    public final void testGetEmail() {
        assertEquals("Email mismatch", "anon@gmail.com",
                anonymousContext.getEmail());
    }

    /** */
    @Test
    public final void testGetFirstname() {
        assertEquals("First name mismatch", "Al",
                anonymousContext.getFirstname());
    }

    /** */
    @Test
    public final void testGetHomeURL() {
        assertEquals("Home URL misamtch", "http://www.schoellerfamily.org/",
                anonymousContext.getHomeURL());
    }

    /** */
    @Test
    public final void testGetLastname() {
        assertEquals("Last name mismatch", "Anonymous",
                anonymousContext.getLastname());
    }

    /** */
    @Test
    public final void testGetMaintainerEmail() {
        assertEquals("Maintainer email mismatch", "schoeller@comcast.net",
                anonymousContext.getMaintainerEmail());
    }

    /** */
    @Test
    public final void testGetMaintainerName() {
        assertEquals("Maintainer name mismatch", "Richard Schoeller",
                anonymousContext.getMaintainerName());
    }

    /** */
    @Test
    public final void testGetPassword() {
        assertNull("Expected null password", anonymousContext.getPassword());
    }

    /** */
    @Test
    public final void testGetUsername() {
        assertEquals("Username mismatch", "Anonymous",
                anonymousContext.getUsername());
    }

    /** */
    @Test
    public final void testGetVersion() {
        assertEquals("Version mismatch", GedObject.VERSION,
                anonymousContext.getVersion());
    }

    /** */
    @Test
    public final void testGetRoles() {
        assertEquals("Expected empty roles", 0,
                anonymousContext.getRoles().length);
    }

    /** */
    @Test
    public final void testHasRole() {
        assertTrue("Expected user role",
                RenderingContext.user(appInfo).hasRole("USER"));
    }

    /** */
    @Test
    public final void testNullUserGetEmail() {
        final RenderingContext rc = new RenderingContext(null, null, null);
        assertNull("Expected null email", rc.getEmail());
    }

    /** */
    @Test
    public final void testNullUserGetFirstname() {
        final RenderingContext rc = new RenderingContext(null, null, null);
        assertNull("Expected null first name", rc.getFirstname());
    }

    /** */
    @Test
    public final void testNullUserGetLastname() {
        final RenderingContext rc = new RenderingContext(null, null, null);
        assertNull("Expected null last name", rc.getLastname());
    }

    /** */
    @Test
    public final void testNullUserGetPassword() {
        final RenderingContext rc = new RenderingContext(null, null, null);
        assertNull("Expected null password", rc.getPassword());
    }

    /** */
    @Test
    public final void testNullUserGetUsername() {
        final RenderingContext rc = new RenderingContext(null, null, null);
        assertNull("Expected null username",  rc.getUsername());
    }

    /** */
    @Test
    public final void testNullUserGetRoles() {
        final RenderingContext rc = new RenderingContext(null, null, null);
        assertEquals("Expected empty roles", 0, rc.getRoles().length);
    }

    /** */
    @Test
    public final void testNullUserHasRole() {
        final RenderingContext rc = new RenderingContext(null, null, null);
        assertFalse("Expected hasn't role user", rc.hasRole("USER"));
    }
}
