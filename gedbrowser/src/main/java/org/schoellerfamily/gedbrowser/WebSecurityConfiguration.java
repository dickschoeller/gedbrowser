package org.schoellerfamily.gedbrowser;

import org.schoellerfamily.gedbrowser.renderer.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders
    .AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication
    .configurers.provisioning.InMemoryUserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.web.builders
    .HttpSecurity;
import org.springframework.security.config.annotation.web.configuration
    .EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration
    .WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication
    .SavedRequestAwareAuthenticationSuccessHandler;

/**
 * @author Dick Schoeller
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    /** The user details that we know about. */
    @Autowired
    private Users users;

    /** Base path in URL. */
    @Value("${server.servlet-path}")
    private transient String servletPath;

    /**
     * {@inheritDoc}
     */
    @Override
    protected final void configure(final HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/**").permitAll().anyRequest()
                .authenticated();

        // TODO haven't figured out how to retain referer after login error.
        final SavedRequestAwareAuthenticationSuccessHandler handler =
                new SavedRequestAwareAuthenticationSuccessHandler();
        handler.setDefaultTargetUrl(servletPath + "/person?db=schoeller&id=I1");
        handler.setAlwaysUseDefaultTargetUrl(false);
        handler.setTargetUrlParameter("targetUrl");

        http.formLogin().loginPage(servletPath + "/login")
                .successHandler(handler)
                .permitAll();
        http.logout().deleteCookies("JSESSIONID").invalidateHttpSession(true)
                .logoutUrl(servletPath + "/logout")
                .logoutSuccessUrl(servletPath + "/login").permitAll();
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
