package org.schoellerfamily.gedbrowser.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.schoellerfamily.gedbrowser.Users;
import org.schoellerfamily.gedbrowser.UsersConfiguration;
import org.schoellerfamily.gedbrowser.renderer.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Dick Schoeller
 */
@RunWith(SpringJUnit4ClassRunner.class)
public final class UsersConfigurationTest {
    /** */
    @Value("${gedbrowser.home:/var/lib/gedbrowser}")
    private transient String gedbrowserHome;

    /** */
    @Test
    public void testUserFile() {
        final String userFile = gedbrowserHome + "/testUserFile.csv";
        final UsersConfiguration uc = new UsersConfiguration();
        final Users users = uc.readUserFile(userFile);
        final int expected = 2;
        final int actual = users.size();
        assertEquals("Found file should have two", expected, actual);
    }

    /** */
    @Test
    public void testUserFileNotFound() {
        final String userFile = gedbrowserHome + "/XXX";
        final UsersConfiguration uc = new UsersConfiguration();
        final Users users = uc.readUserFile(userFile);
        final int expected = 1;
        final int actual = users.size();
        assertEquals("Not found file should have only one",
                expected, actual);
    }

    /** */
    @Test
    public void testUserFileNotFoundContainsGues() {
        final String userFile = gedbrowserHome + "/XXX";
        final UsersConfiguration uc = new UsersConfiguration();
        final Users users = uc.readUserFile(userFile);
        final User guest = users.get("guest");
        assertTrue("Should have role USER", guest.hasRole("USER"));
    }
}
