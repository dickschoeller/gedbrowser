package org.schoellerfamily.gedbrowser.api;

import org.schoellerfamily.gedbrowser.security.auth.AuthenticationFailureHandler;
import org.schoellerfamily.gedbrowser.security.auth.AuthenticationSuccessHandler;
import org.schoellerfamily.gedbrowser.security.auth.LogoutSuccess;
import org.schoellerfamily.gedbrowser.security.auth.RestAuthenticationEntryPoint;
import org.schoellerfamily.gedbrowser.security.auth.TokenAuthenticationFilter;
import org.schoellerfamily.gedbrowser.security.model.SecurityUser;
import org.schoellerfamily.gedbrowser.security.model.SecurityUsers;
import org.schoellerfamily.gedbrowser.security.model.UserImpl;
import org.schoellerfamily.gedbrowser.security.service.impl.CustomUserDetailsService;
import org.schoellerfamily.gedbrowser.users.UsersReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * @author Dick Schoeller
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    /** */
    @Value("${jwt.cookie:AUTH-TOKEN}")
    private String cookie;

    /** */
    @Value("${spring.profiles.active:production}")
    private String activeProfile;

    /** */
    @Value("${gedbrowser.home:/var/lib/gedbrowser}")
    private transient String gedbrowserHome;

    /** */
    @Autowired
    private CustomUserDetailsService jwtUserDetailsService;

    /** */
    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    /** */
    @Autowired
    private LogoutSuccess logoutSuccess;

    /** */
    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    /** */
    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    /**
     * This is the bean to get the definitions of users that we need
     * throughout the application.
     *
     * @return the Users object
     */
    @Bean
    public SecurityUsers users() {
        final String userFile = gedbrowserHome + "/userFile.csv";
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

    /**
     * @return the token authentication filter
     */
    @Bean
    public TokenAuthenticationFilter jwtAuthenticationTokenFilter() {
      return new TokenAuthenticationFilter();
    }

    /**
     * {@inheritDoc}
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
      return super.authenticationManagerBean();
    }

    /**
     * @return the encoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
        return new DummyEncoder();
    }

    /**
     * @param authenticationManagerBuilder the builder
     * @throws Exception if something goes wrong
     */
    @Autowired
    public void configureGlobal(
            final AuthenticationManagerBuilder authenticationManagerBuilder)
            throws Exception {
        authenticationManagerBuilder.userDetailsService(jwtUserDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        handleCsrf(http)
            .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and().exceptionHandling()
                .authenticationEntryPoint(restAuthenticationEntryPoint)
            .and().addFilterBefore(jwtAuthenticationTokenFilter(),
                    BasicAuthenticationFilter.class)
                .authorizeRequests()
                .anyRequest()
                .authenticated()
            .and().formLogin()
                .loginPage("/gedbrowserng/v1/login")
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler)
            .and().logout()
                .logoutRequestMatcher(
                        new AntPathRequestMatcher("/gedbrowserng/v1/logout"))
                .logoutSuccessHandler(logoutSuccess)
                .deleteCookies(cookie);
    }

    /**
     * Work from the http security object and enable or disable CSRF handling,
     * as requested in the application properties.
     *
     * @param http the http security object
     * @return the http security object
     * @throws Exception if there is a problem
     */
    private HttpSecurity handleCsrf(final HttpSecurity http)
            throws Exception {
        if ("test".equals(activeProfile)) {
            return http.csrf().disable();
        } else {
            return http.csrf().ignoringAntMatchers(
                        "/gedbrowserng/v1/login",
                        "/gedbrowserng/v1/signup")
                    .csrfTokenRepository(
                            CookieCsrfTokenRepository.withHttpOnlyFalse())
                    .and();
        }
    }

    /**
     * @author Dick Schoeller
     */
    private static final class DummyEncoder implements PasswordEncoder {
        /**
         * {@inheritDoc}
         */
        @Override
        public String encode(final CharSequence rawPassword) {
            return rawPassword.toString();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean matches(final CharSequence rawPassword,
                final String encodedPassword) {
            if (encodedPassword == null && rawPassword == null) {
                return true;
            }
            if (encodedPassword == null || rawPassword == null) {
                return false;
            }
            return encodedPassword.equals(rawPassword.toString());
        }
    }
}
