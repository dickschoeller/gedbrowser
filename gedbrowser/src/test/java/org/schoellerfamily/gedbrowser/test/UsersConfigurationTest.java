package org.schoellerfamily.gedbrowser.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.schoellerfamily.gedbrowser.UsersConfiguration;
import org.schoellerfamily.gedbrowser.datamodel.users.User;
import org.schoellerfamily.gedbrowser.datamodel.users.UserRoleName;
import org.schoellerfamily.gedbrowser.datamodel.users.Users;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author Dick Schoeller
 */
@ExtendWith(SpringExtension.class)
public final class UsersConfigurationTest {
    /** */
    @Value("${gedbrowser.home:/var/lib/gedbrowser}")
    private transient String gedbrowserHome;

    /** */
    @Test
    public void testUserFile() {
        final String userFile = gedbrowserHome + "/testUserFile.csv";
        final UsersConfiguration uc = new UsersConfiguration();
        final Users<? extends User> users = uc.readUserFile(userFile);
        final int expected = 2;
        final int actual = users.size();
        assertEquals(expected, actual, "Found file should have two");
    }

    /** */
    @Test
    public void testUserFileNotFound() {
        final String userFile = gedbrowserHome + "/XYX";
        final UsersConfiguration uc = new UsersConfiguration();
        final Users<? extends User> users = uc.readUserFile(userFile);
        final int expected = 1;
        final int actual = users.size();
        assertEquals(expected, actual, "Not found file should have only one");
    }

    /** */
    @Test
    public void testUserFileNotFoundContainsGues() {
        final String userFile = gedbrowserHome + "/XYX";
        final UsersConfiguration uc = new UsersConfiguration();
        final Users<? extends User> users = uc.readUserFile(userFile);
        final User guest = users.get("guest");
        assertTrue(guest.hasRole(UserRoleName.USER), "Should have role USER");
    }
}
