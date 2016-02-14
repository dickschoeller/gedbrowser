package org.schoellerfamily.gedbrowser;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders
    .AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers
    .provisioning.InMemoryUserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration
    .WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration
    .EnableWebMvcSecurity;

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
        try (
                final FileInputStream fis = new FileInputStream(userFile);
                final Reader reader = new InputStreamReader(fis, "UTF-8");
                final BufferedReader br = new BufferedReader(reader);
                ) {
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
}
