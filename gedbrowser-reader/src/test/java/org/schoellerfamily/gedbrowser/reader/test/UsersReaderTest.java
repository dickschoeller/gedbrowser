package org.schoellerfamily.gedbrowser.reader.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.schoellerfamily.gedbrowser.datamodel.users.User;
import org.schoellerfamily.gedbrowser.datamodel.users.UserImpl;
import org.schoellerfamily.gedbrowser.datamodel.users.Users;
import org.schoellerfamily.gedbrowser.datamodel.users.UsersImpl;
import org.schoellerfamily.gedbrowser.reader.users.UsersReader;

/**
 * Contains tests for users reader.
 *
 * @author Richard Schoeller
 */
class UsersReaderTest {
    private static final String TEST_USER_FILE_CSV = System.getProperty("gedbrowser.home",
        System.getProperty("user.dir") + "/src/test/resources") + "/testUserFile.csv";

    @Test
    void test() {
        final Users<User> users = readUserFile(TEST_USER_FILE_CSV);
        final int expected = 2;
        final int actual = users.size();
        assertEquals(expected, actual, "Number of users is wrong");
    }

    @Test
    void testBadFile() {
        final Users<User> users = readUserFile("foo.bar");
        final int expected = 1;
        final int actual = users.size();
        assertEquals(expected, actual, "Number of users is wrong");
    }

    private Users<User> readUserFile(final String userFile) {
        final UsersReader<User, Users<User>> usersReader = new UsersReader<>();
        return usersReader.readUserFile(userFile, () -> new UsersImpl<>(), () -> new UserImpl());
    }
}
