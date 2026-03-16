package org.schoellerfamily.gedbrowser.api;

import org.schoellerfamily.gedbrowser.reader.users.UsersReader;
import org.schoellerfamily.gedbrowser.security.model.SecurityUser;
import org.schoellerfamily.gedbrowser.security.model.SecurityUsers;
import org.schoellerfamily.gedbrowser.security.model.UserImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;



/**
 * Configures components related to users.
 *
 * @author Richard Schoeller
 */
@Configuration
@RequiredArgsConstructor
public class UsersConfiguration {
    /** */
    @Value("${gedbrowser.userFile:/var/lib/gedbrowser/userFile.csv}")
    private final String userFile;

    /**
     * This is the bean to get the definitions of users that we need
     * throughout the application.
     *
     * @return the Users object
     */
    @Bean
    public SecurityUsers users() {
        return readUserFile();
    }

    private SecurityUsers readUserFile() {
        final UsersReader<SecurityUser, SecurityUsers> usersReader =
                new UsersReader<>();
        return (SecurityUsers) usersReader.readUserFile(userFile,
                () -> new SecurityUsers(userFile),
                () -> new UserImpl()
        );
    }
}
