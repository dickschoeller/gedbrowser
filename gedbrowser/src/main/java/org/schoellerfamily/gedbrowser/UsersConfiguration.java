package org.schoellerfamily.gedbrowser;

import org.schoellerfamily.gedbrowser.datamodel.users.UserImpl;
import org.schoellerfamily.gedbrowser.datamodel.users.Users;
import org.schoellerfamily.gedbrowser.datamodel.users.UsersImpl;
import org.schoellerfamily.gedbrowser.reader.users.UsersReader;
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
    public Users<UserImpl> users() {
        final String userFile = gedbrowserHome + "/userFile.csv";
        return readUserFile(userFile);
    }

    /**
     * @param userFile the user file to read
     * @return the set of users from the user file
     */
    public Users<UserImpl> readUserFile(final String userFile) {
        final UsersReader<UserImpl, UsersImpl<UserImpl>> usersReader =
                new UsersReader<>();
        return usersReader.readUserFile(userFile,
                () -> new UsersImpl<UserImpl>(),
                () -> new UserImpl()
        );
    }
}
