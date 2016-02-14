package org.schoellerfamily.gedbrowser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.InMemoryUserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;

/**
 * @author Dick Schoeller
 */
@Configuration
@EnableWebMvcSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    /**
     * {@inheritDoc}
     */
    @Override
    protected final void configure(final HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/**").permitAll()
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/surnames?db=schoeller&letter=A")
                .permitAll()
                .and()
            .logout()
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .permitAll();
    }

    /**
     * @param auth the authentication manager builder
     * @throws Exception if there is a problem
     */
    @Autowired
    public final void configureGlobal(final AuthenticationManagerBuilder auth)
            throws Exception {
        final InMemoryUserDetailsManagerConfigurer<
            AuthenticationManagerBuilder> configurer =
            auth.inMemoryAuthentication();
        final Set<User> users = readUserFile();
        for (final User user : users) {
            configurer.withUser(user.getUsername())
                    .password(user.getPassword()).roles(user.getRoles());
        }
    }

    /**
     * @return the set of users from the user file
     */
    private Set<User> readUserFile() {
        final String userFile = "/var/lib/gedbrowser/userFile.csv";
        final Set<User> set = new HashSet<>();
        try (BufferedReader br = new BufferedReader(new FileReader(userFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                final String[] userFields = line.split(",");
                final User user = buildUser(userFields);
                set.add(user);
            }
        } catch (IOException e) {
            final User user = new User();
            user.setUsername("guest");
            user.setPassword("guest");
            user.addRole("USER");
            set.add(user);
        }
        return set;
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

    /**
     * @author Dick Schoeller
     */
    private class User {
        /** */
        private String username;
        /** */
        private String firstname;
        /** */
        private String lastname;
        /** */
        private String email;
        /** */
        private String password;
        /** */
        private final Set<String> roles = new HashSet<>();

        /**
         * @return the username
         */
        public String getUsername() {
            return username;
        }

        /**
         * @param username the username
         */
        public void setUsername(final String username) {
            this.username = username;
        }

        /**
         * @return the user's first name
         */
        public String getFirstname() {
            return firstname;
        }

        /**
         * @param firstname the user's first name
         */
        public void setFirstname(final String firstname) {
            this.firstname = firstname;
        }

        /**
         * @return the user's last name
         */
        public String getLastname() {
            return lastname;
        }

        /**
         * @param lastname the user's last name
         */
        public void setLastname(final String lastname) {
            this.lastname = lastname;
        }

        /**
         * @return the user's email address
         */
        public String getEmail() {
            return email;
        }

        /**
         * @param email the user's email address
         */
        public void setEmail(final String email) {
            this.email = email;
        }

        /**
         * @return the user's password
         */
        public String getPassword() {
            return password;
        }

        /**
         * @param password the user's password
         */
        public void setPassword(final String password) {
            this.password = password;
        }

        /**
         * The roles supported are user and admin.
         *
         * @return the set of roles for this user
         */
        public String[] getRoles() {
            return this.roles.toArray(new String[0]);
        }

        /**
         * @param role the role to add to the role set
         */
        public void addRole(final String role) {
            this.roles.add(role);
        }

        /**
         * Clear the role set.
         */
        public void clearRoles() {
            this.roles.clear();
        }
    }
}
