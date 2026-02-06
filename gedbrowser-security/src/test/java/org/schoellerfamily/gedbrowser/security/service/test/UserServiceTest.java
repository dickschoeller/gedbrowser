package org.schoellerfamily.gedbrowser.security.service.test;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.schoellerfamily.gedbrowser.security.model.SecurityUser;
import org.schoellerfamily.gedbrowser.security.model.UserRequest;
import org.schoellerfamily.gedbrowser.security.service.impl.UserServiceImpl;
import org.schoellerfamily.gedbrowser.security.test.AbstractTest;
import org.schoellerfamily.gedbrowser.security.test.SecurityTestHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;

/**
 * @author Dick Schoeller
 */
final class UserServiceTest extends AbstractTest {

    /** */
    @Autowired
    private UserServiceImpl userService;

    /** */
    @Value("${gedbrowser.home:#{ systemProperties['user.dir'] }/src/test/resources}")
    private String gedbrowserHome;

    @BeforeEach
    void setUp() {
        SecurityTestHelper.resetUserFile(gedbrowserHome + "/testUserFile.csv");
    }

    @AfterEach
    void tearDown() {
        SecurityTestHelper.resetUserFile(gedbrowserHome + "/testUserFile.csv");
    }

    @Test
    void testFindAllWithoutUser() throws AccessDeniedException {
        assertThatExceptionOfType(AccessDeniedException.class)
            .isThrownBy(() -> userService.findAll());
    }

    @Test
    void testFindAllWithUser() throws AccessDeniedException {
        mockAuthenticatedUser(buildTestUser());
        assertThatExceptionOfType(AccessDeniedException.class)
            .isThrownBy(() -> userService.findAll());
    }

    @Test
    void testFindAllWithAdmin() throws AccessDeniedException {
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
//    void testFindByIdWithoutUser() throws AccessDeniedException {
//      userService.findById(1L);
//    }
//
//    @Test(expected = AccessDeniedException.class)
//    void testFindByIdWithUser() throws AccessDeniedException {
//      mockAuthenticatedUser(buildTestUser());
//      userService.findById(1L);
//    }
//
//    @Test
//    void testFindByIdWithAdmin() throws AccessDeniedException {
//      mockAuthenticatedUser(buildTestAdmin());
//      userService.findById(1L);
//    }

    @Test
    void testFindByUsernameWithoutUser() throws AccessDeniedException {
        final SecurityUser user = userService.findByUsername("guest");
        assertEquals("guest", user.getUsername(), "Username doesn't match");
    }

    @Test
    void testFindByUsernameWithUser() throws AccessDeniedException {
        mockAuthenticatedUser(buildTestUser());
        final SecurityUser user = userService.findByUsername("guest");
        assertEquals("guest", user.getUsername(), "Username doesn't match");
    }

    @Test
    void testFindByUsernameWithAdmin() throws AccessDeniedException {
        mockAuthenticatedUser(buildTestAdmin());
        final SecurityUser user = userService.findByUsername("guest");
        assertEquals("guest", user.getUsername(), "Username doesn't match");
    }

    @Test
    void testCreateWithAdmin() throws AccessDeniedException {
        mockAuthenticatedUser(buildTestAdmin());
        UserRequest request = new UserRequest();
        request.setUsername("user");
        request.setFirstname("John");
        request.setLastname("User");
        request.setPassword("password");
        final SecurityUser user = userService.save(request);
        assertEquals("user", user.getUsername(), "Username doesn't match");
    }
}
