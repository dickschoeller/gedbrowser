package org.schoellerfamily.gedbrowser.security.test;

import org.schoellerfamily.gedbrowser.reader.users.UsersReader;
import org.schoellerfamily.gedbrowser.security.model.SecurityUser;
import org.schoellerfamily.gedbrowser.security.model.SecurityUsers;
import org.schoellerfamily.gedbrowser.security.model.UserImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;

/**
 * @author Dick Schoeller
 */
@Configuration
@ComponentScan("org.schoellerfamily.gedbrowser.security")
@RequiredArgsConstructor
public class TestConfiguration {
    /** */
    @Value("${gedbrowser.home:#{ systemProperties['user.dir'] }/src/test/resources}")
    private String gedbrowserHome;

    /**
     * This is the bean to get the definitions of users that we need
     * throughout the application.
     *
     * @return the Users object
     */
    @Bean
    public SecurityUsers users() {
        final String userFile = gedbrowserHome + "/testUserFile.csv";
        return readUserFile(userFile);
    }

    /**
     * @param userFile the user file to read
     * @return the set of users from the user file
     */
    private SecurityUsers readUserFile(final String userFile) {
        final UsersReader<SecurityUser, SecurityUsers> usersReader =
                new UsersReader<>();
        return (SecurityUsers) usersReader.readUserFile(userFile,
                () -> new SecurityUsers(userFile),
                () -> new UserImpl()
        );
    }
}
