package org.schoellerfamily.gedbrowser.reader.users;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import org.schoellerfamily.gedbrowser.datamodel.users.User;
import org.schoellerfamily.gedbrowser.datamodel.users.Users;

/**
 * Reads users data from an external source.
 *
 * @author Richard Schoeller
 * @param <T> the type of User implementation
 * @param <U> the type of Users implementation
 */
public final class UsersReader<T extends User, U extends Users<T>> {
    /**
     * Executes read user file.
     *
     * @param userFile the user file to use
     * @param usersFactory the users factory
     * @param builder the builder
     * @return the resulting users
     */
    public Users<T> readUserFile(final String userFile,
            final UsersFactory<U> usersFactory,
            final UserFactory<T> builder) {
        final Users<T> users = usersFactory.createUsers();
        try (FileInputStream fis = new FileInputStream(userFile);
                Reader reader = new InputStreamReader(fis, "UTF-8");
                BufferedReader br = new BufferedReader(reader);) {
            String line;
            while ((line = br.readLine()) != null) {
                final String[] userFields = line.split(",");
                final T user = buildUser(userFields, builder);
                users.add(user);
            }
        } catch (IOException e) {
            final String[] strings = {"guest", "", "", "", "guest", "USER"};
            users.add(buildUser(strings, builder));
        }
        return users;
    }

    private T buildUser(final String[] strings, final UserFactory<T> builder) {
        final T user = builder.createUser();
        int i = 0;
        user.setUsername(strings[i++]);
        user.setFirstname(strings[i++]);
        user.setLastname(strings[i++]);
        user.setEmail(strings[i++]);
        user.setPassword(strings[i++]);
        for (; i < strings.length; i++) {
            user.addRole(strings[i]);
        }
        return user;
    }
}
