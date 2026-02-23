package org.schoellerfamily.gedbrowser;

import org.schoellerfamily.gedbrowser.datamodel.users.User;
import org.schoellerfamily.gedbrowser.datamodel.users.UserRoleName;
import org.schoellerfamily.gedbrowser.datamodel.users.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.InMemoryUserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import lombok.RequiredArgsConstructor;

/**
 * Configures web security for the application.
 *
 * @author Dick Schoeller
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfiguration {
    /** Collection of known users. */
    private final Users<? extends User> users;

    /** URL context path. */
    @Value("${server.servlet.context-path:/gedbrowser}")
    private final String servletPath;

    /**
     * Configure the security filter chain using the modern approach.
     *
     * @param http the security object
     * @return the filter chain
     */
    @Bean
    public SecurityFilterChain filterChain(final HttpSecurity http) {
        http.authorizeHttpRequests(
            authorize -> authorize.requestMatchers("/**").permitAll().anyRequest().authenticated())
            // TODO haven't figured out how to retain referer after login error.
            .formLogin(form -> {
                final SavedRequestAwareAuthenticationSuccessHandler handler =
                    new SavedRequestAwareAuthenticationSuccessHandler();
                handler.setDefaultTargetUrl(servletPath + "/person?db=schoeller&id=I1");
                handler.setAlwaysUseDefaultTargetUrl(false);
                handler.setTargetUrlParameter("targetUrl");

                form.loginPage(servletPath + "/login").successHandler(handler).permitAll();
            })
            .logout(logout -> logout.deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .logoutUrl(servletPath + "/logout")
                .logoutSuccessUrl(servletPath + "/login")
                .permitAll());

        return http.build();
    }

    /**
     * Configure the in-memory authentication manager.
     *
     * @param auth the authentication manager builder
     */
    @Autowired
    public final void configureGlobal(final AuthenticationManagerBuilder auth) {
        final InMemoryUserDetailsManagerConfigurer<AuthenticationManagerBuilder> configurer = auth
            .inMemoryAuthentication();
        for (final User user : users) {
            final UserRoleName[] roles = user.getRoles();
            final String[] roleStrings = createRoleStrings(roles);
            for (int i = 0; i < roles.length; i++) {
                roleStrings[i] = roles[i].toString().replace("ROLES_", "");
            }
            configurer.withUser(user.getUsername()).password(user.getPassword()).roles(roleStrings);
        }
    }

    /**
     * Create an array of role names from an array of roles.
     *
     * @param roles the user's roles
     * @return the names of the user's roles
     */
    @SuppressWarnings({ "PMD.UseVarargs" })
    private String[] createRoleStrings(final UserRoleName[] roles) {
        return new String[roles.length];
    }
}
