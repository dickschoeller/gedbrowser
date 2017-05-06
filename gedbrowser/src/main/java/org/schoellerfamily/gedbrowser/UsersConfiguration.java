package org.schoellerfamily.gedbrowser;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import org.schoellerfamily.gedbrowser.renderer.user.User;
import org.schoellerfamily.gedbrowser.renderer.user.UserImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Dick Schoeller
 */
@Configuration
public class UsersConfiguration {
    /** */
    @Value("${gedbrowser.home}")
    private transient String gedbrowserHome;

    /**
     * This is the bean to get the definitions of users that we need
     * throughout the application.
     *
     * @return the Users object
     */
    @Bean
    public Users users() {
        final String userFile = gedbrowserHome + "/userFile.csv";
        return readUserFile(userFile);
    }

    /**
     * @param userFile the user file to read
     * @return the set of users from the user file
     */
    public Users readUserFile(final String userFile) {
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
    private User buildUser(final String[] userFields) {
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
