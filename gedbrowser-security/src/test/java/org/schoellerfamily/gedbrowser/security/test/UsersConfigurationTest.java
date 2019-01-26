package org.schoellerfamily.gedbrowser.security.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.schoellerfamily.gedbrowser.security.model.User;
import org.schoellerfamily.gedbrowser.security.model.UserImpl;
import org.schoellerfamily.gedbrowser.security.model.UserRoleName;
import org.schoellerfamily.gedbrowser.security.model.Users;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Dick Schoeller
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = { Application.class })
public final class UsersConfigurationTest {
    /** */
    @Value("${gedbrowser.home:/var/lib/gedbrowser}")
    private transient String gedbrowserHome;

    /** */
    @Test
    public void testUserFile() {
        final String userFile = gedbrowserHome + "/testUserFile.csv";
        final Users users = Users.Builder.build(userFile);
        final int expected = 2;
        final int actual = users.size();
        assertEquals("Found file should have two", expected, actual);
    }

    /** */
    @Test
    public void testUserFileNotFound() {
        final String userFile = gedbrowserHome + "/XYX";
        final Users users = Users.Builder.build(userFile);
        final int expected = 1;
        final int actual = users.size();
        assertEquals("Not found file should have only one",
                expected, actual);
    }

    /** */
    @Test
    public void testUserFileNotFoundContainsGues() {
        final String userFile = gedbrowserHome + "/XYX";
        final Users users = Users.Builder.build(userFile);
        final User guest = users.get("guest");
        assertTrue("Should have role USER",
                guest.hasRole(UserRoleName.ROLE_USER));
    }

    /** */
    @Test
    public void testUsersClear() {
        final String userFile = gedbrowserHome + "/testUserFile.csv";
        final Users users = Users.Builder.build(userFile);
        final int expected = 0;
        users.clear();
        final int actual = users.size();
        assertEquals("Found file should have 0, because we did clear", expected,
                actual);
    }

    /** */
    @Test
    public void testUsersAdd() {
        final String userFile = gedbrowserHome + "/testUserFile.csv";
        final Users users = Users.Builder.build(userFile);
        final int expected = 3;
        final UserImpl user = new UserImpl();
        user.setUsername("add-username");
        user.setPassword("password");
        users.add(user);
        final int actual = users.size();
        assertEquals("Found file should have 3, because we did add", expected,
                actual);
    }

    /** */
    @Test
    public void testUsersGet() {
        final String userFile = gedbrowserHome + "/testUserFile.csv";
        final Users users = Users.Builder.build(userFile);
        final UserImpl user = new UserImpl();
        user.setUsername("add-username");
        user.setPassword("password");
        users.add(user);
        assertEquals("Found file should have user, because we did add",
                user, users.get("add-username"));
    }

    /** */
    @Test
    public void testUsersAddRemoveGet() {
        final String userFile = gedbrowserHome + "/testUserFile.csv";
        final Users users = Users.Builder.build(userFile);
        final UserImpl user = new UserImpl();
        user.setUsername("add-username");
        user.setPassword("password");
        users.add(user);
        users.remove(user);
        assertNull("Found file should not have user, because we did removed",
                users.get("add-username"));
    }

    /** */
    @Test
    public void testUsersIterator() {
        final String userFile = gedbrowserHome + "/testUserFile.csv";
        final Users users = Users.Builder.build(userFile);
        for (User user: users) {
            assertTrue(
                    "Found file should not have user, because we did removed",
                    user.getUsername() != null);
        }
    }
}
