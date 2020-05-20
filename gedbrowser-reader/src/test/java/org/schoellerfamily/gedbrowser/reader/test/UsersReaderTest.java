package org.schoellerfamily.gedbrowser.reader.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.schoellerfamily.gedbrowser.datamodel.users.User;
import org.schoellerfamily.gedbrowser.datamodel.users.UserImpl;
import org.schoellerfamily.gedbrowser.datamodel.users.Users;
import org.schoellerfamily.gedbrowser.datamodel.users.UsersImpl;
import org.schoellerfamily.gedbrowser.reader.users.UsersReader;

/**
 * @author Dick Schoeller
 */
public class UsersReaderTest {
    /**
     * The name of the user file for tests.
     */
    private static final String TEST_USER_FILE_CSV = "/var/lib/gedbrowser/testUserFile.csv";

    @Test
    public void test() {
        final Users<User> users = readUserFile(TEST_USER_FILE_CSV);
        final int expected = 2;
        final int actual = users.size();
        assertEquals("Number of users is wrong", expected, actual);
    }

    @Test
    public void testBadFile() {
        final Users<User> users = readUserFile("foo.bar");
        final int expected = 1;
        final int actual = users.size();
        assertEquals("Number of users is wrong", expected, actual);
    }

    /**
     * @param userFile the user file to read
     * @return the set of users from the user file
     */
    private Users<User> readUserFile(final String userFile) {
        final UsersReader<User, Users<User>> usersReader = new UsersReader<>();
        return usersReader.readUserFile(userFile, () -> new UsersImpl<>(), () -> new UserImpl());
    }
}
