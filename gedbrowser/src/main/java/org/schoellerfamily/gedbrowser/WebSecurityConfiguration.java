package org.schoellerfamily.gedbrowser;

import org.schoellerfamily.gedbrowser.renderer.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders
.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication
.configurers.provisioning.InMemoryUserDetailsManagerConfigurer;
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
    /** The user details that we know about. */
    @Autowired
    private Users users;

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
        for (final User user : users) {
            configurer.withUser(user.getUsername())
                    .password(user.getPassword()).roles(user.getRoles());
        }
    }
}
