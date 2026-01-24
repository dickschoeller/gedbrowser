package org.schoellerfamily.gedbrowser.writer.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import org.schoellerfamily.gedbrowser.datamodel.users.User;
import org.schoellerfamily.gedbrowser.datamodel.users.UserImpl;
import org.schoellerfamily.gedbrowser.datamodel.users.Users;
import org.schoellerfamily.gedbrowser.datamodel.users.UsersImpl;
import org.schoellerfamily.gedbrowser.reader.users.UsersReader;
import org.schoellerfamily.gedbrowser.writer.users.UsersWriter;

/**
 * @author Dick Schoeller
 */
public class UsersWriterTest {
    /** */
    private static final String TEST_USER_FILE_CSV = System.getProperty("gedbrowser.home",
        System.getProperty("user.dir") + "/target") + "/temp.tmp";

    @Test
    void test() {
        writeUserFile(TEST_USER_FILE_CSV);
        final Users<User> users = readUserFile(TEST_USER_FILE_CSV);
        final int expected = 3;
        final int actual = users.size();
        assertEquals(expected, actual, "Number of users is wrong");
    }

    @Test
    void testNull() {
        try {
            writeUserFile(null);
            fail("Should throw null pointer exception");
        } catch (NullPointerException e) {
            assertNull(e.getMessage(), "Should be a null message");
        }
    }

    @Test
    void testBad() {
        writeUserFile("/etc");
        final Users<User> users = readUserFile("/etc");
        assertEquals(1, users.size(), "should be the one dummy because of failure");
    }

    /**
     * @param userFile the user file to read
     * @return the set of users from the user file
     */
    private Users<User> readUserFile(final String userFile) {
        final UsersReader<User, Users<User>> usersReader = new UsersReader<>();
        return usersReader.readUserFile(userFile, () -> new UsersImpl<>(), () -> new UserImpl());
    }

    private void writeUserFile(final String userFile) {
        final UsersImpl<User> users = new UsersImpl<>();
        users.add(createUser("username1"));
        users.add(createUser("username2"));
        users.add(createUser("username3"));
        final UsersWriter usersWriter = new UsersWriter(users, userFile);
        usersWriter.write();
    }

    private UserImpl createUser(final String username) {
        final UserImpl user = new UserImpl();
        user.setUsername(username);
        user.setPassword("password");
        user.setFirstname("first");
        user.setLastname("last");
        user.addRole("USER");
        return user;
    }
}
