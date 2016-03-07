package org.schoellerfamily.gedbrowser;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import org.schoellerfamily.gedbrowser.renderer.User;
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
    // We turn off checkstyle because bean methods must not be final
    // CHECKSTYLE:OFF
    @Bean
    public Users users() {
        // CHECKSTYLE:ON
        return readUserFile();
    }

    /**
     * @return the set of users from the user file
     */
    private Users readUserFile() {
        final String userFile = gedbrowserHome + "/userFile.csv";
        final Users users = new Users();
        try (
                final FileInputStream fis = new FileInputStream(userFile);
                final Reader reader = new InputStreamReader(fis, "UTF-8");
                final BufferedReader br = new BufferedReader(reader);
                ) {
            String line;
            while ((line = br.readLine()) != null) {
                final String[] userFields = line.split(",");
                final User user = buildUser(userFields);
                users.add(user);
            }
        } catch (IOException e) {
            final User user = new User();
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
    private User buildUser(final String[] userFields) {
        final User user = new User();
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
