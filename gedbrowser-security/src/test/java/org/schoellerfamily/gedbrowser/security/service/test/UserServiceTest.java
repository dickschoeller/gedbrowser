package org.schoellerfamily.gedbrowser.security.service.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.schoellerfamily.gedbrowser.security.model.SecurityUser;
import org.schoellerfamily.gedbrowser.security.model.UserRequest;
import org.schoellerfamily.gedbrowser.security.service.UserService;
import org.schoellerfamily.gedbrowser.security.test.AbstractTest;
import org.schoellerfamily.gedbrowser.security.test.SecurityTestHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;

/**
 * @author Dick Schoeller
 */
public class UserServiceTest extends AbstractTest {

    /** */
    @Autowired
    private UserService userService;

    /** */
    @Value("${gedbrowser.home:/var/lib/gedbrowser}")
    private String gedbrowserHome;

    /**
     * Reset test file before.
     */
    @Before
    public void before() {
        SecurityTestHelper.resetUserFile(gedbrowserHome + "/testUserFile.csv");
    }

    /**
     * Reset test file after.
     */
    @After
    public void after() {
        SecurityTestHelper.resetUserFile(gedbrowserHome + "/testUserFile.csv");
    }

    /**
     * @throws AccessDeniedException if we can't get it
     */
    @Test(expected = AccessDeniedException.class)
    public void testFindAllWithoutUser() throws AccessDeniedException {
        userService.findAll();
    }

    /**
     * @throws AccessDeniedException if we can't get it
     */
    @Test(expected = AccessDeniedException.class)
    public void testFindAllWithUser() throws AccessDeniedException {
        mockAuthenticatedUser(buildTestUser());
        userService.findAll();
    }

    /**
     * @throws AccessDeniedException if we can't get it
     */
    @Test
    public void testFindAllWithAdmin() throws AccessDeniedException {
        mockAuthenticatedUser(buildTestAdmin());
        UserRequest request = new UserRequest();
        request.setUsername("dummy");
        request.setFirstname("John");
        request.setLastname("Dummy");
        request.setPassword("password");
        userService.save(request);
        final List<SecurityUser> list = userService.findAll();
        for (SecurityUser u : list) {
            if ("dummy".equals(u.getUsername())) {
                return;
            }
        }
        fail("should have found dummy");
    }

//    @Test(expected = AccessDeniedException.class)
//    public void testFindByIdWithoutUser() throws AccessDeniedException {
//      userService.findById(1L);
//    }
//
//    @Test(expected = AccessDeniedException.class)
//    public void testFindByIdWithUser() throws AccessDeniedException {
//      mockAuthenticatedUser(buildTestUser());
//      userService.findById(1L);
//    }
//
//    @Test
//    public void testFindByIdWithAdmin() throws AccessDeniedException {
//      mockAuthenticatedUser(buildTestAdmin());
//      userService.findById(1L);
//    }

    /**
     * @throws AccessDeniedException if we can't get it
     */
    @Test
    public void testFindByUsernameWithoutUser() throws AccessDeniedException {
        final SecurityUser user = userService.findByUsername("guest");
        assertEquals("Username doesn't match", "guest", user.getUsername());
    }

    /**
     * @throws AccessDeniedException if we can't get it
     */
    @Test
    public void testFindByUsernameWithUser() throws AccessDeniedException {
        mockAuthenticatedUser(buildTestUser());
        final SecurityUser user = userService.findByUsername("guest");
        assertEquals("Username doesn't match", "guest", user.getUsername());
    }

    /**
     * @throws AccessDeniedException if we can't get it
     */
    @Test
    public void testFindByUsernameWithAdmin() throws AccessDeniedException {
        mockAuthenticatedUser(buildTestAdmin());
        final SecurityUser user = userService.findByUsername("guest");
        assertEquals("Username doesn't match", "guest", user.getUsername());
    }

    /**
     * @throws AccessDeniedException if we can't get it
     */
    @Test
    public void testCreateWithAdmin() throws AccessDeniedException {
        mockAuthenticatedUser(buildTestAdmin());
        UserRequest request = new UserRequest();
        request.setUsername("user");
        request.setFirstname("John");
        request.setLastname("User");
        request.setPassword("password");
        final SecurityUser user = userService.save(request);
        assertEquals("Username doesn't match", "user", user.getUsername());
    }
}
