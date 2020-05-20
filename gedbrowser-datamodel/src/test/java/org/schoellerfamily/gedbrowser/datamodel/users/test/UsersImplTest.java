package org.schoellerfamily.gedbrowser.datamodel.users.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import org.junit.Test;
import org.schoellerfamily.gedbrowser.datamodel.users.User;
import org.schoellerfamily.gedbrowser.datamodel.users.UserImpl;
import org.schoellerfamily.gedbrowser.datamodel.users.Users;
import org.schoellerfamily.gedbrowser.datamodel.users.UsersImpl;

/**
 * @author Dick Schoeller
 */
public class UsersImplTest {
    @Test
    public void testAddGet() {
        final Users<User> users = new UsersImpl<>();
        final UserImpl expected = new UserImpl();
        expected.setUsername("username");
        users.add(expected);
        final User actual = users.get("username");
        assertSame("not the same", expected, actual);
    }

    @Test
    public void testAddIterate() {
        final Users<User> users = new UsersImpl<>();
        final String[] userNames = {"user1", "user2"};
        for (final String userName : userNames) {
            final UserImpl impl = new UserImpl();
            impl.setUsername(userName);
            users.add(impl);
        }
        int i = 0;
        for (final User actual : users) {
            assertEquals("not the same username", userNames[i++], actual.getUsername());
        }
    }

    @Test
    public void testAddSize() {
        final Users<User> users = new UsersImpl<>();
        final String[] userNames = {"user1", "user2"};
        for (final String userName : userNames) {
            final UserImpl impl = new UserImpl();
            impl.setUsername(userName);
            users.add(impl);
        }
        assertEquals("size doesn't match", 2, users.size());
    }

    @Test
    public void testAddClearSize() {
        final Users<User> users = new UsersImpl<>();
        final String[] userNames = {"user1", "user2"};
        for (final String userName : userNames) {
            final UserImpl impl = new UserImpl();
            impl.setUsername(userName);
            users.add(impl);
        }
        users.clear();
        assertEquals("size doesn't match", 0, users.size());
    }

    @Test
    public void testAddRemoveSize() {
        final Users<User> users = new UsersImpl<>();
        final UserImpl impl1 = new UserImpl();
        impl1.setUsername("user1");
        users.add(impl1);
        final UserImpl impl2 = new UserImpl();
        impl2.setUsername("user2");
        users.add(impl2);
        users.remove(impl1);
        assertEquals("size doesn't match", 1, users.size());
    }
}
