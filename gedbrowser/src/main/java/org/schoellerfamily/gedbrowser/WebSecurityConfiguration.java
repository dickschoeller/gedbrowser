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
 * @author Dick Schoeller
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfiguration {
    /** The user details that we know about. */
    @Autowired
    private final Users<? extends User> users;

    /** Base path in URL. */
    @Value("${server.servlet.context-path}")
    private final transient String servletPath;


    /**
     * Configure the security filter chain using the modern approach.
     */
    @Bean
    public SecurityFilterChain filterChain(final HttpSecurity http) throws Exception {
        // Use the lambda-based configuration APIs instead of the older chained style
        http
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/**").permitAll()
                .anyRequest().authenticated())
            // TODO haven't figured out how to retain referer after login error.
            .formLogin(form -> {
                final SavedRequestAwareAuthenticationSuccessHandler handler =
                        new SavedRequestAwareAuthenticationSuccessHandler();
                handler.setDefaultTargetUrl(servletPath + "/person?db=schoeller&id=I1");
                handler.setAlwaysUseDefaultTargetUrl(false);
                handler.setTargetUrlParameter("targetUrl");

                form.loginPage(servletPath + "/login")
                        .successHandler(handler)
                        .permitAll();
            })
            .logout(logout -> logout
                    .deleteCookies("JSESSIONID")
                    .invalidateHttpSession(true)
                    .logoutUrl(servletPath + "/logout")
                    .logoutSuccessUrl(servletPath + "/login")
                    .permitAll());

        return http.build();
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
            final UserRoleName[] roles = user.getRoles();
            final String[] roleStrings = createRoleStrings(roles);
            for (int i = 0; i < roles.length; i++) {
                roleStrings[i] = roles[i].toString().replace("ROLES_", "");
            }
            configurer.withUser(user.getUsername())
                    .password(user.getPassword()).roles(roleStrings);
        }
    }

    /**
     * @param roles the user's roles
     * @return the names of the user's roles
     */
    @SuppressWarnings({ "PMD.UseVarargs" })
    private String[] createRoleStrings(final UserRoleName[] roles) {
        return new String[roles.length];
    }
}