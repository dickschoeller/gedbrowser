package org.schoellerfamily.gedbrowser.security.model;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Dick Schoeller
 */
public final class Users implements Iterable<User> {
    /** Holds the known users. */
    private final Map<String, User> users = new HashMap<>();

    /**
     * @param user the user to add
     * @return that user
     */
    public User add(final User user) {
        return users.put(user.getUsername(), user);
    }

    /**
     * @param user the user to remove
     * @return that user
     */
    public User remove(final User user) {
        return users.remove(user.getUsername());
    }

    /**
     * @param username the username of the user we are getting
     * @return that user
     */
    public User get(final String username) {
        return users.get(username);
    }

    /**
     * Resets to an empty set.
     */
    public void clear() {
        users.clear();
    }

    /**
     * @return the iterator for the value collection
     */
    public Iterator<User> iterator() {
        return users.values().iterator();
    }

    /**
     * @return the iterator for the value collection
     */
    public int size() {
        return users.values().size();
    }

    /**
     * Build users data from data file.
     */
    public static final class Builder {
        /**
         * Private constructor.
         */
        private Builder() {
        }

        /**
         * @param userFile the user file to read
         * @return the set of users from the user file
         */
        public static Users build(final String userFile) {
            final Users users = new Users();
            try (FileInputStream fis = new FileInputStream(userFile);
                    Reader reader = new InputStreamReader(fis, "UTF-8");
                    BufferedReader br = new BufferedReader(reader);) {
                String line;
                while ((line = br.readLine()) != null) {
                    final String[] userFields = line.split(",");
                    final User user = buildUser(userFields);
                    users.add(user);
                }
            } catch (IOException e) {
                final UserImpl user = new UserImpl();
                user.setUsername("guest");
                user.setPassword("guest");
                user.addRole("USER");
                users.add(user);
            }
            return users;
        }

        /**
         * @param userFields string array from the reader
         * @return built user
         */
        @SuppressWarnings("PMD.UseVarargs")
        private static User buildUser(final String[] userFields) {
            final UserImpl user = new UserImpl();
            int i = 0;
            user.setUsername(userFields[i++]);
            user.setFirstname(userFields[i++]);
            user.setLastname(userFields[i++]);
            user.setEmail(userFields[i++]);
            user.setPassword(userFields[i++]);
            for (; i < userFields.length; i++) {
                user.addRole(userFields[i]);
            }
            return user;
        }
    }
}
