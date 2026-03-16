package org.schoellerfamily.gedbrowser.security.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.schoellerfamily.gedbrowser.datamodel.users.UserRoleName;
import org.schoellerfamily.gedbrowser.reader.users.UsersReader;
import org.schoellerfamily.gedbrowser.security.model.SecurityUser;
import org.schoellerfamily.gedbrowser.security.model.SecurityUsers;
import org.schoellerfamily.gedbrowser.security.model.UserImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Dick Schoeller
 */
@SpringBootTest(classes = { Application.class })
public final class UsersConfigurationTest {
    /**
     * The name of the user file for tests.
     */
    private static final String TEST_USER_FILE_CSV = "testUserFile.csv";

    /** */
    @Value("${gedbrowser.home:#{ systemProperties['user.dir'] }/src/test/resources}")
    private transient String gedbrowserHome;

    @BeforeEach
    void setUp() {
        SecurityTestHelper.resetUserFile(gedbrowserHome + "/" + TEST_USER_FILE_CSV);
    }

    @AfterEach
    void tearDown() {
        SecurityTestHelper.resetUserFile(gedbrowserHome + "/" + TEST_USER_FILE_CSV);
    }

    @Test
    void testUserFile() {
        final String userFile = gedbrowserHome + "/" + TEST_USER_FILE_CSV;
        final SecurityUsers users = readUserFile(userFile);
        final int expected = 2;
        final int actual = users.size();
        assertEquals(expected, actual, "Found file should have two");
    }

    @Test
    void testUserFileNotFound() {
        final String userFile = gedbrowserHome + "/XYX";
        final SecurityUsers users = readUserFile(userFile);
        final int expected = 1;
        final int actual = users.size();
        assertEquals(expected, actual, "Not found file should have only one");
    }

    @Test
    void testUserFileNotFoundContainsGues() {
        final String userFile = gedbrowserHome + "/XYX";
        final SecurityUsers users = readUserFile(userFile);
        final SecurityUser guest = users.get("guest");
        assertTrue(guest.hasRole(UserRoleName.USER), "Should have role USER");
    }

    @Test
    void testUsersClear() {
        final String userFile = gedbrowserHome + "/" + TEST_USER_FILE_CSV;
        final SecurityUsers users = readUserFile(userFile);
        final int expected = 0;
        users.clear();
        final int actual = users.size();
        assertEquals(expected, actual, "Found file should have 0, because we did clear");
    }

    @Test
    void testUsersAdd() {
        final String userFile = gedbrowserHome + "/" + TEST_USER_FILE_CSV;
        final SecurityUsers users = readUserFile(userFile);
        final int expected = 3;
        final UserImpl user = new UserImpl();
        user.setUsername("add-username");
        user.setPassword("password");
        users.add(user);
        final int actual = users.size();
        assertEquals(expected, actual, "Found file should have 3, because we did add");
    }

    @Test
    void testUsersGet() {
        final String userFile = gedbrowserHome + "/" + TEST_USER_FILE_CSV;
        final SecurityUsers users = readUserFile(userFile);
        final UserImpl user = new UserImpl();
        user.setUsername("add-username");
        user.setPassword("password");
        users.add(user);
        assertEquals(user, users.get("add-username"),
            "Found file should have user, because we did add");
    }

    @Test
    void testUsersAddRemoveGet() {
        final String userFile = gedbrowserHome + "/" + TEST_USER_FILE_CSV;
        final SecurityUsers users = readUserFile(userFile);
        final UserImpl user = new UserImpl();
        user.setUsername("add-username");
        user.setPassword("password");
        users.add(user);
        users.remove(user);
        assertNull(users.get("add-username"),
            "Found file should not have user, because we did removed");
    }

    @Test
    void testUsersIterator() {
        final String userFile = gedbrowserHome + "/" + TEST_USER_FILE_CSV;
        final SecurityUsers users = readUserFile(userFile);
        for (final SecurityUser user : users) {
            assertNotNull(user.getUsername(), "Should never be a null username");
        }
    }

    private SecurityUsers readUserFile(final String userFile) {
        final UsersReader<SecurityUser, SecurityUsers> usersReader = new UsersReader<>();
        return (SecurityUsers) usersReader.readUserFile(userFile, () -> new SecurityUsers(userFile),
            () -> new UserImpl());
    }
}
