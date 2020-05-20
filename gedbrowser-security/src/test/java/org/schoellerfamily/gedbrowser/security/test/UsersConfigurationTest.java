package org.schoellerfamily.gedbrowser.security.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.schoellerfamily.gedbrowser.datamodel.users.UserRoleName;
import org.schoellerfamily.gedbrowser.reader.users.UsersReader;
import org.schoellerfamily.gedbrowser.security.model.SecurityUser;
import org.schoellerfamily.gedbrowser.security.model.SecurityUsers;
import org.schoellerfamily.gedbrowser.security.model.UserImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Dick Schoeller
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = { Application.class })
public final class UsersConfigurationTest {
    /**
     * The name of the user file for tests.
     */
    private static final String TEST_USER_FILE_CSV = "testUserFile.csv";

    /** */
    @Value("${gedbrowser.home:/var/lib/gedbrowser}")
    private transient String gedbrowserHome;

    /** */
    @Before
    public void before() {
        SecurityTestHelper.resetUserFile(
                gedbrowserHome + "/" + TEST_USER_FILE_CSV);
    }

    /** */
    @After
    public void after() {
        SecurityTestHelper.resetUserFile(
                gedbrowserHome + "/" + TEST_USER_FILE_CSV);
    }

    /** */
    @Test
    public void testUserFile() {
        final String userFile = gedbrowserHome + "/" + TEST_USER_FILE_CSV;
        final SecurityUsers users = readUserFile(userFile);
        final int expected = 2;
        final int actual = users.size();
        assertEquals("Found file should have two", expected, actual);
    }

    /** */
    @Test
    public void testUserFileNotFound() {
        final String userFile = gedbrowserHome + "/XYX";
        final SecurityUsers users = readUserFile(userFile);
        final int expected = 1;
        final int actual = users.size();
        assertEquals("Not found file should have only one",
                expected, actual);
    }

    /** */
    @Test
    public void testUserFileNotFoundContainsGues() {
        final String userFile = gedbrowserHome + "/XYX";
        final SecurityUsers users = readUserFile(userFile);
        final SecurityUser guest = users.get("guest");
        assertTrue("Should have role USER",
                guest.hasRole(UserRoleName.USER));
    }

    /** */
    @Test
    public void testUsersClear() {
        final String userFile = gedbrowserHome + "/" + TEST_USER_FILE_CSV;
        final SecurityUsers users = readUserFile(userFile);
        final int expected = 0;
        users.clear();
        final int actual = users.size();
        assertEquals("Found file should have 0, because we did clear", expected,
                actual);
    }

    /** */
    @Test
    public void testUsersAdd() {
        final String userFile = gedbrowserHome + "/" + TEST_USER_FILE_CSV;
        final SecurityUsers users = readUserFile(userFile);
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
        final String userFile = gedbrowserHome + "/" + TEST_USER_FILE_CSV;
        final SecurityUsers users = readUserFile(userFile);
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
        final String userFile = gedbrowserHome + "/" + TEST_USER_FILE_CSV;
        final SecurityUsers users = readUserFile(userFile);
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
        final String userFile = gedbrowserHome + "/" + TEST_USER_FILE_CSV;
        final SecurityUsers users = readUserFile(userFile);
        for (SecurityUser user: users) {
            assertTrue(
                    "Found file should not have user, because we did removed",
                    user.getUsername() != null);
        }
    }

    /**
     * @param userFile the user file to read
     * @return the set of users from the user file
     */
    private SecurityUsers readUserFile(final String userFile) {
        final UsersReader<SecurityUser, SecurityUsers> usersReader =
                new UsersReader<>();
        return (SecurityUsers) usersReader.readUserFile(userFile,
                () -> new SecurityUsers(userFile),
                () -> new UserImpl()
        );
    }
}
